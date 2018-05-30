package com.example.prashant.app79;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prashant.app79.Model.CountryDataSource;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int SPEAK_REQUEST = 10;


    TextView txtValue;
    Button btnVoiceIntent;

    public static CountryDataSource countryDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtValue = (TextView)findViewById(R.id.txtValue);
        btnVoiceIntent = (Button)findViewById(R.id.btnVoiceIntent);
        btnVoiceIntent.setOnClickListener(MainActivity.this);

        Hashtable<String,String> countriesAndMessages = new Hashtable<>();
        countriesAndMessages.put("Mumbai","Welcome to Mumbai.Happy Visiting");
        countriesAndMessages.put("France","Welcome to France. Happy Visiting");
        countriesAndMessages.put("Brazil","Welcome to Brazil. Happy Visiting");
        countriesAndMessages.put("United States","Welcome to United States. Happy Visiting");
        countriesAndMessages.put("Japan","Welcome to Japan. Happy Visiting");
        countriesAndMessages.put("China","Welcome to China. Happy Visiting");
        countriesAndMessages.put("Nepal","Welcome to Nepal. Happy Visiting");
        countriesAndMessages.put("Russia","Welcome to Russia. Happy Visiting");
        countriesAndMessages.put("North Korea","Welcome to North Korea. Happy Visiting");

        countryDataSource = new CountryDataSource(countriesAndMessages);

        // we are checking that user device support Speech Recognition

        PackageManager packageManager = this.getPackageManager();
        List<ResolveInfo> listOfInformation = packageManager.queryIntentActivities(new
                Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH),0);
        if(listOfInformation.size()>0){

            Toast.makeText(MainActivity.this,
                    "Your device does Support Speech Recognition",Toast.LENGTH_SHORT).show();
        listenToUserVoice();
        }
        else
        {
            Toast.makeText(MainActivity.this,
                    "Your device does not Support Speech Recognition",Toast.LENGTH_SHORT).show();

        }


    }

    public void listenToUserVoice(){


        Intent voiceIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        voiceIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Talk to Me!");
        voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        voiceIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,10);
        startActivityForResult(voiceIntent,SPEAK_REQUEST);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

   //Getting Value that the User has spoken
    if(requestCode==SPEAK_REQUEST && resultCode==RESULT_OK){

        ArrayList<String> voiceWords = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

        float[] confidLevels = data.getFloatArrayExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES);

        /*int index=0;
        for(String userWord : voiceWords){

            if(confidLevels!=null && index<confidLevels.length){
                txtValue.setText(userWord + " - " + confidLevels[index]);
            }
        }*/

        String countryMatchedWithUserWord = countryDataSource.matchWithMinimumConfidanceLevelOfUserWords
                                                       (voiceWords,confidLevels);

        Intent myMapActivity = new Intent(MainActivity.this,MapsActivity.class);
        myMapActivity.putExtra(countryDataSource.COUNTRY_KEY,countryMatchedWithUserWord);
        startActivity(myMapActivity);

    }


    }

    @Override
    public void onClick(View view) {
        listenToUserVoice();
    }
}

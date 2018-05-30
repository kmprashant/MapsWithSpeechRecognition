package com.example.prashant.app79.Model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by Prashant on 29-05-2018.
 */

public class CountryDataSource {

    public static final String COUNTRY_KEY = "country";
    public static final float MINIMUM_CONFIDENCE_LEVEL = 0.4F;
    public static final String DEFAULT_COUNTRY_NAME = "mumbai";
    public static final double DEFAULT_COUNTRY_LATITIUDE =19.0760;
    public static final double DEFAULT_COUNTRY_LONGITUDE = 72.8777;
    public static final String DEFAULT_MESSAGE = "Be Happy!";

    private Hashtable<String,String> countriesAndMessages;

    public CountryDataSource(Hashtable<String ,String >countriesAndMessages){
        this.countriesAndMessages = countriesAndMessages;
    }

    public String matchWithMinimumConfidanceLevelOfUserWords(ArrayList<String> userWords,float[] confidanceLevels){

        if(userWords==null || confidanceLevels==null){
            return  DEFAULT_COUNTRY_NAME;
        }

        int numberOfuserWords = userWords.size();


        Enumeration<String> countries;
        for(int index=0; index<numberOfuserWords && index<confidanceLevels.length;index++){

            if(confidanceLevels[index]<MINIMUM_CONFIDENCE_LEVEL){

                break;
            }
            String acceptedUserWord = userWords.get(index);

            countries = countriesAndMessages.keys();
            while (countries.hasMoreElements()){

                String selectedCountry = countries.nextElement();

                if (acceptedUserWord.equalsIgnoreCase(selectedCountry)){

                    return acceptedUserWord;
                }
            }


        }
        return DEFAULT_COUNTRY_NAME;
    }

    public String getTheInfoOfTheCountry(String country){

        return countriesAndMessages.get(country);
    }





}

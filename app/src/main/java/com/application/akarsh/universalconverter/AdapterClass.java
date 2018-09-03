package com.application.akarsh.universalconverter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Akarsh on 15-04-2017.
 */

public class AdapterClass {

    private String[] myString;
    private List<String> myList, myFinal;
    private ArrayAdapter<String> arrayAdapter;
    private Context context;

    public AdapterClass(Context context){
        this.context = context;
    }

    public ArrayAdapter getCategoriesAdapter(){
        String[] myString = context.getResources().getStringArray(R.array.myCategories);
        myList = Arrays.asList(myString);
        Collections.sort(myList);
        arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, myList);
        return arrayAdapter;
    }

    public ArrayAdapter getAdapter(String type){
        switch (type){
            case "Currency":
                myFinal = new ArrayList<>();

                for(int i=0; i<context.getResources().getStringArray(R.array.Currency).length-1; ++i){
                    String strings = context.getResources().getStringArray(R.array.Currency)[i];
                    myString = strings.split(":");
                    myList = Arrays.asList(myString[0]);
                    myFinal.addAll(myList);
                }

                break;

            case "Distance/Length":
                myFinal = new ArrayList<>();

                for(int i=0; i<context.getResources().getStringArray(R.array.distance_length).length-1; ++i){
                    String strings = context.getResources().getStringArray(R.array.distance_length)[i];
                    myString = strings.split(":");
                    myList = Arrays.asList(myString[0]);
                    myFinal.addAll(myList);
                }
                break;
        }

        Collections.sort(myFinal);
        arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, myFinal);

        return arrayAdapter;
    }
}

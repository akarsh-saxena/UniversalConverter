package com.application.akarsh.universalconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    TextView outputNumber;
    private Spinner spinnerCategory, spinnerFrom, spinnerTo;
    private EditText inputNumber;
    AdapterClass adapterClass;
    Calculations calculations;

    String convertFrom, convertTo;
    Double myInputNumber;

    public MainActivity(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputNumber = (TextView) findViewById(R.id.outputNumber);
        inputNumber = (EditText) findViewById(R.id.inputNumber);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        spinnerFrom = (Spinner) findViewById(R.id.spinnerFrom);
        spinnerTo = (Spinner) findViewById(R.id.spinnerTo);

        adapterClass = new AdapterClass(this);
        calculations = new Calculations(this);

        spinnerCategory.setAdapter(adapterClass.getCategoriesAdapter());

        spinnerCategory.setOnItemSelectedListener(
                new Spinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(MainActivity.this, spinnerCategory.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                        spinnerFrom.setAdapter(adapterClass.getAdapter(spinnerCategory.getItemAtPosition(position).toString()));
                        spinnerTo.setAdapter(adapterClass.getAdapter(spinnerCategory.getItemAtPosition(position).toString()));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        spinnerFrom.setOnItemSelectedListener(
                new Spinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        convertFrom = calculations.getCurrencyCode(position);
                        if (inputNumber.length() > 0) {
                            convert();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        spinnerTo.setOnItemSelectedListener(
                new Spinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        convertTo = calculations.getCurrencyCode(position);
                        if (inputNumber.length() > 0) {
                            convert();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        inputNumber.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        convert();
                    }
                }
        );
    }

        void convert(){
        try {
            myInputNumber = Double.parseDouble(inputNumber.getText().toString());
            if(convertTo.equals(convertFrom)){
                outputNumber.setText(myInputNumber+"");
                return;
            }

            calculations.calculateCurrency(outputNumber, myInputNumber, convertFrom, convertTo);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

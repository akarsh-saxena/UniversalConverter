package com.application.akarsh.universalconverter;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 * Created by Akarsh on 16-04-2017.
 */

public class Calculations {

    Context context;
    static Double myText;

    public Calculations(Context context){
        this.context = context;
    }


    public String getCurrencyCode(int currencyPosition){
        String[] currency = context.getResources().getStringArray(R.array.Currency);
        String[] currencyList = Arrays.copyOf(currency, currency.length-1);
        Arrays.sort(currencyList);

        for(int i=0; i<currencyList.length; ++i){
            currency[i] = currencyList[i].split(":")[1];
        }

        return currency[currencyPosition];
    }

    public void calculateCurrency(TextView outputNumber, Double input, String convertFrom, String convertTo) throws ExecutionException, InterruptedException {
        myCurrencyFetcher fetchCurrency = new myCurrencyFetcher(outputNumber);
        fetchCurrency.execute(Double.toString(input), convertFrom, convertTo);
    }
}

class myCurrencyFetcher extends AsyncTask<String, Double, Double>{

    URL url;
    StringBuffer stringBuffer;


    private WeakReference<TextView> weakOutputNumber;

    public myCurrencyFetcher (TextView outputNumber) {
        weakOutputNumber = new WeakReference<>(outputNumber);
    }

    @Override
    protected Double doInBackground(String... params) {
        try{
            Double myInputNumber = Double.parseDouble(params[0]);
            String convertFrom = params[1];
            String convertTo = params[2];
            url = new URL("http://api.fixer.io/latest?base="+convertFrom+"&symbols="+convertTo);

            HttpURLConnection connection;
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                stringBuffer = new StringBuffer();

                String line = "";
                while((line = reader.readLine()) != null){
                    stringBuffer.append(line);
                }
            String finalJSON = stringBuffer.toString();

            JSONObject parentObject = new JSONObject(finalJSON);
            JSONObject finalObject = parentObject.getJSONObject("rates");

            String rate = finalObject.getString(convertTo);
            Double myRate = Double.parseDouble(rate);

            return myInputNumber*myRate;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    @Override
    protected void onPostExecute(Double result) {
        super.onPostExecute(result);

        result = round(result, 4);

        if (weakOutputNumber != null) {
            TextView outputNumber = weakOutputNumber.get();
            if (outputNumber != null) {
                outputNumber.setText(String.valueOf(result));
            }
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
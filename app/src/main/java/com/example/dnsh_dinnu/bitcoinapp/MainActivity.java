package com.example.dnsh_dinnu.bitcoinapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    String PRICE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner currency_spinner = (Spinner)findViewById(R.id.currency_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.support_simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        currency_spinner.setAdapter(adapter);

        currency_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            String currency = currency_spinner.getSelectedItem().toString();

            letsDoSomeNetworking(PRICE_URL+currency);

            Log.d("BitcoinApp", "URL :"+PRICE_URL+currency);



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Toast.makeText(getApplicationContext(), "Nothing Selected", Toast.LENGTH_LONG).show();

            }
        });


    }

    private void letsDoSomeNetworking(String url) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                Log.d("BitcoinApp", "JSON: " + response.toString());
                try {
                   TextView priceView = (TextView)findViewById(R.id.priceview);
                   Log.d("BitcoinApp", ""+response.getDouble("ask"));
                   priceView.setText(Double.toString(response.getDouble("ask")));
                }catch (JSONException e){
                e.printStackTrace();}
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("ERROR", e.toString());
                Toast.makeText(getApplicationContext(), "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }
}

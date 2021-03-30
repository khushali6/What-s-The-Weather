package com.example.whatstheweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText city;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city=findViewById(R.id.cityName);
        result=findViewById(R.id.display);
    }
    public void get(View v){
        String apikey="YOUR API KEY";
        String cityName=city.getText().toString();
        String url="https://api.openweathermap.org/data/2.5/weather?q="+cityName+"&appid="+apikey;
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object=response.getJSONObject("main");
                    String temprature=object.getString("temp");
                    String pressure=object.getString("pressure");
                    String humidity=object.getString("humidity");

                    JSONObject object1=response.getJSONObject("wind");
                    String speed=object1.getString("speed");
                    String degree=object1.getString("deg");


                    result.setText("Temprature: "+temprature+"\nPressure: "+pressure+"\nHumidity: "+humidity+"\nWind Speed: "+speed+"\nDegree: "+degree);
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(request);
    }
}

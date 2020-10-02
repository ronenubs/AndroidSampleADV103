package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayPersons extends AppCompatActivity {

    RequestQueue requestQueue;
    StringRequest stringRequest;

    ArrayList<Person> personList;
    ListView lvPersons;
    String[] strPersons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_persons);
        NukeSSLCerts.Nuke.nuke();
        lvPersons = findViewById(R.id.lvPersons);
        loadPersons();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_expandable_list_item_1,
                strPersons);
        lvPersons.setAdapter(adapter);


    }

    private void loadPersons(){
        personList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, "https://192.168.254.108/android_connect/displaypersons.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jArray = obj.getJSONArray("persons");
                            strPersons = new String[jArray.length()];
                            for(int i = 0; i < jArray.length(); i++){
                                JSONObject jObject = jArray.getJSONObject(i);
                                personList.add(
                                        new Person(
                                                jObject.getString("lastname"),
                                                jObject.getString("firstname")
                                                )
                                );

                                strPersons[i] = jObject.getString("lastname") + ", " +
                                        jObject.getString("firstname");
                                Log.v("data", personList.get(i).getLastname());
                            }
                            Log.v("SIZE: ", String.valueOf(personList.size()));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("error", error.getMessage());
                    }
                });

        requestQueue.add(stringRequest);
    }
}
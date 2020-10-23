package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

public class DisplayPersons extends AppCompatActivity {

    RequestQueue requestQueue;
    StringRequest stringRequest;

    ListView lvPersons;
    ArrayList<Person> personList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_persons);
        NukeSSLCerts.Nuke.nuke();
        personList = new ArrayList<>();
        loadPersons();

        lvPersons = findViewById(R.id.lvPersons);
        lvPersons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                Toast.makeText(getApplicationContext(),
//                        String.valueOf(personList.get(i).getPersonID()),
//                        Toast.LENGTH_LONG).show();
                deletePerson(personList.get(i).getPersonID());
            }
        });
    }

    private void deletePerson(final int personID){
        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.POST,
                "https://192.168.254.108/android_connect/deleteperson.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();

                map.put("personID", String.valueOf(personID));
                return map;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void loadPersons(){
        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, "https://192.168.254.108/android_connect/displaypersons.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jArray = obj.getJSONArray("persons");
                            //Log.v("ARRAY LENGTH: ", String.valueOf(jArray.length()));

                            for(int i = 0; i < jArray.length(); i++){
                                JSONObject jObject = jArray.getJSONObject(i);
                                personList.add(
                                        new Person(
                                                Integer.parseInt(jObject.getString("personID")),
                                                jObject.getString("lastname"),
                                                jObject.getString("firstname")
                                        ));

                                //Log.v("DATA: ", "[" + String.valueOf(i) + "]" +personList.get(i).getLastname());
                            }
                            //Log.v("SIZE: ", String.valueOf(personList.size()));
                            ListAdapter adapter = new PersonAdapter(getApplicationContext(), personList);
                            lvPersons.setAdapter(adapter);
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
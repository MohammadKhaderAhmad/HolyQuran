package com.example.holyquran;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuranActivity extends AppCompatActivity {
    private static final String TAG = "QuranActivity";

    private RequestQueue queue;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran);


        queue = Volley.newRequestQueue(this);
        listView = findViewById(R.id.listView);
        loadSurahs();
    }

    private void loadSurahs() {
        String url = "https://api.alquran.cloud/v1/surah";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("data");

                            ArrayList<String> surahNames = new ArrayList<>();
                            ArrayList<JSONObject> surahDetails = new ArrayList<>();

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject obj = data.getJSONObject(i);
                                surahNames.add(obj.getString("name"));
                                surahDetails.add(obj);
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(QuranActivity.this,
                                    android.R.layout.simple_list_item_1, surahNames);
                            listView.setAdapter(adapter);

                            listView.setOnItemClickListener((parent, view, position, id) -> {
                                JSONObject selectedSurah = surahDetails.get(position);
                                showSurahDetails(selectedSurah);
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);
    }
    private void showSurahDetails(JSONObject surah) {
        StringBuilder details = new StringBuilder();
        try {
            details.append("Name: ").append(surah.getString("name")).append("\n");
            details.append("English Name: ").append(surah.getString("englishName")).append("\n");
            details.append("Translation: ").append(surah.getString("englishNameTranslation")).append("\n");
            details.append("Number of Ayahs: ").append(surah.getInt("numberOfAyahs")).append("\n");
            details.append("Revelation Type: ").append(surah.getString("revelationType"));

            AlertDialog.Builder builder = new AlertDialog.Builder(QuranActivity.this);
            builder.setTitle("Surah Details")
                    .setMessage(details.toString())
                    .setPositiveButton("OK", null)
                    .create()
                    .show();
        } catch (JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }


}


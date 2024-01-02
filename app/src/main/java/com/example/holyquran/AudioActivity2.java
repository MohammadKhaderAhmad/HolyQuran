package com.example.holyquran;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.holyquran.R;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import android.media.MediaPlayer;
import java.io.IOException;

public class AudioActivity2 extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> surahNames;
    private ArrayList<String> audioUrls;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        listView = findViewById(R.id.listVieww);
        surahNames = new ArrayList<>();
        audioUrls = new ArrayList<>();
        mediaPlayer = new MediaPlayer();

        loadSurahData();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            playAudio(audioUrls.get(position));
        });
    }

    private void loadSurahData() {
        String urlSurah = "https://api.alquran.cloud/v1/surah";
        String urlAudioFiles = "https://api.quran.com/api/v4/chapter_recitations/7";

        // Request for Surah Names
        JsonObjectRequest jsonObjectRequestSurah = new JsonObjectRequest(Request.Method.GET, urlSurah, null,
                response -> {
                    try {
                        JSONArray surahs = response.getJSONArray("data");
                        for (int i = 0; i < surahs.length(); i++) {
                            JSONObject surah = surahs.getJSONObject(i);
                            String name = surah.getString("name");
                            surahNames.add(name);
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                                android.R.layout.simple_list_item_1, surahNames);
                        listView.setAdapter(adapter);

                        // After loading surah names, load the audio URLs
                        loadAudioUrls(urlAudioFiles);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error -> error.printStackTrace());

        Volley.newRequestQueue(this).add(jsonObjectRequestSurah);
    }

    private void loadAudioUrls(String url) {
        // Request for Audio URLs
        JsonObjectRequest jsonObjectRequestAudio = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray audioFiles = response.getJSONArray("audio_files");
                        for (int i = 0; i < audioFiles.length(); i++) {
                            JSONObject audioFile = audioFiles.getJSONObject(i);
                            String audioUrl = audioFile.getString("audio_url");
                            audioUrls.add(audioUrl);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error -> error.printStackTrace());

        Volley.newRequestQueue(this).add(jsonObjectRequestAudio);
    }

    private void playAudio(String url) {
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(MediaPlayer::start);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
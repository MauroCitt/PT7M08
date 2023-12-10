package com.example.pt7m08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pt7m08.Model.Album;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_album extends AppCompatActivity {
    private RequestQueue queue = null;
    ImageView imagenDisco;
    TextView canciones;

    private final List<Album> elements = new ArrayList<>();

    public List<Album> getElements() {
        return elements;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        canciones = findViewById(R.id.canciones);
        imagenDisco = findViewById(R.id.imagen);


        Intent intent = getIntent();
        String imagen = intent.getStringExtra("img");
        String nombreArtsta = intent.getStringExtra("name");
        String nombreAlbum = intent.getStringExtra("nombreAlbum");

        nombreArtsta = nombreArtsta.replace(" ", "%20");
        nombreAlbum = nombreAlbum.replace(" ", "%20");

        System.out.println("Nombre album" + nombreAlbum);

        String URL = "https://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=8a263c45ed98a92790c525a6ee0d5c76&artist=" + nombreArtsta + "&album=" + nombreAlbum + "&format=json";

        System.out.println(URL);
        System.out.println(imagen);

        loadImage(imagen);
        loadData(URL);

    }

    private void loadImage(String imagen) {

        ImageRequest imageRequest = new ImageRequest(
                imagen,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imagenDisco.setImageBitmap(response);
                    }
                },
                0,
                0,
                ImageView.ScaleType.CENTER_INSIDE,
                null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_album.this, "Error loading image: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add requests to the Volley queue
        queue = Volley.newRequestQueue(this);
        queue.add(imageRequest);
    }

    private void loadData(String url) {
        if (queue == null)
            queue = Volley.newRequestQueue(this);
        //Definimos un JsonRequest
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray trackArray = response.getJSONObject("album").getJSONObject("tracks").getJSONArray("track");
                            for (int i = 0; i < trackArray.length(); i++) {
                                JSONObject trackObject = trackArray.getJSONObject(i);
                                String name = trackObject.getString("name");

                                Album song = new Album(name);

                                elements.add(song);
                            }
                            StringBuilder songsStringBuilder = new StringBuilder();
                            for (int j = 0; j < elements.size(); j++) {
                                songsStringBuilder.append(elements.get(j).getNombreCancion());

                                // Add newline character for each item except the last one
                                if (j < elements.size() - 1) {
                                    songsStringBuilder.append("\n");
                                }
                            }

                            canciones.setText(songsStringBuilder.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_album.this, "Error loading image: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonObjectRequest);
    }
}
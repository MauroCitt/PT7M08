package com.example.pt7m08;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pt7m08.Model.Artista;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_musica extends AppCompatActivity {

    private final List<Artista> artistas = new ArrayList<>();
    private RequestQueue queue = null;
    public List<Artista> getElements(){ return artistas; }
    String URLMusica;

    Button cargar;
    String artista;
    EditText inputArtista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musica);

        inputArtista = findViewById(R.id.inputArtista);
        cargar = findViewById(R.id.btnBuscarArtista);
        System.out.println(inputArtista);

        RecyclerViewApp adapter = new RecyclerViewApp(artistas);
        RecyclerView listaAlbumes = findViewById(R.id.listaRecycler);
        listaAlbumes.setAdapter(adapter);
        findViewById(R.id.btnBuscarArtista).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hayConexion()){
                    String nom = inputArtista.getText().toString().toLowerCase();
                    String nombreArtista = nom.replace(" ", "%20");

                    URLMusica = "https://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&artist=" + nombreArtista + "&api_key=8a263c45ed98a92790c525a6ee0d5c76&format=json";
                    loadData(listaAlbumes, URLMusica);
                }
            }
        });

    }
    private boolean hayConexion() {
        boolean resultat = false;

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        // Comprovem la versió del dispositiu Android
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                    resultat = true;
                }
            }
        } else { //versions anteriors d'Android
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                resultat = true;
            } else {
                resultat = false;
            }
        }
        return resultat;
    }

    private void loadData(RecyclerView listaAlbumes, String url) {
        if ( queue == null )
            queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            artistas.clear();
                            JSONArray jsonArray = response.getJSONObject("topalbums").getJSONArray("album");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Artista artista = new Artista(
                                        jsonArray.getJSONObject(i).getJSONObject("artist").getString("name"),
                                        jsonArray.getJSONObject(i).getString("name"),
                                        jsonArray.getJSONObject(i).getJSONArray("image").getJSONObject(2).getString("#text")
                                );
                                artistas.add(artista);
                                System.out.println(artista.getNombreAlbum());
                            }
                            listaAlbumes.getAdapter().notifyDataSetChanged();
                            System.out.println("Number of items in artistas: " + artistas.size());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(Activity_musica.this, "Error en obtenir dades", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(request);
    }
}
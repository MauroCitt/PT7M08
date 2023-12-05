package com.example.pt7m08;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pt7m08.Model.Pelicula;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_peliculas extends AppCompatActivity implements View.OnClickListener {

    String apiKey = "d368dd8a";
    String pelicula;
    EditText titulo;
    Button btnBuscar;
    TextView sinopsis;
    TextView reparto;
    ImageView img;
    private RequestQueue queue = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peliculas);

        btnBuscar = findViewById(R.id.button);
        btnBuscar.setOnClickListener(this);
        titulo = findViewById(R.id.titulo);
        sinopsis = findViewById(R.id.sinopsis);
        reparto = findViewById(R.id.reparto);
        img = findViewById(R.id.img);
    }

        @Override
        public void onClick(View v) {
            System.out.println("aaa");
            String pelicula = titulo.getText().toString();
            titulo.setText("");
            pelicula = pelicula.replace(" ", "%20");
            String URL = "https://www.omdbapi.com/?t=" + pelicula + "&apikey=" + apiKey;
            loadData(URL);
        }


    private void buscarPelicula() {
        String tituloIntroducido = titulo.getText().toString();
        tituloIntroducido = tituloIntroducido.replace(" ", "½20");

        String URL = "https://www.omdbapi.com/?t=" + tituloIntroducido + "&apikey=" + apiKey;

        loadData(URL);

    }

    private boolean hayConexion() {
        boolean resultado = false;

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check the Android device version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                    resultado = true;
                }
            }
        } else { // For older versions of Android
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                resultado = true;
            }
        }

        return resultado;
    }

    private void loadData(String URL) {
        JsonObjectRequest request = new JsonObjectRequest(URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Pelicula pelicula = new Pelicula(
                            response.getString("Title"),
                            response.getString("Actors"),
                            response.getString("Poster"),
                            response.getString("Plot"));

                    reparto.setText(pelicula.getActores());
                    sinopsis.setText(pelicula.getSinopsis());
                    String poster =(pelicula.getPoster());

                    ImageRequest ir = new ImageRequest(poster, new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            img.setImageBitmap(response);
                        }
                    }, 0, 0, ImageView.ScaleType.CENTER_INSIDE, null, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Activity_peliculas.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(Activity_peliculas.this);
                    requestQueue.add(ir);


                } catch (JSONException e) {
                    Toast.makeText(Activity_peliculas.this, "La película no se encuentra", Toast.LENGTH_LONG).show();                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_peliculas.this, "Error en obtener datos", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });

        // Add the request to the RequestQueue
        if (queue == null) {
            queue = Volley.newRequestQueue(this);
        }
        queue.add(request);
    }
}

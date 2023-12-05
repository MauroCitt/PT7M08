package com.example.pt7m08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button musica;
    Button peliculas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        peliculas = findViewById(R.id.peliculas);
        peliculas.setOnClickListener(this);
        musica = findViewById(R.id.musica);
        musica.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        if (v.getId() == peliculas.getId()) {
            Intent intent = new Intent(this, Activity_peliculas.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, Activity_peliculas.class);
            startActivity(intent);
        }
    }
}
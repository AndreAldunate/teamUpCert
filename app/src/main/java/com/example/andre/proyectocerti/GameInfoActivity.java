package com.example.andre.proyectocerti;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class GameInfoActivity extends AppCompatActivity {

    private TextView nombreJuegoTextView;
    private ImageView imagenJuegoImageView;
    private TextView descripcionJuegoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);

        nombreJuegoTextView = findViewById(R.id.nombreJuegoTextView);
        imagenJuegoImageView = findViewById(R.id.imagenJuegoImageView);
        descripcionJuegoImageView = findViewById(R.id.descripcionJuegoTextView);

        String nombre = getIntent().getStringExtra("nombre");
        String imagen = getIntent().getStringExtra("photoURL");
        String descripcion = getIntent().getStringExtra("descripcion");

        nombreJuegoTextView.setText(nombre);
        descripcionJuegoImageView.setText(descripcion);
        Glide.with(this).load(imagen).into(imagenJuegoImageView);

    }
}

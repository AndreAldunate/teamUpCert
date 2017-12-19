package com.example.andre.proyectocerti;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.andre.proyectocerti.adapters.JuegosAdapter;
import com.example.andre.proyectocerti.chat.ChatActivity;
import com.example.andre.proyectocerti.models.Juego;
import com.example.andre.proyectocerti.models.OnGameClickListener;
import com.example.andre.proyectocerti.models.OnGameInfoClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class GruposActivity extends AppCompatActivity {

    private RecyclerView juegosRecyclerView;
    private JuegosAdapter juegoAdapter;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos);

        juegosRecyclerView = findViewById(R.id.juegosRecyclerView);
        juegosRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        juegosRecyclerView.setLayoutManager(linearLayoutManager);

        juegoAdapter = new JuegosAdapter(this);
        juegosRecyclerView.setAdapter(juegoAdapter);
        juegoAdapter.setOnGameClickListener(new OnGameClickListener() {
            @Override
            public void onGameClick(Juego juego) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("nombre", juego.getNombre());
                startActivity(intent);
            }
        });

        juegoAdapter.setOnGameInfoClickListener(new OnGameInfoClickListener() {
            @Override
            public void onGameInfoClick(Juego juego) {
                Intent intent = new Intent(getApplicationContext(), GameInfoActivity.class);
                intent.putExtra("nombre", juego.getNombre());
                intent.putExtra("photoURL", juego.getPhotoUrl());
                intent.putExtra("descripcion", juego.getDescription());
                startActivity(intent);
            }
        });


        db = FirebaseFirestore.getInstance();

        loadData();
    }

    private void loadData() {

        db.collection("juegos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            juegoAdapter.clear();

                            for (DocumentSnapshot document : task.getResult()) {

                                String nombre = document.getString("name");
                                String photoURL = document.getString("photoURL");
                                String descripcion = document.getString("description");


                                Juego j = new Juego(nombre, photoURL, descripcion);

                                juegoAdapter.addContact(j);

                            }

                        } else {

                        }
                    }
                });
    }
}

package com.example.andre.proyectocerti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private Button gruposButton;
    private Button invitarAmigosButton;
    private Button perfilButton;
    private Button ajustesButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gruposButton = findViewById(R.id.gruposButton);
        invitarAmigosButton = findViewById(R.id.invitarAmigosButton);
        perfilButton = findViewById(R.id.perfilButton);
        ajustesButton = findViewById(R.id.ajustesButton);

        gruposButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goGruposScreen();
            }
        });

        invitarAmigosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goInvitarAmigosScreen();
            }
        });

        perfilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPerfilScreen();
            }
        });

        ajustesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAjustesScreen();
            }
        });



        firebaseAuth = FirebaseAuth.getInstance();

        verifyUser();


    }

    private void goAjustesScreen() {
        Intent intent = new Intent(this, AjustesActivity.class);
        startActivity(intent);
    }

    private void goPerfilScreen() {
        Intent intent = new Intent(this, PerfilActivity.class);
        startActivity(intent);
    }

    private void goInvitarAmigosScreen() {
        Intent intent = new Intent(this, InvitarAmigosActivity.class);
        startActivity(intent);
    }

    private void goGruposScreen() {
        Intent intent = new Intent(this, GruposActivity.class);
        startActivity(intent);
    }

    private void verifyUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            goLogInScreen();
        }
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logOut(View view) {
        firebaseAuth.signOut();
        goLogInScreen();
    }
}

package com.example.andre.proyectocerti;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;
import android.widget.TextView;

public class AjustesActivity extends AppCompatActivity {
    private Spinner spinner;
    private TextView escogerTextView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        context = this;
        escogerTextView = (TextView) findViewById(R.id.escogerTextView);
        spinner = (Spinner) findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        escogerTextView.setText( " MOSTRAR: " + prefs.getString("MOSTRARID", "-----"));


    }


}


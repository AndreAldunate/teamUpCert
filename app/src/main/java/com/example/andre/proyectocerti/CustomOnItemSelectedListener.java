package com.example.andre.proyectocerti;

/**
 * Created by andre on 12/17/2017.
 */

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class CustomOnItemSelectedListener implements OnItemSelectedListener {


    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        String s = parent.getItemAtPosition(pos).toString();
        if(!s.equals("ESCOGER")) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(parent.getContext());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("MOSTRARID", s);
            editor.apply();
            Toast.makeText(parent.getContext(), "Datos actualizados", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}

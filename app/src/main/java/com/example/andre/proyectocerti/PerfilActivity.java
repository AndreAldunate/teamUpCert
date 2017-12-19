package com.example.andre.proyectocerti;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class PerfilActivity extends AppCompatActivity {

    private Context context;
    private TextView psTextView;
    private TextView xboxTextView;
    private TextView steamTextView;
    private ImageView fotoImageView;
    private Button actFotoButton;
    private Button actPsButton;
    private Button actContrasenatoButton;
    private Button actXboxButton;
    private Button actSteamButton;
    private EditText oldPEditText;
    private EditText newPEditText;
    private EditText psEditText;
    private EditText xboxPEditText;
    private EditText steamPEditText;
    private FirebaseUser user;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private Button btnUpload;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        setContentView(R.layout.activity_perfil);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        psTextView = (TextView) findViewById(R.id.psidTextView);
        xboxTextView = (TextView)findViewById(R.id.xboxidTextView);
        steamTextView = (TextView)findViewById(R.id.steamidTextView);
        fotoImageView = (ImageView) findViewById(R.id.fotoImageView);
        actFotoButton = (Button) findViewById(R.id.cambiarFotoButton);
       actFotoButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               chooseImage();
           }
       });
        actPsButton = (Button)findViewById(R.id.actPsButton);
        actPsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarPSid();
            }
        }) ;
        actXboxButton = (Button)findViewById(R.id.actXboxButton);
        actXboxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarXboxid();
            }
        });
        actSteamButton = (Button)findViewById(R.id.actSteamButton);
        actSteamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarSteamid();
            }
        });
        actContrasenatoButton =(Button) findViewById(R.id.cambiarButton);
       actContrasenatoButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               changePassword();
           }
       });
        oldPEditText = (EditText) findViewById(R.id.oldEditText);
        newPEditText = (EditText)findViewById(R.id.newEditText);
        psEditText = (EditText)findViewById(R.id.psEditText);
        xboxPEditText = (EditText)findViewById(R.id.xboxEditText);
        steamPEditText =(EditText) findViewById(R.id.steamEditText);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        super.onCreate(savedInstanceState);
        showDataFromPreferences();

    }

    private void uploadImage() {
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            user = FirebaseAuth.getInstance().getCurrentUser();
            final String email = user.getEmail();

            StorageReference ref = storageReference.child("images/"+ email);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                fotoImageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void actualizarSteamid() {
        String id = steamPEditText.getText().toString();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("STEAMID",id);
        editor.apply();
        Toast.makeText(context,"Datos actualizados", Toast.LENGTH_SHORT).show();
    }

    private void actualizarXboxid() {
        String id = xboxPEditText.getText().toString();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("XBOXID",id);
        editor.apply();
        Toast.makeText(context,"Datos actualizados", Toast.LENGTH_SHORT).show();
    }

    private void actualizarPSid() {
        String id = psEditText.getText().toString();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("PSID",id);
        editor.apply();
        Toast.makeText(context,"Datos actualizados", Toast.LENGTH_SHORT).show();
    }



    private void showDataFromPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

            psTextView.setText(prefs.getString("PSID", "-----"));
            xboxTextView.setText(prefs.getString("XBOXID", "-----"));
            steamTextView.setText(prefs.getString("STEAMID", "-----"));

    }

    private void changePassword(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();
        final String newPass = newPEditText.getText().toString();
        String oldpass = oldPEditText.getText().toString();
        AuthCredential credential = EmailAuthProvider.getCredential(email,oldpass);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(context,"Datos actualizados", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(context,"Datos actualizados", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }else {
                    Toast.makeText(context,"Se actualizaron los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}





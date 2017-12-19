package com.example.andre.proyectocerti;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.andre.proyectocerti.adapters.InvitarAmigosAdapter;
import com.example.andre.proyectocerti.models.Contacto;
import com.example.andre.proyectocerti.models.OnContactClickListener;

public class InvitarAmigosActivity extends AppCompatActivity {

    private static final String LOG = InvitarAmigosActivity.class.getSimpleName();
    private Context context;

    private RecyclerView invitarAmigosRecyclerView;
    private InvitarAmigosAdapter invitarAmgigosAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitar_amigos);
        context = this;

        invitarAmigosRecyclerView = findViewById(R.id.invitarRecyclerView);
        invitarAmigosRecyclerView.setHasFixedSize(true);
        invitarAmigosRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        invitarAmgigosAdapter = new InvitarAmigosAdapter(this);
        invitarAmigosRecyclerView.setAdapter(invitarAmgigosAdapter);
        invitarAmgigosAdapter.setOnContactClickListener(new OnContactClickListener() {
            @Override
            public void onContactClick(Contacto contacto) {
                Toast.makeText(context,contacto.getNombre()+" "+contacto.getNumero(),Toast.LENGTH_SHORT).show();
                String mensaje = "Unete a esta nueva app de chat para gamers!";

                /*Uri uri = Uri.parse("smsto:"+contacto.getNumero());
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", mensaje);
                startActivity(it);*/

                //sendSMS(contacto.getNumero(),mensaje);
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Log.e(LOG,"Whatsapp no instalado.");
                }
            }
        });

        getContacts();
    }

    /*public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }*/

    public void getContacts() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        invitarAmgigosAdapter.clear();

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        //Log.i(LOG, "Name: " + name);
                        //Log.i(LOG, "Phone Number: " + phoneNo);
                        invitarAmgigosAdapter.addContact(new Contacto(name, phoneNo));
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
    }
}

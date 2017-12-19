package com.example.andre.proyectocerti.adapters;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.andre.proyectocerti.R;
import com.example.andre.proyectocerti.models.Juego;
import com.example.andre.proyectocerti.models.OnContactClickListener;
import com.example.andre.proyectocerti.models.OnGameClickListener;
import com.example.andre.proyectocerti.models.OnGameInfoClickListener;

import java.util.ArrayList;

/**
 * Created by Adrian on 12/18/2017.
 */


public class JuegosAdapter extends RecyclerView.Adapter<JuegosAdapter.ViewHolder> {
    private ArrayList<Juego> datos;
    private Context context;

    private OnGameClickListener onGameClickListener;
    private OnGameInfoClickListener onGameInfoClickListener;

    public JuegosAdapter(Context context) {
        datos = new ArrayList<Juego>();
        this.context = context;
    }

    @Override
    public JuegosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_juego, parent, false);
        return new JuegosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Juego j = datos.get(position);
        Glide.with(context).load(j.getPhotoUrl()).into(holder.juegoImageView);

        holder.juegoButton.setText(j.getNombre());
        holder.juegoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGameClickListener != null) {
                    onGameClickListener.onGameClick(j);
                }
            }
        });

        holder.infoFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGameInfoClickListener != null) {
                    onGameInfoClickListener.onGameInfoClick(j);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public void colocarDatos(ArrayList<Juego> datos) {
        this.datos = datos;
        notifyDataSetChanged();
    }

    public void addContact(Juego j) {
        datos.add(j);
        notifyDataSetChanged();
    }

    public void clear() {
        datos.clear();
        notifyDataSetChanged();
    }

    public void setOnGameClickListener(OnGameClickListener onGameClickListener) {
        this.onGameClickListener = onGameClickListener;
    }

    public void setOnGameInfoClickListener(OnGameInfoClickListener onGameInfoClickListener) {
        this.onGameInfoClickListener = onGameInfoClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView juegoImageView;
        private Button juegoButton;
        private FloatingActionButton infoFloatingActionButton;

        public ViewHolder(View itemView) {
            super(itemView);

            juegoImageView = itemView.findViewById(R.id.juegoImageView);
            juegoButton = itemView.findViewById(R.id.juegoButton);
            infoFloatingActionButton = itemView.findViewById(R.id.infoFloatingActionButton);
        }
    }


}


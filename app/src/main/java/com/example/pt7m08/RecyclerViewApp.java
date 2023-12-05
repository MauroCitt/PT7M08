package com.example.pt7m08;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.pt7m08.Model.Artista;

import java.util.List;

public class RecyclerViewApp extends RecyclerView.Adapter<RecyclerViewApp.ViewHolder> {
    private List<Artista> elements;

    public RecyclerViewApp(List<Artista> elements) {
        this.elements = elements;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewElement = LayoutInflater.from(parent.getContext()).inflate(R.layout.linea_recycler, parent, false);

        return new ViewHolder(viewElement);
    }


    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        holder.getTxtElement().setText(elements.get(position).getNombreAlbum());
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtElement;

        public ViewHolder(View itemView) {
            super(itemView);
            //Quan fem click a la llista mostrem l'element
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder.this.mostraElement(v);
                }
            });

            txtElement = itemView.findViewById(R.id.textView);
        }

        private void mostraElement(View v) {

            // Cridem la pantalla de mostrar personatge i li passem les dades
            Intent mostrarAlmbumes = new Intent(v.getContext(), Activity_musica.class);
            Artista artista = elements.get(getAdapterPosition());
            mostrarAlmbumes.putExtra("name", artista.getNombreArtista());
            mostrarAlmbumes.putExtra("nombreAlbum", artista.getNombreAlbum());
            v.getContext().startActivity(mostrarAlmbumes);

        }

        public TextView getTxtElement() {
            return txtElement;
        }

    }

}

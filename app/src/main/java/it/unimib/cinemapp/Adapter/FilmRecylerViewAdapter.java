package it.unimib.cinemapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.unimib.cinemapp.Modello.Film;
import it.unimib.cinemapp.R;

public class FilmRecylerViewAdapter extends RecyclerView.Adapter<FilmRecylerViewAdapter.FilmViewHolder>{
    public interface OnItemClickListener{
        void onFilmClick(Film film);
        void onDeleteButtonPressed(int position);
    }
    private final List<Film> filmList;
    private final OnItemClickListener onItemClickListener;


    public FilmRecylerViewAdapter(List<Film> filmList, OnItemClickListener onItemClickListener){
        this.filmList=filmList;
        this.onItemClickListener=onItemClickListener;
    }
    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_list_item, parent,false);
        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        holder.bind(filmList.get(position));
    }

    @Override
    public int getItemCount() {
        if(filmList != null){
            return filmList.size();
        }
        else {return 0;}
    }

    public class FilmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView textViewTitolo;
        private final TextView textViewRegista;
        private final Button elimina;

        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitolo=itemView.findViewById(R.id.titolo);
            textViewRegista=itemView.findViewById(R.id.regista);
            elimina= itemView.findViewById(R.id.bottone_eliminazione_lista);
            itemView.setOnClickListener(this);
            elimina.setOnClickListener(this);
        }
        public void bind(Film film){
            textViewTitolo.setText(film.getTitolo());
            textViewRegista.setText(film.getRegista());
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.bottone_eliminazione_lista){
                filmList.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                onItemClickListener.onDeleteButtonPressed(getAdapterPosition());
            }
            else{
            onItemClickListener.onFilmClick(filmList.get(getAdapterPosition()));
            }
        }
    }
}

package it.unimib.cinemapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import it.unimib.cinemapp.Modello.Film;
import it.unimib.cinemapp.R;

public class FilmListAdapter extends ArrayAdapter<Film> {
    private final List<Film> filmList;
    private final int layout;
    private final OnDeleteButtonClickListener onDeleteButtonClickListener;

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(Film film);
    }

    public FilmListAdapter(@NonNull Context context, int layout, @NonNull List<Film> filmList,
                           OnDeleteButtonClickListener onDeleteButtonClickListener) {
        super(context, layout, filmList);
        this.layout = layout;
        this.filmList = filmList;
        this.onDeleteButtonClickListener = onDeleteButtonClickListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(layout, parent, false);
        }
        TextView textViewTitolo = convertView.findViewById(R.id.titolo);
        TextView textViewRegista = convertView.findViewById(R.id.regista);
        Button buttonDelete = convertView.findViewById(R.id.bottone_eliminazione_lista);

        textViewTitolo.setText(filmList.get(position).getTitolo());
        textViewRegista.setText(filmList.get(position).getSinossi());

        buttonDelete.setOnClickListener(v -> {
            Film film= filmList.get(position);
            filmList.remove(film);
            // Call this method to refresh the UI and update the content of ListView
            notifyDataSetChanged();
            onDeleteButtonClickListener.onDeleteButtonClick(film);
        });

        return convertView;
    }
}

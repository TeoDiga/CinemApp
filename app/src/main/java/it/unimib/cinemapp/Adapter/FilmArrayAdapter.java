package it.unimib.cinemapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import it.unimib.cinemapp.Modello.Film;
import it.unimib.cinemapp.Modello.FilmCercati;
import it.unimib.cinemapp.R;

public class FilmArrayAdapter extends ArrayAdapter<Film> {
    private static final String TAG = FilmArrayAdapter.class.getSimpleName();
    private Film[] filmArray;
    private int layout;

    public FilmArrayAdapter(@NonNull Context context, int layout, @NonNull Film[] filmArray) {
        super(context, layout, filmArray);
        this.layout = layout;
        this.filmArray = filmArray;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(layout,
                    parent, false);
        }

        TextView textViewTitolo= convertView.findViewById(R.id.titolo);
        TextView textViewRegista=convertView.findViewById(R.id.regista);

        textViewTitolo.setText(filmArray[position].getTitolo());
        textViewRegista.setText(filmArray[position].getSinossi());
        return convertView;
    }
}

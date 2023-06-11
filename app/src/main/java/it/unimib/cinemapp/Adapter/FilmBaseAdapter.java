package it.unimib.cinemapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import it.unimib.cinemapp.Modello.Film;
import it.unimib.cinemapp.Modello.FilmCercati;
import it.unimib.cinemapp.R;

public class FilmBaseAdapter extends BaseAdapter {
    private final List<Film> filmList;

    public FilmBaseAdapter(List<Film> filmList) {
        this.filmList = filmList;
    }

    @Override
    public int getCount() {
        if(filmList!=null){
            return filmList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return filmList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.film_list_item, parent, false);
        }

        TextView textViewTitolo = convertView.findViewById(R.id.titolo);
        TextView textViewRegista = convertView.findViewById(R.id.regista);
        Button bottoneElimina = convertView.findViewById(R.id.bottone_eliminazione_lista);

        textViewTitolo.setText(filmList.get(position).getTitolo());
        textViewRegista.setText(filmList.get(position).getSinossi());

        bottoneElimina.setOnClickListener(v -> {
            filmList.remove(position);
            // Call this method to refresh the UI and update the content of ListView
            notifyDataSetChanged();
        });

        return convertView;
    }
}

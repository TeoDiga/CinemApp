package it.unimib.cinemapp.UI.main;

import static it.unimib.cinemapp.Util.Costanti.AVATAR_TEST;
import static it.unimib.cinemapp.Util.Costanti.MATRIX_TEST;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.unimib.cinemapp.Adapter.FilmRecylerViewAdapter;
import it.unimib.cinemapp.Modello.Film;
import it.unimib.cinemapp.Modello.FilmCercati;
import it.unimib.cinemapp.R;
import it.unimib.cinemapp.Util.JSONparser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilmPreferitiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilmPreferitiFragment extends Fragment {

    private static final String TAG = FilmPreferitiFragment.class.getSimpleName();

    public FilmPreferitiFragment() {
        // Required empty public constructor
    }

    public static FilmPreferitiFragment newInstance() {

        return new FilmPreferitiFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_film_preferiti, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear();
                menuInflater.inflate(R.menu.cancella_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });
        RecyclerView recyclerViewPreferiti=view.findViewById(R.id.recyclerView_pref);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false);
        List<Film> filmList= filmListGson();
        FilmRecylerViewAdapter filmRecylerViewAdapter= new FilmRecylerViewAdapter(filmList,
                new FilmRecylerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onFilmClick(Film film) {
                        Snackbar.make(view, film.getTitolo(), Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDeleteButtonPressed(int position) {
                        Snackbar.make(view, filmList.size()+" elementi rimasti", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPreferitiButtonPressed(int position, Film film) {
                        film.setStato(Film.Stato.PREFERITO);
                        Snackbar.make(view, "ti piace molto"+film.getTitolo(), Snackbar.LENGTH_SHORT).show();
                    }
                });
        recyclerViewPreferiti.setLayoutManager(layoutManager);
        recyclerViewPreferiti.setAdapter(filmRecylerViewAdapter);
    }

    private List<Film> filmListGson(){
        JSONparser jsoNparser=new JSONparser(requireActivity().getApplication());
        try {
            return jsoNparser.parseJSONFileWithGson(MATRIX_TEST).conversioneFilm();

        }catch (IOException e){e.printStackTrace();}
        return null;
    }

}
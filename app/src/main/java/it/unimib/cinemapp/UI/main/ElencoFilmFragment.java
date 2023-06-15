package it.unimib.cinemapp.UI.main;

import static it.unimib.cinemapp.Util.Costanti.AVATAR_TEST;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.unimib.cinemapp.Adapter.FilmArrayAdapter;
import it.unimib.cinemapp.Adapter.FilmRecylerViewAdapter;
import it.unimib.cinemapp.Modello.Film;
import it.unimib.cinemapp.Modello.FilmApiResponse;
import it.unimib.cinemapp.Modello.FilmCercati;
import it.unimib.cinemapp.R;
import it.unimib.cinemapp.Util.JSONparser;
import it.unimib.cinemapp.Util.ResponseCallback;
import it.unimib.cinemapp.repository.FilmRepositoryDaFile;
import it.unimib.cinemapp.repository.FilmRepositoryInterfaccia;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ElencoFilmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ElencoFilmFragment extends Fragment implements ResponseCallback {

    private static final String TAG = ElencoFilmFragment.class.getSimpleName();
    private List<Film> films;
    private FilmRepositoryInterfaccia filmRepositoryInterfaccia;
    private ProgressBar progressBar;

    public ElencoFilmFragment() {
        // Required empty public constructor
    }


    public static ElencoFilmFragment newInstance() {
        return new ElencoFilmFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filmRepositoryInterfaccia= new FilmRepositoryDaFile(requireActivity().getApplication(), this,
                FilmRepositoryInterfaccia.JsonParserType.GSON);
        films= new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elenco_film, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear();
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });
        RecyclerView recyclerViewCerca=view.findViewById(R.id.recycle_view_cerca);
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
        });
        recyclerViewCerca.setLayoutManager(layoutManager);
        recyclerViewCerca.setAdapter(filmRecylerViewAdapter);
    }

    private List<Film> filmListGson(){

        JSONparser jsoNparser=new JSONparser(requireActivity().getApplication());

        try {
            FilmApiResponse filmApiResponse=jsoNparser.parseJSONFileWithGson(AVATAR_TEST);
            List<Film> esito= filmApiResponse.conversioneFilm();
            return esito;

        }catch (IOException e){

            e.printStackTrace();}
        return null;
    }

    @Override
    public void successoRicerca(List<Film> filmList, long ultimoAggiornamento) {
        if(filmList !=null){
            this.films.clear();
            this.films.addAll(filmList);
            //sharedpreferences
        }
    }



    @Override
    public void fallimentoRicerca(String errore) {
        progressBar.setVisibility(View.GONE);
        Snackbar.make(requireActivity().findViewById(android.R.id.content), errore, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void filmCambiatoStatus(Film film) {

    }
}
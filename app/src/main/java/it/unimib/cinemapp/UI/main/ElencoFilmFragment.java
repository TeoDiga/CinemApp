package it.unimib.cinemapp.UI.main;

import static it.unimib.cinemapp.Util.Costanti.AVATAR_TEST;
import static it.unimib.cinemapp.Util.Costanti.LINGUA_SCELTA;
import static it.unimib.cinemapp.Util.Costanti.SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.cinemapp.Util.Costanti.ULTIMO_AGGIORNAMENTO;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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

import com.google.android.gms.common.util.SharedPreferencesUtils;
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
import it.unimib.cinemapp.Util.SharedPrefUtil;
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
    private FilmRecylerViewAdapter filmRecylerViewAdapter;
    private FilmRepositoryInterfaccia filmRepositoryInterfaccia;
    private ProgressBar progressBar;
    private SharedPrefUtil sharedPrefUtil;

    public ElencoFilmFragment() {
        // Required empty public constructor
    }
    public ElencoFilmFragment(List<Film> input){
        this.films=input;
    }


    public static ElencoFilmFragment newInstance() {
        return new ElencoFilmFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filmRepositoryInterfaccia= new FilmRepositoryDaFile(requireActivity().getApplication(), this,
                FilmRepositoryInterfaccia.JsonParserType.GSON);
        sharedPrefUtil= new SharedPrefUtil(requireActivity().getApplication());
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
        progressBar = view.findViewById(R.id.progressBar);

        RecyclerView recyclerViewCerca=view.findViewById(R.id.recycle_view_cerca);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false);

        films= getArguments().getParcelableArrayList("Lista");

        FilmRecylerViewAdapter filmRecylerViewAdapter= new FilmRecylerViewAdapter(films,
                new FilmRecylerViewAdapter.OnItemClickListener() {
            @Override
            public void onFilmClick(Film film) {
                Snackbar.make(view, film.getTitolo(), Snackbar.LENGTH_SHORT).show();
                Navigation.findNavController(requireView()).navigate(R.id.action_mostra_film);
            }

            @Override
            public void onDeleteButtonPressed(int position) {
                Snackbar.make(view, films.size()+" elementi rimasti", Snackbar.LENGTH_SHORT).show();
            }

            @Override
             public void onPreferitiButtonPressed(int position, Film film) {

                film.setStato(Film.Stato.PREFERITO);
                Snackbar.make(view, "ti piace molto"+film.getTitolo(), Snackbar.LENGTH_SHORT).show();
            }
        });
        recyclerViewCerca.setLayoutManager(layoutManager);
        recyclerViewCerca.setAdapter(filmRecylerViewAdapter);
        /*
        String ultimoAggiornamento ="0";
        if (sharedPrefUtil.leggiString(SHARED_PREFERENCES_FILE_NAME, ULTIMO_AGGIORNAMENTO)!=null){
            ultimoAggiornamento=sharedPrefUtil.leggiString(SHARED_PREFERENCES_FILE_NAME, ULTIMO_AGGIORNAMENTO);
        }
        progressBar.setVisibility(View.VISIBLE);
        filmRepositoryInterfaccia.cercaFilm(sharedPrefUtil.leggiString(SHARED_PREFERENCES_FILE_NAME, LINGUA_SCELTA),  Long.parseLong(ultimoAggiornamento));
        */
    }
    /*
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
    */
    @Override
    public void successoRicerca(List<Film> filmList, long ultimoAggiornamento) {
        if(filmList !=null){
            this.films.clear();
            this.films.addAll(filmList);
            sharedPrefUtil.scriviString(SHARED_PREFERENCES_FILE_NAME, ULTIMO_AGGIORNAMENTO,
                    String.valueOf(ultimoAggiornamento));
        }
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                filmRecylerViewAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        });

    }



    @Override
    public void fallimentoRicerca(String errore) {
        progressBar.setVisibility(View.GONE);
        Snackbar.make(requireActivity().findViewById(android.R.id.content), errore, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void filmCambiatoStatus(Film film) {
        if(film.getStato()== Film.Stato.PREFERITO){
            Snackbar.make(requireActivity().findViewById(android.R.id.content),film.getTitolo()+" aggiunto ai preferiti",
                    Snackbar.LENGTH_SHORT).show();
        }
        else {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),film.getTitolo()+" rimosso dai preferiti",
                    Snackbar.LENGTH_SHORT).show();
        }
    }
}
package it.unimib.cinemapp.UI.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unimib.cinemapp.Modello.Example;
import it.unimib.cinemapp.Modello.Film;
import it.unimib.cinemapp.Modello.FilmApiResponse;
import it.unimib.cinemapp.Modello.FilmCercati;
import it.unimib.cinemapp.Modello.Result;
import it.unimib.cinemapp.Modello.TestFilm;
import it.unimib.cinemapp.R;
import it.unimib.cinemapp.Util.FilmRestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CercaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CercaFragment extends Fragment {

    private static final String TAG= CercaFragment.class.getSimpleName();

    public CercaFragment() {
        // Required empty public constructor
    }

    public static CercaFragment newInstance() {
        return new CercaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cerca, container, false);
    }

    private Activity mainActivity;

    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (Activity) context;
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
        final TextInputEditText inputEditText= view.findViewById(R.id.queryString);

        final Button bottoneRicera= view.findViewById(R.id.bottone_ricerca);
        bottoneRicera.setOnClickListener(v -> {
            Retrofit retrofit=new Retrofit.Builder().baseUrl("https://imdb-api.com/").addConverterFactory(GsonConverterFactory.create()).build();
            FilmRestService service= retrofit.create(FilmRestService.class);
            String query= inputEditText.getText().toString();
            Example lista= null;

                Call<Example> call=service.listFilm(query);
                call.enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
                        Snackbar.make(view, response.body().getResults().get(0).getTitle(), Snackbar.LENGTH_SHORT).show();
                        List<Result> results= response.body().getResults();
                        ArrayList<Film> lista= conversioneFilm(results);

                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("Lista", lista);

                        Navigation.findNavController(
                                requireView()).navigate(R.id.action_cercaFragment_to_elencoFilmFragment, bundle);



                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                        Snackbar.make(view, "Errore", Snackbar.LENGTH_SHORT).show();
                    }
                });
                //lista = service.chiamataTest().execute().body();
                //Snackbar.make(view, lista.getExpression().toString(), Snackbar.LENGTH_SHORT).show();


            //Navigation.findNavController(requireView()).navigate(R.id.action_cercaFragment_to_elencoFilmFragment);
        });
    }
    public ArrayList<Film> conversioneFilm(List<Result> results){
        ArrayList<Film> lista= new ArrayList<>();
        Iterator<Result> iterator= results.iterator();
        while (iterator.hasNext()){
            Result result=iterator.next();
            Film film= new Film(result);
            lista.add(film);
        }
        return lista;
    }
}

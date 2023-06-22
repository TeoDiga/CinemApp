package it.unimib.cinemapp.Util;

import com.google.android.gms.location.Geofence;

import java.util.List;

import it.unimib.cinemapp.Modello.Example;
import it.unimib.cinemapp.Modello.Film;
import it.unimib.cinemapp.Modello.FilmApiResponse;
import it.unimib.cinemapp.Modello.TestFilm;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FilmRestService {
    @GET("en/API/SearchMovie/k_i9h6l2b5/{titolo}")
    Call<Example> listFilm(@Path("titolo") String titolo);

    @GET("en/API/SearchMovie/k_i9h6l2b5/ciao")
    Call<Example> chiamataTest();
}


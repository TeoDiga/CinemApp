package it.unimib.cinemapp.repository;

import static it.unimib.cinemapp.Util.Costanti.AVATAR_TEST;
import static it.unimib.cinemapp.Util.Costanti.MATRIX_TEST;

import android.app.Application;
import android.widget.ProgressBar;

import com.google.android.gms.common.api.Response;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import it.unimib.cinemapp.Database.FilmDataAccessObject;
import it.unimib.cinemapp.Database.FilmRoomDatabase;
import it.unimib.cinemapp.Modello.Film;
import it.unimib.cinemapp.Modello.FilmApiResponse;
import it.unimib.cinemapp.Util.JSONparser;
import it.unimib.cinemapp.Util.ResponseCallback;

public class FilmRepositoryDaFile implements FilmRepositoryInterfaccia{
    private final Application application;
    private final ResponseCallback responseCallback;
    //private final FilmDataAccessObject filmDAO;
    private final JsonParserType jsonParserType;


    public FilmRepositoryDaFile(Application application, ResponseCallback responseCallback, JsonParserType jsonParserType){
        this.application=application;
        this.responseCallback=responseCallback;
        this.jsonParserType=jsonParserType;
        //FilmRoomDatabase filmRoomDatabase=
    }
    @Override
    public void cercaFilm(String queryUtente) {
        FilmApiResponse filmApiResponse=null;
        JSONparser jsoNparser=new JSONparser(application);
        switch (jsonParserType){
            case JSON_READER:
                try {
                    filmApiResponse=jsoNparser.parseJSONFileWithJsonReader(AVATAR_TEST);
                }catch (IOException e){e.printStackTrace();}
                break;
            case JSON_OBJECT_ARRAY:
                try {
                    filmApiResponse=jsoNparser.parseJSONFileWithJSONObjectArray(AVATAR_TEST);
                }catch (IOException | JSONException e){e.printStackTrace();}
                break;
            case GSON:
                try {
                    filmApiResponse=jsoNparser.parseJSONFileWithGson(AVATAR_TEST);
                }catch (IOException e){e.printStackTrace();}
                break;
            case JSON_ERROR:
                responseCallback.fallimentoRicerca("andato male");
        }
        List<Film> films= filmApiResponse.conversioneFilm();
        if(films.isEmpty()){
            responseCallback.fallimentoRicerca("andata male");
        }
        else{
            //place holder
        }
    }

    @Override
    public void scaricaFilm(String id, long ultimoAgg) {

    }

    @Override
    public void aggiornaFilm(Film film) {

    }

    @Override
    public void getFilmPreferiti() {

    }

    @Override
    public void cancellaFilm() {

    }


}

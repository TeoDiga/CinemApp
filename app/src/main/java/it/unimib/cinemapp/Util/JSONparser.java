package it.unimib.cinemapp.Util;

import android.app.Application;
import android.content.Context;
import android.util.JsonReader;
import android.util.JsonToken;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import it.unimib.cinemapp.Modello.Film;
import it.unimib.cinemapp.Modello.FilmApiResponse;
import it.unimib.cinemapp.Modello.FilmCercati;

public class JSONparser {
    private static final String TAG = JSONparser.class.getSimpleName();

    private final Context context;

    private final String searchTypeParametro= "searchType";
    private final String expressionParametro="expression";
    private final String resultsParametro = "results";
    private final String errorMessageParametro = "errorMessage";

    private final String idParametro="id";
    private final String resultTypeParametro = "resultType";
    private final String imageParametro= "image";
    private final String titleParametro= "title";
    private final String descriptionParametro= "description";

    public JSONparser(Application application){
        this.context=application.getApplicationContext();
    }
    public FilmApiResponse parseJSONFileWithJsonReader(String fileName) throws IOException {
        InputStream inputStream = context.getAssets().open(fileName);
        JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream));
        FilmApiResponse filmApiResponse = new FilmApiResponse();
        List<FilmCercati> listFilmCercati = null;

        jsonReader.beginObject();
        while (jsonReader.hasNext()){
            String rootJSONParam = jsonReader.nextName();
            if(rootJSONParam.equals(searchTypeParametro)){
                filmApiResponse.setSearchType(jsonReader.nextString());
            } else if (rootJSONParam.equals(expressionParametro)) {
                filmApiResponse.setExpression(jsonReader.nextString());
            } else if (rootJSONParam.equals(resultsParametro)) {
                jsonReader.beginArray();
                listFilmCercati= new ArrayList<>();
                while(jsonReader.hasNext()){
                    jsonReader.beginObject();
                    FilmCercati filmCercato= new FilmCercati();
                    while (jsonReader.hasNext()){
                        String filmJSONParam = jsonReader.nextName();
                        if(jsonReader.peek() != JsonToken.NULL &&
                                filmJSONParam.equals(idParametro)){
                            String id= jsonReader.nextString();
                            filmCercato.setId(id);
                        } else if (jsonReader.peek() != JsonToken.NULL &&
                                filmJSONParam.equals(resultTypeParametro)) {
                            String tipo = jsonReader.nextString();
                            filmCercato.setResultType(tipo);
                        } else if (jsonReader.peek() != JsonToken.NULL &&
                                filmJSONParam.equals(imageParametro)) {
                            String immagine= jsonReader.nextString();
                            filmCercato.setImage(immagine);
                        } else if (jsonReader.peek() != JsonToken.NULL &&
                                filmJSONParam.equals(titleParametro)) {
                            String titolo= jsonReader.nextString();
                            filmCercato.setTitle(titolo);
                        } else if (jsonReader.peek() != JsonToken.NULL &&
                                filmJSONParam.equals(descriptionParametro)) {
                            String descrizione= jsonReader.nextString();
                            filmCercato.setDescription(descrizione);
                        }else {
                            jsonReader.skipValue();
                        }
                    }

                    jsonReader.endObject();
                    listFilmCercati.add(filmCercato);
                }
                jsonReader.endArray();
            } else if (rootJSONParam.equals(errorMessageParametro)) {
                filmApiResponse.setErrorMessage(jsonReader.nextString());
            }else {
                jsonReader.skipValue();
            }
        }

        jsonReader.endObject(); // End of JSON object

        filmApiResponse.setResults(listFilmCercati);

        return filmApiResponse;
    }
    public FilmApiResponse parseJSONFileWithJSONObjectArray (String fileName)throws  IOException,
            JSONException {
        InputStream inputStream = context.getAssets().open(fileName);
        String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

        JSONObject rootJSONObject = new JSONObject(content);

        FilmApiResponse filmApiResponse = new FilmApiResponse();
        filmApiResponse.setSearchType(rootJSONObject.getString(searchTypeParametro));
        filmApiResponse.setExpression(rootJSONObject.getString(expressionParametro));
        filmApiResponse.setErrorMessage(rootJSONObject.getString(errorMessageParametro));
        JSONArray filmJSONArray = rootJSONObject.getJSONArray(resultsParametro);

        List<FilmCercati> filmCercatiList = null;
        int filmCount = filmJSONArray.length();
        if(filmCount>0){
            filmCercatiList = new ArrayList<>();
            FilmCercati film;
            for (int i=0; i<filmCount; i++){
                JSONObject filmJSONObject=filmJSONArray.getJSONObject(i);
                film= new FilmCercati();
                film.setId(filmJSONObject.getString(idParametro));
                film.setResultType(filmJSONObject.getString(resultTypeParametro));
                film.setImage(filmJSONObject.getString(imageParametro));
                film.setTitle(filmJSONObject.getString(titleParametro));
                film.setDescription(filmJSONObject.getString(descriptionParametro));
                filmCercatiList.add(film);
            }
        }
        filmApiResponse.setResults(filmCercatiList);
        return filmApiResponse;

    }
    public FilmApiResponse parseJSONFileWithGson(String fileName)throws IOException{
        InputStream inputStream= context.getAssets().open(fileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        return new Gson().fromJson(bufferedReader, FilmApiResponse.class);
    }

}

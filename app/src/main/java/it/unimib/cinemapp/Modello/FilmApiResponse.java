package it.unimib.cinemapp.Modello;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.google.gson.annotations.SerializedName;



public class FilmApiResponse implements Parcelable {
    //private static final String TAG= FilmApiResponse.class.getSimpleName();
    private String searchType;
    private String expression;
    private List<FilmCercati> results;
    private String errorMessage;

    public FilmApiResponse(){}


    public FilmApiResponse(String searchType, String expression, List<FilmCercati> results, String errorMessage) {
        this.searchType = searchType;
        this.expression = expression;
        this.results = results;
        this.errorMessage = errorMessage;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public List<FilmCercati> getResults() {
        return results;
    }

    public void setResults(List<FilmCercati> results) {
        this.results = results;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "FilmApiResponse{" +
                "searchType='" + searchType + '\'' +
                ", expression='" + expression + '\'' +
                ", results=" + results +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.searchType);
        dest.writeString(this.expression);
        dest.writeTypedList(this.results);
        dest.writeString(this.errorMessage);
    }

    public void readFromParcel(Parcel source) {
        this.searchType = source.readString();
        this.expression = source.readString();
        this.results = source.createTypedArrayList(FilmCercati.CREATOR);
        this.errorMessage = source.readString();
    }

    protected FilmApiResponse(Parcel in) {
        this.searchType = in.readString();
        this.expression = in.readString();
        this.results = in.createTypedArrayList(FilmCercati.CREATOR);
        this.errorMessage = in.readString();
    }

    public static final Creator<FilmApiResponse> CREATOR = new Creator<FilmApiResponse>() {
        @Override
        public FilmApiResponse createFromParcel(Parcel source) {
            return new FilmApiResponse(source);
        }

        @Override
        public FilmApiResponse[] newArray(int size) {
            return new FilmApiResponse[size];
        }
    };
    public List<Film> conversioneFilm(){
        List<Film> lista= new ArrayList<>();
        Iterator<FilmCercati> iterator= results.iterator();
        while (iterator.hasNext()){
            FilmCercati filmCercato=iterator.next();
            Film film= new Film(filmCercato);
            lista.add(film);
        }
        return lista;
    }
}

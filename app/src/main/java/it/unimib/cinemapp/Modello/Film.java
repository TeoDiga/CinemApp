package it.unimib.cinemapp.Modello;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Film implements Parcelable {
    private String Titolo;
    private String Regista;
    private String Paese_prod;
    private String Anno_prod;
    private String Sceneggiatore;
    private String Cast;
    private String Durata;
    private String ID;

    public Film(String id, String titolo, String regista, String paese, String anno, String sceneggiatore, String cast, String durata){
        this.ID =id;
        this.Titolo = titolo;
        this.Regista = regista;
        this.Paese_prod = paese;
        this.Anno_prod = anno;
        this.Sceneggiatore= sceneggiatore;
        this.Cast =cast;
        this.Durata= durata;
    }

    public String getTitolo() {
        return Titolo;
    }

    public void setTitolo(String titolo) {
        Titolo = titolo;
    }

    public String getRegista() {
        return Regista;
    }

    public void setRegista(String regista) {
        Regista = regista;
    }

    public String getPaese_prod() {
        return Paese_prod;
    }

    public void setPaese_prod(String paese_prod) {
        Paese_prod = paese_prod;
    }

    public String getAnno_prod() {
        return Anno_prod;
    }

    public void setAnno_prod(String anno_prod) {
        Anno_prod = anno_prod;
    }

    public String getSceneggiatore() {
        return Sceneggiatore;
    }

    public void setSceneggiatore(String sceneggiatore) {
        Sceneggiatore = sceneggiatore;
    }

    public String getCast() {
        return Cast;
    }

    public void setCast(String cast) {
        Cast = cast;
    }

    public String getDurata() {
        return Durata;
    }

    public void setDurata(String durata) {
        Durata = durata;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Film{" +
                "Titolo='" + Titolo + '\'' +
                ", Regista='" + Regista + '\'' +
                ", Paese_prod='" + Paese_prod + '\'' +
                ", Anno_prod='" + Anno_prod + '\'' +
                ", Sceneggiatore='" + Sceneggiatore + '\'' +
                ", Cast='" + Cast + '\'' +
                ", Durata='" + Durata + '\'' +
                ", ID='" + ID + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Titolo);
        dest.writeString(this.Regista);
        dest.writeString(this.Paese_prod);
        dest.writeString(this.Anno_prod);
        dest.writeString(this.Sceneggiatore);
        dest.writeString(this.Cast);
        dest.writeString(this.Durata);
        dest.writeString(this.ID);
    }

    public void readFromParcel(Parcel source) {
        this.Titolo = source.readString();
        this.Regista = source.readString();
        this.Paese_prod = source.readString();
        this.Anno_prod = source.readString();
        this.Sceneggiatore = source.readString();
        this.Cast = source.readString();
        this.Durata = source.readString();
        this.ID = source.readString();
    }

    protected Film(Parcel in) {
        this.Titolo = in.readString();
        this.Regista = in.readString();
        this.Paese_prod = in.readString();
        this.Anno_prod = in.readString();
        this.Sceneggiatore = in.readString();
        this.Cast = in.readString();
        this.Durata = in.readString();
        this.ID = in.readString();
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel source) {
            return new Film(source);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
}

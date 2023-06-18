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
    private String urlImmagine;
    private String Sinossi;
    private Stato stato;

    public enum Stato{
        PREFERITO,
        VISTO,
        DA_VEDERE,
        EVITARE
    };


    public Film() {
    }

    public Film(String titolo, String regista, String paese_prod, String anno_prod,
                String sceneggiatore, String cast, String durata, String ID, String urlImmagine, String sinossi) {
        Titolo = titolo;
        Regista = regista;
        Paese_prod = paese_prod;
        Anno_prod = anno_prod;
        Sceneggiatore = sceneggiatore;
        Cast = cast;
        Durata = durata;
        this.ID = ID;
        this.urlImmagine = urlImmagine;
        Sinossi = sinossi;
    }
    public Film(FilmCercati fonte){
        this.ID=fonte.getId();
        this.Titolo=fonte.getTitle();
        this.Sinossi=fonte.getDescription();
        this.urlImmagine=fonte.getImage();
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

    public String getUrlImmagine() {
        return urlImmagine;
    }

    public void setUrlImmagine(String urlImmagine) {
        this.urlImmagine = urlImmagine;
    }

    public String getSinossi() {
        return Sinossi;
    }

    public void setSinossi(String sinossi) {
        Sinossi = sinossi;
    }

    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
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
                ", urlImmagine='" + urlImmagine + '\'' +
                ", Sinossi='" + Sinossi + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Paese_prod);
        dest.writeString(this.Regista);
        dest.writeString(this.Titolo);
        dest.writeString(this.Anno_prod);
        dest.writeString(this.Sceneggiatore);
        dest.writeString(this.Cast);
        dest.writeString(this.Durata);
        dest.writeString(this.ID);
        dest.writeString(this.urlImmagine);
        dest.writeString(this.Sinossi);
    }

    public void readFromParcel(Parcel source) {
        this.Paese_prod = source.readString();
        this.Regista = source.readString();
        this.Titolo = source.readString();
        this.Anno_prod = source.readString();
        this.Sceneggiatore = source.readString();
        this.Cast = source.readString();
        this.Durata = source.readString();
        this.ID = source.readString();
        this.urlImmagine = source.readString();
        this.Sinossi = source.readString();
    }

    protected Film(Parcel in) {
        this.Paese_prod = in.readString();
        this.Regista = in.readString();
        this.Titolo = in.readString();
        this.Anno_prod = in.readString();
        this.Sceneggiatore = in.readString();
        this.Cast = in.readString();
        this.Durata = in.readString();
        this.ID = in.readString();
        this.urlImmagine = in.readString();
        this.Sinossi = in.readString();
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

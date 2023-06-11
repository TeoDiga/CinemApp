package it.unimib.cinemapp.Modello;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class FilmCercati implements Parcelable {
    private String id;
    private String resultType;
    private String image;
    private String title;
    private String description;

    public FilmCercati() {
    }

    public FilmCercati(String id, String resultType, String image, String title, String description) {
        this.id = id;
        this.resultType = resultType;
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "FilmCercati{" +
                "id='" + id + '\'' +
                ", resultType='" + resultType + '\'' +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.resultType);
        dest.writeString(this.image);
        dest.writeString(this.title);
        dest.writeString(this.description);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.resultType = source.readString();
        this.image = source.readString();
        this.title = source.readString();
        this.description = source.readString();
    }

    protected FilmCercati(Parcel in) {
        this.id = in.readString();
        this.resultType = in.readString();
        this.image = in.readString();
        this.title = in.readString();
        this.description = in.readString();
    }

    public static final Creator<FilmCercati> CREATOR = new Creator<FilmCercati>() {
        @Override
        public FilmCercati createFromParcel(Parcel source) {
            return new FilmCercati(source);
        }

        @Override
        public FilmCercati[] newArray(int size) {
            return new FilmCercati[size];
        }
    };
}

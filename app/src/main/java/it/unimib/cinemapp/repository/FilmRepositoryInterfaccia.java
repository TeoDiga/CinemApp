package it.unimib.cinemapp.repository;

import it.unimib.cinemapp.Modello.Film;

public interface FilmRepositoryInterfaccia {
    enum JsonParserType{
        JSON_READER,
        JSON_OBJECT_ARRAY,
        GSON,
        JSON_ERROR
    };
    void cercaFilm(String queryUtente);
    void scaricaFilm(String id, long ultimoAgg);

    void aggiornaFilm(Film film);

    void getFilmPreferiti();

    void cancellaFilm();
}

package it.unimib.cinemapp.Util;

import java.util.List;

import it.unimib.cinemapp.Modello.Film;
import it.unimib.cinemapp.Modello.FilmCercati;

public interface ResponseCallback {
    void successoRicerca(List<Film> films, long ultimoAggiornamento);

    void fallimentoRicerca(String errore);
    void filmCambiatoStatus(Film film);
}

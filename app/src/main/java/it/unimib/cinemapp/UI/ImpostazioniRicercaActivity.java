package it.unimib.cinemapp.UI;

import static it.unimib.cinemapp.Util.Costanti.ANNO;
import static it.unimib.cinemapp.Util.Costanti.CAST;
import static it.unimib.cinemapp.Util.Costanti.DURATA;
import static it.unimib.cinemapp.Util.Costanti.FRANCESE;
import static it.unimib.cinemapp.Util.Costanti.INFORMAZIONI_INTERESSANTI;
import static it.unimib.cinemapp.Util.Costanti.INGLESE;
import static it.unimib.cinemapp.Util.Costanti.ITALIANO;
import static it.unimib.cinemapp.Util.Costanti.LINGUA_SCELTA;
import static it.unimib.cinemapp.Util.Costanti.PAESE;
import static it.unimib.cinemapp.Util.Costanti.REGISTA;
import static it.unimib.cinemapp.Util.Costanti.SCENEGGIATORE;
import static it.unimib.cinemapp.Util.Costanti.SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.cinemapp.Util.Costanti.SPAGNOLO;
import static it.unimib.cinemapp.Util.Costanti.TEDESCO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashSet;
import java.util.Set;

import it.unimib.cinemapp.Modello.Film;
import it.unimib.cinemapp.R;

public class ImpostazioniRicercaActivity extends AppCompatActivity  {
    private static final String TAG = ImpostazioniRicercaActivity.class.getSimpleName();

    private Spinner spinnerLingua;
    private CheckBox checkRegista;
    private CheckBox checkAnno;
    private CheckBox checkPaese;
    private CheckBox checkSceneggiatore;
    private CheckBox checkCast;
    private CheckBox checkDurata;
    private int pressioniBottone;
    private static final String PRESSIONI_BOTTONE_CONTATORE="PRESSIONI_BOTTONE_PRECEDENTI_KEY";
    private static final String FILM_PASSATI="FILM_KEY";


    private Film film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Log.d(TAG, "la mail è: "+getIntent().getStringExtra("email"));

        setContentView(R.layout.activity_impostazioni_ricerca);

        //inizializzo robba
        spinnerLingua = findViewById(R.id.spinner_lingua);

        checkRegista = findViewById(R.id.checkBox_regista);
        checkAnno= findViewById(R.id.checkBox_anno);
        checkPaese=findViewById(R.id.checkBox_paese);
        checkCast=findViewById(R.id.checkBox_cast);
        checkDurata=findViewById(R.id.checkBox_durata);
        checkSceneggiatore=findViewById(R.id.checkBox_sceneggiatura);

        final Button buttonFatto = findViewById(R.id.bottone_conferma);


        if(savedInstanceState != null){
            pressioniBottone= savedInstanceState.getInt(PRESSIONI_BOTTONE_CONTATORE);

            film= savedInstanceState.getParcelable(FILM_PASSATI);
            Log.d(TAG, "film in memoria"+ film.toString());

        }
        else{
            Log.d(TAG,"vuoto");
            film= new Film("tt1375666", "Inception", "Christopher Nolan",
                    "Stati Uniti d'America, UK", "2010", "Christopher Nolan",
                    "Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page",
                    "2h 28min");
            Log.d(TAG, "la memoria era vuota, il film nuovo "+ film.toString());
        }
        setStatoPrecedente();



        buttonFatto.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("esito", "123");
            setResult(Activity.RESULT_OK , intent);
            finish();
            if (isInteressato() && isLinguaSelezionata()){
                Log.d(TAG, "hai scelto!");

                salvaImpostazioni();
                pressioniBottone++;
                Log.d(TAG, "hai settato le impostazioni "+pressioniBottone+" volte!");
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("Lingua_scelta", spinnerLingua.getSelectedItem().toString());
        outState.putParcelable("FILM", film);
    }
    private boolean isLinguaSelezionata(){
        if(spinnerLingua.getSelectedItem()!=null){
            return true;
        }else{
            Snackbar.make(findViewById(android.R.id.content), "scegli una lingua", Snackbar.LENGTH_SHORT).show();
            return false;
        }
    }
    private boolean isInteressato(){
        if(checkCast.isChecked()||checkSceneggiatore.isChecked()||checkDurata.isChecked()||checkPaese.isChecked()||
        checkAnno.isChecked()||checkRegista.isChecked()){

            return true;
        }
        else{
            Snackbar.make(findViewById(android.R.id.content), "non ti interessa nulla?", Snackbar.LENGTH_SHORT).show();
            return false;
        }
    }
    private void setStatoPrecedente(){
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFERENCES_FILE_NAME,
                Context.MODE_PRIVATE);
        String linguaggio= sharedPref.getString(LINGUA_SCELTA, null);
        Set<String> informazioniInteressanti=sharedPref.getStringSet(INFORMAZIONI_INTERESSANTI, null);

        if (linguaggio != null) {
            spinnerLingua.setSelection(
                    getSpinnerPositionBasedOnValue(getLinguaggio(linguaggio)));
        }
        if(informazioniInteressanti!= null){
            if (informazioniInteressanti.contains(REGISTA)){
                checkRegista.setChecked(true);
            }
            if (informazioniInteressanti.contains(ANNO)){
                checkAnno.setChecked(true);
            }
            if (informazioniInteressanti.contains(PAESE)){
                checkPaese.setChecked(true);
            }
            if (informazioniInteressanti.contains(DURATA)){
                checkDurata.setChecked(true);
            }
            if (informazioniInteressanti.contains(SCENEGGIATORE)){
                checkSceneggiatore.setChecked(true);
            }
            if (informazioniInteressanti.contains(CAST)){
                checkCast.setChecked(true);
            }
        }
    }
    private void salvaImpostazioni(){
        String lingua=spinnerLingua.getSelectedItem().toString();
        String linguaggioScelto= null;
        switch (lingua){
            case "italiano":
                linguaggioScelto = ITALIANO;
                break;
            case "francese":
                linguaggioScelto =FRANCESE;
                break;
            case "tedesco":
                linguaggioScelto = TEDESCO;
                break;
            case "inglese":
                linguaggioScelto =INGLESE;
                break;
            case "spagnolo":
                linguaggioScelto = SPAGNOLO;
                break;
        }
        Set<String> informazioni = new HashSet<>();
        if (checkRegista.isChecked()){
            informazioni.add(REGISTA);
        }
        if (checkAnno.isChecked()){informazioni.add(ANNO);}
        if (checkPaese.isChecked()){informazioni.add(PAESE);}
        if (checkCast.isChecked()){informazioni.add(CAST);}
        if (checkDurata.isChecked()){informazioni.add(DURATA);}
        if (checkSceneggiatore.isChecked()){informazioni.add(SCENEGGIATORE);}

        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(LINGUA_SCELTA, linguaggioScelto);
        editor.putStringSet(INFORMAZIONI_INTERESSANTI, informazioni);
        editor.apply();
    }
    private String getLinguaggio(String lingua){
        switch (lingua){
            case ITALIANO:
                return getString(R.string.italiano);
            case INGLESE:
                return getString(R.string.inglese);
            case SPAGNOLO:
                return getString(R.string.spagnolo);
            case FRANCESE:
                return getString(R.string.francese);
            case TEDESCO:
                return getString(R.string.tedesco);
            default:
                return null;
        }
    }
    private int getSpinnerPositionBasedOnValue(String value) {

        String[] countries = getResources().getStringArray(R.array.lingue);

        for (int i = 0; i < countries.length; i++) {
            if (countries[i].equals(value)) {
                return i;
            }
        }

        return 0;
    }

}
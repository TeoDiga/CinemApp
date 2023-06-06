package it.unimib.cinemapp.UI;

import static it.unimib.cinemapp.Util.Costanti.EMAIL;
import static it.unimib.cinemapp.Util.Costanti.ENCRYPTED_DATA_FILE_NAME;
import static it.unimib.cinemapp.Util.Costanti.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.cinemapp.Util.Costanti.PASSWORD;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;
import java.security.GeneralSecurityException;

import it.unimib.cinemapp.R;
import it.unimib.cinemapp.Util.DataEncryptionUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    private static final String TAG = LoginFragment.class.getSimpleName();
    private TextInputLayout TIL_email;
    private TextInputLayout TIL_password;

    private DataEncryptionUtil deu;


    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TIL_email= view.findViewById(R.id.text_input_layout_mail);
        TIL_password=view.findViewById(R.id.text_input_layout_pass);
        final Button bottoneLogin= view.findViewById(R.id.bottone_login);

        deu=new DataEncryptionUtil(requireContext());
        try{
            Log.d(TAG,"indirizzo mail memorizzato: "+deu.readSecretDataWithEncryptedSharedPreferences
                    (ENCRYPTED_DATA_FILE_NAME, EMAIL));
            Log.d(TAG,"password memorizzata: "+deu.readSecretDataWithEncryptedSharedPreferences
                    (ENCRYPTED_DATA_FILE_NAME, PASSWORD));

            Log.d(TAG, "file criptato: "+deu.readSecretDataOnFile(ENCRYPTED_DATA_FILE_NAME));
        }
        catch (GeneralSecurityException | IOException e){
            e.printStackTrace();
        }
        bottoneLogin.setOnClickListener(v -> {
            String email=TIL_email.getEditText().getText().toString();
            String password= TIL_password.getEditText().getText().toString();
            if(isEmailOK(email)||isPassOk(password)) {
                Log.d(TAG, "pass e mail sono ok");
                salvaDatiLogin(email, password);
                /*
                Intent intent = new Intent(this, ImpostazioniRicercaActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
                */

            }else {
                Snackbar.make(requireActivity().findViewById(android.R.id.content), "ricontrolla i dati gnucco",
                        Snackbar.LENGTH_SHORT).show();
            }
        });
    }
    private boolean isEmailOK(String email){
        if(!EmailValidator.getInstance().isValid(email)){
            TIL_email.setError("indirizzo mail non valido!");
            return false;
        }else {
            TIL_email.setError(null);
            return true;
        }
    }
    private boolean isPassOk(String pass){
        if(pass.isEmpty()){
            TIL_password.setError("password assente");
            return false;
        }
        else{
            TIL_password.setError(null);
            return true;
        }
    }
    private void salvaDatiLogin(String email, String password){
        try{
            deu.writeSecretDataWithEncryptedSharedPreferences(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, EMAIL, email);
            deu.writeSecretDataWithEncryptedSharedPreferences(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, PASSWORD, password);
            deu.writeSecreteDataOnFile(ENCRYPTED_DATA_FILE_NAME, email.concat(":").concat(password));
        }catch (GeneralSecurityException | IOException e){
            e.printStackTrace();
        }
    }
}
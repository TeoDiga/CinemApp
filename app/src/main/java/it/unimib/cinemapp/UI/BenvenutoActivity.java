package it.unimib.cinemapp.UI;

import static it.unimib.cinemapp.Util.Costanti.EMAIL;
import static it.unimib.cinemapp.Util.Costanti.ENCRYPTED_DATA_FILE_NAME;
import static it.unimib.cinemapp.Util.Costanti.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.cinemapp.Util.Costanti.PASSWORD;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.security.GeneralSecurityException;

import it.unimib.cinemapp.R;
import it.unimib.cinemapp.Util.DataEncryptionUtil;
import org.apache.commons.validator.routines.EmailValidator;


public class BenvenutoActivity extends AppCompatActivity {
    private static final String TAG = BenvenutoActivity.class.getSimpleName();

    private TextInputLayout TIL_email;
    private TextInputLayout TIL_password;

    private DataEncryptionUtil deu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benvenuto);
        TIL_email= findViewById(R.id.text_input_layout_mail);
        TIL_password=findViewById(R.id.text_input_layout_pass);
        final Button bottoneLogin= findViewById(R.id.bottone_login);

        deu=new DataEncryptionUtil(this);

        ActivityResultLauncher<String> getContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result);
                            ((ImageView) findViewById(R.id.logo_app)).setImageBitmap(bitmap);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });
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
                getContent.launch("image/*");
            }else {
                Snackbar.make(findViewById(android.R.id.content), "ricontrolla i dati gnucco",
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
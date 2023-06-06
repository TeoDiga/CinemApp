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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benvenuto);
    }

}
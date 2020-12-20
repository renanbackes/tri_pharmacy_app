package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.model.BearerTokenUser;
import com.example.myapplication.model.User;
import com.example.myapplication.service.ConectorTriPharmacy;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etLogin = findViewById( R.id.LogDiUsu );
        final EditText etSenha = findViewById( R.id.logdiSen );
        final Button botaoLogin = findViewById( R.id.btnLogin );
        botaoLogin.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                String usuario = etLogin.getText().toString();
                String senha = etSenha.getText().toString();
                String url = getResources().getString( R.string.BASE_URL );

                if ( usuario == null || senha == null ) {
                    Toast.makeText( MainActivity.this,
                            "Username / Password required",
                            Toast.LENGTH_LONG ).show();
                    return;
                }
                validateLogin( usuario, senha, url );
            }
        });
    }

    public void validateLogin( String usuario, String senha, String url ) {
        Call< BearerTokenUser > requestToken = null;
        try {
            requestToken = new ConectorTriPharmacy( url ).getAuthToken( usuario, senha );
        } catch ( IOException e ) {
            Log.e( TAG, "Erro: " + e.getMessage() );
        }

        requestToken.enqueue( new Callback< BearerTokenUser >() {

            @Override
            @SuppressLint("NewApi")
            public void onResponse( Call< BearerTokenUser > call, Response< BearerTokenUser > response ) {
                if ( response.isSuccessful() && response.code() == 200 ) {
                    Toast.makeText( MainActivity.this,
                            "Login Sucessfull",
                            Toast.LENGTH_LONG ).show();
                    BearerTokenUser userToken  = response.body();
                    ConectorTriPharmacy.tokenUser = userToken;
                    getUser( userToken.getId() );
                } else {
                    Toast.makeText( MainActivity.this,
                            "Login Failed",
                            Toast.LENGTH_LONG ).show();
                }
            }

            @Override
            public void onFailure( Call< BearerTokenUser > call, Throwable t ) {
                Log.e( TAG, "Erro: " + t.getMessage() );
            }
        });
    }

    private void getUser( Integer idUsuario ) {
        Call< User > requestUser = null;
        String url = getResources().getString( R.string.BASE_URL );
        try {
            requestUser = new ConectorTriPharmacy( url ).getUserById( idUsuario );
        } catch ( IOException e ) {
            Log.e( TAG, "Erro: " + e.getMessage() );
        }

        requestUser.enqueue( new Callback< User >() {

            @SuppressLint("NewApi")
            @Override
            public void onResponse( Call< User > call, Response< User > response ) {
                    if ( response.isSuccessful() && response.code() == 200 ) {
                    User user  = response.body();
                    user.setDsSenha( "vicenti" );
                    new Handler().postDelayed( new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, ScrollMenuActivity.class )
                                    .putExtra( "user", user );
                            startActivity( intent );
                        }
                    }, 700 );
                } else {
                    Log.e( TAG, "Erro: " + response.errorBody().toString() + " - " + response.body() );
                }
            }

            @Override
            public void onFailure( Call< User > call, Throwable t ) {
                Log.e( TAG, "Erro: " + t.getMessage() );
            }
        });
    }
}
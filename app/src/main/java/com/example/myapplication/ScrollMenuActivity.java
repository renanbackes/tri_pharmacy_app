package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.fragment.PerfilFragment;
import com.example.myapplication.fragment.JogadorFragment;
import com.example.myapplication.fragment.NoticiaFragment;
import com.example.myapplication.fragment.TituloFragment;
import com.example.myapplication.model.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ScrollMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private Integer idTime;
    private User user;
    private TextView textLogin;
    private TextView textEmail;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;

    private final Integer REQUEST_PERMISSION_LOCALIZATION = 221;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_menu);
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            this.user = (User) intent.getSerializableExtra("user");
        }

        this.callGoogleApiClient();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textLogin.setText(user.getDsNome());
                textEmail.setText(user.getDsEmail());
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setTitle("Notícias");
        NoticiaFragment noticiaFragment = new NoticiaFragment(this.idTime);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.conteudo_fragment, noticiaFragment)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.scroll_menu, menu);
        this.textLogin = findViewById(R.id.main_menu_nome_login);
        this.textEmail = findViewById(R.id.main_menu_email);
        this.textLogin.setText(this.user.getDsNome());
        this.textEmail.setText(this.user.getDsEmail());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        this.textLogin.setText(this.user.getDsNome());
        this.textEmail.setText(this.user.getDsEmail());

        Log.e("scroll", "onNavigationItemSelected");
        if (id == R.id.nav_noticias) {
            setTitle("Notícias");
            NoticiaFragment noticiaFragment = new NoticiaFragment(this.idTime);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.conteudo_fragment, noticiaFragment, "Noticias")
                    .commit();
        } else if (id == R.id.nav_jogadores) {
            setTitle("Jogadores");
            JogadorFragment jogadorFragment = new JogadorFragment(this.idTime);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.conteudo_fragment, jogadorFragment, "Jogadores")
                    .commit();
        } else if (id == R.id.nav_titulos) {
            setTitle("Títulos");
            TituloFragment tituloFragment = new TituloFragment(this.idTime);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.conteudo_fragment, tituloFragment, "Titulos")
                    .commit();
        } else if (id == R.id.nav_perfil) {
            setTitle("Perfil");
            PerfilFragment perfilFragment = new PerfilFragment(this.user);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.conteudo_fragment, perfilFragment, "Perfil")
                    .commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private synchronized void callGoogleApiClient() {
        ActivityCompat.requestPermissions( ( Activity ) this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION },
                REQUEST_PERMISSION_LOCALIZATION );
        if ( ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        this.mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            Log.i( "Latitude: ", String.valueOf(mLastLocation.getLatitude()));
                            Log.i( "Longitude: ", String.valueOf(mLastLocation.getLongitude()));
                        } else {

                            //Não há localização conhecida ou houve uma excepção
                            //A excepção pode ser obtida com task.getException()
                        }
                    }
                });
    }
}
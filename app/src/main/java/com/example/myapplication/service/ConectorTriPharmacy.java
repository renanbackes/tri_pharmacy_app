package com.example.myapplication.service;

import com.example.myapplication.model.BearerTokenUser;
import com.example.myapplication.model.Gol;
import com.example.myapplication.model.Jogador;
import com.example.myapplication.model.LoginParamsTimeConector;
import com.example.myapplication.model.Noticia;
import com.example.myapplication.model.Time;
import com.example.myapplication.model.Titulo;
import com.example.myapplication.model.User;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConectorTriPharmacy {

    private static final int UNAUTHORIZED = 401;
    private final String urlBase;
    private final TriPharmacyService service;
    public static BearerTokenUser tokenUser;

    public ConectorTriPharmacy(String url ) {
        this.urlBase = url;
        this.service = this.create();
    }

    private TriPharmacyService create() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout( 10, TimeUnit.MINUTES )
                .connectTimeout( 30, TimeUnit.SECONDS )
                .addInterceptor( interceptor -> this.validateAuth( interceptor ) )
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( this.urlBase )
                .client( client )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        return retrofit.create( TriPharmacyService.class );
    }

    public final < T > Call< T > handleTokenException( Call< T > type ) throws IOException {
        if ( tokenUser.getToken() != null ) {
           return type;
        } else {
            throw new IOException();
        }
    }

    public Call< BearerTokenUser > getAuthToken( String user, String password ) throws IOException {
        LoginParamsTimeConector auth = new LoginParamsTimeConector( user, password );
        return this.service.getAuthToken( auth );
    }

    public Call< User > getUserById( Integer idUsuario ) throws IOException {
        return this.handleTokenException( this.service.getUserById( tokenUser.toString(), idUsuario ) );
    }

    public Call< User > saveUserById( User user ) throws IOException {
        return this.handleTokenException( this.service.saveUserById( tokenUser.toString(), user.getIdUsuario(), user ) );
    }


    public Call< List < Time > > getTimes() throws IOException {
        return this.handleTokenException( this.service.getTeams( tokenUser.toString() ) );
    }

    public Call< List < Jogador > > getPlayersByTeam( Integer idTime ) throws IOException {
        return this.handleTokenException( this.service.getPlayersByTeam( tokenUser.toString(), idTime ) );
    }

    public Call< List < Noticia > > getNewsByTeam( Integer idTime ) throws IOException {
        return this.handleTokenException( this.service.getNewsByTeam( tokenUser.toString(), idTime ) );
    }

    public Call< List < Titulo > > getTitlesByTeam( Integer idTime ) throws IOException {
        return this.handleTokenException( this.service.getTitlesByTeam( tokenUser.toString(), idTime ) );
    }

    public Call< List < Gol > > getGoalsByTeam( Integer idTime ) throws IOException {
        return this.handleTokenException( this.service.getGoalsByTeam( tokenUser.toString(), idTime ) );
    }

    private okhttp3.Response validateAuth( Interceptor.Chain chain ) throws IOException {
        okhttp3.Response response = chain.proceed( chain.request() );
        int code = response.code();
        if ( code == UNAUTHORIZED )
            throw new IOException( new NaoPermitidoException( "Não Autorizado, senha ou usuario incorreto" ) );
        return response;
    }
}

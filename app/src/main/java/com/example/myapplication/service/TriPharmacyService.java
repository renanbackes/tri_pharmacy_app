package com.example.myapplication.service;

import com.example.myapplication.model.BearerTokenUser;
import com.example.myapplication.model.Gol;
import com.example.myapplication.model.Jogador;
import com.example.myapplication.model.LoginParamsTimeConector;
import com.example.myapplication.model.Noticia;
import com.example.myapplication.model.Time;
import com.example.myapplication.model.Titulo;
import com.example.myapplication.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TriPharmacyService {

    @POST( "auth" )
    Call< BearerTokenUser > getAuthToken( @Body LoginParamsTimeConector params );

    @GET( "usuario/{idUsuario}" )
    Call< User > getUserById( @Header( "Authorization" ) String token, @Path( "idUsuario" ) Integer idUsuario );

    @PUT( "usuario/{idUsuario}" )
    Call< User > saveUserById( @Header( "Authorization" ) String token, @Path( "idUsuario" ) Integer idUsuario, @Body User user );

    @GET( "Times" )
    Call< List < Time > > getTeams(@Header( "Authorization" ) String token );

    @GET( "Noticias/{idTime}" )
    Call< List < Noticia > > getNewsByTeam( @Header( "Authorization" ) String token, @Path( "idTime" ) Integer idTime );

    @GET( "Jogadores/{idTime}" )
    Call< List < Jogador > > getPlayersByTeam( @Header( "Authorization" ) String token, @Path( "idTime" ) Integer idTime );

    @GET( "Titulos/{idTime}" )
    Call< List < Titulo > > getTitlesByTeam(@Header( "Authorization" ) String token, @Path( "idTime" ) Integer idTime );

    @GET( "Gols/{idTime}" )
    Call< List < Gol > > getGoalsByTeam(@Header( "Authorization" ) String token, @Path( "idTime" ) Integer idTime );


}

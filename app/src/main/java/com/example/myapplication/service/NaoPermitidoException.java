package com.example.myapplication.service;

public class NaoPermitidoException extends Exception {

    private static final long serialVersionUID = 1L;

    public NaoPermitidoException() {
    }

    public NaoPermitidoException( Throwable t ) {
        super( t );
    }

    public NaoPermitidoException( String msg ) {
        super( msg );
    }

    public NaoPermitidoException( String msg, Throwable t ) {
        super( msg, t );
    }
}
package com.example.myapplication.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.Jogador;
import com.example.myapplication.service.ConectorTriPharmacy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JogadorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JogadorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Integer idTime;

    ListView listView;
    ArrayList< String > listNomeJogadores = new ArrayList<>();

    public JogadorFragment() {
        // Required empty public constructor
    }

    public JogadorFragment( Integer idTime ) {
        this.idTime = idTime;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JogadorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JogadorFragment newInstance(String param1, String param2) {
        JogadorFragment fragment = new JogadorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_jogador, container, false );

        loadPlayers( v );
        // Inflate the layout for this fragment
        return v;
    }

    private void loadPlayers( View v ) {
        String url = getResources().getString( R.string.BASE_URL );
        Call< List< Jogador > > requestJogadores = null;
        try {
            requestJogadores = new ConectorTriPharmacy( url ).getPlayersByTeam( this.idTime );
        } catch ( IOException e ) {
            Log.e( "ScrollMenuJogador", "Erro: " + e.getMessage() );
        }

        requestJogadores.enqueue( new Callback< List < Jogador > >() {

            @SuppressLint("NewApi")
            @Override
            public void onResponse( Call< List < Jogador > > call, Response< List < Jogador > > response ) {
                if ( response.isSuccessful() && response.code() == 200 ) {
                    List < Jogador > jogadores  = response.body();
                    jogadores.forEach( jogador -> {
                        listNomeJogadores.add( jogador.getNmJogador() );
                    });

                    listView = v.findViewById( R.id.listViewJogadores );
                    JogadorAdapter adapter = new JogadorAdapter( v.getContext(), listNomeJogadores );
                    listView.setAdapter( adapter );
                } else {
                    Toast.makeText( getContext(),
                            "Can't get List of Times",
                            Toast.LENGTH_LONG ).show();
                }
            }

            @Override
            public void onFailure( Call< List < Jogador > > call, Throwable t ) {
                Log.e( "ScrollMenuJogador", "Erro: " + t.getMessage() );
            }
        });
    }


    class JogadorAdapter extends ArrayAdapter< String > {
        Context context;
        ArrayList< String > nomeJogadores;

        JogadorAdapter ( Context c, ArrayList< String > nomeJogadores ) {
            super( c, R.layout.linhajogador, R.id.nomeJogador, nomeJogadores );
            this.context = c;
            this.nomeJogadores = nomeJogadores;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = ( LayoutInflater ) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View linha = layoutInflater.inflate( R.layout.linhajogador, parent, false );
            TextView nomeJogadorText = linha.findViewById(R.id.nomeJogador);
            nomeJogadorText.setText( this.nomeJogadores.get( position ) );

            return super.getView(position, convertView, parent);
        }
    }

}
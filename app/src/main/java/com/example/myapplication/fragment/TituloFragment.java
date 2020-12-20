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
import com.example.myapplication.model.Titulo;
import com.example.myapplication.service.ConectorTriPharmacy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TituloFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TituloFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Integer idTime;

    ListView listView;
    ArrayList< String > listTitulos = new ArrayList<>();
    ArrayList< String > listQuantidade = new ArrayList<>();
    ArrayList< String > listAnos = new ArrayList<>();

    public TituloFragment() {
        // Required empty public constructor
    }

    public TituloFragment( Integer idTime ) {
        this.idTime = idTime;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TituloFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TituloFragment newInstance(String param1, String param2) {
        TituloFragment fragment = new TituloFragment();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate( R.layout.fragment_titulo, container, false );
        loadTitles( v );
        return v;

    }

    private void loadTitles( View v ) {
        String url = getResources().getString( R.string.BASE_URL );
        Call< List< Titulo > > requestTitles = null;
        try {
            requestTitles = new ConectorTriPharmacy( url ).getTitlesByTeam( this.idTime );
        } catch ( IOException e ) {
            Log.e( "ScrollMenuTitles", "Erro: " + e.getMessage() );
        }

        requestTitles.enqueue( new Callback< List < Titulo > >() {

            @SuppressLint("NewApi")
            @Override
            public void onResponse( Call< List < Titulo > > call, Response< List < Titulo > > response ) {
                if ( response.isSuccessful() && response.code() == 200 ) {
                    List < Titulo > titulos  = response.body();
                    titulos.forEach( titulo -> {
                        listTitulos.add( titulo.getDsTitulo() );
                        listQuantidade.add( titulo.getDsQuantidade() );
                        listAnos.add( titulo.getDsAno() );
                    });

                    listView = v.findViewById( R.id.listViewTitulos );
                    TituloFragment.TituloAdapter adapter =
                            new TituloFragment.TituloAdapter( v.getContext(), listTitulos, listQuantidade, listAnos );
                    listView.setAdapter( adapter );
                } else {
                    Toast.makeText( getContext(),
                            "Can't get List of Times",
                            Toast.LENGTH_LONG ).show();
                }
            }

            @Override
            public void onFailure( Call< List < Titulo > > call, Throwable t ) {
                Log.e( "ScrollMenuTitulo", "Erro: " + t.getMessage() );
            }
        });
    }


    class TituloAdapter extends ArrayAdapter< String > {
        Context context;
        ArrayList< String > descricaoTitulos;
        ArrayList< String > descricaoQuantidades;
        ArrayList< String > descricaoAnos;

        TituloAdapter( Context c,
                       ArrayList< String > descricaoTitulo,
                       ArrayList< String > descricaoQuantidade,
                       ArrayList< String > descricaoAno ) {
            super( c, R.layout.linhatitulo, R.id.descricaoTitulo, descricaoTitulo );
            this.context = c;
            this.descricaoTitulos = descricaoTitulo;
            this.descricaoQuantidades = descricaoQuantidade;
            this.descricaoAnos = descricaoAno;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent ) {
            LayoutInflater layoutInflater = ( LayoutInflater ) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View linha = layoutInflater.inflate( R.layout.linhatitulo, parent, false );

            TextView descricaoTituloText = linha.findViewById( R.id.descricaoTitulo );
            descricaoTituloText.setText( "Titulo: " + this.descricaoTitulos.get( position ) );

            TextView descricaoQuantidadeText = linha.findViewById( R.id.descricaoQuantidade );
            descricaoQuantidadeText.setText( "Quantidade: " + this.descricaoQuantidades.get( position ) );

            TextView descricaoAnosText = linha.findViewById( R.id.descricaoAnos );
            descricaoAnosText.setText( "Anos: " + this.descricaoAnos.get( position ) );

            return linha;
        }
    }
}
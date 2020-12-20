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
import com.example.myapplication.model.Noticia;
import com.example.myapplication.service.ConectorTriPharmacy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoticiaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoticiaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Integer idTime;

    ListView listView;
    ArrayList< String > listDescricaoNoticias = new ArrayList<>();

    public NoticiaFragment() {
        // Required empty public constructor
    }

    public NoticiaFragment( Integer idTime ) {
        this.idTime = idTime;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoticiaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoticiaFragment newInstance(String param1, String param2) {
        NoticiaFragment fragment = new NoticiaFragment();
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
        View v = inflater.inflate( R.layout.fragment_noticia, container, false );

        loadNews( v );
        return v;
    }

    private void loadNews( View v ) {
        String url = getResources().getString( R.string.BASE_URL );
        Call< List< Noticia > > requestNoticias = null;
        try {
            requestNoticias = new ConectorTriPharmacy( url ).getNewsByTeam( this.idTime );
        } catch ( IOException e ) {
            Log.e( "ScrollMenuNoticias", "Erro: " + e.getMessage() );
        }

        requestNoticias.enqueue( new Callback< List < Noticia > >() {

            @SuppressLint("NewApi")
            @Override
            public void onResponse( Call< List < Noticia > > call, Response< List < Noticia > > response ) {
                if ( response.isSuccessful() && response.code() == 200 ) {
                    List < Noticia > noticias  = response.body();
                    noticias.forEach( noticia -> {
                        listDescricaoNoticias.add( noticia.getDsNoticias() );
                    });

                    listView = v.findViewById( R.id.listViewNoticias );
                    NoticiaFragment.NoticiaAdapter adapter = new NoticiaFragment.NoticiaAdapter( v.getContext(), listDescricaoNoticias );
                    listView.setAdapter( adapter );
                } else {
                    Toast.makeText( getContext(),
                            "Can't get List of Times",
                            Toast.LENGTH_LONG ).show();
                }
            }

            @Override
            public void onFailure( Call< List < Noticia > > call, Throwable t ) {
                Log.e( "ScrollMenuJogador", "Erro: " + t.getMessage() );
            }
        });
    }


    class NoticiaAdapter extends ArrayAdapter< String > {
        Context context;
        ArrayList< String > descricaoNoticias;

        NoticiaAdapter( Context c, ArrayList< String > descricaoNoticia ) {
            super( c, R.layout.linhanoticia, R.id.descricaoNoticia, descricaoNoticia );
            this.context = c;
            this.descricaoNoticias = descricaoNoticia;
        }

        @NonNull
        @Override
        public View getView( int position, @Nullable View convertView, @NonNull ViewGroup parent ) {
            LayoutInflater layoutInflater = ( LayoutInflater ) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View linha = layoutInflater.inflate( R.layout.linhanoticia, parent, false );
            TextView descricaoNoticiaText = linha.findViewById( R.id.descricaoNoticia );
            descricaoNoticiaText.setText( this.descricaoNoticias.get( position ) );

            return super.getView(position, convertView, parent);
        }
    }
}
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.Jogador;
import com.example.myapplication.model.User;
import com.example.myapplication.service.ConectorTriPharmacy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private User user;
    private EditText etNome;
    private EditText etEmail;
    private EditText etCelular;
    private EditText etResidencial;

    ArrayList< Jogador > listJogadoresComGols = new ArrayList<>();

    public PerfilFragment() {
        // Required empty public constructor
    }

    public PerfilFragment(User user) {
        this.user = user;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GolFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
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
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);
        this.populateFields( v );
        final Button botaoSalvarUser = v.findViewById(R.id.btnSalvarUser);
        botaoSalvarUser.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveUser( v );
                    }
                }

        );
        return v;
    }

    private void populateFields( View view ) {
        this.etNome = view.findViewById( R.id.txtNome );
        this.etEmail = view.findViewById( R.id.txtEmail );
        this.etCelular = view.findViewById( R.id.txtCelular );
        this.etResidencial = view.findViewById( R.id.txtResidencial );

        this.etNome.setText(this.user.getDsNome());
        this.etEmail.setText(this.user.getDsEmail());
        this.etCelular.setText(this.user.getNoTelefoneCel());
        this.etResidencial.setText(this.user.getNoTelefoneRes());
    }


    private void saveUser(View v) {
        String url = getResources().getString( R.string.BASE_URL );
        this.user.setDsNome(this.etNome.getText().toString());
        this.user.setDsEmail(this.etEmail.getText().toString());
        this.user.setNoTelefoneCel(this.etCelular.getText().toString());
        this.user.setNoTelefoneRes(this.etResidencial.getText().toString());

        Call< User > requestSaveUser = null;
        try {
            requestSaveUser = new ConectorTriPharmacy( url ).saveUserById(this.user);
        } catch ( IOException e ) {
            Log.e( "ScrollMenuGol", "Erro: " + e.getMessage() );
        }

        requestSaveUser.enqueue( new Callback< User >() {

            @SuppressLint("NewApi")
            @Override
            public void onResponse( Call< User > call, Response< User > response ) {
                if ( response.isSuccessful() && response.code() == 200 ) {
                    user  = ( User ) response.body();
                } else {
                    Log.e("SaveUser",response.errorBody().toString());
                }
            }

            @Override
            public void onFailure( Call< User > call, Throwable t ) {
                Log.e( "ScrollMenuJogador", "Erro: " + t.getMessage() );
            }
        });
    }
}
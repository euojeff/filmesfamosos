package com.jeffersonaraujo.filmesfamosos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaFilmesActivity extends AppCompatActivity implements CardFilmeAdapter.CardFilmeAdapterOnclickHandler {

    private RequestQueue mRequestQueue;

    private ArrayList<String> listaFilmes = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private CardFilmeAdapter mAdapter;

    private Context context;

    private static String KEY_LISTA_CARREGADA = "lista_filmes";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_ordenar_avaliacao:
                consultarMelhorClassificados();
                return true;
            case R.id.menu_ordenar_popular:
                consultarPopulares();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(KEY_LISTA_CARREGADA, listaFilmes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_filmes);

        context = this;

        mRecyclerView = findViewById(R.id.recycler_filmes);
        mAdapter = new CardFilmeAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        int numeroColunas = Util.calculaNumeroColunas(this);

        GridLayoutManager manager = new GridLayoutManager(this, numeroColunas);

        mRecyclerView.setLayoutManager(manager);

        mRequestQueue = Volley.newRequestQueue(this);

        if(savedInstanceState == null){
            consultarPopulares();
        }else{
            listaFilmes = (ArrayList<String>)savedInstanceState.getSerializable(KEY_LISTA_CARREGADA);
            mAdapter.setListaFilmes(listaFilmes);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCardClick(String jsonFilme) {

        Intent intent = new Intent(this, DetalheFilmeActivity.class);
        intent.putExtra(DetalheFilmeActivity.BUNDLE_JSON_FILME, jsonFilme);

        startActivity(intent);
    }

    private void consultarPopulares(){
        consultaFilmes(Util.montarURLMaisPopular().toString());
    }

    private void consultarMelhorClassificados(){
        consultaFilmes(Util.montarURLMelhorClassificado().toString());
    }

    private void consultaFilmes(String query){

        listaFilmes.clear();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, query, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray itens = response.getJSONArray("results");

                            for(int i = 0; i < itens.length(); i++){
                                listaFilmes.add(itens.getJSONObject(i).toString());
                            }
                            mAdapter.setListaFilmes(listaFilmes);
                            mAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, R.string.falha_conexao, Toast.LENGTH_LONG).show();
                    }
                });

        mRequestQueue.add(jsObjRequest);
    }
}

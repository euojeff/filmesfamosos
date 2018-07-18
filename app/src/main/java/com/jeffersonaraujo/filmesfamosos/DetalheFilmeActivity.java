package com.jeffersonaraujo.filmesfamosos;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jeffersonaraujo.filmesfamosos.helpers.FilmeJsonHelper;
import com.jeffersonaraujo.filmesfamosos.helpers.TrailerFilmeJsonHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetalheFilmeActivity extends AppCompatActivity implements CardTrailerAdapter.CardTrailerAdapterOnclickHandler {

    public final static String BUNDLE_JSON_FILME = "json_filme";

    private RequestQueue mRequestQueue;

    private FilmeJsonHelper mhelper;
    private ArrayList<TrailerFilmeJsonHelper> mListaTrailers = new ArrayList<>();

    private RecyclerView mTrailersRV;
    private CardTrailerAdapter mTrailerAdapter;

    private ImageView cartazIV;
    private ImageView backDropIV;
    private TextView tituloTv;
    private TextView tituloOriginalTv;
    private TextView avaliacaoTv;
    private TextView lancamentoTv;
    private TextView sinopseTv;
    private String idFilme;

    private void preencherInformacoes(){
        try {
            Picasso.with(this).load(mhelper.getBackDrop()).into(backDropIV);
            Picasso.with(this).load(mhelper.getPathCartaz()).into(cartazIV);

            tituloTv.setText(mhelper.getTitulo());
            tituloOriginalTv.setText(mhelper.getTituloOriginal());
            avaliacaoTv.setText(mhelper.getAvaliacao() + "/10");
            lancamentoTv.setText(mhelper.getDataLancamento());
            sinopseTv.setText(mhelper.getSinopse());
            idFilme = mhelper.getId();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_filme);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cartazIV = findViewById(R.id.img_miniatura_filme_detalhe);
        backDropIV = findViewById(R.id.img_back_filme_detalhe);
        tituloTv = findViewById(R.id.titulo_filme_detalhe);
        tituloOriginalTv = findViewById(R.id.detalhe_titulo_original);
        avaliacaoTv = findViewById(R.id.detalhe_avaliacao);
        lancamentoTv = findViewById(R.id.detalhe_data_lancamento);
        sinopseTv = findViewById(R.id.detalhe_sinopse);

        mRequestQueue = Volley.newRequestQueue(this);

        configuraTrailers();

        if(getIntent().hasExtra(BUNDLE_JSON_FILME)){
            try {
                mhelper = new FilmeJsonHelper(getIntent().getStringExtra(BUNDLE_JSON_FILME));
                preencherInformacoes();
                consultarTrailers();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void configuraTrailers(){

        mTrailersRV = findViewById(R.id.recycler_trailer);
        mTrailerAdapter = new CardTrailerAdapter(this, this);
        mTrailersRV.setAdapter(mTrailerAdapter);

        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mTrailersRV.setLayoutManager(horizontalLayoutManagaer);
    }

    private void consultarTrailers(){

        String query = Util.montarURLFilmeVideos(idFilme).toString();

        mListaTrailers.clear();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, query, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray itens = response.getJSONArray("results");

                            for(int i = 0; i < itens.length(); i++){
                                mListaTrailers.add(new TrailerFilmeJsonHelper(itens.getJSONObject(i).toString()));
                                Log.d("YOUTUBE>>>>>>>>>>", itens.getJSONObject(i).toString());
                            }
                            //TODO setar adapter filme aqui
                            mTrailerAdapter.setListaTrailers(mListaTrailers);
                            //mAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), R.string.falha_conexao, Toast.LENGTH_LONG).show();
                    }
                });

        mRequestQueue.add(jsObjRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onCardTrailerClick(TrailerFilmeJsonHelper helper) {
        try {
            startActivity(new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(helper.getUrlYoutube())));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), R.string.falha_youtube, Toast.LENGTH_LONG).show();
        }
    }
}

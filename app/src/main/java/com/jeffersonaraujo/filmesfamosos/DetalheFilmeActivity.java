package com.jeffersonaraujo.filmesfamosos;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jeffersonaraujo.filmesfamosos.database.AppDatabase;
import com.jeffersonaraujo.filmesfamosos.database.FilmeFavoritoEntry;
import com.jeffersonaraujo.filmesfamosos.helpers.FilmeJsonHelper;
import com.jeffersonaraujo.filmesfamosos.helpers.ReviewJsonHelper;
import com.jeffersonaraujo.filmesfamosos.helpers.TrailerFilmeJsonHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetalheFilmeActivity extends AppCompatActivity implements CardTrailerAdapter.CardTrailerAdapterOnclickHandler {

    public final static String BUNDLE_JSON_FILME = "json_filme";

    private RequestQueue mRequestQueue;

    private FilmeJsonHelper mhelper;
    private ArrayList<TrailerFilmeJsonHelper> mListaTrailers = new ArrayList<>();
    private ArrayList<ReviewJsonHelper> mListaReviews = new ArrayList<>();

    private RecyclerView mTrailersRV;
    private RecyclerView mReviewsRV;
    private CardTrailerAdapter mTrailerAdapter;
    private CardReviewAdapter mReviewsAdapter;

    private ImageView cartazIV;
    private ImageView backDropIV;
    private TextView tituloTv;
    private TextView tituloOriginalTv;
    private TextView avaliacaoTv;
    private TextView lancamentoTv;
    private TextView sinopseTv;
    private String idFilme;
    private ToggleButton favoritarTB;

    private static String KEY_LISTA_TRAILERS = "lista_trailers";
    private static String KEY_LISTA_REVIEWS = "lista_reviews";

    private AppDatabase mDb;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(KEY_LISTA_TRAILERS, mListaTrailers);
        outState.putSerializable(KEY_LISTA_REVIEWS, mListaReviews);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mDb = AppDatabase.getInstance(this);

        setContentView(R.layout.activity_detalhe_filme);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cartazIV = findViewById(R.id.img_miniatura_filme_detalhe);
        backDropIV = findViewById(R.id.img_back_filme_detalhe);
        tituloTv = findViewById(R.id.titulo_filme_detalhe);
        tituloOriginalTv = findViewById(R.id.detalhe_titulo_original);
        avaliacaoTv = findViewById(R.id.detalhe_avaliacao);
        lancamentoTv = findViewById(R.id.detalhe_data_lancamento);
        sinopseTv = findViewById(R.id.detalhe_sinopse);
        favoritarTB = findViewById(R.id.btn_favoritar_detalhe);

        mRequestQueue = Volley.newRequestQueue(this);

        configuraBtnFavoritar();
        configuraTrailers();
        configuraReviews();

        if(getIntent().hasExtra(BUNDLE_JSON_FILME)){
            try {
                mhelper = new FilmeJsonHelper(getIntent().getStringExtra(BUNDLE_JSON_FILME));
                preencherInformacoes();
                configuraViewModel(mhelper.getId());
                consultarTrailers(savedInstanceState);
                consultarReviews(savedInstanceState);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void configuraViewModel(String key) {

        DetalheFilmeModelFactory factory = new DetalheFilmeModelFactory(mDb, key);

        final DetalheFilmeViewModel viewModel
                = ViewModelProviders.of(this, factory).get(DetalheFilmeViewModel.class);

        viewModel.getFilmeFavorito().observe(this, new Observer<FilmeFavoritoEntry>() {
            @Override
            public void onChanged(@Nullable FilmeFavoritoEntry filme) {
                viewModel.getFilmeFavorito().removeObserver(this);

                if(filme != null){
                    favoritarTB.setChecked(true);
                }else{
                    favoritarTB.setChecked(false);
                }
            }
        });
    }

    private void configuraBtnFavoritar(){

        favoritarTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favoritarTB.isChecked()){
                    FilmeFavoritoEntry filme =
                            new FilmeFavoritoEntry(idFilme, tituloTv.getText().toString(), new Date());
                    new AsyncTask<FilmeFavoritoEntry, Void, Void>(){
                        @Override
                        protected Void doInBackground(FilmeFavoritoEntry... filme) {
                            mDb.filmeFavoritoDao().insertFilmeFavorito(filme[0]);
                            return null;
                        }
                    }.execute(filme);
                }else{
                    new AsyncTask<String, Void, Void>(){
                        @Override
                        protected Void doInBackground(String... key) {
                            mDb.filmeFavoritoDao().deleteFilmeFavorito(key[0]);
                            return null;
                        }
                    }.execute(idFilme);
                }
            }
        });
    }

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

    private void configuraTrailers(){

        mTrailersRV = findViewById(R.id.recycler_trailer);
        mTrailerAdapter = new CardTrailerAdapter(this, this);
        mTrailersRV.setAdapter(mTrailerAdapter);

        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mTrailersRV.setLayoutManager(horizontalLayoutManagaer);
    }

    private void configuraReviews(){

        mReviewsRV = findViewById(R.id.recycler_reviews);
        mReviewsAdapter = new CardReviewAdapter(this);
        mReviewsRV.setAdapter(mReviewsAdapter);

        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mReviewsRV.setLayoutManager(lm);
    }

    private void consultarReviews(Bundle savedInstanceState){

        String query = Util.montarURLFilmeReviews(idFilme).toString();

        if(savedInstanceState != null
                && savedInstanceState.getSerializable(KEY_LISTA_REVIEWS) != null){
            mListaReviews = (ArrayList<ReviewJsonHelper>)savedInstanceState.getSerializable(KEY_LISTA_REVIEWS);
            atualizarRecycleReviews();
        }else{

            mListaReviews.clear();

            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, query, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONArray itens = response.getJSONArray("results");

                                for(int i = 0; i < itens.length(); i++){
                                    mListaReviews.add(new ReviewJsonHelper(itens.getJSONObject(i).toString()));
                                    Log.d("REVIEWS>>>>>>>>>>", itens.getJSONObject(i).toString());
                                }
                                atualizarRecycleReviews();

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
    }

    private void consultarTrailers(Bundle savedInstanceState){

        String query = Util.montarURLFilmeVideos(idFilme).toString();

        if(savedInstanceState != null
                && savedInstanceState.getSerializable(KEY_LISTA_TRAILERS) != null){
            mListaTrailers = (ArrayList<TrailerFilmeJsonHelper>)savedInstanceState.getSerializable(KEY_LISTA_TRAILERS);
            atualizarRecycleTrailers();
        }else{

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
                                atualizarRecycleTrailers();

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
    }

    private void atualizarRecycleTrailers(){
        mTrailerAdapter.setListaTrailers(mListaTrailers);
        mTrailerAdapter.notifyDataSetChanged();
    }

    private void atualizarRecycleReviews(){
        mReviewsAdapter.setListaReviews(mListaReviews);
        mReviewsAdapter.notifyDataSetChanged();
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

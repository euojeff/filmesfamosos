package com.jeffersonaraujo.filmesfamosos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeffersonaraujo.filmesfamosos.helpers.FilmeJsonHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

public class DetalheFilmeActivity extends AppCompatActivity {

    public final static String BUNDLE_JSON_FILME = "json_filme";

    private FilmeJsonHelper mhelper;

    private ImageView cartazIV;
    private ImageView backDropIV;
    private TextView tituloTv;
    private TextView tituloOriginalTv;
    private TextView avaliacaoTv;
    private TextView lancamentoTv;
    private TextView sinopseTv;

    private void preencherInformacoes(){
        try {
            Picasso.with(this).load(mhelper.getBackDrop()).into(backDropIV);
            Picasso.with(this).load(mhelper.getPathCartaz()).into(cartazIV);

            tituloTv.setText(mhelper.getTitulo());
            tituloOriginalTv.setText(mhelper.getTituloOriginal());
            avaliacaoTv.setText(mhelper.getAvaliacao() + "/10");
            lancamentoTv.setText(mhelper.getDataLancamento());
            sinopseTv.setText(mhelper.getSinopse());

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

        if(getIntent().hasExtra(BUNDLE_JSON_FILME)){
            try {
                mhelper = new FilmeJsonHelper(getIntent().getStringExtra(BUNDLE_JSON_FILME));
                preencherInformacoes();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

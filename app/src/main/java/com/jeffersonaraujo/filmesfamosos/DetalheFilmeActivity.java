package com.jeffersonaraujo.filmesfamosos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetalheFilmeActivity extends AppCompatActivity {

    public final static String BUNDLE_JSON_FILME = "json_filme";
    TextView detalhe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_filme);

        detalhe = findViewById(R.id.detalhe);


        if(getIntent().hasExtra(BUNDLE_JSON_FILME)){
            detalhe.setText(getIntent().getStringExtra(BUNDLE_JSON_FILME));
        }
    }
}

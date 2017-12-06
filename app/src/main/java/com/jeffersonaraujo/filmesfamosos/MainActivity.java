package com.jeffersonaraujo.filmesfamosos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;

    private ArrayList<String> listaFilmes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestQueue = Volley.newRequestQueue(this);

        configuraRecycler();

        request();
    }

    private  void configuraRecycler(){
        RecyclerView mRecyclerView = findViewById(R.id.recycler_filmes);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setAdapter(new ItemAdapter());
    }

    private void request(){

        String url = Util.buildPopularMoviesUrl().toString();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray itens = response.getJSONArray("results");

                            Toast.makeText(getApplicationContext(), itens.length() + "", Toast.LENGTH_LONG);

                            StringBuilder b = new StringBuilder();

                            for(int i = 0; i < itens.length(); i++){
                                listaFilmes.add(itens.getJSONObject(i).getString("original_title"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        mRequestQueue.add(jsObjRequest);


    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        public ItemHolder(View itemView) {
            super(itemView);
            this.mTextView = itemView.findViewById(R.id.view_item_text);
        }
    }

    class ItemAdapter extends RecyclerView.Adapter <ItemHolder> {
        @Override public int getItemCount() {
            return listaFilmes.size();
        }
        @Override public ItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = getLayoutInflater().inflate(R.layout.item, viewGroup, false);
            return new ItemHolder(view);
        }
        @Override public void onBindViewHolder(ItemHolder simpleHolder, int i) {
            simpleHolder.mTextView.setText(listaFilmes.get(i));
        }
    }
}

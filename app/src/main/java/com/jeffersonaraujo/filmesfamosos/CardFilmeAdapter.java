package com.jeffersonaraujo.filmesfamosos;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jeffersonaraujo.filmesfamosos.helpers.FilmeJsonHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by jeff on 07/12/2017.
 */

class CardFilmeAdapter extends RecyclerView.Adapter <CardFilmeAdapter.CardFilmeHolder> {

    static class CardFilmeHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        CardView mCard;
        public CardFilmeHolder(View itemView) {
            super(itemView);
            this.mCard = itemView.findViewById(R.id.card_filme);
            this.mImageView = itemView.findViewById(R.id.img_cartaz);
        }
    }

    public interface CardFilmeAdapterOnclickHandler{
        void onCardClick(String jsonFilme);
    }

    private ArrayList<String> listaFilmes;
    private CardFilmeAdapterOnclickHandler mHandler;
    private Context mContext;

    public void setListaFilmes(ArrayList<String> listaFilmes) {
        this.listaFilmes = listaFilmes;
    }

    public CardFilmeAdapter(CardFilmeAdapterOnclickHandler handler, Context context){
        this.mHandler = handler;
        this.mContext = context;
        this.listaFilmes = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return listaFilmes.size();
    }

    @Override
    public CardFilmeAdapter.CardFilmeHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.card_filme, viewGroup, false);

        return new CardFilmeAdapter.CardFilmeHolder(view);
    }

    @Override
    public void onBindViewHolder(CardFilmeAdapter.CardFilmeHolder holder, final int i) {
        try {

            FilmeJsonHelper filme = new FilmeJsonHelper(listaFilmes.get(i));

            holder.mCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHandler.onCardClick(listaFilmes.get(i));
                }
            });

            Picasso.with(mContext).load(filme.getPathCartaz()).into(holder.mImageView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
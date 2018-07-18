package com.jeffersonaraujo.filmesfamosos;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jeffersonaraujo.filmesfamosos.helpers.FilmeJsonHelper;
import com.jeffersonaraujo.filmesfamosos.helpers.TrailerFilmeJsonHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by jeff on 07/12/2017.
 */

class CardTrailerAdapter extends RecyclerView.Adapter <CardTrailerAdapter.CardTrailerHolder> {

    static class CardTrailerHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        CardView mCard;

        public CardTrailerHolder(View itemView) {
            super(itemView);
            this.mCard = itemView.findViewById(R.id.card_trailer);
            this.mImageView = itemView.findViewById(R.id.img_trailer);
        }
    }

    public interface CardTrailerAdapterOnclickHandler {
        void onCardTrailerClick(TrailerFilmeJsonHelper helper);
    }

    private ArrayList<TrailerFilmeJsonHelper> listaTrailers;
    private CardTrailerAdapterOnclickHandler mHandler;
    private Context mContext;

    public void setListaTrailers(ArrayList<TrailerFilmeJsonHelper> listaTrailers) {
        this.listaTrailers = listaTrailers;
    }

    public CardTrailerAdapter(CardTrailerAdapterOnclickHandler handler, Context context){
        this.mHandler = handler;
        this.mContext = context;
        this.listaTrailers = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return listaTrailers.size();
    }

    @Override
    public CardTrailerAdapter.CardTrailerHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.card_trailer, viewGroup, false);

        return new CardTrailerAdapter.CardTrailerHolder(view);
    }

    @Override
    public void onBindViewHolder(CardTrailerAdapter.CardTrailerHolder holder, int i) {
        final int posicao = i;
        try {

            TrailerFilmeJsonHelper trailer = listaTrailers.get(i);

            holder.mCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHandler.onCardTrailerClick(listaTrailers.get(posicao));
                }
            });

            Picasso.with(mContext).load(trailer.getUrlYoutubeThunbnail()).into(holder.mImageView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
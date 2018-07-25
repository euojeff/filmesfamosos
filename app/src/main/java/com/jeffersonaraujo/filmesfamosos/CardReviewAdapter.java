package com.jeffersonaraujo.filmesfamosos;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeffersonaraujo.filmesfamosos.helpers.ReviewJsonHelper;
import com.jeffersonaraujo.filmesfamosos.helpers.TrailerFilmeJsonHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by jeff on 18/07/2018.
 */
class CardReviewAdapter extends RecyclerView.Adapter <CardReviewAdapter.CardReviewHolder> {

    static class CardReviewHolder extends RecyclerView.ViewHolder {
        TextView mUserTv;
        TextView mReviewTv;
        CardView mCard;

        public CardReviewHolder(View itemView) {
            super(itemView);
            this.mCard = itemView.findViewById(R.id.card_review);
            this.mUserTv = itemView.findViewById(R.id.tv_card_review_user);
            this.mReviewTv = itemView.findViewById(R.id.tv_card_review_review);

            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            {
                mCard.getBackground().setAlpha(0);
            }else{
                mCard.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), android.R.color.transparent));
            }
        }
    }

    private ArrayList<ReviewJsonHelper> listaReviews;
    private Context mContext;

    public void setListaReviews(ArrayList<ReviewJsonHelper> listaReviews) {
        this.listaReviews = listaReviews;
    }

    public CardReviewAdapter(Context context){
        this.mContext = context;
        this.listaReviews = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return listaReviews.size();
    }

    @Override
    public CardReviewAdapter.CardReviewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.card_review, viewGroup, false);

        return new CardReviewAdapter.CardReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardReviewAdapter.CardReviewHolder holder, int i) {
        final int posicao = i;
        try {

            ReviewJsonHelper helper = listaReviews.get(i);

            holder.mUserTv.setText(helper.getUser());
            holder.mReviewTv.setText(helper.getReview());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
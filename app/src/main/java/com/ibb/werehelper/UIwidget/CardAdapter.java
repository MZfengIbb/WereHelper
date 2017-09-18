package com.ibb.werehelper.UIwidget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ibb.werehelper.R;
import com.ibb.werehelper.itemObjects.CardObjects;

import java.util.ArrayList;

public class CardAdapter  extends RecyclerView.Adapter<CardViewHolder> {
    private ArrayList<CardObjects> cards;
    private Context context;

    public CardAdapter(Context context, ArrayList<CardObjects> cards) {
        this.cards = cards;
        this.context = context;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, null);
        CardViewHolder rcv = new CardViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        holder.card_image.setImageResource(holder.card_photo = cards.get(position).getFace());
        holder.card_name = cards.get(position).getName();
    }

    @Override
    public int getItemCount() {
        return this.cards.size();
    }
}
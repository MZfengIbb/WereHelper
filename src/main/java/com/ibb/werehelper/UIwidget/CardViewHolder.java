package com.ibb.werehelper.UIwidget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ibb.werehelper.R;
import com.ibb.werehelper.activity.WerewolfSettings;

/**
 * Created by Ibb on 2016/11/2.
 */

public class CardViewHolder extends RecyclerView.ViewHolder {
    public String card_name;
    public int card_photo;
    public ImageView card_image;

    public CardViewHolder(View itemView) {
        super(itemView);
        itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(card_image.getContext(), card_name, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                WerewolfSettings.deckOperation(view,card_name,card_photo,getAdapterPosition());
            }
        });

        card_image = (ImageView) itemView.findViewById(R.id.card_image);
    }


}

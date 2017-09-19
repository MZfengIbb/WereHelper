package com.ibb.werehelper.UIwidget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibb.werehelper.R;

/**
 * Created by Ibb on 2016/11/18.
 */
public class SeatViewHolder extends RecyclerView.ViewHolder{
    public ImageView seatProfile;
    public TextView seatText;

    public SeatViewHolder(final View itemView) {
        super(itemView);
        seatProfile = (ImageView) itemView.findViewById(R.id.seat_profile);
        seatText = (TextView) itemView.findViewById(R.id.seat_text);
    }
}

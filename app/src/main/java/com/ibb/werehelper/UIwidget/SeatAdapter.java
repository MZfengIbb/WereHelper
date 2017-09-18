package com.ibb.werehelper.UIwidget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ibb.werehelper.R;
import com.ibb.werehelper.activity.WerewolfDistribution;
import com.ibb.werehelper.itemObjects.SeatObjects;

import java.util.ArrayList;

/**
 * Created by Ibb on 2016/11/18.
 */

public class SeatAdapter extends RecyclerView.Adapter<SeatViewHolder> {
    private ArrayList<SeatObjects> seats;
    private Context context;

    public SeatAdapter(Context context, ArrayList<SeatObjects> seats) {
        this.context = context;
        this.seats = seats;
    }
    @Override
    public SeatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View seatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.seat_item, null);
        SeatViewHolder svh = new SeatViewHolder(seatView);
        return svh;
    }

    @Override
    public void onBindViewHolder(SeatViewHolder holder, final int position) {
        holder.seatProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WerewolfDistribution.seatNum.setValue(position + 1);
            }
        });
        holder.seatProfile.setImageBitmap(seats.get(position).getProfile());
        switch (seats.get(position).getSeated()){
            case SeatObjects.NOT_SEATED:
                holder.seatText.setTextColor(context.getResources().getColor(R.color.gray));
                holder.seatText.setText(""+ (position+1) + "○");
                break;
            case SeatObjects.ALREADY_SEATED:
                holder.seatText.setTextColor(context.getResources().getColor(R.color.blue));
                holder.seatText.setText(""+ (position+1) + "√");
                break;
            case SeatObjects.WELL_SEATED:
                holder.seatText.setTextColor(context.getResources().getColor(R.color.green));
                holder.seatText.setText(""+ (position+1) + "√");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return seats.size();
    }
}

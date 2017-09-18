package com.ibb.werehelper.itemObjects;

import android.graphics.Bitmap;

import com.ibb.werehelper.R;

import java.util.ArrayList;

/**
 * Created by Ibb on 2016/11/19.
 */

public class PlayerObjects extends SeatObjects {
    private boolean alive;
    private boolean haveSkill;
    private ArrayList<Bitmap> skillIcon = new ArrayList<Bitmap>(2);
    private Bitmap profile;
    private int seated;
    private int face;
    private String name;
    private String comment;

    public PlayerObjects(SeatObjects seat) {
        super(seat);
        alive = true;

        seated = seat.getSeated();
        face = seat.getCard().getFace();
        name = seat.getCard().getName();
        comment = seat.getCard().getComment();

        if(seat.getFace() != R.drawable.villager){
            haveSkill = true;
            initialSkill();
        }
    }

    private void initialSkill() {

    }

    @Override
    public int getFace() {
        return face;
    }

    @Override
    public Bitmap getProfile() {
        return profile;
    }

    public ArrayList<Bitmap> getSkillIcon() {
        return skillIcon;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public CardObjects getCard() {
        return super.getCard();
    }
}

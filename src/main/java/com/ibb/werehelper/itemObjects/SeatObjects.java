package com.ibb.werehelper.itemObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ibb.werehelper.R;
import com.ibb.werehelper.activity.WerewolfDistribution;

/**
 * Created by Ibb on 2016/11/18.
 */

public class SeatObjects extends CardObjects{
    private int seated;
    private Bitmap profile;
    public static final int NOT_SEATED = 0;
    public static final int ALREADY_SEATED = 1;
    public static final int WELL_SEATED = 2;
    private CardObjects card;
    private static final Bitmap defaultBack = BitmapFactory.decodeResource(WerewolfDistribution.myContext.getResources(), R.drawable.werewolf_back);

    public SeatObjects(CardObjects card){
        super(card);
        this.card = card;
        profile = defaultBack;
        seated = NOT_SEATED;
    }

    public void setProfile(Bitmap profile) {
        this.profile = Bitmap.createScaledBitmap(profile, 192, 192, true);
        seated = WELL_SEATED;
    }

    public void setSeated(int seated) {
        this.seated = seated;
    }

    public Bitmap getProfile() {return profile;}

    public int getSeated(){return seated;}

    public CardObjects getCard() {
        return card;
    }
}

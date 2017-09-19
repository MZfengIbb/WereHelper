package com.ibb.werehelper.itemObjects;

import com.ibb.werehelper.R;

import java.util.ArrayList;

/**
 * Created by Ibb on 2016/11/3.
 */

public class DeckCards{
    public final static int MAX_NUM = 20;
    public static ArrayList<CardObjects> cards = new ArrayList<CardObjects>(MAX_NUM);
    public static ArrayList<CardObjects> DeckCards(){
        if(cards.size() == 0)
        cards.add(new CardObjects("badge", R.drawable.badge));
        return cards;
    }
}

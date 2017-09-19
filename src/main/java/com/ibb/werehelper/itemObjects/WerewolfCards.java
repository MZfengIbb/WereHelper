package com.ibb.werehelper.itemObjects;


import com.ibb.werehelper.R;

import java.util.ArrayList;
/**
 * Created by Ibb on 2016/11/1.
 */
public class WerewolfCards {
    public static final int IDENTITY_NUM = 32;
    public static ArrayList<CardObjects> getCards() {
        ArrayList<CardObjects> cards = new ArrayList<CardObjects>(IDENTITY_NUM);

        cards.add(new CardObjects("badge", R.drawable.badge));
        cards.add(new CardObjects("seer",R.drawable.seer));
        cards.add(new CardObjects("villager",R.drawable.villager));
        cards.add(new CardObjects("werewolf",R.drawable.werewolf));

        cards.add(new CardObjects("Cupid",R.drawable.cupid));
        cards.add(new CardObjects("witch",R.drawable.witch));
        cards.add(new CardObjects("guard",R.drawable.guard));
        cards.add(new CardObjects("white wolf",R.drawable.whitewolf));

        cards.add(new CardObjects("knignt",R.drawable.knight));
        cards.add(new CardObjects("hunter",R.drawable.hunter));
        cards.add(new CardObjects("moron",R.drawable.moron));
        cards.add(new CardObjects("thief",R.drawable.thief));

        cards.add(new CardObjects("angel",R.drawable.angel));
        cards.add(new CardObjects("rust sword knight",R.drawable.rust_sword_knight));
        cards.add(new CardObjects("elder",R.drawable.elder));
        cards.add(new CardObjects("wild child",R.drawable.wild_child));

        cards.add(new CardObjects("stuttering judge",R.drawable.stuttering_judge));
        cards.add(new CardObjects("bear trainer",R.drawable.bear_trainer));
        cards.add(new CardObjects("religion elder",R.drawable.religion_elder));
        cards.add(new CardObjects("fatherwolf",R.drawable.fatherwolf));

        cards.add(new CardObjects("maid",R.drawable.maid));
        cards.add(new CardObjects("fox",R.drawable.fox));
        cards.add(new CardObjects("piper",R.drawable.piper ));
        cards.add(new CardObjects("wildwolf",R.drawable.wildwolf));

        cards.add(new CardObjects("crow",R.drawable.crow));
        cards.add(new CardObjects("little girl",R.drawable.little_girl));
        cards.add(new CardObjects("sisters",R.drawable.sisters));
        cards.add(new CardObjects("worfhound",R.drawable.worfhound));

        cards.add(new CardObjects("scapegoat",R.drawable.scapegoat));
        cards.add(new CardObjects("arsonist",R.drawable.arsonist));
        cards.add(new CardObjects("brothers",R.drawable.brothers));
        cards.add(new CardObjects("actor",R.drawable.actor));

        return cards;
    }
}

package com.ibb.werehelper.itemObjects;

/**
 * Created by Ibb on 2016/11/2.
 */

public class CardObjects {
    private int face;
    private String name;
    private String comment;

    public CardObjects(String name, int id){
        this.name = name;
        this.face = id;
        this.comment = name;
    }

    public CardObjects(String name, int id, String comment){
        this.name = name;
        this.face = id;
        this.comment = comment;

    }

    public CardObjects(CardObjects card){
        face = card.getFace();
        name = card.getName();
        comment = card.getComment();
    }

    public String getName(){return name;}
    public int getFace() {
        return face;
    }
    public String getComment(){
        return comment;
    }
}

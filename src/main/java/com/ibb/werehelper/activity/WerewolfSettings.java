package com.ibb.werehelper.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ibb.werehelper.R;
import com.ibb.werehelper.UIwidget.CardAdapter;
import com.ibb.werehelper.itemObjects.CardObjects;
import com.ibb.werehelper.itemObjects.DeckCards;
import com.ibb.werehelper.itemObjects.WerewolfCards;

public class WerewolfSettings extends AppCompatActivity implements View.OnClickListener{

    private boolean initialed;
    public static Animation cardShake;
    @SuppressLint("StaticFieldLeak")
    public static RecyclerView deckView;
    @SuppressLint("StaticFieldLeak")
    public static CardAdapter deckAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.werewolf_settings);

        initialView();

        Button select = (Button)findViewById(R.id.btn_distribute);
        select.setOnClickListener(this);
        Button receive = (Button)findViewById(R.id.btn_receive);
        receive.setOnClickListener(this);

        Button start = (Button)findViewById(R.id.startWerewolfGame);
        start.setOnClickListener(this);

        cardShake = AnimationUtils.loadAnimation(this, R.anim.card_shake);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.view_settings);
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        CardAdapter werewolfAdapter = new CardAdapter(WerewolfSettings.this, WerewolfCards.getCards());
        recyclerView.setAdapter(werewolfAdapter);

        deckView();
    }

    @Override
    public void onBackPressed() {
        if (initialed) this.finish();
        else initialView();
    }

    private void initialView() {

        RelativeLayout rly = (RelativeLayout) findViewById(R.id.lay_blur);

        initialed = true;
            View view = findViewById(R.id.settings_view);
            view.setDrawingCacheEnabled(true);

            new Handler().postDelayed(() -> {
                try {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        rly.setBackground(new BitmapDrawable(fastBlur(view.getDrawingCache(), 1, 80)));
                    }
                } catch (Exception ignored) {
                }
                finally {rly.setVisibility(View.VISIBLE);}

            }, 100);

    }

    public void deckView(){
        deckView = (RecyclerView) findViewById(R.id.deck_view);
        deckView.setHasFixedSize(true);
        StaggeredGridLayoutManager deckManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        deckView.setLayoutManager(deckManager);

        DeckCards.DeckCards();
        deckAdapter = new CardAdapter(WerewolfSettings.this, DeckCards.cards);
        deckView.setAdapter(deckAdapter);
    }

    public static void deckOperation(View view, String card_name, int card_photo, int index) {

        ViewGroup parent = ((ViewGroup) view.getParent());
        if (R.id.view_settings == parent.getId()) {
            view.startAnimation(WerewolfSettings.cardShake);
            if(DeckCards.cards.size() > 19)
                Toast.makeText(view.getContext(),"游戏人数过多！",Toast.LENGTH_SHORT).show();
            else{
                DeckCards.cards.add(new CardObjects(card_name, card_photo));
                deckView.scrollToPosition(DeckCards.cards.size()-1);
            }
        }
        if (R.id.deck_view == parent.getId() && index!= 0){
            DeckCards.cards.remove(index);
        }

        Toast.makeText(view.getContext(),"当前游戏人数：1名法官和"+
                (DeckCards.cards.size()-1)+ "名玩家",Toast.LENGTH_SHORT).show();
        deckAdapter.notifyDataSetChanged();
        deckAdapter.notifyItemRemoved(index);
    }

    @Override
    public void onClick(final View view) {
        initialed = false;
        if(view.getId() == R.id.startWerewolfGame){

            String[] permissions = new String[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.DONUT) {
                permissions = new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                };
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this, permissions, 2);
            }

            new AlertDialog.Builder(view.getContext())
                    .setTitle("身份选择完毕")
                    .setMessage("开始分发身份？")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        // continue with delete
                        startActivity(new Intent(view.getContext(),WerewolfDistribution.class));
                    })
                    .setNegativeButton(android.R.string.no, (dialog, which) -> {
                        // do nothing
                    })
                    .setIcon(R.drawable.icon_next)
                    .show();
        }

        if(view.getId() == R.id.btn_distribute){
            RelativeLayout rly = (RelativeLayout) findViewById(R.id.lay_blur);
            rly.setVisibility(View.INVISIBLE);
        }

        if(view.getId() == R.id.btn_receive){
            startActivity(new Intent(view.getContext(),WerewolfReceive.class));
        }
    }

    private Bitmap fastBlur(Bitmap sentBitmap, float scale, int radius) {

        int width = Math.round(sentBitmap.getWidth() * scale);
        int height = Math.round(sentBitmap.getHeight() * scale);
        sentBitmap = Bitmap.createScaledBitmap(sentBitmap, width, height, false);

        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = ( 0xff000000 & pix[yi] ) | ( dv[rsum] << 16 ) | ( dv[gsum] << 8 ) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }
}
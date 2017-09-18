package com.ibb.werehelper.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ibb.werehelper.R;
import com.ibb.werehelper.UIwidget.FlipAnimation;
import com.ibb.werehelper.itemObjects.PlayerObjects;
import com.ibb.werehelper.itemObjects.SeatObjects;

import java.util.ArrayList;

/**
 * Created by Ibb on 2016/11/18.
 */

public class WerewolfOverview extends AppCompatActivity {
    private ArrayList<PlayerObjects> players = new ArrayList<PlayerObjects>(WerewolfDistribution.seats.size());
    private ArrayList<View> playerViews = new ArrayList<View>(WerewolfDistribution.seats.size());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.werewolf_overview);

        final int iNumberOfPlayers = WerewolfDistribution.seats.size();
        final Context context = this;

        int playerNum = 0;
        for (SeatObjects seat :
                WerewolfDistribution.seats) {
            players.add(new PlayerObjects(seat));

            playerNum++;
            View playerView = iNumberOfPlayers > 12 ?
                    LayoutInflater.from(this).inflate(R.layout.player_item_small,null)
                :   LayoutInflater.from(this).inflate(R.layout.player_item,null);

            ((ImageView)findViewById(R.id.judge_display)).setImageBitmap(WerewolfDistribution.judge);
            ((ImageView)playerView.findViewById(R.id.player_face)).setImageResource(seat.getCard().getFace());
            ((ImageView)playerView.findViewById(R.id.player_profile)).setImageBitmap(seat.getProfile());
            ((TextView)playerView.findViewById(R.id.player_num)).setText("   " + playerNum);
            playerView.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(final View view) {
                                                  final Dialog dialog = new Dialog(context);
                                                  dialog.setContentView(R.layout.death_items);
                                                  dialog.setTitle("      标记玩家");
                                                  dialog.findViewById(R.id.clawed_image).setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          ((ImageView)view.findViewById(R.id.label)).setVisibility(View.VISIBLE);
                                                          ((ImageView)view.findViewById(R.id.label)).setImageResource(R.drawable.dead_clawed);
                                                          dialog.dismiss();
                                                      }
                                                  });
                                                  dialog.findViewById(R.id.hanged_image).setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          ((ImageView)view.findViewById(R.id.label)).setVisibility(View.VISIBLE);
                                                          ((ImageView)view.findViewById(R.id.label)).setImageResource(R.drawable.dead_hanged);
                                                          dialog.dismiss();
                                                      }
                                                  });
                                                  dialog.findViewById(R.id.boom_image).setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          ((ImageView)view.findViewById(R.id.label)).setVisibility(View.VISIBLE);
                                                          ((ImageView)view.findViewById(R.id.label)).setImageResource(R.drawable.dead_boom);
                                                          dialog.dismiss();
                                                      }
                                                  });
                                                  dialog.findViewById(R.id.badge_image).setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          ((ImageView)view.findViewById(R.id.label)).setVisibility(View.VISIBLE);
                                                          ((ImageView)view.findViewById(R.id.label)).setImageResource(R.drawable.sergeant);
                                                          dialog.dismiss();
                                                      }
                                                  });
                                                  dialog.findViewById(R.id.poisoned_image).setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          ((ImageView)view.findViewById(R.id.label)).setVisibility(View.VISIBLE);
                                                          ((ImageView)view.findViewById(R.id.label)).setImageResource(R.drawable.dead_poisoned);
                                                          dialog.dismiss();
                                                      }
                                                  });
                                                  dialog.findViewById(R.id.shot_image).setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          ((ImageView)view.findViewById(R.id.label)).setVisibility(View.VISIBLE);
                                                          ((ImageView)view.findViewById(R.id.label)).setImageResource(R.drawable.dead_shot);
                                                          dialog.dismiss();
                                                      }
                                                  });
                                                  dialog.findViewById(R.id.dead_image).setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          ((ImageView)view.findViewById(R.id.label)).setVisibility(View.VISIBLE);
                                                          ((ImageView)view.findViewById(R.id.label)).setImageResource(R.drawable.dead);
                                                          dialog.dismiss();
                                                      }
                                                  });
                                                  dialog.findViewById(R.id.flip_image).setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          ((ImageView)view.findViewById(R.id.label)).setVisibility(View.VISIBLE);
                                                          ((ImageView)view.findViewById(R.id.label)).setImageResource(R.drawable.flip_identity);
                                                          dialog.dismiss();
                                                      }
                                                  });
                                                  dialog.findViewById(R.id.lover_image).setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          ((ImageView)view.findViewById(R.id.label)).setVisibility(View.VISIBLE);
                                                          ((ImageView)view.findViewById(R.id.label)).setImageResource(R.drawable.lover);
                                                          dialog.dismiss();
                                                      }
                                                  });
                                                  dialog.findViewById(R.id.for_love_image).setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          ((ImageView)view.findViewById(R.id.label)).setVisibility(View.VISIBLE);
                                                          ((ImageView)view.findViewById(R.id.label)).setImageResource(R.drawable.dead_forlove);
                                                          dialog.dismiss();
                                                      }
                                                  });
                                                  dialog.findViewById(R.id.bewolf_image).setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          ((ImageView)view.findViewById(R.id.label)).setVisibility(View.VISIBLE);
                                                          ((ImageView)view.findViewById(R.id.label)).setImageResource(R.drawable.be_wolf);
                                                          dialog.dismiss();
                                                      }
                                                  });
                                                  dialog.findViewById(R.id.cancel_image).setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          ((ImageView)view.findViewById(R.id.label)).setVisibility(View.INVISIBLE);
                                                          dialog.dismiss();
                                                      }
                                                  });
                                                  dialog.show();
                                              }
                                          });
            playerViews.add(playerView);
        }

        RelativeLayout playerLayout = (RelativeLayout) findViewById(R.id.player_layout);
        playerLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                findViewById(R.id.day).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(findViewById(R.id.day).getVisibility() == View.VISIBLE)
                        findViewById(R.id.day_night).startAnimation(new FlipAnimation(findViewById(R.id.day) , findViewById(R.id.night)));
                    }
                });
                findViewById(R.id.night).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(findViewById(R.id.night).getVisibility() == View.VISIBLE)
                            findViewById(R.id.day_night).startAnimation(new FlipAnimation(findViewById(R.id.night) , findViewById(R.id.day)));
                    }
                });

                double dIncrease = Math.PI * 2 / iNumberOfPlayers,
                        dAngle = 0,
                        x = 0,
                        y = 0;

                RelativeLayout playerLayout = (RelativeLayout) findViewById(R.id.player_layout);
                // Ensure you call it only once :
                playerLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                int x0,y0,delta;
                int iWidth = playerLayout.getMeasuredWidth();
                int iHeight = playerLayout.getMeasuredHeight();

                if(iNumberOfPlayers > 12){
                    delta = 170;
                    x0 = (iWidth - delta)/2;
                    y0 = (iHeight - delta - 40)/2;
                }

                else{
                    delta = 244;
                    x0 = (iWidth - delta)/2;
                    y0 = (iHeight - delta - 40)/2;
                }

                for(int i = 0; i<iNumberOfPlayers; i++)
                {
                    x = 370 * Math.cos(dAngle) + x0;
                    y = 370 * Math.sin(dAngle) + y0;

                    dAngle += dIncrease;

                    RelativeLayout.LayoutParams xParams = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    xParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        xParams.addRule(RelativeLayout.ALIGN_PARENT_START);
                    }
                    xParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    xParams.leftMargin = (int) x;
                    xParams.topMargin =  (int) y;

                    playerLayout.addView(playerViews.get(i),xParams);
                }
            }
        });

        Button viewDisplay = (Button) findViewById(R.id.view_display);
        viewDisplay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    for (View playerView :
                            playerViews) {
                        if (playerView.findViewById(R.id.player_face).getVisibility() == View.INVISIBLE)
                            playerView.startAnimation(new FlipAnimation(playerView.findViewById(R.id.player_profile) , playerView.findViewById(R.id.player_face)));
                    }
                }

                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    for (View playerView :
                            playerViews) {
                        if (playerView.findViewById(R.id.player_face).getVisibility() == View.VISIBLE)
                            playerView.startAnimation(new FlipAnimation(playerView.findViewById(R.id.player_face) , playerView.findViewById(R.id.player_profile)));
                    }
                }
                return true;
            }
        });

        Button backHome = (Button)findViewById(R.id.back_home);
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setIcon(R.drawable.icon_home)
                        .setTitle("返回身份选择界面")
                        .setMessage("是否重新开局？")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                startActivity(new Intent(context,WerewolfSettings.class));
                            }
                        })
                        .setNegativeButton(android.R.string.no,null)
                        .show();
            }
        });
    }
}
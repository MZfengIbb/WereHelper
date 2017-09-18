package com.ibb.werehelper.activity;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibb.werehelper.R;
import com.ibb.werehelper.UIwidget.FlipAnimation;
import com.libra.sinvoice.LogHelper;
import com.libra.sinvoice.SinVoiceRecognition;

/**
 * Created by Ibb on 2017/4/7.
 */

public class WerewolfReceive extends AppCompatActivity implements SinVoiceRecognition.Listener{

    private final static String TAG = "WerewolfReceive";
    private final static int MAX_NUMBER = 5;
    private final static int MSG_SET_RECG_TEXT = 1;
    private final static int MSG_RECG_START = 2;
    private final static int MSG_RECG_END = 3;

    private static Context context;
    private final static String CODEBOOK = "01234";

    private static Handler mHandler;
    private static SinVoiceRecognition mRecognition;

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.werewolf_receive);

        context = this;


        String[] permissions = new String[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            permissions = new String[]{
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
            };
        }

        Button btn_home = (Button) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(view -> finish());
        ActivityCompat.requestPermissions(this, permissions, 1);

        mRecognition = new SinVoiceRecognition(CODEBOOK);
        mRecognition.setListener(this);

        TextView txt_show = (TextView) findViewById(R.id.txt_show);
        mHandler = new WerewolfReceive.RegHandler(txt_show);

        Button btn_receive = (Button)findViewById(R.id.btn_receive);
        btn_receive.setOnClickListener(view -> {
            mRecognition.start();
            txt_show.setText("Waiting for audio message...");
            btn_receive.setVisibility(View.INVISIBLE);
        });
    }

    private static class RegHandler extends Handler {
        private StringBuilder mTextBuilder = new StringBuilder();
        private TextView mRecognisedTextView;

        RegHandler(TextView textView) {
            mRecognisedTextView = textView;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SET_RECG_TEXT:
                    char ch = (char) msg.arg1;
                    mTextBuilder.append(ch);
                    if (null != mRecognisedTextView) {
                        mRecognisedTextView.setText("Decoding the message..");
                    }
                    break;

                case MSG_RECG_START:
                    mRecognisedTextView.setText("Decoding the message...");
                    mTextBuilder.delete(0, mTextBuilder.length());
                    break;

                case MSG_RECG_END:
                    try {
                        LogHelper.d(TAG, "recognition end");
                        String str = mTextBuilder.toString();
                        int int_num = 0,int_identity = 0;
                        for (int i = 0; i < str.indexOf('0'); i++) {
                            int_num = 2*int_num + (Character.getNumericValue(str.charAt(i))+1)%2;
                        }
                        for (int i = str.indexOf('0') + 1; i < str.length(); i++) {
                            int_identity = 2*int_identity + (Character.getNumericValue(str.charAt(i))+1)%2;
                        }
                        mRecognisedTextView.setText("You seat number is  "+ int_num + "..." );
                        ImageView imgFace = (ImageView) ((View) mRecognisedTextView.getParent()).findViewById(R.id.img_face);
                        imgFace.setImageResource(int_identity);
                        ImageView imgBack = (ImageView) ((View) mRecognisedTextView.getParent()).findViewById(R.id.img_back);
                        imgBack.setImageResource(R.drawable.werewolf_back);
                        imgBack.setVisibility(View.VISIBLE);
                        imgBack.setAnimation(AnimationUtils.loadAnimation(context,R.anim.card_flyin));
                        imgBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ((View)view.getParent()).startAnimation(new FlipAnimation(imgBack, imgFace));
                            }
                        });
                        imgFace.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ((View)view.getParent()).startAnimation(new FlipAnimation(imgFace, imgBack));
                            }
                        });

                        mRecognition.stop();
                        break;
                    }catch(Exception ignored){
                        mRecognisedTextView.setText("Waiting for audio message...");
                        mRecognition.start();
                }
            }
            super.handleMessage(msg);
        }
    }
    public void onRecognitionStart() {
        mHandler.sendEmptyMessage(MSG_RECG_START);
    }

    @Override
    public void onRecognition(char ch) {
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_RECG_TEXT, ch, 0));
    }

    @Override
    public void onRecognitionEnd() {
        mHandler.sendEmptyMessage(MSG_RECG_END);
    }

}

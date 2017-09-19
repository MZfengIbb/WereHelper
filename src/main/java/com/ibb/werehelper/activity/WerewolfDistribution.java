package com.ibb.werehelper.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ibb.werehelper.R;
import com.ibb.werehelper.UIwidget.CameraPreview;
import com.ibb.werehelper.UIwidget.FlipAnimation;
import com.ibb.werehelper.UIwidget.SeatAdapter;
import com.ibb.werehelper.UIwidget.SeatedAnimation;
import com.ibb.werehelper.itemObjects.CardObjects;
import com.ibb.werehelper.itemObjects.DeckCards;
import com.ibb.werehelper.itemObjects.SeatObjects;
import com.libra.sinvoice.LogHelper;
import com.libra.sinvoice.SinVoicePlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Ibb on 2016/11/1.
 */

public class WerewolfDistribution extends AppCompatActivity implements SinVoicePlayer.Listener {
    private static final int IMAGE_ORIENTATION = 90;
    private static final int IMAGE_SIZE = 512;
    private static final String TAG = "WerewolfDistribution";
    public static ArrayList<SeatObjects> seats = new ArrayList<SeatObjects>(DeckCards.cards.size());
    private Camera mCamera;
    private CameraPreview cameraPreview;
    public static Context myContext;
    private Camera.PictureCallback mPicture;
    private Bitmap bitmap;
    private SeatAdapter seatAdapter;
    private static boolean animLock = false;
    @SuppressLint("StaticFieldLeak")
    public static NumberPicker seatNum;
    private Uri uri;
    private RecyclerView distributionView;
    private RelativeLayout idDisplay;
    public static Bitmap judge;
    private static boolean canStart = false;
    private final static String CODEBOOK = "01234";
    private SinVoicePlayer mSinVoicePlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.werewolf_identities);
        myContext = this;

        seats = new ArrayList<>(DeckCards.cards.size());
        for (CardObjects card:
             DeckCards.cards) {
            seats.add(new SeatObjects(card));
        }
        seats.remove(0);
        Collections.shuffle(seats, new Random(System.nanoTime()));

        LinearLayout cameraTake = (LinearLayout) findViewById(R.id.camera_take);
        cameraPreview = new CameraPreview(myContext,mCamera);
        cameraTake.addView(cameraPreview);

        distributionView = (RecyclerView) findViewById(R.id.distribution_view);
        distributionView.setHasFixedSize(true);
        StaggeredGridLayoutManager dsManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);
        distributionView.setLayoutManager(dsManager);
        seatAdapter = new SeatAdapter(WerewolfDistribution.this,seats);
        distributionView.setAdapter(seatAdapter);

        Button startOverview = (Button) findViewById(R.id.START);
        startOverview.setOnTouchListener((view, motionEvent) -> {
            if(uri != null) myContext.getContentResolver().delete(uri,null,null);
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                capturePicture();
                new Handler().postDelayed(() -> {
                    canStart = true;
                    judge = bitmap;
                },500);
            }
            if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                if(canStart) {
                    startActivity(new Intent(myContext, WerewolfOverview.class));
                    canStart = false;
                }
                else
                    Toast.makeText(myContext,"长按后松手以开始",Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        idDisplay = (RelativeLayout) findViewById(R.id.id_display);
        seatNum = (NumberPicker) findViewById(R.id.seat_num);
        seatNum.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        seatNum.setValue(1);
        seatNum.setMinValue(1);
        seatNum.setMaxValue(DeckCards.cards.size() - 1);
        seatNum.setOnValueChangedListener((numberPicker, oldNum, newNum) -> distributionView.scrollToPosition(newNum - 1));

        Button send = (Button) findViewById(R.id.SEND);
        send.setOnClickListener(view -> {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("image/png");
            uri = Uri.parse(MediaStore.Images.Media.insertImage(myContext.getContentResolver(),
                    BitmapFactory.decodeResource(myContext.getResources(), seats.get(seatNum.getValue() - 1).getFace()), null, null));
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "身份发送");
            myContext.startActivity(Intent.createChooser(shareIntent, "分享图片至"));
            seats.get(seatNum.getValue() - 1).setSeated(SeatObjects.ALREADY_SEATED);
            seatAdapter.notifyDataSetChanged();
        });

        mSinVoicePlayer = new SinVoicePlayer(CODEBOOK);
        mSinVoicePlayer.setListener(this);

        Button btn_broadcast = (Button)findViewById(R.id.btn_broadcast);
        Button btn_sending = (Button)findViewById(R.id.btn_sending);
        TextView txt_send = (TextView)findViewById(R.id.txt_send);
        btn_broadcast.setOnClickListener(view -> {
            int int_num = seatNum.getValue();
            int int_identity = (seats.get(seatNum.getValue() - 1).getFace());
            mSinVoicePlayer.play(genText(int_num,int_identity), true, 1000);
            txt_send.setText("Sending......");
            btn_broadcast.setVisibility(View.INVISIBLE);
            btn_sending.setVisibility(View.VISIBLE);
            });

        btn_sending.setOnClickListener(view -> {
            mSinVoicePlayer.stop();
            txt_send.setText("Send to nearby");
            btn_broadcast.setVisibility(View.VISIBLE);
            btn_sending.setVisibility(View.INVISIBLE);
        });

        Button shot = (Button)findViewById(R.id.SHOT);
        shot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                ImageView identityFace = (ImageView) findViewById(R.id.identity_face);
                identityFace.setImageResource(seats.get(seatNum.getValue() - 1).getFace());
                ImageView identityBack = (ImageView) findViewById(R.id.identity_back);
                identityBack.setImageBitmap(seats.get(seatNum.getValue() - 1).getProfile());
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    capturePicture();
                    new Handler().postDelayed(() -> {
                        releaseCamera();
                        ImageView identityFace1 = (ImageView) findViewById(R.id.identity_face);
                        ImageView identityBack1 = (ImageView) findViewById(R.id.identity_back);
                        idDisplay.startAnimation(new FlipAnimation(identityBack1, identityFace1));
                        new Handler().postDelayed(() -> {
                            ImageView identityBack11 = (ImageView) idDisplay.findViewById(R.id.identity_back);
                            seats.get(seatNum.getValue() - 1).setProfile(bitmap);
                            identityBack11.setImageBitmap(seats.get(seatNum.getValue() - 1).getProfile());
                            animLock = true;
                        },600);
                    },500);
                }

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (animLock) {
                        flipAndSeated();
                    }
                    else new Handler().postDelayed(() -> {
                        Toast.makeText(myContext,"长按以查看身份",Toast.LENGTH_SHORT).show();
                        flipAndSeated();
                    },1000);
                }
                return true;
            }

            private void flipAndSeated() {
                ImageView identityFace = (ImageView) findViewById(R.id.identity_face);
                ImageView identityBack = (ImageView) findViewById(R.id.identity_back);
                idDisplay.startAnimation(new SeatedAnimation(identityFace, identityBack));
                new Handler().postDelayed(() -> {
                    seatAdapter.notifyDataSetChanged();
                    openCamera();
                    animLock = false;
                }, 600);
            }
        });
    }

    private String genText(int num, int identity) {
        StringBuilder sb_num = new StringBuilder(),sb_identity = new StringBuilder();
        boolean isOdd = true,isEven = true;
        int x,y;
        while(num > 0){
            x = num%2;
            sb_num.append(isOdd ? x + 1 : x + 3 );
            isOdd = !isOdd;
            num = num/2;
        }
        while(identity > 0)
        {
            y = identity%2;
            sb_identity.append(isEven ? y+1:y+3);
            isEven = !isEven;
            identity = identity/2;
        }

        return sb_num.reverse().toString() + "0" + sb_identity.reverse().toString();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(uri != null) myContext.getContentResolver().delete(uri,null,null);
        openCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //when on Pause, release camera in order to be used from other applications
        releaseCamera();
    }

    private void openCamera(){
        if (mCamera == null) {
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            mCamera.setDisplayOrientation(IMAGE_ORIENTATION);
            mPicture = getPictureCallback();
            cameraPreview.refreshCamera(mCamera);
            cameraPreview.setVisibility(View.VISIBLE);
        }
    }

    private void releaseCamera() {
        // stop and release camera
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
            cameraPreview.setVisibility(View.INVISIBLE);
        }
    }

    private void capturePicture(){
        mCamera.takePicture(null, null, mPicture);
    }
    private  Camera.PictureCallback getPictureCallback() {
        Camera.PictureCallback picture = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                try {
                    bitmap = processImage(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //refresh camera to continue preview
                cameraPreview.refreshCamera(mCamera);
            }
        };
        return picture;
    }

    private Bitmap processImage(byte[] data) throws IOException {
        // Determine the width/height of the image
        int width = mCamera.getParameters().getPictureSize().width;
        int height = mCamera.getParameters().getPictureSize().height;

        // Load the bitmap from the byte array
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

        // Rotate and crop the image into a square
        int lengthShort = (width > height) ? height : width;
        int lengthLong = (width < height) ? height : width;

        Matrix matrix = new Matrix();
        matrix.postRotate(360-IMAGE_ORIENTATION);
        Bitmap cropped = Bitmap.createBitmap(bitmap,lengthLong-lengthShort,0,lengthShort,lengthShort , matrix, true);
        bitmap.recycle();

        // Scale down to the output size
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(cropped, IMAGE_SIZE, IMAGE_SIZE, true);
        cropped.recycle();

        return scaledBitmap;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.icon_home)
                .setTitle("取消身份分发")
                .setMessage("确定取消吗？")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no,null)
                .show();
    }

    @Override
    public void onPlayStart() {
        LogHelper.d(TAG, "start play");
    }

    @Override
    public void onPlayEnd() {
        LogHelper.d(TAG, "stop play");
    }
}
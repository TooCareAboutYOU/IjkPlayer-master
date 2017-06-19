package com.dnion.ijkplayer.master;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dnion.ijkplayer.master.ijkmedia.PlayerView;

import ijkplayer.listener.OnShowThumbnailListener;
import ijkplayer.utils.MediaUtils;
import ijkplayer.widget.PlayStateParams;

public class PlayerActivity extends Activity {

    private static final String TAG = "PlayerActivity";

    private String RtmpURL="";

    private PlayerView mPlayer;
    private String mTitle="Rtmp点播流";
    private PowerManager.WakeLock pmw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_player_view_player);
        RtmpURL=getIntent().getStringExtra("rtmpUrl");
        Log.i(TAG, "onCreate: "+RtmpURL);

        /** 常亮  */
        //PowerManager pm= (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        //pmw=pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,"liveTAG");
        //pmw.acquire();

        mPlayer=new PlayerView(this)
                .setTitle(mTitle)
                .setScaleType(PlayStateParams.fitparent)
                .hideMenu(true)
                .hideSteam(true)
                .setForbidDoulbeUp(true)
                .hideCenterPlayer(true)
                .hideControlPanl(true)
                .showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView imageView) {
                        Glide.with(PlayerActivity.this)
                                .load(R.id.glideloadingiv)
                                .placeholder(R.color.transparent)
                                .error(R.color.transparent)
                                .into(imageView);
                    }
                });

        mPlayer.setPlaySource(RtmpURL).startPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPlayer!=null){
            mPlayer.onPause();
        }
        MediaUtils.muteAudioFocus(this,true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPlayer != null) {
            mPlayer.onResume();
        }
        MediaUtils.muteAudioFocus(this, false);
//        if (pmw != null) {
//            pmw.acquire();
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mPlayer != null) {
            mPlayer.onConfigurationChanged(newConfig);
        }
    }
}

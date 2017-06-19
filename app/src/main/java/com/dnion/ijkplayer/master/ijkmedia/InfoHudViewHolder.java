package com.dnion.ijkplayer.master.ijkmedia;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import android.view.View;
import android.widget.TableLayout;

import com.dnion.ijkplayer.master.R;

import java.util.HashMap;
import java.util.Locale;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public class InfoHudViewHolder {
    private TableLayoutBinder mTableLayoutBinder;
    private SparseArray<View> mRowMap = new SparseArray<View>();
    private IMediaPlayer mMediaPlayer;
    private long mLoadCost = 0;
    private long mSeekCost = 0;

    public InfoHudViewHolder(Context context, TableLayout tableLayout) {
        mTableLayoutBinder = new TableLayoutBinder(context, tableLayout);
    }

    private void appendSection(int nameId) {
        mTableLayoutBinder.appendSection(nameId);
    }

    private void appendRow(int nameId) {
        View rowView = mTableLayoutBinder.appendRow2(nameId, null);
        mRowMap.put(nameId, rowView);
    }

    private void setRowValue(int id, String value) {
        View rowView = mRowMap.get(id);
        if (rowView == null) {
            rowView = mTableLayoutBinder.appendRow2(id, value);
            mRowMap.put(id, rowView);
        } else {
            mTableLayoutBinder.setValueText(rowView, value);
        }
    }

    public void setMediaPlayer(IMediaPlayer mp) {
        mMediaPlayer = mp;
        if (mMediaPlayer != null) {
            mHandler.sendEmptyMessageDelayed(MSG_UPDATE_HUD, 500);
        } else {
            mHandler.removeMessages(MSG_UPDATE_HUD);
        }
    }

    private static String formatedDurationMilli(long duration) {
        if (duration >=  1000) {
            return String.format(Locale.US, "%.2f sec", ((float)duration) / 1000);
        } else {
            return String.format(Locale.US, "%d msec", duration);
        }
    }

    private static String formatedSpeed(long bytes, long elapsed_milli) {
        if (elapsed_milli <= 0) {
            return "0 B/s";
        }

        if (bytes <= 0) {
            return "0 B/s";
        }

        float bytes_per_sec = ((float)bytes) * 1000.f /  elapsed_milli;
        if (bytes_per_sec >= 1000 * 1000) {
            return String.format(Locale.US, "%.2f MB/s", ((float)bytes_per_sec) / 1000 / 1000);
        } else if (bytes_per_sec >= 1000) {
            return String.format(Locale.US, "%.1f KB/s", ((float)bytes_per_sec) / 1000);
        } else {
            return String.format(Locale.US, "%d B/s", (long)bytes_per_sec);
        }
    }

    public void updateLoadCost(long time)  {
        mLoadCost = time;
    }

    public void updateSeekCost(long time)  {
        mSeekCost = time;
    }

    private static String formatedSize(long bytes) {
        if (bytes >= 100 * 1000) {
            return String.format(Locale.US, "%.2f MB", ((float)bytes) / 1000 / 1000);
        } else if (bytes >= 100) {
            return String.format(Locale.US, "%.1f KB", ((float)bytes) / 1000);
        } else {
            return String.format(Locale.US, "%d B", bytes);
        }
    }

    public void startUpdate(){
        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_HUD, 500);
    }

    public void updateInfo(HashMap<Integer,Long> a, int type){
        if (a == null)
            return;

        if(type==1){
            long uploadvideo = a.get(R.string.uploadvideo);
            long encodevideo = a.get(R.string.encodevideo);
            long receivevideo = a.get(R.string.receivevideo);
            long uploadvideorate = a.get(R.string.videorate);
            setRowValue(R.string.uploadvideo, String.format(Locale.US,"%s", formatedSize(uploadvideo)));
            setRowValue(R.string.encodevideo, String.format(Locale.US,"%s", formatedSize(encodevideo)));
            setRowValue(R.string.receivevideo, String.format(Locale.US,"%s",formatedSize(receivevideo)));
            setRowValue(R.string.videorate, String.format(Locale.US,"%s", formatedSpeed(uploadvideorate,1000)));
        }

        long uploadaudio = a.get(R.string.uploadaudio);
        long encodeaudio = a.get(R.string.encodeaudio);
        long receiveaudio = a.get(R.string.receiveaudio);
        long uploadaudiorate = a.get(R.string.audiorate);


        setRowValue(R.string.uploadaudio, String.format(Locale.US,"%s", formatedSize(uploadaudio)));

        setRowValue(R.string.encodeaudio, String.format(Locale.US,"%s", formatedSize(encodeaudio)));

        setRowValue(R.string.receiveaudio, String.format(Locale.US,"%s",formatedSize(receiveaudio)));

        setRowValue(R.string.audiorate, String.format(Locale.US,"%s",   formatedSpeed(uploadaudiorate,1000)));
    }

    private static final int MSG_UPDATE_HUD = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_HUD: {

                    setRowValue(R.string.v_cache, "111");
                    setRowValue(R.string.a_cache, "222");
                    setRowValue(R.string.load_cost, "333");
                    setRowValue(R.string.seek_cost, "4444");
                    setRowValue(R.string.seek_load_cost, "555");
                    setRowValue(R.string.tcp_speed, "");
                    setRowValue(R.string.bit_rate, "");


                    mHandler.removeMessages(MSG_UPDATE_HUD);
                    mHandler.sendEmptyMessageDelayed(MSG_UPDATE_HUD, 500);
                }
            }
        }
    };
}

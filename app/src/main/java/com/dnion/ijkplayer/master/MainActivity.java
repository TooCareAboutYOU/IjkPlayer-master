package com.dnion.ijkplayer.master;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    private String _RtmpURL="rtmp://192.168.83.62/live/100101";

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText= (EditText) findViewById(R.id.ed_RtmpUrl);
        mEditText.setHint(_RtmpURL);
        findViewById(R.id.btn_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,PlayerActivity.class);
                String str=mEditText.getText().toString();
                if (TextUtils.isEmpty(str)){
                    intent.putExtra("rtmpUrl",_RtmpURL);
                }else {
                    intent.putExtra("rtmpUrl",str);
                }
                startActivity(intent);
            }
        });
    }
}

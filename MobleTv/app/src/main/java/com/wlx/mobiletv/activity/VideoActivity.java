package com.wlx.mobiletv.activity;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.widget.TableLayout;

import com.wlx.mobiletv.widget.medie.AndroidMediaController;
import com.wlx.mobiletv.widget.medie.IjkVideoView;
import com.wlx.mtvlibrary.base.BaseActivity;

import butterknife.InjectView;

/**
 * 作者：LucianWang
 * 日期：2016/12/14 14:32
 * 邮箱：wlx3079@163.com
 * 描述：
 */

public class VideoActivity extends BaseActivity {

    private static final String TAG = "VideoActivity";

    @InjectView(R.id.video_view)
    IjkVideoView mVideoView;
    @InjectView(R.id.hud_view)
    TableLayout mHudView;

    @Override
    protected void init() {
        ActionBar actionBar = getSupportActionBar();

        AndroidMediaController mMediaController = new AndroidMediaController(this, false);
        Log.i(TAG, "init: "+actionBar.toString());
        mMediaController.setSupportActionBar(actionBar);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setHudView(mHudView);
        mVideoView.setVideoURI(Uri.parse("http://58.135.196.138:8090/live/db3bd108e3364bf3888ccaf8377af077/index.m3u8"));
        mVideoView.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.stopPlayback();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video;
    }

}

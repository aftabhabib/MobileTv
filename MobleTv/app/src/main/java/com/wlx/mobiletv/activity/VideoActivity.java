package com.wlx.mobiletv.activity;

import android.content.pm.ActivityInfo;
import android.net.Uri;
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
    public void init() {
        AndroidMediaController mMediaController = new AndroidMediaController(this, false);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setHudView(mHudView);
        mVideoView.setVideoURI(Uri.parse("http://hls.yun.gehua.net.cn:8088/live/HeiLongJiangHD_1200.m3u8?a=1&provider_id=gehua&assetID=92429&ET=1483026617127&TOKEN=c9119aa61e12034a5dfc2dff45dc72ae"));
        mVideoView.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.stopPlayback();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_video;
    }

}

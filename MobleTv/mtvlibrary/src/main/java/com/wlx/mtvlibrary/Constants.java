package com.wlx.mtvlibrary;

import android.os.Environment;

import java.io.File;

/**
 * 作者：LucianWang
 * 日期：2017/2/6
 * 邮箱：wlx3079@163.com
 * 描述：
 */

public class Constants {
    public static final String AES_KEY = "1234r5";
    public static class Config {
        public static final boolean DEVELOPER_MODE = BuildConfig.DEBUG;
    }
    public static String SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String STORAGE_ROOT = SDCARD + File.separator + "手机电视" + File.separator;
    public static final String HTTP_CACHE = STORAGE_ROOT + "cache_http";
    public static final String IMG_CACHE = STORAGE_ROOT + "cache_img";

}

package com.wlx.mtvlibrary.net;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.wlx.mtvlibrary.Constants;
import com.wlx.mtvlibrary.utils.LogUtil;
import com.wlx.mtvlibrary.utils.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 作者：LucianWang
 * 日期：2017/2/6
 * 邮箱：wlx3079@163.com
 * 描述：
 */

class AsyncHttp {
    private static final long DEFAULT_TIMEOUT = 10 * 1000;
    private OkHttpClient okHttpClient;
    private static AsyncHttp asyncHttpClient;
    private Handler mainHandler;

    private AsyncHttp() {
        Cache cache = new Cache(new File(Constants.HTTP_CACHE), 10 * 1024 * 1024L);
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(2 * DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .cache(cache)
                .build();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public static AsyncHttp getInstance() {
        if (asyncHttpClient == null) {
            synchronized (AsyncHttp.class) {
                if (asyncHttpClient == null) {
                    asyncHttpClient = new AsyncHttp();
                }
            }
        }
        return asyncHttpClient;
    }

    public void get(String url, final CallBack callBack) {
        get(url, null, callBack);
    }

    public void get(String url, RequestParam params, final CallBack callBack) {
        if (TextUtils.isEmpty(url)) {
            LogUtil.e(callBack.getClass().getName() + ":the request url is empty");
//            ToastUtils.showToast("错误的地址");
            return;
        }
        if (params != null) {
            StringBuilder builder = new StringBuilder(url);
            if (url.contains("?")) {
                builder.append("&").append(params.buildGet());
            } else {
                builder.append("?").append(params.buildGet());
            }
            url = builder.toString();
        }
        LogUtil.d("HTTP", url);
        final Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack != null) {
                            callBack.onFailure(call, e);
                        }
                    }
                });

            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                final String content = response.body().string();
                final Headers headers = response.headers();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack != null) {
                            callBack.onSuccess(content, headers, response);
                        }
                    }
                });
            }
        });
    }

    public void post(String url, RequestBody requestBody, final CallBack callBack) {
        if (TextUtils.isEmpty(url)) {
            LogUtil.e(callBack.getClass().getName() + ":the request url is empty");
            ToastUtils.showToast("错误的链接地址");
            return;
        }
        LogUtil.d("HTTP", url);
        LogUtil.d("HTTP", requestBody.toString());
        final Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure(call, e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String content = response.body().string();
                final Headers headers = response.headers();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(content, headers, response);
                    }
                });
            }

        });
    }

    public void download(String url, final String dir, final String file, final DownloadListener downloadListener) {
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                saveFile(response, dir, file, downloadListener);
            }


        });
    }

    public interface CallBack {
        void onFailure(Call call, IOException e);

        void onSuccess(String content, Headers headers, Response response);
    }


    public File saveFile(Response response, String destFileDir, String destFileName, final DownloadListener downloadListener) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            final long total = response.body().contentLength();
            long sum = 0;

            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (downloadListener != null) {
                            downloadListener.inProgress(finalSum * 1.0f / total);
                        }
                    }
                });
            }
            fos.flush();

            return file;

        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }

        }
    }

    public interface DownloadListener {
        void inProgress(float progress);
    }

}

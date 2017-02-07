package com.wlx.mtvlibrary.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import readtv.ghs.tv.App;
import readtv.ghs.tv.R;

public class ToastUtils {

    private static Toast mToast;
    private static View toastView;
    private static TextView toastTv;

    public static void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(App.getCurrentApp(), "", Toast.LENGTH_LONG);
            toastView = LayoutInflater.from(App.getCurrentApp()).inflate(R.layout.view_toast, null);
            toastTv = (TextView) toastView.findViewById(R.id.view_toast_tv);

        }
        toastTv.setText(msg);
        mToast.setView(toastView);
        mToast.setGravity(Gravity.BOTTOM, 0, (int) App.getCurrentApp().getResources().getDimension(R.dimen._200));
        mToast.show();

    }

}

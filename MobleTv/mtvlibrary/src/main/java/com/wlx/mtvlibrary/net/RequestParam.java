package com.wlx.mtvlibrary.net;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wlx.mtvlibrary.utils.PasswordUtil;
import com.wlx.mtvlibrary.utils.StringUtil;

import java.lang.reflect.Type;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 作者：LucianWang
 * 日期：2017/2/6
 * 邮箱：wlx3079@163.com
 * 描述：
 */

public class RequestParam {private HashMap<String, Object> params = new HashMap<>();
    private Gson gson = new Gson();
    private String formatedParams;

    public String buildGet() {
        if (StringUtil.isNullOrEmpty(formatedParams)) {
            return formatParams().toString();
        } else {
            return formatedParams;
        }
    }

    public RequestBody buildPost() {
        Type dataType = new TypeToken<HashMap<String, Object>>() {}.getType();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(params, dataType).toString());
        return requestBody;
    }

    public RequestParam addToken() {
        StringBuilder sb = formatParams();
        String token = PasswordUtil.encryptGetParams(sb.toString());
        sb.append("&token=" + token);
        formatedParams = sb.toString();
        params.put("token", token);
        return this;
    }

    @NonNull
    private StringBuilder formatParams() {
        StringBuilder sb = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        return sb;
    }


    public RequestParam addParam(String key, Object value) {
        params.put(key, value);
        return this;
    }
}

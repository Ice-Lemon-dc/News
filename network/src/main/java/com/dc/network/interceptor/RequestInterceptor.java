package com.dc.network.interceptor;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.dc.network.INetworkRequestInfo;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {

    private INetworkRequestInfo mNetworkRequestInfo;

    public RequestInterceptor(INetworkRequestInfo networkRequestInfo) {
        this.mNetworkRequestInfo = networkRequestInfo;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (mNetworkRequestInfo != null) {
            HashMap<String, String> headerMap = mNetworkRequestInfo.getRequestHeaderMap();
            if (headerMap != null) {
                for (String key : headerMap.keySet()) {
                    if (!TextUtils.isEmpty(headerMap.get(key))) {
                        builder.addHeader(key, headerMap.get(key));
                    }
                }
            }
        }
        return chain.proceed(builder.build());
    }
}

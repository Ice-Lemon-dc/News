package com.dc.network.interceptor;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ResponseInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        long startRequestTime = System.currentTimeMillis();
        String url = request.url().toString();
        Response response;
        response = chain.proceed(request);
        String rawJson = response.body() == null ? "" : response.body().string();
        return response.newBuilder().body(ResponseBody.create(response.body().contentType(), rawJson)).build();
    }
}

package com.dc.network.errorhandler;

import com.dc.network.beans.BaseResponse;

import io.reactivex.functions.Function;

public class AppDataErrorHandler implements Function<BaseResponse,BaseResponse> {

    @Override
    public BaseResponse apply(BaseResponse response) throws Exception {
        if (response.showapiResCode != 0) {
            throw new RuntimeException(response.showapiResCode + "" + (response.showapiResError != null ? response.showapiResError : ""));
        }
        return response;
    }
}

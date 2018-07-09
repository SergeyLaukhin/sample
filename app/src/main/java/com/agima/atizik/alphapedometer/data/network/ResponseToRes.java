package com.agima.atizik.alphapedometer.data.network;


import com.agima.atizik.alphapedometer.utils.NetworkAvailableError;
import com.agima.atizik.alphapedometer.utils.NetworkStatusChecker;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by DiNo on 14.03.2018.
 */

public class ResponseToRes<R> implements Observable.Transformer<Response<R>, R> {

    public ResponseToRes() {

    }

    @Override
    public Observable<R> call(Observable<Response<R>> responseObservable) {
        return NetworkStatusChecker.isInternetAvailable()
                .switchMap(aBoolean -> {

                    if (aBoolean) {
                        return responseObservable;
                    } else {
                        return Observable.error(new NetworkAvailableError());
                    }
                })
                .switchMap((Response<R> tResponse) -> {
                    switch (tResponse.code()) {
                        case 200:
                            return Observable.just(tResponse).map(response -> (R) response.body());
                        case 201:
                            return Observable.just(tResponse.body());
                        case 202:
                            return Observable.just(tResponse.body());
                        case 304:
                            return Observable.empty();
                        default:
                            return Observable.empty();/*Observable.error(new ApiError(tResponse.errorBody()))*/
                    }
                });
    }

}

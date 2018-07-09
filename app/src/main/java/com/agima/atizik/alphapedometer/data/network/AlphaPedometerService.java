package com.agima.atizik.alphapedometer.data.network;

import android.text.TextUtils;

import com.agima.atizik.alphapedometer.BuildConfig;

import com.squareup.moshi.Moshi;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by DiNo on 08.03.2018.
 */

public interface AlphaPedometerService {

    @POST("api/v1/user/auth/")
    Observable<Response<Void>> postLogin(String email,String password);


    class Factory {


        public static AlphaPedometerService makeService() {
            return createService(AlphaPedometerService.class);
        }



        public static <S> S createService(
                Class<S> serviceClass) {
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(cookieManager);

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BASIC : HttpLoggingInterceptor.Level.NONE);

            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                  /*  .connectTimeout(Configuration.RESPONSE_WAITING_TIME, TimeUnit.SECONDS)
                    .readTimeout(Configuration.RESPONSE_WAITING_TIME, TimeUnit.SECONDS)*/
                    .addInterceptor(logging)
                    .addInterceptor(new HeaderInterceptor())
                    .addInterceptor(new LoggingInterceptor());
            Retrofit.Builder retrofit = new Retrofit.Builder()
                    .baseUrl("http://test.com/")
                    .addConverterFactory(MoshiConverterFactory.create(createMoshi()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

            retrofit.client(okHttpClient.build());
            return retrofit.build().create(serviceClass);
        }
        static Moshi createMoshi() {
            return new Moshi.Builder()
                    .build();
        }
    }


}

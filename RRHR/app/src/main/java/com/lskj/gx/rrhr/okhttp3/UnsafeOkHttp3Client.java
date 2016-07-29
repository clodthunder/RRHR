package com.lskj.gx.rrhr.okhttp3;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.lskj.gx.rrhr.app.base.MApplication;

import java.io.File;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Home on 16/7/28.
 * The created OkHttpClient disables all SSL certificate checks.
 */
public class UnsafeOkHttp3Client {

    public static OkHttpClient getUnsafeOkHttpClient() {
        //添加日志
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        TrustManagerFactory trustManagerFactory = null;
        try {
            trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            try {
                trustManagerFactory.init((KeyStore) null);
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            SSLContext sslContext = null;
            sslContext = SSLContext.getInstance("TLS");
            try {
                sslContext.init(null, new TrustManager[]{trustManager}, null);
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, trustManager)
                    .protocols(Arrays.asList(Protocol.HTTP_1_1))
                    .connectTimeout(5000, TimeUnit.SECONDS)
                    .writeTimeout(3000, TimeUnit.SECONDS)
                    .readTimeout(3000, TimeUnit.SECONDS)
                    .cache(new Cache(
                            new File(MApplication.getmApplication().getCacheDir() + File.separator + "response")
                            , 1024 * 1024 * 10))
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String s, SSLSession sslSession) {
                            return true;
                        }
                    })
//                    .addInterceptor(provideOfflineCacheInterceptor())
                    .addInterceptor(httpLoggingInterceptor)
                    //添加FaceBook 调试工具
                    .addNetworkInterceptor(new StethoInterceptor())
//                    .addNetworkInterceptor(provideCacheInterceptor())
                    .build();
            return okHttpClient;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.lskj.gx.rrhr.main.interfaces;

import android.widget.Toast;

import com.lskj.gx.rrhr.RxBus.RxBus;
import com.lskj.gx.rrhr.app.base.MApplication;
import com.lskj.gx.rrhr.log.Logger;
import com.lskj.gx.rrhr.main.bean.CResponse;
import com.lskj.gx.rrhr.main.bean.SubjectsBean;
import com.lskj.gx.rrhr.main.event.MovieEvent;
import com.lskj.gx.rrhr.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Home on 16/7/25.
 */
public class IServicesManager {
    private static IServicesManager mIServicesManager;
    private IService iService;
    private static final String CACHE_CONTROL = "Cache-Control";

    public IService getiService() {
        return iService;
    }

    private Retrofit mRetrofit;
    private final String BASE_URL_MOVIE_LIST = "http://api.douban.com/v2/movie/";

            public IServicesManager() {
                mRetrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL_MOVIE_LIST)
                        .client(provideOkHttpClient())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
                iService = mRetrofit.create(IService.class);
            }

        public static IServicesManager getInstance() {
            if (mIServicesManager == null) {
            synchronized (IServicesManager.class) {
                if (mIServicesManager == null) {
                    mIServicesManager = new IServicesManager();
                }
            }
        }
        return mIServicesManager;
    }

    private static OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(provideOfflineCacheInterceptor())
                .addNetworkInterceptor(provideCacheInterceptor())
                .cache(provideCache())
                .build();
    }

    private static Cache provideCache() {
        Cache cache = null;
        try {
            cache = new Cache(new File(MApplication.getmApplication().getCacheDir(), "http-cache"),
                    10 * 1024 * 1024); // 10 MB
        } catch (Exception e) {
            Logger.e(e, "Could not create Cache!");
        }
        return cache;
    }

//    private static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
//        HttpLoggingInterceptor httpLoggingInterceptor =
//                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//                    @Override
//                    public void log(String message) {
//                        Logger.d(message);
//                    }
//                });
//        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.HEADERS : HttpLoggingInterceptor.Level.NONE);
//        return httpLoggingInterceptor;
//    }
//

    /**
     * 获取MovieList
     *
     * @param cpage
     * @param type
     */
    public Subscription getMovieList2(int cpage, MovieEvent.OperateType type) {
        MovieEvent event = new MovieEvent();
        event.setOperateType(type);

        return iService.getMovie(cpage, 20)
                .flatMap(new Func1<CResponse, Observable<SubjectsBean>>() {
                    @Override
                    public Observable<SubjectsBean> call(CResponse cResponse) {
                        return Observable.from(cResponse.getSubjects());
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .onErrorResumeNext(Observable.just(new ArrayList<SubjectsBean>()))
//                .timeout(5000, TimeUnit.SECONDS)
                .subscribe(new Subscriber<List<SubjectsBean>>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(MApplication.getmApplication(), "onComplete!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void setProducer(Producer p) {
                        super.setProducer(p);

                    }

                    @Override
                    public void onNext(List<SubjectsBean> list) {
                        if (list.isEmpty()) {
                            Toast.makeText(MApplication.getmApplication(), "on Error resumeNext", Toast.LENGTH_SHORT).show();
                        }
                        RxBus.getRxInstance().post(new MovieEvent(list, type));
                    }
                });
    }

    public static Interceptor provideCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());

                // re-write response header to force use of cache
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(60, TimeUnit.SECONDS)
                        .build();

                return response.newBuilder()
                        .header(CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }

    public static Interceptor provideOfflineCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                if (!NetworkUtils.isNetworkAvailable(MApplication.getmApplication())) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                }
                return chain.proceed(request);
            }
        };
    }

}

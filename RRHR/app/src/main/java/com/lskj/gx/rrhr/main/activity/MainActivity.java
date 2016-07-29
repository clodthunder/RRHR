package com.lskj.gx.rrhr.main.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lskj.gx.rrhr.R;
import com.lskj.gx.rrhr.RxBus.RxBus;
import com.lskj.gx.rrhr.app.base.BaseActivity;
import com.lskj.gx.rrhr.app.base.MApplication;
import com.lskj.gx.rrhr.main.adapter.MovieAdapter;
import com.lskj.gx.rrhr.main.bean.SubjectsBean;
import com.lskj.gx.rrhr.main.event.MovieEvent;
import com.lskj.gx.rrhr.main.interfaces.IServicesManager;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends BaseActivity {

    private List<SubjectsBean> mSubjects;
    private CompositeSubscription compositeSubscription;
    private MovieAdapter mMovieAdapter;
    private int currentPage = 0;
    private int pageCount = 20;
    @BindView(R.id.xrv_get_rxbus_data)
    XRecyclerView mRecyclerView;
    @BindView(R.id.root_main_view)
    LinearLayout rootView;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        compositeSubscription = new CompositeSubscription();
        View header = LayoutInflater.from(this).inflate(R.layout.recyclerview_header, (ViewGroup) findViewById(android.R.id.content), false);
        mRecyclerView.addHeaderView(header);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotateMultiple);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }
        });
        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                currentPage = 0;
                IServicesManager.getInstance().getMovieList2(currentPage, MovieEvent.OperateType.Refresh);
            }

            @Override
            public void onLoadMore() {
                currentPage++;
                new IServicesManager().getMovieList2(currentPage * pageCount, MovieEvent.OperateType.LoadMore);
            }
        });
        mRecyclerView.setAdapter(mMovieAdapter);
        compositeSubscription.add((Subscription) RxBus.getRxInstance()
                .toObserverable(MovieEvent.class)
                .subscribe(new Action1<MovieEvent>() {
                    @Override
                    public void call(MovieEvent movieEvents) {
                        List<SubjectsBean> beanList = movieEvents.getSubjectsBeanList();
                        MovieEvent.OperateType type = movieEvents.getOperateType();
                        if (beanList.isEmpty()) {
                            Toast.makeText(MainActivity.this, "收到 empty list", Toast.LENGTH_SHORT).show();
                            if (type.equals(MovieEvent.OperateType.Refresh)) {
                                mRecyclerView.refreshComplete();
                            } else {
                                mRecyclerView.loadMoreComplete();
                            }
                            mMovieAdapter.setData(Collections.EMPTY_LIST);
                            mRecyclerView.setEmptyView(LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_empty_view, null));
                            return;
                        }
                        if (movieEvents.getOperateType().equals(MovieEvent.OperateType.LoadMore)) {
                            mRecyclerView.loadMoreComplete();
                            //pageCount 是每页显示的数据量
                            if (beanList.size() < pageCount) {
                                mRecyclerView.setIsnomore(true);
                            }
                            mMovieAdapter.addData(beanList);
                        } else {
                            mRecyclerView.refreshComplete();
                            if (mSubjects != null) {
                                mSubjects.clear();
                            }
                            mSubjects = beanList;
                        }
                        beanList = null;
                        mMovieAdapter.setData(mSubjects);
                    }
                }));
        initGlideQuery();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeSubscription != null && !compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }

    @OnClick({R.id.btn_get_movie, R.id.btn_get_filter_data, R.id.btn_get_rxbus_data, R.id.btn_show_cache_activity})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_movie:
                new IServicesManager().getMovieList2(2 * 20, MovieEvent.OperateType.LoadMore);
                break;
            case R.id.btn_get_filter_data:
                initGlideQuery();
                break;
            case R.id.btn_get_rxbus_data:
                new IServicesManager().getMovieList2(currentPage, MovieEvent.OperateType.Refresh);
                break;
            case R.id.btn_show_cache_activity:
                Intent intent = new Intent(MainActivity.this, MyCacheActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * getPermission in app
     */
    private void initGlideQuery() {
        //核实app权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            //初始化
            Glide.get(MApplication.getmApplication());
            IServicesManager.getInstance().getMovieList2(0, MovieEvent.OperateType.Refresh);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            //write
            case 1:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Glide.get(MApplication.getmApplication());
                    new IServicesManager().getMovieList2(0, MovieEvent.OperateType.Refresh);
                }
                break;
        }
    }
}

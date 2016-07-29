package com.lskj.gx.rrhr.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lskj.gx.rrhr.R;
import com.lskj.gx.rrhr.app.base.BaseActivity;
import com.lskj.gx.rrhr.app.base.MApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Scheduler;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Home on 16/7/27.
 */
public class MyCacheActivity extends BaseActivity {
    @BindView(R.id.tv_cache_glide_size)
    TextView mGlideSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_cache_back, R.id.btn_cache_show_sp, R.id.btn_cache_show_glide, R.id.btn_cache_show_response})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cache_back:
                this.finish();
                break;
            case R.id.btn_cache_show_sp:

                break;
            case R.id.btn_cache_show_glide:
                Scheduler.Worker worker = Schedulers.io().createWorker();
                worker.schedule(new Action0() {
                    @Override
                    public void call() {
                        Glide.get(MApplication.getmApplication()).clearDiskCache();
                        worker.unsubscribe();
                    }
                });
                break;
            case R.id.btn_cache_show_response:

                break;

        }
    }
}

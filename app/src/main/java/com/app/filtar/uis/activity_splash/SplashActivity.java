package com.app.filtar.uis.activity_splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;


import com.app.filtar.R;
import com.app.filtar.databinding.ActivitySplashBinding;
import com.app.filtar.uis.activity_base.BaseActivity;
import com.app.filtar.uis.activity_home.HomeActivity;
import com.app.filtar.uis.activity_intro_slider.IntroSliderActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {
    private ActivitySplashBinding binding;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        initView();

    }

    private void initView() {
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if ( result.getResultCode() == RESULT_OK) {
              navigateToHomeActivity();
            }
        });
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        if (getUserSettings()==null||getUserSettings().isFirstTime()) {
                            navigateToIntroSliderActivity();

                        }else {
                            if (getUserModel() == null) {
                                navigateToHomeActivity();
                            } else {
                                if (getUserModel().getData().getStore_name() == null) {
                                    navigateToHomeActivity();
                                } else {

                                }
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void navigateToIntroSliderActivity() {

        Intent intent = new Intent(this, IntroSliderActivity.class);
        launcher.launch(intent);
    }


    private void navigateToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}


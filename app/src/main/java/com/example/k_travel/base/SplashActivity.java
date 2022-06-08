package com.example.k_travel.base;

import android.content.Intent;
import android.os.Handler;

import com.example.k_travel.MainActivity;
import com.example.k_travel.R;
import com.example.k_travel.databinding.ActivitySplashBinding;
import com.example.k_travel.utils.AppLoginUtil;

import androidx.databinding.DataBindingUtil;


public class SplashActivity extends BaseActivity {

    private ActivitySplashBinding binding;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
    }

    @Override
    protected void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toHome();
                finish();
            }
        }, 2000);
    }

    @Override
    protected void initData() {

    }

    private void toHome() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
    }
}

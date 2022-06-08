package com.example.k_travel.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.example.k_travel.MyApp;
import com.example.k_travel.been.User;
import com.example.k_travel.utils.ToastUtil;

import androidx.fragment.app.FragmentActivity;

public abstract class BaseActivity extends FragmentActivity {

    protected final String TAG = this.getClass().getSimpleName();
    private boolean isShowTitle = true;
    private boolean isShowStatusBar = true;
    private boolean isAllowScreenRoate = true;

    protected Context mContext;
    protected Bundle mBundle;

    public User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = BaseActivity.this;
        mBundle = getIntent().getExtras();
        if (!isShowTitle) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if (!isShowStatusBar) {
            getWindow().setFlags(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        }
        initBinding();

        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            if (!isAllowScreenRoate) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
        mUser = MyApp.mUserLogin;
        initView();
        initData();
    }

    protected abstract void initBinding();

    protected abstract void initView();

    protected abstract void initData();


    protected void showToast(String msg) {
        ToastUtil.showShort(this, msg);
    }

    protected void showToast(int msg) {
        ToastUtil.showShort(this, msg);
    }


    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (view != null && null != imm) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.showSoftInput(view, 0);
        }
    }

}

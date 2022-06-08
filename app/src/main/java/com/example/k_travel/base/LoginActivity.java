package com.example.k_travel.base;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.k_travel.MainActivity;
import com.example.k_travel.MyApp;
import com.example.k_travel.R;
import com.example.k_travel.been.User;
import com.example.k_travel.databinding.ActivityLoginBinding;
import com.example.k_travel.db.DBServer;
import com.example.k_travel.utils.SPUtil;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ActivityLoginBinding binding;
    private boolean seePwd = false;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }

    @Override
    protected void initView() {
        binding.btnLogin.setOnClickListener(this);
        binding.btnRegister.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        SPUtil.clear(MyApp.getInstance());
        MyApp.mUserLogin = null;
        Log.e("lsh", "clear cache");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String phone = binding.etPhone.getText().toString().trim();
                String pwd = binding.etPwd.getText().toString().trim();
                if (!TextUtils.isEmpty(binding.etPhone.getText().toString())) {
                    if (TextUtils.isEmpty(binding.etPwd.getText().toString())) {
                        showToast("password null");
                        return;
                    }
                    login(phone, pwd);
                } else {
                    showToast("phone null");
                    return;
                }
                break;
            case R.id.btn_register:
                startActivity(new Intent(LoginActivity.this, RegisterSetPwdActivity.class));
                break;
        }
    }

    private void login(String phone, String pwd) {
        User user = DBServer.searchUserByPhone(LoginActivity.this, phone);
        if (null == user) {
            showToast("no login");
        } else {
            if (user.pwd.equals(pwd)) {
                MyApp.mUserLogin = user;
                showToast("login success");
                SPUtil.put(LoginActivity.this, SPUtil.USER_ID, MyApp.mUserLogin.getId());
                SPUtil.put(LoginActivity.this, SPUtil.HEADER, MyApp.mUserLogin.getHeader() + "");
                SPUtil.put(LoginActivity.this, SPUtil.MOBILE, MyApp.mUserLogin.getPhone() + "");
                SPUtil.put(LoginActivity.this, SPUtil.PASSWORD, MyApp.mUserLogin.getPwd() + "");
                SPUtil.put(LoginActivity.this, SPUtil.GENDER, MyApp.mUserLogin.getGender() + "");
                SPUtil.put(LoginActivity.this, SPUtil.HOBBY, MyApp.mUserLogin.getHobby() + "");
                SPUtil.put(LoginActivity.this, SPUtil.IS_LOGIN, true);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("phone", phone);
                startActivity(intent);
                finish();
            } else {
                showToast("password error");
            }
        }
    }

}

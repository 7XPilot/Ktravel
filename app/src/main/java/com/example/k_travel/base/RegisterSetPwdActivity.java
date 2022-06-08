package com.example.k_travel.base;

import android.text.TextUtils;
import android.view.View;

import com.example.k_travel.R;
import com.example.k_travel.been.User;
import com.example.k_travel.databinding.ActivityRegisterSetpwdBinding;
import com.example.k_travel.db.DBServer;
import com.example.k_travel.model.RegisterModel;

import androidx.databinding.DataBindingUtil;

public class RegisterSetPwdActivity extends BaseActivity implements View.OnClickListener {

    private ActivityRegisterSetpwdBinding binding;
    private RegisterModel registerModel;
    private boolean seePwd = false;
    private boolean seeConfirmPwd = false;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_setpwd);
    }

    @Override
    protected void initView() {
        binding.btnConfirm.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        registerModel = new RegisterModel();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                String phone = binding.etPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(binding.etPhone.getText().toString().trim())) {
                    registerModel.setPhone(phone);
                } else {
                    showToast("phone null");
                    return;
                }
                String pwd = binding.etPwd.getText().toString().trim();
                String confirmPwd = binding.etConfirmPwd.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)) {
                    showToast("password null");
                    return;
                } else if (!pwd.equals(confirmPwd)) {
                    showToast("Please check your password, it is not the same as your password.");
                    return;
                }
                registerModel.setPassword(pwd);
                checkPhone();
                break;
        }
    }


    private void checkPhone() {
        User user = DBServer.searchUserByPhone(RegisterSetPwdActivity.this, binding.etPhone.getText().toString().trim());
        if (null == user) {
            user = new User();
            user.phone = binding.etPhone.getText().toString().trim();
            user.pwd = binding.etPwd.getText().toString().trim();
            DBServer.addUser(RegisterSetPwdActivity.this, user);
            showToast("Sign success！");
            finish();
        } else {
            showToast("The account has been registered, please log in directly！");
            finish();
        }
    }
}

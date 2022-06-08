package com.example.k_travel.base;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.example.k_travel.R;
import com.example.k_travel.databinding.ActivityResetPwdBinding;
import com.example.k_travel.db.DBServer;

import androidx.databinding.DataBindingUtil;

public class ResetPwdActivity extends BaseActivity implements View.OnClickListener {

    private ActivityResetPwdBinding binding;
    private String phone;
    private boolean seePwd = false;
    private boolean seeConfirmPwd = false;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_pwd);
    }

    @Override
    protected void initView() {
        binding.btnConfirm.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        phone = getIntent().getStringExtra("phone");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                String pwd = binding.etPwd.getText().toString().trim();
                String confirmPwd = binding.etConfirmPwd.getText().toString().trim();
                if (pwd.length() < 6) {
                    showToast("Please set a password of no less than 6 digits");
                    return;
                } else if (!pwd.equals(confirmPwd)) {
                    showToast("Confirm that the password is inconsistent with the password, please check carefully");
                    return;
                }
                resetPassword(pwd);
                break;
        }
    }

    /**
     *
     */
    private void resetPassword(String pwd) {
        mUser.setPwd(pwd);
        DBServer.updateUser(ResetPwdActivity.this, mUser);
        showToast("Modified successfully");
        onBackPressed();
    }
}

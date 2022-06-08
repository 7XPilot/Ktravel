package com.example.k_travel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.example.k_travel.base.BaseActivity;
import com.example.k_travel.been.Scence;
import com.example.k_travel.databinding.ActivitySendEmailBinding;

public class SendEmailActivity extends BaseActivity {

    private ActivitySendEmailBinding binding;
    private String toMail;

    public static void startActivity(Context context, String toMail) {
        Intent intent = new Intent(context, SendEmailActivity.class);
        intent.putExtra("toMail", toMail);
        context.startActivity(intent);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_send_email);
    }

    @Override
    protected void initView() {

        binding.etSubject.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String et_info = binding.etSubject.getText().toString();
                if (TextUtils.isEmpty(et_info) && !hasFocus) {
                    showToast("subject null");
                }
            }
        });


        binding.etContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String et_info = binding.etContent.getText().toString();
                if (TextUtils.isEmpty(et_info) && !hasFocus) {
                    showToast("content null");
                }
            }
        });


        binding.tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.etSubject.getText().toString().trim())) {
                    Toast.makeText(SendEmailActivity.this, "subject null", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(binding.etContent.getText().toString().trim())) {
                    showToast("content null");
                    return;
                }
                Uri uri = Uri.parse("mailto:" + toMail);
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra(Intent.EXTRA_SUBJECT, binding.etSubject.getText().toString().trim());
                intent.putExtra(Intent.EXTRA_TEXT, binding.etContent.getText().toString().trim());
                startActivity(Intent.createChooser(intent, "choose app"));

            }
        });
    }

    @Override
    protected void initData() {
        toMail = getIntent().getStringExtra("toMail");
        binding.tvToMail.setText("To：" + toMail);
    }
}
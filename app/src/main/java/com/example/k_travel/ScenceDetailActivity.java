package com.example.k_travel;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.DatePicker;

import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.k_travel.base.BaseActivity;
import com.example.k_travel.base.LoginActivity;
import com.example.k_travel.been.Booking;
import com.example.k_travel.been.Scence;
import com.example.k_travel.databinding.ActivityScenceDetailBinding;
import com.example.k_travel.db.DBServer;
import com.example.k_travel.utils.AppLoginUtil;

import java.util.Calendar;

public class ScenceDetailActivity extends BaseActivity {

    private ActivityScenceDetailBinding binding;
    private Scence mScence;

    public static void startActivity(Context context, Scence scence) {
        Intent intent = new Intent(context, ScenceDetailActivity.class);
        intent.putExtra("scence", scence);
        context.startActivity(intent);
    }


    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scence_detail);
    }

    @Override
    protected void initView() {
        binding.titlebar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhone(mScence.getPhone());
            }
        });

        binding.tvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppLoginUtil.hasLogin()) {
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog dialog = new DatePickerDialog(ScenceDetailActivity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    System.out.println("Year-->" + year + "Month-->"
                                            + monthOfYear + "day-->" + dayOfMonth);
                                    Booking booking = new Booking();
                                    booking.setScenceId(mScence.getId());
                                    booking.setUserId(MyApp.mUserLogin.getId());
                                    booking.setDate(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                    int result = DBServer.addBooking(ScenceDetailActivity.this, booking);
                                    if (result > 0) {
                                        showToast("booking success");
                                    }
                                }
                            }, calendar.get(Calendar.YEAR), calendar
                            .get(Calendar.MONTH), calendar
                            .get(Calendar.DAY_OF_MONTH));
                    dialog.setTitle("choose date");
                    dialog.show();
                } else {
                    startActivity(new Intent(ScenceDetailActivity.this, LoginActivity.class));
                }
            }
        });

        binding.tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendEmailActivity.startActivity(ScenceDetailActivity.this, mScence.getEmail());
            }
        });
    }

    @Override
    protected void initData() {
        mScence = (Scence) getIntent().getSerializableExtra("scence");
        Glide.with(mContext).load(mScence.getUrl()).into(binding.ivHeader);
        binding.tvName.setText(mScence.getName());
        binding.tvIntrod.setText(mScence.getIntrod());
        binding.tvEval.setText(mScence.getEval());
        binding.tvPrice.setText("$" + mScence.getPrice());
    }


    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
}
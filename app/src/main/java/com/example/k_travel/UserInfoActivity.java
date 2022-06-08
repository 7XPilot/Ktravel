package com.example.k_travel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.k_travel.adapter.HobbyAdapter;
import com.example.k_travel.base.BaseActivity;
import com.example.k_travel.base.LoginActivity;
import com.example.k_travel.been.Scence;
import com.example.k_travel.databinding.ActivitySendEmailBinding;
import com.example.k_travel.databinding.ActivityUserInfoBinding;
import com.example.k_travel.db.DBServer;
import com.example.k_travel.model.Hobby;
import com.example.k_travel.utils.AppLoginUtil;
import com.example.k_travel.utils.SPUtil;

import java.util.ArrayList;

public class UserInfoActivity extends BaseActivity {


    private ActivityUserInfoBinding binding;
    private ArrayList<Hobby> hobbies = new ArrayList<>();
    private HobbyAdapter mAdapter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info);
    }

    @Override
    protected void initView() {
        mAdapter = new HobbyAdapter(UserInfoActivity.this, hobbies);
        mAdapter.setOnItemClickListener(new HobbyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(HobbyAdapter adapter, int position, boolean b) {
                hobbies.get(position).setChcek(b);
            }
        });
        binding.rcvList.setLayoutManager(new GridLayoutManager(UserInfoActivity.this, 2));
        binding.rcvList.setAdapter(mAdapter);

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hobby = "";
                for (Hobby h : hobbies) {
                    if (h.isChcek()) {
                        hobby = hobby + "_" + h.getName();
                    }
                }
                mUser.setHobby(hobby);
                if (binding.radioMan.isChecked()) {
                    mUser.setGender("1");
                }
                if (binding.radioWoman.isChecked()) {
                    mUser.setGender("0");
                }
                DBServer.updateUser(UserInfoActivity.this, mUser);
                SPUtil.put(UserInfoActivity.this, SPUtil.GENDER, mUser.getGender() + "");
                SPUtil.put(UserInfoActivity.this, SPUtil.HOBBY, mUser.getHobby() + "");
                MyApp.mUserLogin = AppLoginUtil.createLoginModel();
                showToast("save success");
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        hobbies.clear();
        hobbies.add(new Hobby("Swimming"));
        hobbies.add(new Hobby("Running"));
        hobbies.add(new Hobby("Basketball"));
        hobbies.add(new Hobby("Shopping"));
        hobbies.add(new Hobby("Reading"));

        String myHobby = mUser.getHobby();
        for (Hobby h : hobbies) {
            if (myHobby.contains(h.getName())) {
                h.setChcek(true);
            } else {
                h.setChcek(false);
            }
        }

        mAdapter.notifyDataSetChanged();

        if (!TextUtils.isEmpty(mUser.getGender()) && mUser.getGender().equals("1")) {
            binding.radioMan.setChecked(true);
        }

        if (!TextUtils.isEmpty(mUser.getGender()) && mUser.getGender().equals("0")) {
            binding.radioWoman.setChecked(true);
        }
    }
}
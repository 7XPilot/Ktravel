package com.example.k_travel.fragments;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.k_travel.MyApp;
import com.example.k_travel.R;
import com.example.k_travel.ScenceDetailActivity;
import com.example.k_travel.WebViewActivity;
import com.example.k_travel.adapter.ScenceAdapter;
import com.example.k_travel.base.BaseFragment;
import com.example.k_travel.been.Scence;
import com.example.k_travel.been.User;
import com.example.k_travel.databinding.FragmentOneBinding;
import com.example.k_travel.db.DBServer;
import com.example.k_travel.model.BannerModel;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends BaseFragment implements View.OnClickListener {

    private FragmentOneBinding mBinding;

    private ArrayList<Scence> scences = new ArrayList<>();
    private ArrayList<Scence> allScences = new ArrayList<>();
    private ScenceAdapter mAdapter;

    //banner
    private ArrayList<BannerModel> bannerModels = new ArrayList<>();


    public OneFragment() {
        // Required empty public constructor
    }


    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_one, container, false);
        return mBinding.getRoot();
    }

    @Override
    protected void init() {

        mAdapter = new ScenceAdapter(getActivity(), scences);
        mAdapter.setOnItemClickListener(new ScenceAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(ScenceAdapter adapter, int position) {
                ScenceDetailActivity.startActivity(getActivity(), scences.get(position));
            }

            @Override
            public void onItemLongClicked(ScenceAdapter adapter, int position) {

            }
        });

        mBinding.rcvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.rcvList.setAdapter(mAdapter);

        scences.clear();
        scences.addAll(DBServer.searchAllScence(getActivity()));
        allScences.addAll(DBServer.searchAllScence(getActivity()));
        mAdapter.notifyDataSetChanged();

        mBinding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(mBinding.etSearch.getText().toString().trim())) {
                        showToast("search key null");
                    } else {
                        scences.clear();
                        String key = mBinding.etSearch.getText().toString().trim();
                        for (Scence scence : allScences) {
                            if (scence.getName().toLowerCase().contains(key.toLowerCase())) {
                                scences.add(scence);
                            }
                        }
                        if (scences.size() == 0) {
                            showToast("no sence");
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                    return true;
                }
                return false;
            }
        });

        mBinding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(mBinding.etSearch.getText().toString().trim())) {
                    scences.clear();
                    scences.addAll(allScences);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });


        bannerModels.add(new BannerModel("https://img0.baidu.com/it/u=100141830,3331692399&fm=253&fmt=auto&app=138&f=JPEG?w=550&h=369", "https://www.newzealand.com/nz/"));
        bannerModels.add(new BannerModel("https://img1.baidu.com/it/u=2660978924,1631760196&fm=253&fmt=auto&app=138&f=JPEG?w=679&h=453", "https://www.waikatonz.com"));
        bannerModels.add(new BannerModel("https://img2.baidu.com/it/u=4197305399,3067495278&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=500", "https://www.australia.com/en-nz"));
        bannerModels.add(new BannerModel("https://img2.baidu.com/it/u=3257327809,4198792059&fm=253&fmt=auto&app=138&f=JPEG?w=894&h=500", "https://www.airbnb.co.nz"));
        initBanner();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    //初始化banner
    private void initBanner() {
        mBinding.banner.setAdapter(new BannerImageAdapter<BannerModel>(bannerModels) {
            @Override
            public void onBindView(BannerImageHolder holder, BannerModel data, int position, int size) {
                //图片加载自己实现
                holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(holder.itemView)
                        .load(data.getImgUrl())
                        .apply(new RequestOptions()
                                .transforms(new RoundedCorners(16)
                                ))
                        .into(holder.imageView);
            }
        })
                .setCurrentItem(0)
                .addBannerLifecycleObserver(getActivity())//添加生命周期观察者
                .setIndicator(new CircleIndicator(getActivity()), false)//设置指示器
                //.setBannerGalleryEffect(15, 15)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(Object data, int position) {
                        WebViewActivity.startActivity(getActivity(), bannerModels.get(position).getUrl());
                    }
                });
    }
}

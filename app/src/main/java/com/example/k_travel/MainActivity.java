package com.example.k_travel;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.k_travel.base.BaseActivity;
import com.example.k_travel.databinding.ActivityMainBinding;
import com.example.k_travel.fragments.MineFragment;
import com.example.k_travel.fragments.OneFragment;
import com.example.k_travel.fragments.TwoFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;

    List<String> mPermissionList = new ArrayList<>();
    private final int mRequestCode = 100;//权限请求码


    public static boolean isForeground = false;


    OneFragment mOneFragment;
    TwoFragment mTwoFragment;
    MineFragment mMineFragment;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void initView() {
        getPermissions();
    }

    private void initViewPager() {
        // init fragment
        mFragments = new ArrayList<>(3);
        mOneFragment = new OneFragment();
        mTwoFragment = new TwoFragment();
        mMineFragment = new MineFragment();
        mFragments.add(mOneFragment);
        mFragments.add(mTwoFragment);
        mFragments.add(mMineFragment);
        // init view pager
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        binding.fragmentVp.setAdapter(mAdapter);
        // register listener
        binding.fragmentVp.setOffscreenPageLimit(3);
        binding.fragmentVp.addOnPageChangeListener(mPageChangeListener);
        binding.tabGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    @Override
    protected void initData() {
        initViewPager();
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }




    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.fragmentVp.removeOnPageChangeListener(mPageChangeListener);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        setClinic(intent.getIntExtra("index", 0));
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            RadioButton radioButton = (RadioButton) binding.tabGroup.getChildAt(position);
            radioButton.setChecked(true);
            initMenuTextColor(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            for (int i = 0; i < group.getChildCount(); i++) {
                if (group.getChildAt(i).getId() == checkedId) {
                    binding.fragmentVp.setCurrentItem(i);
                    initMenuTextColor(i);
                    return;
                }
            }
        }
    };

    public void setClinic(int index) {
        binding.fragmentVp.setCurrentItem(index);
    }


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.mList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return this.mList == null ? null : this.mList.get(position);
        }

        @Override
        public int getCount() {
            return this.mList == null ? 0 : this.mList.size();
        }
    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "Press once more to close app", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        if(null != mMineFragment){
//            mMineFragment.initUserCenter();
//        }
    }

    private void initMenuTextColor(int position) {
        switch (position) {
            case 0:
                binding.oneTab.setTextColor(getResources().getColor(R.color.theme_color));
                binding.twoTab.setTextColor(getResources().getColor(R.color.txt_color_3));
                binding.threeTab.setTextColor(getResources().getColor(R.color.txt_color_3));
                break;
            case 1:
                binding.oneTab.setTextColor(getResources().getColor(R.color.txt_color_3));
                binding.twoTab.setTextColor(getResources().getColor(R.color.theme_color));
                binding.threeTab.setTextColor(getResources().getColor(R.color.txt_color_3));
                break;
            case 2:
                binding.oneTab.setTextColor(getResources().getColor(R.color.txt_color_3));
                binding.twoTab.setTextColor(getResources().getColor(R.color.txt_color_3));
                binding.threeTab.setTextColor(getResources().getColor(R.color.theme_color));
                break;
        }
    }


    /**
     * 6.0android
     */
    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECORD_AUDIO};
            mPermissionList.clear();
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);
                }
            }
            if (mPermissionList.size() > 0) {
                ActivityCompat.requestPermissions(this, permissions, mRequestCode);
            } else {

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (mRequestCode == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }
            if (hasPermissionDismiss) {

            } else {

            }
        }
    }

}

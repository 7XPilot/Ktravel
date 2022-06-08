package com.example.k_travel.fragments;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.k_travel.MyApp;
import com.example.k_travel.R;
import com.example.k_travel.UserInfoActivity;
import com.example.k_travel.base.BaseFragment;
import com.example.k_travel.base.LoginActivity;
import com.example.k_travel.base.ResetPwdActivity;
import com.example.k_travel.been.User;
import com.example.k_travel.databinding.FragmentMineBinding;
import com.example.k_travel.db.DBServer;
import com.example.k_travel.utils.AppLoginUtil;
import com.example.k_travel.utils.SPUtil;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private FragmentMineBinding mBinding;

    private String mPwd;

    private static final int CHOOSE_PHOTO = 1001;

    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mine, container, false);
        return mBinding.getRoot();
    }

    @Override
    protected void init() {
        mBinding.llyExit.setOnClickListener(this);
        mBinding.ivHeader.setOnClickListener(this);
        mBinding.llyUser.setOnClickListener(this);
        mBinding.llyUpdatePwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lly_exit:
                new AlertDialog.Builder(getActivity())
                        .setTitle("")
                        .setMessage("confirm exit？")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SPUtil.clear(getActivity());
                                MyApp.mUserLogin = null;
                                initUserCenter();
                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
                break;
            case R.id.iv_header:
                if (AppLoginUtil.hasLogin()) {
                    checkPermission();
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.lly_user:
                if (!AppLoginUtil.hasLogin()) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    UserInfoActivity.startActivity(getActivity());
                }
                break;
            case R.id.lly_update_pwd:
                if (!AppLoginUtil.hasLogin()) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), ResetPwdActivity.class));
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initUserCenter();
    }

    public void initUserCenter() {
        if (AppLoginUtil.hasLogin()) {
            mBinding.tvPhone.setVisibility(View.VISIBLE);
            mBinding.tvLogin.setVisibility(View.GONE);
            User u = MyApp.mUserLogin;
            if (null == MyApp.mUserLogin) {
                showToast("Data errors");
                return;
            }
            if (null != u.getPhone() && u.getPhone().length() == 11) {
                mBinding.tvPhone.setText("" + u.getPhone().substring(0, 3) + "****" + u.getPhone().substring(7, 11));
            } else {
                mBinding.tvPhone.setText("" + u.getPhone().substring(0, 2) + "****" + u.getPhone().substring(u.getPhone().length()-2, u.getPhone().length()));
            }
            mBinding.tvPhone.setVisibility(View.VISIBLE);
            mPwd = SPUtil.get(getActivity(), SPUtil.LOCK, "");
            if (!TextUtils.isEmpty(u.getHeader())) {
                Bitmap bitImage = BitmapFactory.decodeFile(u.getHeader());
                mBinding.ivHeader.setImageBitmap(bitImage);
            }
            mBinding.llyUpdatePwd.setVisibility(View.VISIBLE);
        } else {
            mBinding.tvPhone.setVisibility(View.GONE);
            mBinding.tvLogin.setVisibility(View.VISIBLE);
            mBinding.ivHeader.setImageResource(R.mipmap.ic_launcher);
            mBinding.llyUpdatePwd.setVisibility(View.GONE);
        }
    }


    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }

    public void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);//弹出授权的窗口，这条语句也可以删除，没有影响
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(getActivity(), "Authorisation failed, unable to operate", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnkitKat(data);//高于4.4版本使用此方法处理图片
                    } else {
                        handleImageBeforeKitKat(data);//低于4.4版本使用此方法处理图片
                    }
                }
                break;
            default:
                break;
        }
    }


    @TargetApi(19)
    private void handleImageOnkitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(getActivity(), uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    public String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null); //内容提供器
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)); //获取路径
            }
        }
        cursor.close();
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            MyApp.mUserLogin.header = imagePath;
            DBServer.updateUser(getActivity(), MyApp.mUserLogin);
            SPUtil.put(getActivity(), SPUtil.HEADER, MyApp.mUserLogin.getHeader() + "");
            Bitmap bitImage = BitmapFactory.decodeFile(imagePath);
            mBinding.ivHeader.setImageBitmap(bitImage);
        } else {
            Toast.makeText(getActivity(), "Failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
}

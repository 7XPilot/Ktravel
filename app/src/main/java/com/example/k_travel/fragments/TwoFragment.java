package com.example.k_travel.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.k_travel.MyApp;
import com.example.k_travel.R;
import com.example.k_travel.adapter.BookingAdapter;
import com.example.k_travel.base.BaseFragment;
import com.example.k_travel.base.LoginActivity;
import com.example.k_travel.been.Booking;
import com.example.k_travel.databinding.FragmentTwoBinding;
import com.example.k_travel.db.DBServer;
import com.example.k_travel.utils.AppLoginUtil;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TwoFragment extends BaseFragment {

    private FragmentTwoBinding mBinding;

    private ArrayList<Booking> bookings = new ArrayList<>();
    private BookingAdapter mAdapter;

    public TwoFragment() {
        // Required empty public constructor
    }


    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_two, container, false);
        return mBinding.getRoot();
    }

    @Override
    protected void init() {
        mAdapter = new BookingAdapter(getActivity(), bookings);
        mAdapter.setOnItemClickListener(new BookingAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(BookingAdapter adapter, int position) {

            }

            @Override
            public void onItemLongClicked(BookingAdapter adapter, int position) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("")
                        .setMessage("confirm delete？")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DBServer.deleteBooking(getActivity(), bookings.get(position));
                                bookings.clear();
                                bookings.addAll(DBServer.searchBookingByUser(getActivity(), MyApp.mUserLogin.getId()));
                                mAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });
        mBinding.rcvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.rcvList.setAdapter(mAdapter);
        if (AppLoginUtil.hasLogin()) {
            bookings.clear();
            bookings.addAll(DBServer.searchBookingByUser(getActivity(), MyApp.mUserLogin.getId()));
            mAdapter.notifyDataSetChanged();
        }
        if (bookings.size() > 0) {
            mBinding.rcvList.setVisibility(View.VISIBLE);
            mBinding.llyNone.setVisibility(View.GONE);
        } else {
            mBinding.rcvList.setVisibility(View.GONE);
            mBinding.llyNone.setVisibility(View.VISIBLE);
        }
        mBinding.swipeRefresh.setColorSchemeResources(R.color.theme_color);
        mBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (AppLoginUtil.hasLogin()) {
                    bookings.clear();
                    bookings.addAll(DBServer.searchBookingByUser(getActivity(), MyApp.mUserLogin.getId()));
                    mAdapter.notifyDataSetChanged();
                    if (bookings.size() > 0) {
                        mBinding.rcvList.setVisibility(View.VISIBLE);
                        mBinding.llyNone.setVisibility(View.GONE);
                    } else {
                        mBinding.rcvList.setVisibility(View.GONE);
                        mBinding.llyNone.setVisibility(View.VISIBLE);
                    }
                    mBinding.swipeRefresh.setRefreshing(false);
                } else {
                    mBinding.swipeRefresh.setRefreshing(false);
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
    }

}

package com.example.k_travel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.k_travel.R;
import com.example.k_travel.been.Booking;
import com.example.k_travel.been.Scence;
import com.example.k_travel.databinding.ItemBookingBinding;
import com.example.k_travel.databinding.ItemHobbyBinding;
import com.example.k_travel.db.DBServer;
import com.example.k_travel.model.Hobby;

import java.util.ArrayList;

/**
 *
 */

public class HobbyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Hobby> mDatas;
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener listener;

    public HobbyAdapter(Context context, ArrayList<Hobby> datas) {
        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemHobbyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_hobby, parent, false);
        return new HobbyAdapter.HobbyHolder(binding);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Hobby hobby = mDatas.get(position);
        HobbyHolder vh = (HobbyHolder) holder;
        vh.binding.checkbox.setText(hobby.getName());
        vh.binding.checkbox.setChecked(hobby.isChcek());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class HobbyHolder extends RecyclerView.ViewHolder {

        ItemHobbyBinding binding;

        private HobbyHolder(ItemHobbyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    listener.onItemClicked(HobbyAdapter.this, getAdapterPosition(), b);
                }
            });
        }
    }


    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClicked(HobbyAdapter adapter, int position, boolean b);
    }
}

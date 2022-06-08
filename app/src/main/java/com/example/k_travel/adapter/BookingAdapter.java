package com.example.k_travel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.k_travel.R;
import com.example.k_travel.been.Booking;
import com.example.k_travel.been.Scence;
import com.example.k_travel.databinding.ItemBookingBinding;
import com.example.k_travel.databinding.ItemScenceBinding;
import com.example.k_travel.db.DBServer;

import java.util.ArrayList;

/**
 *
 */

public class BookingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Booking> mDatas;
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener listener;

    public BookingAdapter(Context context, ArrayList<Booking> datas) {
        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemBookingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_booking, parent, false);
        return new BookingAdapter.BookingHolder(binding);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Booking booking = mDatas.get(position);
        Scence model = DBServer.searchScenceById(mContext, booking.getScenceId());
        BookingHolder vh = (BookingHolder) holder;
        Glide.with(mContext).load(model.getUrl()).into(vh.binding.ivHeader);
        vh.binding.tvName.setText(model.getName());
        vh.binding.tvIntrod.setText(model.getIntrod());
        vh.binding.tvEval.setText(model.getEval());
        vh.binding.tvPrice.setText("$" + model.getPrice());
        vh.binding.tvBooking.setText("bookingTime：" + booking.getDate());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class BookingHolder extends RecyclerView.ViewHolder {

        ItemBookingBinding binding;

        private BookingHolder(ItemBookingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(BookingAdapter.this, getAdapterPosition());
                }
            });
            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClicked(BookingAdapter.this, getAdapterPosition());
                    return true;
                }
            });
        }
    }


    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClicked(BookingAdapter adapter, int position);

        void onItemLongClicked(BookingAdapter adapter, int position);
    }
}

package com.example.k_travel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.k_travel.R;
import com.example.k_travel.been.Scence;
import com.example.k_travel.databinding.ItemScenceBinding;

import java.util.ArrayList;

/**
 *
 */

public class ScenceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Scence> mDatas;
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener listener;

    public ScenceAdapter(Context context, ArrayList<Scence> datas) {
        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemScenceBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_scence, parent, false);
        return new ScenceAdapter.ScenceHolder(binding);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Scence model = mDatas.get(position);
        ScenceHolder vh = (ScenceHolder) holder;
        Glide.with(mContext).load(model.getUrl()).into(vh.binding.ivHeader);
        vh.binding.tvName.setText(model.getName());
        vh.binding.tvIntrod.setText(model.getIntrod());
        vh.binding.tvEval.setText(model.getEval());
        vh.binding.tvPrice.setText("$" + model.getPrice());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class ScenceHolder extends RecyclerView.ViewHolder {

        ItemScenceBinding binding;

        private ScenceHolder(ItemScenceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(ScenceAdapter.this, getAdapterPosition());
                }
            });
            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClicked(ScenceAdapter.this, getAdapterPosition());
                    return true;
                }
            });
        }
    }


    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClicked(ScenceAdapter adapter, int position);

        void onItemLongClicked(ScenceAdapter adapter, int position);
    }
}

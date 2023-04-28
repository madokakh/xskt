package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.NguoiBan;

import java.util.ArrayList;
import java.util.List;

public class NguoiBanAdapter extends ListAdapter<NguoiBan, NguoiBanAdapter.NguoiBanHolder> {

    OnItemClickListener listener;
    public NguoiBanAdapter(@NonNull DiffUtil.ItemCallback<NguoiBan> diffCallback) {
        super(diffCallback);
    }

    @Override
    public NguoiBanHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nguoi_ban_item, parent, false);

        return new NguoiBanHolder(view);
    }

    @Override
    public void onBindViewHolder(NguoiBanHolder holder, int position) {
        NguoiBan current = getItem(position);
        holder.bind(current.getTenNguoiBan());
    }

   static class NguoiBanDiff extends DiffUtil.ItemCallback<NguoiBan> {

        @Override
        public boolean areItemsTheSame(@NonNull NguoiBan oldItem, @NonNull NguoiBan newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull NguoiBan oldItem, @NonNull NguoiBan newItem) {
            return oldItem.getTenNguoiBan().equals(newItem.getTenNguoiBan());
        }
    }

    class NguoiBanHolder extends RecyclerView.ViewHolder {
        private final TextView text_view_nguoi_ban;

        private NguoiBanHolder(View itemView) {
            super(itemView);
            text_view_nguoi_ban = itemView.findViewById(R.id.text_view_ten_nguoi_ban);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }

        public void bind(String text) {
            text_view_nguoi_ban.setText(text);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(NguoiBan nguoiBan);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
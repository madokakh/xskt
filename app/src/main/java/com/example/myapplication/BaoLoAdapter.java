package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.BaoLo;
import com.example.myapplication.model.DauDuoi;
import com.example.myapplication.utils.XoSoUtils;

public class BaoLoAdapter extends ListAdapter<BaoLo, BaoLoAdapter.BaoLoViewHolder> {

    OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;
    public BaoLoAdapter(@NonNull DiffUtil.ItemCallback<BaoLo> diffCallback) {
        super(diffCallback);
    }

    @Override
    public BaoLoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bao_lo_item, parent, false);

        return new BaoLoViewHolder(view);
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public void onBindViewHolder(BaoLoViewHolder holder, int position) {
        BaoLo current = getItem(position);
        holder.bind(current);
    }

   public static class BaoLoDiff extends DiffUtil.ItemCallback<BaoLo> {

        @Override
        public boolean areItemsTheSame(@NonNull BaoLo oldItem, @NonNull BaoLo newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull BaoLo oldItem, @NonNull BaoLo newItem) {
            return oldItem.getBaoLoID() == newItem.getBaoLoID();
        }
    }


    class BaoLoViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvSoCuoc;
        private final TextView text_view_tien_cuoc;


        private BaoLoViewHolder(View itemView) {
            super(itemView);
            tvSoCuoc = itemView.findViewById(R.id.text_view_so_cuoc);
            text_view_tien_cuoc = itemView.findViewById(R.id.text_view_tien_cuoc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });

            itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) {
                    int position = getAdapterPosition();
                    BaoLo item = getItem(position);
                    longClickListener.onItemLongClick(v, item);
                    return true;
                }
                return false;
            });
        }

        public void bind(BaoLo baoLo) {
           text_view_tien_cuoc.setText(baoLo.getTienCuoc());
           tvSoCuoc.setText(baoLo.getSoCuoc());
        }
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, BaoLo item);
    }
    public interface OnItemClickListener {
        void onItemClick(BaoLo baoLo);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
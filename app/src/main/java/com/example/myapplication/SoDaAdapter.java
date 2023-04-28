package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.DauDuoi;
import com.example.myapplication.model.SoDa;
import com.example.myapplication.utils.XoSoUtils;

public class SoDaAdapter extends ListAdapter<SoDa, SoDaAdapter.SoDaViewHolder> {

    OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;
    public SoDaAdapter(@NonNull DiffUtil.ItemCallback<SoDa> diffCallback) {
        super(diffCallback);
    }

    @Override
    public SoDaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.so_da_item, parent, false);

        return new SoDaViewHolder(view);
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public void onBindViewHolder(SoDaViewHolder holder, int position) {
        SoDa current = getItem(position);
        holder.bind(current);
    }

   static class SoDaDiff extends DiffUtil.ItemCallback<SoDa> {

        @Override
        public boolean areItemsTheSame(@NonNull SoDa oldItem, @NonNull SoDa newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull SoDa oldItem, @NonNull SoDa newItem) {
            return oldItem.getSoDaID() == newItem.getSoDaID();
        }
    }


    class SoDaViewHolder extends RecyclerView.ViewHolder {
        private final TextView text_view_so1;
        private final TextView text_view_so2;
        private final TextView text_view_so3;
        private final TextView text_view_hyphen;
        private final TextView text_view_tien_cuoc;
        private SoDaViewHolder(View itemView) {
            super(itemView);
            text_view_so1 = itemView.findViewById(R.id.tvSo1);
            text_view_so2 = itemView.findViewById(R.id.tvSo2);
            text_view_so3 = itemView.findViewById(R.id.tvSo3);
            text_view_hyphen = itemView.findViewById(R.id.tvHyphen);
            text_view_tien_cuoc = itemView.findViewById(R.id.tvTienCuoc);
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
                    SoDa item = getItem(position);
                    longClickListener.onItemLongClick(v, item);
                    return true;
                }
                return false;
            });
        }

        public void bind(SoDa soDa) {

            String so1 = String.format("%02d",soDa.getSoCuocThu1());
            String so2 = String.format("%02d",soDa.getSoCuocThu2());
            String so3 = String.format("%02d",soDa.getSoCuocThu3());

            String tienCuoc = XoSoUtils.getInteger(soDa.getTienCuoc());



            text_view_so1.setText(so1);
            text_view_so2.setText(so2);
            if(so3.equals("00") || so3.equals((AppConstants.KHONG_CUOC_3_CON+""))){
                so3 = "";
                text_view_hyphen.setText("");
            }
            text_view_so3.setText(so3);
            text_view_tien_cuoc.setText(tienCuoc);
        }
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, SoDa item);
    }
    public interface OnItemClickListener {
        void onItemClick(SoDa soDa);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
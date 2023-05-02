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

public class DauDuoiMienBacAdapter extends ListAdapter<DauDuoi, DauDuoiMienBacAdapter.DauDuoiViewHolder> {

    OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;
    public DauDuoiMienBacAdapter(@NonNull DiffUtil.ItemCallback<DauDuoi> diffCallback) {
        super(diffCallback);
    }

    @Override
    public DauDuoiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bao_lo_item, parent, false);

        return new DauDuoiViewHolder(view);
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public void onBindViewHolder(DauDuoiViewHolder holder, int position) {
        DauDuoi current = getItem(position);
        holder.bind(current);
    }

   public static class DauDuoiDiff extends DiffUtil.ItemCallback<DauDuoi> {

        @Override
        public boolean areItemsTheSame(@NonNull DauDuoi oldItem, @NonNull DauDuoi newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull DauDuoi oldItem, @NonNull DauDuoi newItem) {
            return oldItem.getDauDuoiID() == newItem.getDauDuoiID();
        }
    }


    class DauDuoiViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvSoCuoc;
        private final TextView text_view_tien_cuoc;


        private DauDuoiViewHolder(View itemView) {
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
                    DauDuoi item = getItem(position);
                    longClickListener.onItemLongClick(v, item);
                    return true;
                }
                return false;
            });
        }

        public void bind(DauDuoi dauDuoi) {
           text_view_tien_cuoc.setText(XoSoUtils.getInteger(dauDuoi.getTienCuocSoDau()));
           tvSoCuoc.setText(dauDuoi.getSoCuoc());
        }
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, DauDuoi item);
    }
    public interface OnItemClickListener {
        void onItemClick(DauDuoi dauDuoi);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
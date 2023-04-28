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
import com.example.myapplication.model.NguoiBan;
import com.example.myapplication.utils.XoSoUtils;

public class DauDuoiAdapter extends ListAdapter<DauDuoi, DauDuoiAdapter.DauDuoiViewHolder> {

    OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;
    public DauDuoiAdapter(@NonNull DiffUtil.ItemCallback<DauDuoi> diffCallback) {
        super(diffCallback);
    }

    @Override
    public DauDuoiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dau_duoi_item, parent, false);

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

   static class DauDuoiDiff extends DiffUtil.ItemCallback<DauDuoi> {

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
        private final TextView text_view_tien_cuoc_so_dau;
        private final TextView text_view_tien_cuoc_so_duoi;
        private final TextView text_view_so_cuoc;
        private final TextView text_view_dai;

        private DauDuoiViewHolder(View itemView) {
            super(itemView);
            text_view_tien_cuoc_so_dau = itemView.findViewById(R.id.tvTienCuocSoDau);
            text_view_tien_cuoc_so_duoi = itemView.findViewById(R.id.tvTienCuocSoDuoi);
            text_view_so_cuoc = itemView.findViewById(R.id.tvSoCuoc);
            text_view_dai = itemView.findViewById(R.id.tvDai);
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
                    longClickListener.onItemLongClick(v, item,position);
                    return true;
                }
                return false;
            });
        }

        public void bind(DauDuoi dauDuoi) {
            String tienCuocDau = XoSoUtils.getInteger(dauDuoi.getTienCuocSoDau());
            String tienCuocDuoi = XoSoUtils.getInteger(dauDuoi.getTienCuocSoDuoi());


            text_view_tien_cuoc_so_dau.setText(tienCuocDau);
            text_view_tien_cuoc_so_duoi.setText(tienCuocDuoi   );

            if(dauDuoi.getDaiI1D() == AppConstants.KHONG_CUOC){
                text_view_dai.setText(XoSoUtils.tenDaiVietTat(dauDuoi.getDaiI2D()));
            }
            if(dauDuoi.getDaiI2D() == AppConstants.KHONG_CUOC){
                text_view_dai.setText(XoSoUtils.tenDaiVietTat(dauDuoi.getDaiI1D()));
            }
            text_view_so_cuoc.setText(dauDuoi.getSoCuoc() + "");
          //  text_view_dai.setText(dauDuoi.getD + "");
        }
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, DauDuoi item, int position);
    }
    public interface OnItemClickListener {
        void onItemClick(DauDuoi dauDuoi);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
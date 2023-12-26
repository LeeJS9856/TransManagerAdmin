package org.techtown.transmanageradmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TransDataAdapter extends RecyclerView.Adapter<TransDataAdapter.ViewHolder> {

    private Context context;
    public TransDataAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getViewSrc(viewType), parent, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private int viewType;
        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
        }
        public void bind(TransData item) {
            if(viewType==TYPE_CHANGE_DATE) {
                bindChangeDate(item);
            }
            else if(viewType==TYPE_UNCHANGED_DATE) {
                bindUnChangedDate(item);
            }
        }

        private void bindChangeDate(TransData item) {
            TextView date = itemView.findViewById(R.id.text_date);
            TextView vihiclenumber = itemView.findViewById(R.id.text_vihicle_number);
            TextView trans = itemView.findViewById(R.id.text_trans);
            TextView product = itemView.findViewById(R.id.text_product);
            TextView agency = itemView.findViewById(R.id.text_agency);
            TextView quantity = itemView.findViewById(R.id.text_quantity);

            date.setText(item.getMonth()+"."+item.getDay());
            trans.setText(item.getStart()+" - "+item.getEnd());
            vihiclenumber.setText(item.getVihiclenumber());
            product.setText(item.getProduct());
            agency.setText(item.getAgency());
            quantity.setText(item.getQuantity());

        }
        private void bindUnChangedDate(TransData item) {
            TextView trans = itemView.findViewById(R.id.text_trans);
            TextView product = itemView.findViewById(R.id.text_product);
            TextView agency = itemView.findViewById(R.id.text_agency);
            TextView vihiclenumber = itemView.findViewById(R.id.text_vihicle_number);
            TextView quantity = itemView.findViewById(R.id.text_quantity);
            trans.setText(item.getStart()+" - "+item.getEnd());
            product.setText(item.getProduct());
            vihiclenumber.setText(item.getVihiclenumber());
            agency.setText(item.getAgency());
            quantity.setText(item.getQuantity());
        }


    }

    //리사이클러뷰 속 데이터 수정, 삭제 버튼 이벤트 처리하기
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    private ArrayList<TransData> dataSet = new ArrayList();
    public void submitData(ArrayList<TransData> newData) {
        dataSet = newData;
        notifyDataSetChanged();
    }

    //ViewType에 따라 xml파일 연결하는 메서드
    private int TYPE_CHANGE_DATE = 101;
    private int TYPE_UNCHANGED_DATE = 102;
    private int getViewSrc(int viewType) {
        if(viewType==TYPE_CHANGE_DATE) {
            return R.layout.recycler_trans_date;
        }
        else {
            return R.layout.recycler_trans_list;
        }
    }


    public int getItemViewType(int position) {
        String transData = dataSet.get(position).getDay();
        String beforeTransData = "";
        if(position>0){ beforeTransData = dataSet.get(position-1).getDay(); }
        if(transData.equals(beforeTransData)) {
            return TYPE_UNCHANGED_DATE;
        }
        else return TYPE_CHANGE_DATE;
    }

}

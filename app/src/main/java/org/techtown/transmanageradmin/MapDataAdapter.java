package org.techtown.transmanageradmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class MapDataAdapter extends RecyclerView.Adapter<MapDataAdapter.ViewHolder> {

    private Context context;
    public MapDataAdapter(Context context) {this.context = context;}

    @NonNull
    @Override
    public MapDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_data_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MapDataAdapter.ViewHolder holder, int position) {
        holder.bind(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton btn_delete;
        public ViewHolder(View itemView) {
            super(itemView);
            btn_delete = itemView.findViewById(R.id.button_delete);
        }

        public void bind(MapData item) {
            TextView data = itemView.findViewById(R.id.text_data);

            data.setText(item.getName());
        }
    }

    //리사이클러뷰 삭제 이벤트 처리
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(view.getContext());
                alertBuilder.setTitle("경고");
                alertBuilder.setMessage("해당 데이터를 삭제하시겠습니까?");
                alertBuilder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //맵 데이터에서 해당 데이터 삭제
                        Response.Listener<String> ResponseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        };
                        String property = dataSet.get(position).getProperty();
                        String name = dataSet.get(position).getName();
                        RequestDeleteMapData requestDeleteMapData = new RequestDeleteMapData(property, name, ResponseListener);
                        RequestQueue Queue = Volley.newRequestQueue(view.getContext());
                        Queue.add(requestDeleteMapData);

                        //리사이클러뷰에서 해당 데이터 삭제
                        dataSet.remove(position);
                        notifyItemRemoved(position);
                        notifyItemChanged(position, dataSet.size());
                    }
                });
                alertBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = alertBuilder.create();
                dialog.show();
            }
        });
    }

    private ArrayList<MapData> dataSet = new ArrayList<>();
    public void submitData(ArrayList<MapData> newData) {
        dataSet = newData;
        notifyDataSetChanged();
    }
}

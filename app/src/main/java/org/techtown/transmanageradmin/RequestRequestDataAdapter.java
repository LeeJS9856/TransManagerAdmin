package org.techtown.transmanageradmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class RequestRequestDataAdapter extends RecyclerView.Adapter<RequestRequestDataAdapter.ViewHolder> {

    private Context context;
    public RequestRequestDataAdapter(Context context) {this.context = context;}

    @NonNull
    @Override
    public RequestRequestDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_request_regist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestRequestDataAdapter.ViewHolder holder, int position) {
        holder.bind(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        android.widget.Button btn_accept, btn_refuse;
        public ViewHolder(View itemView) {
            super(itemView);
            btn_accept = itemView.findViewById(R.id.btn_accept);
            btn_refuse = itemView.findViewById(R.id.btn_refuse);
        }

        public void bind(ProfileData item) {
            TextView username = itemView.findViewById(R.id.text_name);
            TextView phonenumber = itemView.findViewById(R.id.text_phonenumber);
            TextView vihiclenumber = itemView.findViewById(R.id.text_vihiclenumber);

            username.setText(item.getUsername());
            phonenumber.setText(item.getPhonenumber());
            vihiclenumber.setText(item.getVihiclenumber());
        }
    }

    private ArrayList<ProfileData> dataSet = new ArrayList<>();
    public void submitData(ArrayList<ProfileData> newData) {
        dataSet = newData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

        holder.btn_refuse.setOnClickListener(new View.OnClickListener() {
            //거부버튼 이벤트
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(view.getContext());
                alertBuilder.setTitle("경고");
                alertBuilder.setMessage("해당 요청를 거부하시겠습니까?");
                alertBuilder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Response.Listener<String> deleteResponseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        };
                        String username = dataSet.get(position).getUsername();
                        String vihiclenumber = dataSet.get(position).getVihiclenumber();
                        String phonenumber = dataSet.get(position).getPhonenumber();
                        String password = dataSet.get(position).getPassword();
                        RequestDeleteRequestData requestDeleteRequestData = new RequestDeleteRequestData(username, vihiclenumber, phonenumber, password, deleteResponseListener);
                        RequestQueue Queue = Volley.newRequestQueue(view.getContext());
                        Queue.add(requestDeleteRequestData);

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
        holder.btn_accept.setOnClickListener(new View.OnClickListener() {
            //승인버튼 이벤트
            @Override
            public void onClick(View view) {
                //user테이블에 데이터 추가
                Response.Listener<String> ResponseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                };
                String username = dataSet.get(position).getUsername();
                String vihiclenumber = dataSet.get(position).getVihiclenumber();
                String phonenumber = dataSet.get(position).getPhonenumber();
                String password = dataSet.get(position).getPassword();
                RequestRegistProfileData requestRegistProfileData = new RequestRegistProfileData(username, vihiclenumber, phonenumber, password, ResponseListener);
                RequestQueue Queue = Volley.newRequestQueue(view.getContext());
                Queue.add(requestRegistProfileData);

                Toast.makeText(context, "승인되었습니다.", Toast.LENGTH_SHORT).show();

                //request테이블에서 데이터 삭제
                Response.Listener<String> deleteResponseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                };
                RequestDeleteRequestData requestDeleteRequestData = new RequestDeleteRequestData(username, vihiclenumber, phonenumber, password, deleteResponseListener);
                RequestQueue deleteQueue = Volley.newRequestQueue(view.getContext());
                deleteQueue.add(requestDeleteRequestData);

                dataSet.remove(position);
                notifyItemRemoved(position);
                notifyItemChanged(position, dataSet.size());

            }
        });
    }

}

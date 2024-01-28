package org.techtown.transmanageradmin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.ViewHolder> {

    private Context context;
    private android.widget.Button btn_edit;
    private TextView agency;
    private EditText price;

    public PriceAdapter(Context context) {this.context = context;}

    @NonNull
    @Override
    public PriceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_price, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PriceAdapter.ViewHolder holder, int position) {

        holder.bind(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            btn_edit = itemView.findViewById(R.id.btn_edit);
        }

        public void bind(PriceData item) {
            agency = itemView.findViewById(R.id.text_agency);
            price = itemView.findViewById(R.id.edit_text_price);


            agency.setText(item.getAgency());
            price.setText(item.getPrice());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkPrice = price.getText().toString();
                if(checkPrice.isEmpty()) {
                    Toast.makeText(context, "단가를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    Response.Listener<String> ResponseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    UpdatePriceData updatePriceData = new UpdatePriceData(dataSet.get(position).getAgency(),checkPrice, ResponseListener);
                    RequestQueue DataQueue = Volley.newRequestQueue(context);
                    DataQueue.add(updatePriceData);
                    Toast.makeText(context, "수정되었습니다", Toast.LENGTH_SHORT).show();

                    InputMethodManager inputMethodManager = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.showSoftInput(btn_edit, InputMethodManager.SHOW_IMPLICIT);
                }
            }


        });
    }

    private ArrayList<PriceData> dataSet = new ArrayList<>();
    public void submitData(ArrayList<PriceData> newData) {
        dataSet = newData;
        notifyDataSetChanged();
    }

}

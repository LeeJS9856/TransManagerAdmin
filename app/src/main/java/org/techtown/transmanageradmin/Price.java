package org.techtown.transmanageradmin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Price extends AppCompatActivity {
    ImageButton btn_back;
    RecyclerView recyclerView;
    Context context = this;
    static ArrayList<PriceData> data = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.price);
        xml();
        backButton();
        setRecyclerView();
        super.onCreate(savedInstanceState);
    }

    protected void xml() {
        btn_back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recyclerView);
    }

    protected void backButton() {
        //뒤로가기 버튼
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    protected void setRecyclerView() {

        data.clear();
        //서버에서 리스트 불러오기
        Response.Listener<String> ResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    int length = jsonArray.length();
                    for(int i=0;i<length;i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String agency = item.getString("agency");
                        String price = item.getString("price");
                        PriceData priceData = new PriceData(agency, price);
                        data.add(priceData);
                    }
                    //어댑터 설정하기
                    PriceAdapter priceAdapter = new PriceAdapter(context);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            priceAdapter.submitData(getData());
                            recyclerView.setAdapter(priceAdapter);
                            recyclerView.scrollToPosition(data.size()-1);
                        }
                    }, 1000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RequestPriceData requestPriceData = new RequestPriceData(ResponseListener);
        RequestQueue DataQueue = Volley.newRequestQueue(context);
        DataQueue.add(requestPriceData);
    }

    private ArrayList<PriceData> getData() {return data;}
}

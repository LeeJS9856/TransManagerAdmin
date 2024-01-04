package org.techtown.transmanageradmin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Dispatch extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageButton bt_back;
    LinearLayout bt_add;
    String[] today;
    String today_year, today_month;
    Context context = Dispatch.this;
    static ArrayList<TransData> data = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dispatch_list);
        xml();
        today();
        backButton();
        setBt_add();
        setRecyclerView();
    }

    protected void xml() {
        bt_back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recyclerView);
        bt_add =findViewById(R.id.btn_add);
    }

    protected void backButton() {
        //뒤로가기 버튼
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    protected void setBt_add() {
        //배차요청 버튼
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RequestDispatch.class);
                startActivity(intent);
            }
        });
    }

    protected void setRecyclerView() {
        //리사이클러뷰 이벤트 설정

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
                        String day = item.getString("day");
                        String vihicle_number = item.getString("vihiclenumber");
                        String product = item.getString("product");
                        String start = item.getString("start");
                        String end = item.getString("end");
                        String quantity = item.getString("quantity")+"대";
                        String agency = item.getString("agency");
                        TransData transData = new TransData(i, today_year, today_month, day, vihicle_number, product, start, end, quantity, agency);
                        data.add(transData);
                    }
                    //어댑터 설정하기
                    TransDataAdapter transDataAdapter = new TransDataAdapter(context);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            transDataAdapter.submitData(getData());
                            recyclerView.setAdapter(transDataAdapter);
                            recyclerView.scrollToPosition(data.size()-1);
                        }
                    }, 1000);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RequestDispatchData Request = new RequestDispatchData(today_year, today_month, ResponseListener);
        RequestQueue DataQueue = Volley.newRequestQueue(context);
        DataQueue.add(Request);
    }
    private ArrayList<TransData> getData() {
        return data;
    }
    protected void today() {
        //오늘날짜 구하기
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        today = sdf.format(date).split("-");
        today_year = today[0];
        today_month = today[1];
    }

    public void onBackPressed() { //뒤로가기 누를 시 이전페이지로
        Intent intent = new Intent(context, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

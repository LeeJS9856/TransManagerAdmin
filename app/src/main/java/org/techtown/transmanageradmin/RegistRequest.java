package org.techtown.transmanageradmin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

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

public class RegistRequest extends AppCompatActivity {
    ImageButton btn_back;
    RecyclerView recyclerView;
    Context context = RegistRequest.this;
    static ArrayList<ProfileData> data = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_regist);
        xml();
        backButton();
        setRecyclerView();
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

    private ArrayList<ProfileData> getData() {
        return data;
    }

    protected void setRecyclerView() {
        //리사이클러뷰 설정
        RequestRequestDataAdapter requestRequestDataAdapter = new RequestRequestDataAdapter(context);

        data.clear();
        //리퀘스트 데이터 불러오기
        Response.Listener<String> responseRequestListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    int length = jsonArray.length();
                    for(int i=0;i<length;i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String username = item.getString("username");
                        String vihiclenumber = item.getString("vihiclenumber");
                        String phonenumber = item.getString("phonenumber");
                        String password = item.getString("password");

                        ProfileData profileData = new ProfileData(username, phonenumber, vihiclenumber, password);
                        data.add(profileData);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RequestRequestData requestRequestData =
                new RequestRequestData(responseRequestListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(requestRequestData);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestRequestDataAdapter.submitData(getData());
                recyclerView.setAdapter(requestRequestDataAdapter);
            }
        }, 1000);
    }

}

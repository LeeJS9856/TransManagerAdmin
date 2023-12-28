package org.techtown.transmanageradmin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

public class VihicleProfile extends AppCompatActivity {
    ImageButton bt_back;
    Context context = VihicleProfile.this;
    RecyclerView recyclerView;
    static ArrayList<ProfileData> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vihicle_profile);
        xml();
        backButton();
        setRecyclerView();
    }

    protected void xml() {
        //xml과 연결
        bt_back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recyclerView);
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

    private ArrayList<ProfileData> getData() {
        return data;
    }

    protected void setRecyclerView() {
        data.clear();
        Response.Listener<String> profileResponseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    int length = jsonArray.length() - 1;
                    for (int i = 0; i <= length; i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String username = item.getString("username");
                        String vihiclenumber = item.getString("vihiclenumber");
                        String phonenumber = item.getString("phonenumber");
                        ProfileData profileData = new ProfileData(username, phonenumber, vihiclenumber, "");
                        data.add(profileData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RequestProfileData Request = new RequestProfileData(profileResponseListener);
        RequestQueue Queue = Volley.newRequestQueue(context);
        Queue.add(Request);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ProfileDataAdapter profileDataAdapter = new ProfileDataAdapter(context);
                profileDataAdapter.submitData(getData());
                recyclerView.setAdapter(profileDataAdapter);
            }
        }, 1000);


    }
}

package org.techtown.transmanageradmin;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Home extends AppCompatActivity {
    LinearLayout btn_transList, btn_price, btn_data, btn_dispatch, btn_requestRegist, btn_vihicleProfile;
    TextView notice;
    private long backKeyPressedTime = 0;
    private final int SMS_RECEIVE_PERMISSION = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        xml();
        clickListener();
        setNotice();
    }

    protected void xml() {
        //xml과 연결
        btn_transList = findViewById(R.id.btn_trans_list);
        btn_price = findViewById(R.id.btn_price);
        btn_data = findViewById(R.id.btn_data);
        btn_dispatch = findViewById(R.id.btn_dispatch);
        btn_requestRegist = findViewById(R.id.btn_request_regist);
        btn_vihicleProfile = findViewById(R.id.btn_vihicle_profile);
        notice = findViewById(R.id.notice);
    }

    protected void clickListener() {
        //클릭 이벤트 설정
        btn_transList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, TransList.class);
                startActivity(intent);
            }
        });

        btn_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Data.class);
                startActivity(intent);
            }
        });

        btn_dispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Dispatch.class);
                startActivity(intent);
            }
        });

        btn_requestRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, RegistRequest.class);
                startActivity(intent);
            }
        });

        btn_vihicleProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, VihicleProfile.class);
                startActivity(intent);
            }
        });

    }

    public void onBackPressed() { //뒤로가기 두번 누를시 앱 종료
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            finish();
        }
    }

    public void setNotice() {
        //등록요청 알림숫자
        RequestRequestDataAdapter requestRequestDataAdapter = new RequestRequestDataAdapter(Home.this);
        //리퀘스트 데이터 불러오기
        Response.Listener<String> responseRequestListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    int length = jsonArray.length();
                    if(length==0) {
                        notice.setVisibility(View.INVISIBLE);
                        notice.setText("0");
                    }
                    else {
                        notice.setVisibility(View.VISIBLE);
                        notice.setText(Integer.toString(length));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RequestRequestData requestRequestData =
                new RequestRequestData(responseRequestListener);
        RequestQueue queue = Volley.newRequestQueue(Home.this);
        queue.add(requestRequestData);
    }

}

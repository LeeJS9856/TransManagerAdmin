package org.techtown.transmanageradmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    LinearLayout btn_transList, btn_statistic, btn_data, btn_dispatch, btn_requestRegist, btn_vihicleProfile;
    private long backKeyPressedTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        xml();
        clickListener();

    }

    protected void xml() {
        //xml과 연결
        btn_transList = findViewById(R.id.btn_trans_list);
        btn_statistic = findViewById(R.id.btn_statistic);
        btn_data = findViewById(R.id.btn_data);
        btn_dispatch = findViewById(R.id.btn_dispatch);
        btn_requestRegist = findViewById(R.id.btn_request_regist);
        btn_vihicleProfile = findViewById(R.id.btn_vihicle_profile);
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

        btn_statistic.setOnClickListener(new View.OnClickListener() {
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
}

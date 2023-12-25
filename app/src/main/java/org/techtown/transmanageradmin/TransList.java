package org.techtown.transmanageradmin;

import android.bluetooth.le.TransportDiscoveryData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransList extends AppCompatActivity {
    Spinner spinner_year, spinner_month, spinner_categorie, spinner_data;
    String[] arr_month = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    String[] today, arr_year;
    String choiced_year, choiced_month, choiced_categorie, choiced_data;
    ImageButton bt_back;
    RecyclerView recyclerView;
    Context context = TransList.this;
    static ArrayList<TransData> data = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trans_list);
        xml();
        backButton();

    }
    protected void xml() {
        //xml과 변수 연결
        spinner_year = findViewById(R.id.spinner_year);
        spinner_month = findViewById(R.id.spinner_month);
        spinner_categorie = findViewById(R.id.spinner_categorie);
        spinner_data = findViewById(R.id.spinner_data);
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

    protected void dateSpinner() {
        //오늘날짜 구하기
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        today = sdf.format(date).split("-");
        choiced_year = today[0];
        choiced_month = today[1];

        //연도 배열 만들기
        int n = Integer.parseInt(today[0]) - 2023;
        arr_year = new String[n+1];
        for(int i=0;i<n+1;i++) {
            int m = 2023 + n;
            arr_year[i] = Integer.toString(m); //2023년~현재 연도 배열에 집어넣기
        }
        
    }
}

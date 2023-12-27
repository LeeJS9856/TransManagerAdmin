package org.techtown.transmanageradmin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Data extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageButton btn_back;
    TextView btn_agency, btn_start, btn_end, btn_product;
    RelativeLayout btn_add;
    LinearLayout layout_categorie;
    ArrayList<String> list_from = new ArrayList<>();
    ArrayList<String> list_to = new ArrayList<>();
    ArrayList<String> list_product = new ArrayList<>();
    ArrayList<String> list_agency = new ArrayList<>();
    static ArrayList<MapData> data = new ArrayList<>();
    Context context = Data.this;
    private long btnClickTime = System.currentTimeMillis();
    int categorie = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data);
        xml();
        backButton();
        clickCategorieEvent();
        requestMapData();
        setAddButton();
    }

    protected void xml() {
        //xml에 연결
        recyclerView = findViewById(R.id.recyclerView);
        btn_back = findViewById(R.id.back);
        btn_agency = findViewById(R.id.text_agency);
        btn_start = findViewById(R.id.text_start);
        btn_end = findViewById(R.id.text_end);
        btn_product = findViewById(R.id.text_product);
        btn_add = findViewById(R.id.btn_add);
        layout_categorie = findViewById(R.id.layout_categorie);
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

    protected void clickCategorieEvent() {
        //카테고리 클릭 이벤트 설정
        MapDataAdapter mapDataAdapter = new MapDataAdapter(context);

        //초기 리사이클러뷰 설정
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                data.clear();
                for(String i : list_agency) {
                    data.add(new MapData("대리점", i));
                }
                mapDataAdapter.submitData(getData());
                recyclerView.setAdapter(mapDataAdapter);
            }
        }, 1000);


            btn_agency.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(System.currentTimeMillis() > 1000 + btnClickTime) {
                        categorie = 0;
                        layout_categorie.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_data_button_1));
                        changeTextColor();
                        btn_agency.setTextColor(ContextCompat.getColor(context, R.color.white));

                        //리사이클러뷰 데이터 집어넣기
                        data.clear();
                        for (String i : list_agency) {
                            data.add(new MapData("대리점", i));
                        }
                        mapDataAdapter.submitData(getData());
                        recyclerView.setAdapter(mapDataAdapter);
                    }
                }
            });
            btn_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(System.currentTimeMillis() > 1000 + btnClickTime) {
                        categorie = 1;
                        layout_categorie.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_data_button_2));
                        changeTextColor();
                        btn_start.setTextColor(ContextCompat.getColor(context, R.color.white));

                        //리사이클러뷰 데이터 집어넣기
                        data.clear();
                        for (String i : list_from) {
                            data.add(new MapData("출발지", i));
                        }
                        mapDataAdapter.submitData(getData());
                        recyclerView.setAdapter(mapDataAdapter);
                    }
                }
            });
            btn_end.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(System.currentTimeMillis() > 1000 + btnClickTime) {
                        categorie = 2;
                        layout_categorie.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_data_button_3));
                        changeTextColor();
                        btn_end.setTextColor(ContextCompat.getColor(context, R.color.white));

                        //리사이클러뷰 데이터 집어넣기
                        data.clear();
                        for (String i : list_to) {
                            data.add(new MapData("도착지", i));
                        }
                        mapDataAdapter.submitData(getData());
                        recyclerView.setAdapter(mapDataAdapter);
                    }
                }
            });
            btn_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(System.currentTimeMillis() > 1000 + btnClickTime) {
                        categorie = 3;
                        layout_categorie.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_data_button_4));
                        changeTextColor();
                        btn_product.setTextColor(ContextCompat.getColor(context, R.color.white));

                        //리사이클러뷰 데이터 집어넣기
                        data.clear();
                        for (String i : list_product) {
                            data.add(new MapData("제품", i));
                        }
                        mapDataAdapter.submitData(getData());
                        recyclerView.setAdapter(mapDataAdapter);
                    }
                }
            });


    }

    protected void changeTextColor() {
        //카테고리 클릭시 글자색 변경
        btn_agency.setTextColor(ContextCompat.getColor(context, R.color.sky));
        btn_start.setTextColor(ContextCompat.getColor(context, R.color.sky));
        btn_end.setTextColor(ContextCompat.getColor(context, R.color.sky));
        btn_product.setTextColor(ContextCompat.getColor(context, R.color.sky));

    }

    protected void requestMapData() {
        //맵 데이터 불러오기
        Response.Listener<String> mapResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    int length = jsonArray.length()-1;
                    for(int i=0;i<=length;i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String property = item.getString("property");
                        String name = item.getString("name");

                        if(property.equals("출발지")) {
                            list_from.add(name);
                        }
                        else if(property.equals("도착지")) {
                            list_to.add(name);
                        }
                        else if(property.equals("제품")) {
                            list_product.add(name);
                        }
                        else if(property.equals("대리점")) {
                            list_agency.add(name);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        MapRequest mapRequest = new MapRequest(mapResponseListener);
        RequestQueue MapQueue = Volley.newRequestQueue(Data.this);
        MapQueue.add(mapRequest);
    }

    private ArrayList<MapData> getData() {return data;}

    private void setAddButton() {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddData.class);
                startActivity(intent);
            }
        });
    }
}

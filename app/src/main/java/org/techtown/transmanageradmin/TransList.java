package org.techtown.transmanageradmin;

import android.bluetooth.le.TransportDiscoveryData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransList extends AppCompatActivity {
    Spinner spinner_year, spinner_month, spinner_categorie, spinner_data;
    double sum=0;
    String[] arr_month = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    String[] arr_categorie = {"차량별", "대리점별", "출발지별", "도착지별", "제품별"};
    String[] today, arr_year, arr_data;
    String choiced_year, choiced_month, choiced_categorie, choiced_data;
    ImageButton bt_back;
    RecyclerView recyclerView;
    ArrayList<String> list_from = new ArrayList<>();
    ArrayList<String> list_to = new ArrayList<>();
    ArrayList<String> list_product = new ArrayList<>();
    ArrayList<String> list_agency = new ArrayList<>();
    ArrayList<String> list_vihiclenumber = new ArrayList<>();
    Context context = TransList.this;
    static ArrayList<TransData> data = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trans_list);
        xml();
        backButton();
        getVihicleNumber();
        getCategorieData();
        setSpinner();
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

    protected void setSpinner() {
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

        //어댑터 설정하기
        TransDataAdapter transDataAdapter = new TransDataAdapter(TransList.this);

        //스피너 처리하기
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(
                TransList.this, android.R.layout.simple_spinner_item, arr_year);
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(
                TransList.this, android.R.layout.simple_spinner_item, arr_month);
        ArrayAdapter<String> categorieAdapter = new ArrayAdapter<>(
                TransList.this, android.R.layout.simple_spinner_item, arr_categorie);


        yearAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        monthAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        categorieAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spinner_year.setAdapter(yearAdapter);
        spinner_month.setAdapter(monthAdapter);
        spinner_categorie.setAdapter(categorieAdapter);
        spinner_year.setSelection(Integer.parseInt(today[0])-2023);
        spinner_month.setSelection(Integer.parseInt(today[1])-1); //오늘 날짜로 초기값 지정

        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choiced_year = arr_year[i];
                data.clear();
                //맨 처음 데이터가 두번 불러와지는 오류 수정
                if(System.currentTimeMillis() - now > 1000) getTransData(choiced_year, choiced_month, choiced_categorie, choiced_data);
                //비동기적으로 작동하는 onresponse메서드를 위해 딜레이시켜서 리사이클러뷰 실행
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        transDataAdapter.submitData(getData());
                        recyclerView.setAdapter(transDataAdapter);
                        recyclerView.scrollToPosition(data.size()-1);
                    }
                }, 1000);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choiced_month = arr_month[i];
                data.clear();
                if(System.currentTimeMillis() - now > 1000) getTransData(choiced_year, choiced_month, choiced_categorie, choiced_data);
                //비동기적으로 작동하는 onresponse메서드를 위해 딜레이시켜서 리사이클러뷰 실행
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        transDataAdapter.submitData(getData());
                        recyclerView.setAdapter(transDataAdapter);
                        recyclerView.scrollToPosition(data.size()-1);
                    }
                }, 1000);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_categorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choiced_categorie = arr_categorie[i];
                setDataSpinner();

                data.clear();
                if(System.currentTimeMillis() - now > 1000) getTransData(choiced_year, choiced_month, choiced_categorie, choiced_data);
                //비동기적으로 작동하는 onresponse메서드를 위해 딜레이시켜서 리사이클러뷰 실행


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        transDataAdapter.submitData(getData());
                        recyclerView.setAdapter(transDataAdapter);
                        recyclerView.scrollToPosition(data.size()-1);
                    }
                }, 1000);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            //DataSpinner는 데이터가져오는 처리시간이 있으므로 딜레이 걸고 시작
            @Override
            public void run() {
                setDataSpinner();

                spinner_data.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        choiced_data = arr_data[i];
                        data.clear();
                        Log.d("tmp", choiced_year+" "+choiced_month+" "+choiced_categorie+" "+choiced_data);
                        getTransData(choiced_year, choiced_month, choiced_categorie, choiced_data);
                        //비동기적으로 작동하는 onresponse메서드를 위해 딜레이시켜서 리사이클러뷰 실행
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                transDataAdapter.submitData(getData());
                                recyclerView.setAdapter(transDataAdapter);
                                recyclerView.scrollToPosition(data.size()-1);
                            }
                        }, 1000);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        }, 800);
    }
    private void setDataSpinner() {
        switch (choiced_categorie) {
            case "차량별" :
                arr_data = list_vihiclenumber.toArray(new String[list_vihiclenumber.size()]);
                break;
            case "출발지별" :
                arr_data = list_from.toArray(new String[list_from.size()]);
                break;
            case "도착지별" :
                arr_data = list_to.toArray(new String[list_to.size()]);
                break;
            case "대리점별" :
                arr_data = list_agency.toArray(new String[list_agency.size()]);
                break;
            case "제품별" :
                arr_data = list_product.toArray(new String[list_product.size()]);
                break;
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                TransList.this, android.R.layout.simple_spinner_item, arr_data);
        dataAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner_data.setAdapter(dataAdapter);
    }

    private ArrayList<TransData> getData() {
        return data;
    }


    private void getTransData(String year, String month, String categorie, String value) {
        //서버에서 리스트 불러오기
        Response.Listener<String> ResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    int length = jsonArray.length();
                    sum=0;
                    for(int i=0;i<length;i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        int id = item.getInt("id");
                        String day = item.getString("day");
                        String vihicle_number = item.getString("vihiclenumber");
                        String product = item.getString("product");
                        String start = item.getString("start");
                        String end = item.getString("end");
                        String quantity = item.getString("quantity");
                        String agency = item.getString("agency");

                        switch (categorie) {
                            case "차량별" :
                                if(vihicle_number.equals(value)) {
                                    TransData transData = new TransData(id, year, month, day, vihicle_number, product, start, end, quantity, agency);
                                    sum = sum + Double.parseDouble(quantity);
                                    data.add(transData);
                                }
                                break;
                            case "대리점별" :
                                if(agency.equals(value)) {
                                    TransData transData = new TransData(id, year, month, day, vihicle_number, product, start, end, quantity, agency);
                                    sum = sum + Double.parseDouble(quantity);
                                    data.add(transData);
                                }
                                break;
                            case "출발지별" :
                                if(start.equals(value)) {
                                    TransData transData = new TransData(id, year, month, day, vihicle_number, product, start, end, quantity, agency);
                                    sum = sum + Double.parseDouble(quantity);
                                    data.add(transData);
                                }
                                break;
                            case "도착지별" :
                                if(end.equals(value)) {
                                    TransData transData = new TransData(id, year, month, day, vihicle_number, product, start, end, quantity, agency);
                                    sum = sum + Double.parseDouble(quantity);
                                    data.add(transData);
                                }
                                break;
                            case "제품별" :
                                if(product.equals(value)) {
                                    TransData transData = new TransData(id, year, month, day, vihicle_number, product, start, end, quantity, agency);
                                    sum = sum + Double.parseDouble(quantity);
                                    data.add(transData);
                                }
                                break;

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RequestTransData Request = new RequestTransData(year, month, ResponseListener);
        RequestQueue DataQueue = Volley.newRequestQueue(TransList.this);
        DataQueue.add(Request);
    }

    private void getCategorieData() {
        Response.Listener<String> mapResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    int length = jsonArray.length()-1;
                    for(int i=0;i<length;i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String property = item.getString("property");
                        String name = item.getString("name");
                        switch (property) {
                            case "출발지":
                                list_from.add(name);
                                break;
                            case "도착지":
                                list_to.add(name);
                                break;
                            case "제품":
                                list_product.add(name);
                                break;
                            case "대리점":
                                list_agency.add(name);
                                break;
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        MapRequest mapRequest = new MapRequest(mapResponseListener);
        RequestQueue MapQueue = Volley.newRequestQueue(TransList.this);
        MapQueue.add(mapRequest);
    }

    private void getVihicleNumber() {
        Response.Listener<String> profileResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    int length = jsonArray.length()-1;
                    for(int i=0;i<=length;i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String vihiclenumber = item.getString("vihiclenumber");
                        list_vihiclenumber.add(vihiclenumber);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RequestProfileData Request = new RequestProfileData(profileResponseListener);
        RequestQueue Queue = Volley.newRequestQueue(TransList.this);
        Queue.add(Request);
    }
}

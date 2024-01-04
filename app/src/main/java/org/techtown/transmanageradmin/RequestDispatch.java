package org.techtown.transmanageradmin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RequestDispatch extends AppCompatActivity {
    ImageButton bt_back;
    Spinner spinner_vihiclenumber, spinner_agency, spinner_product, spinner_start, spinner_end;
    EditText editText_quantity;
    android.widget.Button btn_request_dispatch;
    ArrayList<String> list_from = new ArrayList<>();
    ArrayList<String> list_to = new ArrayList<>();
    ArrayList<String> list_product = new ArrayList<>();
    ArrayList<String> list_agency = new ArrayList<>();
    ArrayList<String> list_vihiclenumber = new ArrayList<>();
    String[] arr_from, arr_to, arr_product, arr_agency, arr_vihiclenumber;
    String choiced_from, choiced_to, choiced_product, choiced_agency, choiced_vihiclenumber, entered_quantity;
    Context context = RequestDispatch.this;
    private long mLastClickTime = 0; //중복 클릭 방지
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_dispatch);
        xml();
        backButton();
        getCategorieData();
        getVihicleNumber();
        setSpinner();
        setBtn_request_dispatch();
    }

    protected void xml() {
        bt_back = findViewById(R.id.back);
        spinner_agency = findViewById(R.id.spinner_agency);
        spinner_start = findViewById(R.id.spinner_start);
        spinner_end = findViewById(R.id.spinner_end);
        spinner_vihiclenumber = findViewById(R.id.spinner_vihiclenumber);
        spinner_product = findViewById(R.id.spinner_product);
        editText_quantity = findViewById(R.id.edittext_quantity);
        btn_request_dispatch = findViewById(R.id.button_request_dispatch);
    }

    protected void backButton() {
        //뒤로가기 버튼
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Dispatch.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
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
        RequestQueue MapQueue = Volley.newRequestQueue(context);
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
        RequestQueue Queue = Volley.newRequestQueue(context);
        Queue.add(Request);
    }
    private void setSpinner() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //리스트를 배열에 집어넣기
                arr_from = list_from.toArray(new String[list_from.size()]);
                arr_to = list_to.toArray(new String[list_to.size()]);
                arr_product = list_product.toArray(new String[list_product.size()]);
                arr_agency = list_agency.toArray(new String[list_agency.size()]);
                arr_vihiclenumber = list_vihiclenumber.toArray(new String[list_vihiclenumber.size()]);

                //스피너 적용하기
                ArrayAdapter<String> fromAdapter = new ArrayAdapter<>(
                        context, android.R.layout.simple_spinner_item, arr_from);
                ArrayAdapter<String> toAdapter = new ArrayAdapter<>(
                        context, android.R.layout.simple_spinner_item, arr_to);
                ArrayAdapter<String> productAdapter = new ArrayAdapter<>(
                        context, android.R.layout.simple_spinner_item, arr_product);
                ArrayAdapter<String> agencyAdapter = new ArrayAdapter<>(
                        context, android.R.layout.simple_spinner_item, arr_agency);
                ArrayAdapter<String> vihiclenumberAdapter = new ArrayAdapter<>(
                        context, android.R.layout.simple_spinner_item, arr_vihiclenumber);
                fromAdapter.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                toAdapter.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                productAdapter.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                agencyAdapter.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                vihiclenumberAdapter.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
                spinner_start.setAdapter(fromAdapter);
                spinner_end.setAdapter(toAdapter);
                spinner_product.setAdapter(productAdapter);
                spinner_agency.setAdapter(agencyAdapter);
                spinner_vihiclenumber.setAdapter(vihiclenumberAdapter);


                spinner_start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        choiced_from = arr_from[i];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                spinner_end.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        choiced_to = arr_to[i];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                spinner_product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        choiced_product = arr_product[i];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                spinner_agency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        choiced_agency = arr_agency[i];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                spinner_vihiclenumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        choiced_vihiclenumber = arr_vihiclenumber[i];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }
        }, 1000);
    }
    private void setBtn_request_dispatch() {
        btn_request_dispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //중복 클릭 방지
                if(SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                //오늘날짜 구하기
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String[] getTime = sdf.format(date).split("-");

                entered_quantity = editText_quantity.getText().toString();
                if(entered_quantity.isEmpty()) {
                    Toast.makeText(context, "수량을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonResponse = new JSONObject(response);
                                Toast.makeText(context, "배차요청 되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, Dispatch.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    RequestRegistDispatchData requestRegistDispatchData =
                            new RequestRegistDispatchData(getTime[0], getTime[1], getTime[2], choiced_vihiclenumber,
                                    choiced_from, choiced_to, choiced_product, entered_quantity, choiced_agency, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(context);
                    queue.add(requestRegistDispatchData);
                }
            }
        });
    }


}

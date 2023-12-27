package org.techtown.transmanageradmin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

public class AddData extends AppCompatActivity {
    ImageButton btn_back;
    Spinner spinner_categorie;
    EditText edit_text_data;
    android.widget.Button btn_add_data;
    Context context = AddData.this;
    String[] arr_categorie = {"대리점", "출발지", "도착지", "제품"};
    String choicedCategorie, inputData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);
        xml();
        backButton();
        setSpinner();
        setAddButton();
    }

    protected void xml() {
        btn_back = findViewById(R.id.back);
        spinner_categorie = findViewById(R.id.spinner_categorie);
        edit_text_data = findViewById(R.id.edit_text_data);
        btn_add_data = findViewById(R.id.btn_add_data);
    }

    protected void backButton() {
        //뒤로가기 버튼
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Data.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    protected void setSpinner() {
        //스피너 설정
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, arr_categorie);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner_categorie.setAdapter(adapter);

        spinner_categorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choicedCategorie = arr_categorie[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    protected void setAddButton() {
        //데이터 추가 버튼 설정
        btn_add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //데이터 입력받기
                inputData = edit_text_data.getText().toString();
                if(inputData.isEmpty()) {
                    Toast.makeText(context, "데이터를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if(success) {
                                    Toast.makeText(context, "등록되었습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context, Data.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    RequestRegistMapData requestRegistMapData =
                            new RequestRegistMapData(choicedCategorie, inputData, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(context);
                    queue.add(requestRegistMapData);
                }
            }
        });

    }


}

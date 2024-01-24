package org.techtown.transmanageradmin;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeletePriceData extends StringRequest {
    final static private String URL = "http://gm8668.dothome.co.kr/DeletePriceData.php";
    private Map<String, String> map;

    public DeletePriceData(String agency, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("agency", agency);

    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

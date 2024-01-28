package org.techtown.transmanageradmin;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdatePriceData extends StringRequest {
    final static private String URL = "http://gm8668.dothome.co.kr/UpdatePriceData.php";
    private Map<String, String> map;

    public UpdatePriceData(String agency, String price, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("agency", agency);
        map.put("price", price);
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

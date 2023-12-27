package org.techtown.transmanageradmin;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestRegistMapData extends StringRequest {
    final static private String URL = "http://gm8668.dothome.co.kr/registMapData.php";
    private Map<String, String> map;

    public RequestRegistMapData(String property, String name, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("property", property);
        map.put("name", name);

    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

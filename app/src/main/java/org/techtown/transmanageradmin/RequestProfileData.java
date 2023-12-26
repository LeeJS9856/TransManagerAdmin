package org.techtown.transmanageradmin;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RequestProfileData extends StringRequest {
    final static private String URL = "http://gm8668.dothome.co.kr/requestAllProfile.php";
    private Map<String, String> map;

    public RequestProfileData(Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

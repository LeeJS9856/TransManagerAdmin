package org.techtown.transmanageradmin;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestRegistProfileData extends StringRequest {
    final static private String URL = "http://gm8668.dothome.co.kr/registProfileData.php";
    private Map<String, String> map;

    public RequestRegistProfileData(String username, String vihiclenumber, String phonenumber, String password, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("username", username);
        map.put("vihiclenumber", vihiclenumber);
        map.put("phonenumber", phonenumber);
        map.put("password", password);

    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

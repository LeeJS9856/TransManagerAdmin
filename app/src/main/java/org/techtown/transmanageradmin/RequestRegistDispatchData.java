package org.techtown.transmanageradmin;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestRegistDispatchData extends StringRequest {
    final static private String URL = "http://gm8668.dothome.co.kr/registDispatchData.php";
    private Map<String, String> map;

    public RequestRegistDispatchData(String year, String month, String day, String vihiclenumber, String start, String end,
                                     String product, String quantity, String agency, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("year", year);
        map.put("month", month);
        map.put("day", day);
        map.put("vihiclenumber", vihiclenumber);
        map.put("start", start);
        map.put("end", end);
        map.put("product", product);
        map.put("quantity", quantity);
        map.put("agency", agency);


    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

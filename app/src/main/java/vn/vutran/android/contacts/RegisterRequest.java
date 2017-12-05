package vn.vutran.android.contacts;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String API_REGISTER_REQUEST = "http://api.aladi.co/v1/phone/register";
    private Map<String, String> params;


    public RegisterRequest ( String name, String phone, String password, Response.Listener<String> listener) {
        super(Method.POST, API_REGISTER_REQUEST, listener, null);

        params = new HashMap<>();
        params.put("username", name);
        params.put("phone_number", phone);
        params.put("password", password);
    }

    public Map<String, String> getParams() {
        return params;
    }
}

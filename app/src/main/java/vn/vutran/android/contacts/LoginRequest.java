package vn.vutran.android.contacts;

import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class LoginRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://app.aladi.co/mv1/users/login";
    private Map<String, String> params;

    public LoginRequest(String phone, String password, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("phone_number", phone);
        params.put("password", password);
        params.put("deviceid", "f07a139814f6d116a");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
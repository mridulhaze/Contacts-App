package vn.vutran.android.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import android.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLinkToRegister, btnLogin;
    EditText edtPhone, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        addControls();
        addEvents();
    }

    public void addEvents() {

        btnLogin.setOnClickListener( this );
        btnLinkToRegister.setOnClickListener(this);
    }

    public void addControls() {

        btnLinkToRegister   = findViewById(R.id.btnLinkToRegisterScreen);
        btnLogin    = findViewById(R.id.btnLogin);
        edtPhone    = findViewById(R.id.edt_lgPhone);
        edtPassword = findViewById(R.id.edt_lgPassword);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnLogin:

                String phone    = edtPhone.getText().toString();
                String password = edtPassword.getText().toString();

                Toast.makeText(getBaseContext()," Click ", Toast.LENGTH_SHORT).show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            int error   = jsonResponse.getInt("error");
                            String msg  = jsonResponse.getString("msg");

                            Toast.makeText(getBaseContext(),"Message: \n" + msg, Toast.LENGTH_LONG).show();

                            if ( error == 0 ) {

                                JSONObject data = jsonResponse.getJSONObject("data");

                                String name     = data.getString("username");
                                String phone    = data.getString("phone_number");

                                Toast.makeText(getBaseContext(),"Success:" + "\n username: " + name + " \n phone: " + phone, Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("phone", phone);

                                LoginActivity.this.startActivity(intent);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(phone, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

                break;
            case R.id.btnLinkToRegisterScreen:

                startActivity(new Intent(this, SMSActivity.class));
                break;
        }
    }
}

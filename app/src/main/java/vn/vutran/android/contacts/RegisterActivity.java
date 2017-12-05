package vn.vutran.android.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;

import android.app.AlertDialog;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnRegister, btnLinkToLoginScreen;
    EditText edtPhone, edtPassword, edtName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        addControls();
        addEvents();
    }

    public void addEvents() {
        btnLinkToLoginScreen.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    public void addControls() {
        btnRegister = findViewById( R.id.btnRegister );
        edtPhone    = findViewById( R.id.edt_rgPhone );
        edtName     = findViewById( R.id.edt_rgName );
        edtPassword = findViewById( R.id.edt_rgPassword );

        btnLinkToLoginScreen = findViewById( R.id.btnLinkToLoginScreen );
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnRegister:

                Toast.makeText(getBaseContext()," Click ", Toast.LENGTH_SHORT).show();

                String name     = edtName.getText().toString();
                String phone    = edtPhone.getText().toString();
                String password = edtPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            int error   = jsonResponse.getInt("error");
                            String msg  = jsonResponse.getString("msg");

                            Toast.makeText(getBaseContext(),"Result: " + msg, Toast.LENGTH_LONG).show();

                            if ( error == 0 ) {

                                JSONArray data = jsonResponse.getJSONArray("data");

                                Toast.makeText(getBaseContext(),"Success: \n" + data, Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);

                            } else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed: \n" + msg)
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(name, phone, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue( RegisterActivity.this );
                queue.add( registerRequest );

                break;

            case R.id.btnLinkToLoginScreen:

                startActivity(new Intent(this, LoginActivity.class));
                break;

        }
    }

    protected void onResume() {
        super.onResume();

        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {

                final TextView edtPhone     = findViewById(R.id.edt_rgPhone);
                final PhoneNumber number    = account.getPhoneNumber();

                edtPhone.setText(number == null ? null : number.toString());
                edtPhone.setEnabled(false);
                edtPhone.setFocusable(false);
            }

            @Override
            public void onError(final AccountKitError error) {
            }
        });
    }

}

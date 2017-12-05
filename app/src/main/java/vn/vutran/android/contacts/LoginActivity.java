package vn.vutran.android.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

        // Link to SMS Screen
//        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(),
//                        SMSActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });

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
                //
                break;
            case R.id.btnLinkToRegisterScreen:
                startActivity(new Intent(this, SMSActivity.class));
                break;
        }
    }
}

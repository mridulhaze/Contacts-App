package vn.vutran.android.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Button btnLinkToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        addControls();
        addEvents();
    }

    public void addEvents() {

        // Link to SMS Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        SMSActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void addControls() {

        btnLinkToRegister = findViewById(R.id.btnLinkToRegisterScreen);

    }
}

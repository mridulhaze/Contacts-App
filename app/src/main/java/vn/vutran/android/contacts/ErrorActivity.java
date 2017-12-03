package vn.vutran.android.contacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ErrorActivity extends AppCompatActivity {

    static final String HELLO_TOKEN_ACTIVITY_ERROR_EXTRA =
            "HELLO_TOKEN_ACTIVITY_ERROR_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
    }
}

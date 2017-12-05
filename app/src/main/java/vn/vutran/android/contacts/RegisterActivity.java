package vn.vutran.android.contacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;


public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    EditText edtPhone, edtPassword, edtName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        addControls();
        addEvents();
    }

    public void addEvents() {

    }

    public void addControls() {
        btnRegister = findViewById( R.id.btnRegister );
        edtPhone    = findViewById( R.id.edt_rgPhone );
        edtName     = findViewById( R.id.edt_rgName );
        edtPassword    = findViewById( R.id.edt_rgPassword );


    }

    protected void onResume() {
        super.onResume();

        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {

                final TextView phoneNumber = (TextView) findViewById(R.id.phone);
                final PhoneNumber number = account.getPhoneNumber();
                phoneNumber.setText(number == null ? null : number.toString());

            }

            @Override
            public void onError(final AccountKitError error) {
            }
        });
    }
}

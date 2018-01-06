package vn.vutran.android.contacts;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import vn.vutran.android.data.DatabaseHandler;
import vn.vutran.android.model.Contact;

public class AddContactActivity extends Activity {

    EditText    txtAddName, txtAddPhone, txtAddEmail, txtAddAddress;
    ImageView   imgAddImage;
    Button      btnAdd;

    Uri imageUri = Uri.parse("android.resource://org.intracode.contactmanager/drawable/no_user_logo.png");
    DatabaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);

        addControls();
        addEvents();

    }

    private void addEvents() {

        dba = new DatabaseHandler(AddContactActivity.this);

        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                addContact();
            }

        });

        txtAddName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btnAdd.setEnabled(String.valueOf(txtAddName.getText()).trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        imgAddImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Contact Image"), 1);

            }

        });

    }

    private void addContact() {

        Contact contact = new Contact();
        contact.setName(txtAddName.getText().toString().trim());
        contact.setPhone(txtAddPhone.getText().toString().trim());
        contact.setEmail(txtAddEmail.getText().toString().trim());
        contact.setAddress(txtAddAddress.getText().toString().trim());
        contact.setImageURI(imageUri);

        dba.addContact(contact);
        dba.close();

        Intent i = new Intent(AddContactActivity.this, MainActivity.class);
        startActivity(i);

    }

    private void addControls() {

        txtAddName   = (EditText) findViewById(R.id.txtAddName);
        txtAddPhone  = (EditText) findViewById(R.id.txtAddPhone);
        txtAddEmail     = (EditText) findViewById(R.id.txtAddEmail);
        txtAddAddress   = (EditText) findViewById(R.id.txtAddAddress);

        btnAdd      = (Button) findViewById(R.id.btnAdd);
        imgAddImage = (ImageView) findViewById(R.id.imgAddImage);

    }

    public void onActivityResult(int reqCode, int resCode, Intent data) {

        if (resCode == RESULT_OK) {
            if (reqCode == 1) {
                imageUri = data.getData();
                imgAddImage.setImageURI(data.getData());
            }
        }

    }

}

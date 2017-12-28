package vn.vutran.android.contacts;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddContactActivity extends Activity {

    private static final int EDIT = 0, DELETE = 1;

    EditText txtAddContactName, txtAddContactPhone, textAddContactEmail, txtAddContactAddress;
    ImageView addContactImage;
    Button btnAdd;
    List<Contact> Contacts = new ArrayList<Contact>();
    ListView contactListView;
    Uri imageUri = Uri.parse("android.resource://vn.vutran.android.contacts/mipmap/ic_person_outline");
    DatabaseHandler dbHandler;
    int longClickedItemIndex;
    ArrayAdapter<Contact> contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);

        addControls();
        addEvents();
    }

    private void addEvents() {

//        registerForContextMenu(contactListView);
//
//        contactListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                longClickedItemIndex = position;
//                return false;
//            }
//        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact contact = new Contact(dbHandler.getContactsCount(), String.valueOf(txtAddContactName.getText()), String.valueOf(txtAddContactPhone.getText()), String.valueOf(textAddContactEmail.getText()), String.valueOf(txtAddContactAddress.getText()), imageUri);

                if ( !contactExists(contact) ) {

                    dbHandler.createContact(contact);
                    Contacts.add(contact);
                    contactAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), String.valueOf(txtAddContactName.getText()) + " has been added to your Contacts!", Toast.LENGTH_SHORT).show();
                    return;

                }
                Toast.makeText(getApplicationContext(), String.valueOf(txtAddContactName.getText()) + " already exists. Please use a different name.", Toast.LENGTH_SHORT).show();
            }
        });

        txtAddContactName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btnAdd.setEnabled(String.valueOf(txtAddContactName.getText()).trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        addContactImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Contact Image"), 1);
            }
        });

        if (dbHandler.getContactsCount() != 0)
            Contacts.addAll(dbHandler.getAllContacts());

        populateList();
    }

    private void addControls() {

        txtAddContactName   = findViewById(R.id.txtAddContactName);
        txtAddContactPhone  = findViewById(R.id.txtAddContactPhone);
        textAddContactEmail = findViewById(R.id.textAddContactEmail);
        txtAddContactAddress = findViewById(R.id.txtAddContactAddress);

        btnAdd = findViewById(R.id.btnAdd);

        contactListView = findViewById(R.id.listView);
        addContactImage = findViewById(R.id.addContactImage);

        dbHandler = new DatabaseHandler(getApplicationContext());

    }

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        menu.setHeaderIcon(R.drawable.ic_edit_24dp);
        menu.setHeaderTitle("Contact Options");
        menu.add(Menu.NONE, EDIT, menu.NONE, R.string.mn_list_contact_edit);
        menu.add(Menu.NONE, DELETE, menu.NONE, R.string.mn_list_contact_delete);
    }

    public boolean onContextItemSelected( MenuItem item ) {
        switch (item.getItemId()) {
            case EDIT:
                // TODO: Implement editing a contact
                break;
            case DELETE:
                dbHandler.deleteContact(Contacts.get(longClickedItemIndex));
                Contacts.remove(longClickedItemIndex);
                contactAdapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);
    }

    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == RESULT_OK) {
            if (reqCode == 1) {
                imageUri = data.getData();
                addContactImage.setImageURI(data.getData());
            }
        }
    }

    private boolean contactExists(Contact contact) {
        String name = contact.getName();
        int contactCount = Contacts.size();

        for (int i = 0; i < contactCount; i++) {
            if (name.compareToIgnoreCase(Contacts.get(i).getName()) == 0)
                return true;
        }
        return false;
    }

    private void populateList() {
        contactAdapter = new ContactListAdapter();
        contactListView.setAdapter(contactAdapter);
    }

    private class ContactListAdapter extends ArrayAdapter<Contact> {
        public ContactListAdapter() {
            super (AddContactActivity.this, R.layout.item_contact, Contacts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if ( view == null )
                view = getLayoutInflater().inflate(R.layout.item_contact, parent, false);

            Contact currentContact = Contacts.get(position);

            TextView lContactName = view.findViewById(R.id.listContactName);
            lContactName.setText(currentContact.getName());

            TextView lContactPhone = view.findViewById(R.id.listContactPhone);
            lContactPhone.setText(currentContact.getPhone());

            TextView lContactEmail = view.findViewById(R.id.listContactEmail);
            lContactEmail.setText(currentContact.getEmail());

            TextView lContactAddress = view.findViewById(R.id.listContactAddress);
            lContactAddress.setText(currentContact.getAddress());

            ImageView lContactImage = view.findViewById(R.id.listContactImage);
            lContactImage.setImageURI(currentContact.getImageURI());

            return view;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

}

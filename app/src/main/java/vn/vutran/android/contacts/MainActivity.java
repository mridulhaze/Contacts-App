package vn.vutran.android.contacts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.vutran.android.data.DatabaseHandler;
import vn.vutran.android.model.Contact;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton btnFAB;
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toogle;
    NavigationView navigationView;

    ListView lvContact;
    ArrayList<Contact> dbContacts;
    ArrayAdapter contactAdapter;

    DatabaseHandler dba;

    Uri imageUri = Uri.parse("android.resource://vn.vutran.android.contacts/mipmap/ic_person_outline");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();

    }

    private void addEvents() {
        btnFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( view.getContext(), AddContactActivity.class );
                startActivity( intent );
            }
        });

        drawer.addDrawerListener(toogle);
        toogle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void addControls() {

        toolbar =  findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        btnFAB = findViewById(R.id.btnFAB);

        drawer = findViewById(R.id.drawer_layout);
        toogle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );

        navigationView = findViewById(R.id.nav_panel);

        lvContact       = (ListView) findViewById(R.id.lvContacts);
        dbContacts = new ArrayList<>();

        refreshData();

    }

    private void refreshData() {

        dbContacts.clear();
        dba = new DatabaseHandler(getApplicationContext());

        ArrayList<Contact> contactsFromDB = dba.getContacts();

        for (int i = 0; i < contactsFromDB.size(); i++) {

            int mid = contactsFromDB.get(i).getId();
            String name = contactsFromDB.get(i).getName();
            String phone = contactsFromDB.get(i).getPhone();
            String email = contactsFromDB.get(i).getEmail();
            String address = contactsFromDB.get(i).getAddress();
            Uri image = contactsFromDB.get(i).getImageURI();

            Contact contact = new Contact();
            contact.setName(name);
            contact.setPhone(phone);
            contact.setEmail(email);
            contact.setAddress(address);
            contact.setImageURI(image);
            contact.setId(mid);

            dbContacts.add(contact);

        }

        dba.close();

        contactAdapter = new ContactAdapter(MainActivity.this, R.layout.item_contact, dbContacts);
        lvContact.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (id == R.id.nav_login) {

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity( intent );
            drawer.closeDrawer(GravityCompat.START);

        } else if (id == R.id.nav_import) {

            AlertDialog.Builder mBuilder = new AlertDialog.Builder( this );
            View mView = getLayoutInflater().inflate(R.layout.options_import_contact, null);
            mBuilder.setTitle( R.string.menu_import_title );
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();

            Button btnOptsImportPhone = findViewById( R.id.btnOptsImportPhone);
            Button btnOptsImportVCard = findViewById( R.id.btnOptsImportVCard);
            Button btnOptsImportCSV = findViewById( R.id.btnOptsImportCSV);


        } else if (id == R.id.nav_sync) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public class ContactAdapter extends ArrayAdapter<Contact> {

        Activity activity;
        int layoutResource;
        ArrayList<Contact> mData;

        Contact contact;

        @Override
        public int getCount() {
            return mData.size();
        }

        @Nullable
        @Override
        public Contact getItem(int position) {
            return mData.get(position);
        }

        @Override
        public int getPosition(@Nullable Contact item) {
            return super.getPosition(item);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View row = convertView;
            ViewHolder holder = null;

            if ( row == null || (row.getTag()) == null){

                LayoutInflater inflater = LayoutInflater.from(activity);

                row = inflater.inflate(layoutResource, null);
                holder = new ViewHolder();

                holder.name = (TextView) row.findViewById(R.id.lName);
                holder.phone = (TextView) row.findViewById(R.id.lPhone);
                holder.email = (TextView) row.findViewById(R.id.lEmail);
                holder.adddress = (TextView) row.findViewById(R.id.lAddress);
                holder.avatar   = (ImageView) row.findViewById(R.id.lAvatar);

                row.setTag(holder);

            }else {

                holder = (ViewHolder) row.getTag();
            }

            holder.contact = getItem(position);

            holder.name.setText(holder.contact.getName());
            holder.phone.setText(holder.contact.getPhone());
            holder.email.setText(holder.contact.getEmail());
            holder.adddress.setText(holder.contact.getAddress());
            holder.avatar.setImageURI(holder.contact.getImageURI());

            return row;

        }

        public ContactAdapter(@NonNull Activity act, int resource, @NonNull ArrayList<Contact> data) {
            super(act, resource, data);

            activity = act;
            layoutResource = resource;
            mData = data;

            notifyDataSetChanged();

        }

        class ViewHolder{

            Contact contact;
            int id;
            TextView name;
            TextView phone;
            TextView email;
            TextView adddress;
            ImageView avatar;

        }

    }

}

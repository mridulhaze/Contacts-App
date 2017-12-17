package vn.vutran.android.contacts;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton btnFAB;
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toogle;
    NavigationView navigationView;

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
}

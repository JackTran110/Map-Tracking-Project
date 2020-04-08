package com.example.finalproject.ny_nguyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.finalproject.ApplicationHome;
import com.example.nasaearthyimage.R;
import com.google.android.material.navigation.NavigationView;
/**
 *
 * @author Ny Nguyen
 */
public class TheGuardianMainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    /**
     * This class is used to navigate the View of main page after user click on the option of this application
     */
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set the navigation header, toolbar, navigation menu
        super.onCreate(savedInstanceState);
        setContentView(R.layout.the_guardian_main_layout);
        Toolbar myToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        // getSupportActionBar().setDisplayShowTitleEnabled(false);// Remove title
        getSupportActionBar().setTitle("The Guardian");
        myToolbar.setSubtitle("www.theguardian.com");
        myToolbar.setLogo(R.drawable.logo);

        // for NavigationDrawer:
        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // to register for NavigationItem Selection events:
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu: items are added to the action bar if it is present.
        getMenuInflater().inflate(R.menu.the_guardian_toolbar_main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        String display = null;
        // handle presses on the action bar items:
        switch (item.getItemId()){
            case R.id.helpItem:
               AlertDialog.Builder alert = new AlertDialog.Builder(this);
               alert.setMessage("Please use the menu on the right side to start your search!");
               AlertDialog alertDialog = alert.create();
               alertDialog.show();
        }
        Toast.makeText(this,display, Toast.LENGTH_SHORT).show();
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // handle navigation view items:
        switch (menuItem.getItemId()){
            case R.id.searchPage:
                try{
                    Intent chatInt = new Intent(TheGuardianMainPage.this, TheGuardianNavigationDrawer.class);
                    startActivity(chatInt);
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case R.id.finalProject:

                Intent weatherInt = new Intent(TheGuardianMainPage.this, ApplicationHome.class);
                startActivity(weatherInt);
                break;
        }
        drawer.closeDrawers();
        return false;
    }
}
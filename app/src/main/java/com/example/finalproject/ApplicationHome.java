package com.example.finalproject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finalproject.abraham_mansour.NasaImageOfTheDay;
import com.example.finalproject.phong_tran.InformationPage;
import com.example.nasaearthyimage.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ApplicationHome extends AppCompatActivity {
    private static String TO_GUARDIAN, TO_DAY_IMAGE, TO_EARTH_IMAGE, TO_BBC_NEWS;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_home);

        ListView listView = findViewById(R.id.mainMenuList);
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigationView = findViewById(R.id.navigationView);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        List<MainMenuItem> list = new ArrayList<>();
        MainMenuAdapter adapter = new MainMenuAdapter(this, R.layout.application_home_menu_item, list);
        TO_GUARDIAN = getText(R.string.toGuardian).toString();
        TO_DAY_IMAGE = getText(R.string.toDayImage).toString();
        TO_EARTH_IMAGE = getText(R.string.toEarthImage).toString();
        TO_BBC_NEWS = getText(R.string.toBBCNews).toString();

        listView.setAdapter(adapter);
        list.add(new MainMenuItem(getDrawable(R.drawable.the_guardian), TO_GUARDIAN));
        list.add(new MainMenuItem(getDrawable(R.drawable.nasa), TO_DAY_IMAGE));
        list.add(new MainMenuItem(getDrawable(R.drawable.earth), TO_EARTH_IMAGE));
        list.add(new MainMenuItem(getDrawable(R.drawable.bbc_news), TO_BBC_NEWS));
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            MainMenuItem mainMenuItem = (MainMenuItem) parent.getAdapter().getItem(position);
            moveToActivities(mainMenuItem.getContent());
        });
    }

    private void moveToActivities(String activityName){
        AtomicReference<Intent> intent = new AtomicReference<>(new Intent());
        if(activityName.equals(TO_GUARDIAN)){

        }
        if(activityName.equals(TO_DAY_IMAGE)){
            intent.set(new Intent(this, NasaImageOfTheDay.class));
            startActivity(intent.get());
        }
        if(activityName.equals(TO_EARTH_IMAGE)){
            intent.set(new Intent(this, InformationPage.class));
            startActivity(intent.get());
        }
        if(activityName.equals(TO_BBC_NEWS)){

        }
    }

    private boolean onNavigationItemSelected(MenuItem menuItem) {
        moveToActivities(menuItem.getTitle().toString());
        if (menuItem.getTitle().equals(getString(R.string.help))){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.helpTitle);
            builder.setMessage(R.string.applicationHelpMessage);
            builder.create().show();
        }
        drawerLayout.closeDrawers();
        return true;
    }

    private class MainMenuItem {
        private Drawable icon;
        private String content;

        public MainMenuItem(Drawable icon, String content){
            setContent(content);
            setIcon(icon);
        }

        private void setContent(String content) {
            this.content = content;
        }

        private void setIcon(Drawable icon) {
            this.icon = icon;
        }

        Drawable getIcon() {
            return icon;
        }

        private String getContent() {
            return content;
        }
    }

    private class MainMenuAdapter extends ArrayAdapter<MainMenuItem>{
        private Context context;
        private int layout;
        private List<MainMenuItem> menuList;

        public MainMenuAdapter(Context context, int layout, List<MainMenuItem> menuList){
            super(context, layout, menuList);
            this.context = context;
            this.layout = layout;
            this.menuList = menuList;
        }

        @Override
        public int getCount() {
            return menuList.size();
        }

        @Override
        public MainMenuItem getItem(int position) {
            return menuList.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            ImageView imageView = convertView.findViewById(R.id.itemIcon);
            TextView textView = convertView.findViewById(R.id.itemContent);

            imageView.setImageDrawable(getItem(position).getIcon());
            textView.setText(getItem(position).getContent());
            return convertView;
        }
    }
}


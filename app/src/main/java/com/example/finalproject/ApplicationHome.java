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
import com.example.finalproject.ny_nguyen.TheGuardianMainPage;
import com.example.finalproject.phong_tran.InformationPage;
import com.example.finalproject.trang_nguyen.BBCNewsMain;
import com.example.nasaearthyimage.R;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This class is the main activity of this application.
 *
 * @author Ny Nguyen, Mansour Abraham, Phong Tran, Trang Nguyen.
 * @version 1.0.0.
 */
public class ApplicationHome extends AppCompatActivity {
    /**
     * Key of the value "to The Guardian news".
     */
    private static String TO_GUARDIAN;

    /**
     * Key of the value "to image of the day".
     */
    private static String TO_DAY_IMAGE;

    /**
     * Key of the value "to earth image".
     */
    private static String TO_EARTH_IMAGE;

    /**
     * Key of the value "to BBC news".
     */
    private static String TO_BBC_NEWS;

    /**
     * Drawer layout of this activity.
     */
    private DrawerLayout drawerLayout;

    /**
     * This method overrides its super class's method.
     * <p>
     *     In this class. The user can enter every activity, through the main list view or through the navigation view.
     *     User can see information of this application by selecting the "help" menu item.
     * </p>
     *
     * @param savedInstanceState a Bundle Object of saved instance state.
     */
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

    /**
     * This method setups menu for the list view and the navigation bar.
     *
     * @param activityName Name of the selected activity.
     */
    private void moveToActivities(String activityName){
        AtomicReference<Intent> intent = new AtomicReference<>(new Intent());
        if(activityName.equals(TO_GUARDIAN)){
            intent.set(new Intent(this, TheGuardianMainPage.class));
            startActivity(intent.get());
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
            intent.set(new Intent(this, BBCNewsMain.class));
            startActivity(intent.get());
        }
    }

    /**
     * This method modifies the navigation view of this class.
     *
     * @param menuItem the selected menu item.
     * @return a boolean if the navigation view is setup successfully.
     */
    private boolean onNavigationItemSelected(MenuItem menuItem) {
        try {
            moveToActivities(menuItem.getTitle().toString());
            if (menuItem.getTitle().equals(getString(R.string.help))) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.helpTitle);
                builder.setMessage(R.string.applicationHelpMessage);
                builder.create().show();
            }
            drawerLayout.closeDrawers();
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * This class represents the menu items of the list view.
     */
    private class MainMenuItem {
        /**
         * This is the icon of the memu item.
         */
        private Drawable icon;

        /**
         * this is the title of the menu item.
         */
        private String content;

        /**
         * This constructor is used to create a MainMenuItem Object and setup the fields.
         *
         * @param icon icon of the menu item.
         * @param content title of the menu item.
         */
        public MainMenuItem(Drawable icon, String content){
            setContent(content);
            setIcon(icon);
        }

        /**
         * This method is used to get the icon of the menu item.
         *
         * @return the icon of the menu item.
         */
        Drawable getIcon() {
            return icon;
        }

        /**
         * This method is used to get the title of the menu item.
         *
         * @return the title of the menu item.
         */
        private String getContent() {
            return content;
        }

        /**
         * This method is used to set icon for the menu item.
         *
         * @param icon icon of the menu item.
         */
        private void setIcon(Drawable icon) {
            this.icon = icon;
        }

        /**
         * This method is used to set title for the menu item.
         *
         * @param content title of the menu item.
         */
        private void setContent(String content) {
            this.content = content;
        }
    }

    /**
     * This class is extended from ArrayAdapter class. It can adapt the MainMenuItems to the device's list view.
     */
    private class MainMenuAdapter extends ArrayAdapter<MainMenuItem>{
        /**
         * The parent context.
         */
        private Context context;

        /**
         * The layout of MainMenuItems.
         */
        private int layout;

        /**
         * The list contains the MainMenuItems.
         */
        private List<MainMenuItem> menuList;

        /**
         * This constructor is used to create an adapter and set its fields.
         *
         * @param context the parent context.
         * @param layout the layout of MainMenuItems.
         * @param menuList the list contains the MainMenuItems.
         */
        public MainMenuAdapter(Context context, int layout, List<MainMenuItem> menuList){
            super(context, layout, menuList);
            this.context = context;
            this.layout = layout;
            this.menuList = menuList;
        }

        /**
         * This method is used to get count of the menu items.
         *
         * @return count of menu items.
         */
        @Override
        public int getCount() {
            return menuList.size();
        }

        /**
         * This method is used to get the menu item of the given position.
         *
         * @param position of the menu item.
         * @return the menu item of the given position.
         */
        @Override
        public MainMenuItem getItem(int position) {
            return menuList.get(position);
        }

        /**
         * This method is used to get the item view of the selected position.
         *
         * @param position position of the item view
         * @param convertView the view that about to be converted.
         * @param parent parent view group.
         * @return the view of the position.
         */
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


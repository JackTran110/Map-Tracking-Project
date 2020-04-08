package com.example.finalproject.ny_nguyen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.finalproject.ProjectDatabase;
import com.example.nasaearthyimage.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
/**
 *
 * @author Ny Nguyen
 */

public class TheGuardianArticleList extends AppCompatActivity {
    /**
     * This class is used to pass data into UI components
     */
    ProgressBar progressBar;
    View container;
    Toolbar toolbar;
    FloatingActionButton searchBtn;

    List<TheGuardianArticle> listArticle;
    ListView listView;
    ProjectDatabase db;
    TheGuardianAdapter adapter;

    @SuppressLint("RestrictedApi")
    @Override
    // passing data for activities:
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.the_guardian_list_view_template);
        setContentView(R.layout.the_guardian_list_view_template);

        this.db = new ProjectDatabase(this, 1);

        this.listView = findViewById(R.id.list_view);
        this.progressBar = findViewById(R.id.progress_bar);
        this.container = findViewById(R.id.list_container);
        this.toolbar = findViewById(R.id.list_toolbar);
        this.searchBtn = findViewById(R.id.search_btn);


        this.listArticle = (List<TheGuardianArticle>) getIntent().getSerializableExtra(TheGuardianSearchFragment.ARTICLE_LIST);
        this.adapter = new TheGuardianAdapter(this.listArticle, this);

        this.listView.setAdapter(this.adapter);
        this.progressBar.setVisibility(View.INVISIBLE);
        this.toolbar.setVisibility(View.VISIBLE);
        this.searchBtn.setVisibility(View.INVISIBLE);
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationOnClickListener(e -> finish());
        getSupportActionBar().setTitle(getString(R.string.all_related_article));

        registerForContextMenu(listView);
        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Intent intent = new Intent(this, TheGuardianDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(TheGuardianSearchFragment.ARTICLE, this.listArticle.get(position));
            bundle.putBoolean(TheGuardianSearchFragment.FAVORITE, Boolean.TRUE);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.list_view) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.list_item_menu, menu);
        }
    }
    //set execution to add favorite article into the list
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_favorite_context_menu:
                TheGuardianArticle article = listArticle.get(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
                long id = TheGuardianArticle.insert(db, article);
                article.setId(id);

                listArticle.add(article);
                this.adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}


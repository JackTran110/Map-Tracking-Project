package com.example.finalproject.ny_nguyen;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.finalproject.ProjectDatabase;
import com.example.nasaearthyimage.R;
/**
 *
 * @author Ny Nguyen
 */

public class TheGuardianDetailActivity extends AppCompatActivity {
    /**
     * This class create a view for selected article in the searching list
     */
    TextView title;
    TextView section;
    TextView link;
    Toolbar toolbar;
    TheGuardianArticle article;
    ProjectDatabase db;
    // pass data to the TextViews
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.the_guardian_article_detail);

        this.db = new ProjectDatabase(this, 1);
        this.article = (TheGuardianArticle) getIntent().getSerializableExtra(TheGuardianSearchFragment.ARTICLE);

        this.title = findViewById(R.id.guardian_article_title);
        this.section = findViewById(R.id.guardian_article_section);
        this.link = findViewById(R.id.guardian_article_link);
        this.toolbar = findViewById(R.id.article_detail_toolbar);

        this.title.setText(this.article.getTitle());
        this.section.setText(this.article.getSection());
        this.link.setText(this.article.getUrl());

        if (getIntent().getBooleanExtra(TheGuardianSearchFragment.FAVORITE, Boolean.FALSE)) {
            setSupportActionBar(this.toolbar);
        }
        this.toolbar.setNavigationOnClickListener(e -> finish());
    }
    // set the item menu from toolbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.the_guardian_toolbar_item_menu, menu);
        return true;
    }
    // set execution for add button in the toolbar menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_to_favorite_btn:
                TheGuardianArticle.insert(db, this.article);
                Toast.makeText(this, getString(R.string.The_article_is_added_to_favorite), Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }
        return true;
    }
}

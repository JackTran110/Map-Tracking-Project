package com.example.finalproject.ny_nguyen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.finalproject.ProjectDatabase;
import com.example.nasaearthyimage.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 *
 * @author Ny Nguyen
 */
public class TheGuardianSearchFragment extends Fragment {
    /**
     * This is a program for search articles on the guardian website using input keywords
     */
    // declare Static variables
    public static String ARTICLE_LIST = "news_list";
    public static String ARTICLE = "news";
    public static String FAVORITE  = "allow_favorite";
    // declare the objects
    private FloatingActionButton searchBtn;
    private View root;
    private ProjectDatabase db;
    private List<TheGuardianArticle> articles;
    private TheGuardianAdapter adapter;
    private ListView listView;
    private ProgressBar progressBar;
    private View container;
    private SharedPreferences sharedPr;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * This is onCreateView method which is creates and return the view hierachy associated with the fragment.
         */
        db = new ProjectDatabase (getContext(), 1);
        root = inflater.inflate(R.layout.the_guardian_list_view_template
                , container, false);

        progressBar = root.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        sharedPr = getActivity().getPreferences(Context.MODE_PRIVATE);

        this.container = root.findViewById(R.id.list_container);
        articles = TheGuardianArticle.getAll(db);

        adapter = new TheGuardianAdapter(articles, getContext());

        listView = root.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            TheGuardianArticle idxArticle = articles.get(position);
            Intent intent = new Intent(getContext(), TheGuardianDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ARTICLE, idxArticle);
            bundle.putBoolean(FAVORITE, Boolean.FALSE);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        // Using AlertDialog for onItemLongClickListener when user want to delete the article in the articles list.
        listView.setOnItemLongClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(getString(R.string.delete_this_guardian_news));
            builder.setPositiveButton(getText(R.string.OK), (dialog, which) -> {
                TheGuardianArticle.delete(this.db, id);
                this.articles.remove(position);
                this.adapter.notifyDataSetChanged();
                Snackbar.make(getActivity().findViewById(R.id.drawer_layout), R.string.deleted_success, Snackbar.LENGTH_SHORT).show();
            }).setNegativeButton(getText(R.string.cancel), null);
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        });
        // set AlertDialog for onClickListener when user click on the search button to start searching.
        searchBtn = root.findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(e -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(getString(R.string.search_title));
            final EditText input = new EditText(getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            // SharePreference:
            input.setText(sharedPr.getString(getString(R.string.share_keywords), null));
            // set OK button
            builder.setPositiveButton(getText(R.string.OK), (dialog, which) -> {
                //for sharePreference
                SharedPreferences.Editor edit = sharedPr.edit();
                edit.putString(getString(R.string.share_keywords), input.getText().toString());
                edit.commit();
                // set execution for OK button
                new TheGuardianGet(getContext(), this.progressBar, this.container, input.getText().toString()).execute();
            })
                 // set execution for CANCEL button
                    .setNegativeButton(getText(R.string.cancel), null);
            AlertDialog dialog = builder.create();
            dialog.setView(input, 50, 0, 50, 0);
            dialog.show();
        });

        return root;
    }

    /**
     * the onResume() method to continuing the view on the screen when it is called back.
     */
    @Override
    public void onResume() {
        this.articles = TheGuardianArticle.getAll(this.db);
        this.adapter.setArticles(this.articles);
        this.adapter.notifyDataSetChanged();
        super.onResume();
    }
}

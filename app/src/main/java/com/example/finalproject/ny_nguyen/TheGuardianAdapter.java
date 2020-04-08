package com.example.finalproject.ny_nguyen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nasaearthyimage.R;

import java.util.List;
/**
 *
 * @author Ny Nguyen
 */

public class TheGuardianAdapter extends BaseAdapter {
    /**
     * This class helps fill the data into UI component (Adapter view such as ListView, TextView)
     */
    private Context context;
    private List<TheGuardianArticle> articles;

    public TheGuardianAdapter(List<TheGuardianArticle> list, Context context) {
        /**
         * the constructor with 2 parameters
         * @param List,Context
         */
        setArticles(list);
        setContext(context);
    }

    /**
     * set methods
     */
    public void setContext(Context context) {
        this.context = context;
    }


    public void setArticles(List<TheGuardianArticle> articles) {
        this.articles = articles;
    }

    /**
     * get methods to hold the data
     */
    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public TheGuardianArticle getItem(int position) {
        return articles.get(position);
    }

    @Override
    public long getItemId(int position) {
        Long id = articles.get(position).getId();
        return id == null ? 0 : id;
    }

    /**
     * send data to Adapter views
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.the_guardian_article_list, parent, false);
        }

        TextView titleView = convertView.findViewById(R.id.item_title);
        TextView sectionView = convertView.findViewById(R.id.item_section);

        titleView.setText(articles.get(position).getTitle());
        sectionView.setText(articles.get(position).getSection());

        return convertView;
    }
}

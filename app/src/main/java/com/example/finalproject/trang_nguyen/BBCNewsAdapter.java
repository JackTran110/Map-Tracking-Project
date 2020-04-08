package com.example.finalproject.trang_nguyen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nasaearthyimage.R;

import java.util.List;


/**
 * @author Trang
 * This class extends BaseAdapter that will be used after for the list view.
 * */
class BBCNewsAdapter extends BaseAdapter {
    private List<BBCNews> newsList;
    private Context ctx;

    /**
     * public constructor with two parameters
     * @param ctx context
     * @param list BBCNews list
     */
    public BBCNewsAdapter(Context ctx, List<BBCNews> list) {
        setNewsList(list);
        this.ctx = ctx;
    }

    /**
     * public method that set a new BBCNews list
     * @param newsList list of BBCNews
     */
    public void setNewsList(List<BBCNews> newsList) {
        this.newsList = newsList;
    }

    /**
     * public method return size of the list
     * @return size of list
     */
    @Override
    public int getCount() {
        return newsList.size();
    }

    /**
     * public method return a position of an item in a list
     * @param position position
     * @return position in list
     */
    @Override
    public BBCNews getItem(int position) {
        return newsList.get(position);
    }

    /**
     * public method return id of item in a list
     * @param position position
     * @return item id
     */
    @Override
    public long getItemId(int position) {
        Long res = newsList.get(position).getId();
        return res == null ? 0 : res;
    }

    /**
     * public method that do the view for this adapter class, it does all the layout, and set data works.
     * @param position position
     * @param convertView view
     * @param parent parent
     * @return view of the adapter
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this.ctx);
            convertView = inflater.inflate(R.layout.layout_bbc_item, parent, false);
        }

        TextView titleView = convertView.findViewById(R.id.titleBBCNews);
        TextView dateView = convertView.findViewById(R.id.dateBBCNews);

        BBCNews news = getItem(position);
        titleView.setText(news.getTitle());
        dateView.setText(news.getDate());
        return convertView;
    }


}


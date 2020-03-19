package com.example.finalproject.phong_tran;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nasaearthyimage.R;

import java.util.List;

public class EarthImageAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<EarthImage> list;

    public EarthImageAdapter(Context context, int layout, List<EarthImage> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public EarthImage getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);

        TextView lon = convertView.findViewById(R.id.lonValue);
        TextView lat = convertView.findViewById(R.id.latValue);
        TextView date = convertView.findViewById(R.id.date);
        ImageView earthImage = convertView.findViewById(R.id.earthImage);

        lon.setText(String.format("%s %s.", lon.getText(), getItem(position).getLon()));
        lat.setText(String.format("%s %s.", lat.getText(), getItem(position).getLat()));
        date.setText(String.format("%s %s.", date.getText(), getItem(position).getDate()));
        earthImage.setImageBitmap(getItem(position).getEarthImageBitmap());

        return convertView;
    }
}

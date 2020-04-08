package com.example.finalproject.phong_tran;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.nasaearthyimage.R;
import java.util.List;

/**
 * This class is extended from BaseAdapter class. This adapter can adapt information of Earth Image Objects to the device's list view.
 *
 * @author Phong Tran.
 * @version 1.0.0.
 */
public class EarthImageAdapter extends BaseAdapter {

    /**
     * This Context field represents the parent activity which is using the adapter.
     */
    private Context context;

    /**
     * This int field is the id of the layout that will be converted to the device's list view.
     */
    private int layout;

    /**
     * This field is  a list of Earth Image Objects.
     */
    private List<EarthImage> list;

    /**
     * This constructor is used to create an EarthImageAdapter Object and set every fields.
     *
     * @param context the parent activity.
     * @param layout the layout of each item.
     * @param list the list contain the Earth Images.
     */
    public EarthImageAdapter(Context context, int layout, List<EarthImage> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    /**
     * This method is used to get the count of the items.
     *
     * @return the count of the list view.
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * This method is used to get the item of the given position.
     *
     * @param position position of the item.
     * @return The item of the given position.
     */
    @Override
    public EarthImage getItem(int position) {
        return list.get(position);
    }

    /**
     * This method is used to get the item's id of the given position.
     *
     * @param position position of the item.
     * @return The item's id of the given position.
     */
    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    /**
     * This method is used to get the view of the given position.
     *
     * @param position position od the view.
     * @param convertView the view that about to be converted.
     * @param parent the parent view group.
     * @return the view of the given position.
     */
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

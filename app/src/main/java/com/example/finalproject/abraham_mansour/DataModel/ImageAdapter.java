package com.example.finalproject.abraham_mansour.DataModel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nasaearthyimage.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImagesViewHolder> {
    private Context mCtx;
    private List<listDataModel> imagesList;
    AppDatabase db;

    public ImageAdapter(Context mCtx, List<listDataModel> imagesList, AppDatabase db) {
        this.mCtx = mCtx;
        this.imagesList = imagesList;
        this.db = db;
    }

    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_images, parent, false);
        return new ImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImagesViewHolder holder, final int position) {
        listDataModel t = imagesList.get(position);
        holder.tvDate.setText(t.getImageDate());

        Bitmap bitmap = BitmapFactory.decodeFile(t.getImagePath());
        holder.savedImage.setImageBitmap(bitmap);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final listDataModel imageObj = imagesList.get(position);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                // and deleting
                                db.listDao().delete(imageObj);
                            }
                        });
                    }
                });
                imagesList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, imagesList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    class ImagesViewHolder extends RecyclerView.ViewHolder{

        TextView tvDate;
        ImageView savedImage, delete;

        public ImagesViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            savedImage = itemView.findViewById(R.id.SavedImage);
            delete = itemView.findViewById(R.id.Delete);
        }
    }
}

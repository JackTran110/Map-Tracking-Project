package com.example.finalproject.abraham_mansour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.finalproject.abraham_mansour.DataModel.ImageAdapter;
import com.example.finalproject.abraham_mansour.DataModel.listDataModel;
import com.example.finalproject.abraham_mansour.DataModel.AppDatabase;
import com.example.finalproject.abraham_mansour.DataModel.DatabaseClient;
import com.example.nasaearthyimage.R;

import java.util.List;

public class PreviusRecord extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previus_record);

        recyclerView = findViewById(R.id.recyclerview_images);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getList();
    }

    private void getList() {
        class GetImages extends AsyncTask<Void, Void, List<listDataModel>> {

            @Override
            protected List<listDataModel> doInBackground(Void... voids) {
                List<listDataModel> imagesList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .listDao()
                        .getAll();
                return imagesList;
            }

            @Override
            protected void onPostExecute(List<listDataModel> images) {
                super.onPostExecute(images);
                AppDatabase db = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase();

                ImageAdapter adapter = new ImageAdapter(PreviusRecord.this, images, db);
                recyclerView.setAdapter(adapter);
            }
        }

        GetImages gi = new GetImages();
        gi.execute();
    }

    public void deleteTask(final listDataModel image) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .listDao()
                        .delete(image);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();

    }
}

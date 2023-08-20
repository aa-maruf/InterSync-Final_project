package com.example.project_login_regetration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_login_regetration.Model.Data;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class allJob extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_job);

        recyclerView = findViewById(R.id.recycler_job_all_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();

        CollectionReference publicJobPostCollection = db.collection("PublicJobPostings");

        Query query = publicJobPostCollection.orderBy("date", Query.Direction.DESCENDING);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<Data> dataList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Data data = document.toObject(Data.class);
                    dataList.add(data);
                }
                adapter = new MyAdapter(this, dataList);
                recyclerView.setAdapter(adapter);
            } else {
                Log.e("allJob", "Error getting documents: " + task.getException());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if a new job was posted from postJob activity
        if (getIntent().hasExtra("postedJob")) {
            Data postedJob = getIntent().getParcelableExtra("postedJob");
            if (postedJob != null && adapter != null) {
                // Add the posted job to the list and update the adapter
                adapter.getDataList().add(postedJob);
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void openDetailsActivity(Data data) {
        Intent intent = new Intent(this, JobDetailsActivity.class);
        intent.putExtra("jobData", data);
        startActivity(intent);
    }
}

package com.example.project_login_regetration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_login_regetration.Model.Data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class postJob extends AppCompatActivity {

    private FloatingActionButton fabBtn;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private CollectionReference jobPostCollection;
    private CollectionReference publicJobPostCollection;

    private ArrayList<Data> dataList;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);

        fabBtn = findViewById(R.id.fab_add);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uId = mUser.getUid();

        db = FirebaseFirestore.getInstance();
        jobPostCollection = db.collection("JobPost").document(uId).collection("UserJobs");
        publicJobPostCollection = db.collection("PublicJobPostings"); // Added for public collection

        dataList = new ArrayList<>();
        adapter = new MyAdapter(dataList);

        recyclerView = findViewById(R.id.recycler_job_post_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), insert_job_post.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        jobPostCollection
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    dataList.clear();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Data data = documentSnapshot.toObject(Data.class);
                        dataList.add(data);
                        saveToPublicCollection(data); // Save to public collection
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to fetch job data.", Toast.LENGTH_SHORT).show();
                });
    }

    private void saveToPublicCollection(Data jobData) {
        // Check if the jobData already exists in the public collection
        publicJobPostCollection
                .whereEqualTo("title", jobData.getTitle()) // Assuming title is a unique identifier
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (querySnapshot.isEmpty()) {
                        // Job data doesn't exist in public collection, add it
                        publicJobPostCollection.add(jobData);
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle error if necessary
                });
    }


    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        ArrayList<Data> dataList;

        public MyAdapter(ArrayList<Data> dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.job_post_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Data model = dataList.get(position);
            holder.bind(model);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        // Add new data to the list
        public void addData(Data data) {
            dataList.add(data);
            notifyDataSetChanged();
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView jobNameTextView;
        private TextView jobTitleTextView;
        private TextView jobDateTextView;
        private TextView jobDescriptionTextView;
        private TextView jobSkillsTextView;
        private TextView jobSalaryTextView;
        private TextView jobTEmailTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            jobNameTextView = itemView.findViewById(R.id.job_name);
            jobTitleTextView = itemView.findViewById(R.id.job_title);
            jobDateTextView = itemView.findViewById(R.id.job_post_date);
            jobDescriptionTextView = itemView.findViewById(R.id.job_descreption);
            jobSkillsTextView = itemView.findViewById(R.id.job_skills);
            jobTEmailTextView = itemView.findViewById(R.id.job_email);

            jobSalaryTextView = itemView.findViewById(R.id.job_salary);
        }

        public void bind(Data data) {
            jobNameTextView.setText("Company: " + data.getName());
            jobTitleTextView.setText("Job Title: " + data.getTitle());
            jobDateTextView.setText(data.getDate());
            jobDescriptionTextView.setText("Description: " + data.getDescription());
            jobSkillsTextView.setText("Skills: " + data.getSkills());
            jobTEmailTextView.setText("Email: " + data.getEmail());

            jobSalaryTextView.setText("Salary: " + data.getSalary());
        }
    }

    // After successfully posting the job, navigate to the allJob activity
    private void navigateToAllJobActivity(Data postedJobData) {
        Intent intent = new Intent(getApplicationContext(), allJob.class);
        intent.putExtra("postedJob", postedJobData);
        startActivity(intent);
    }
}




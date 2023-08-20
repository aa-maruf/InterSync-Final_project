package com.example.project_login_regetration;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_login_regetration.Model.Data;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Date;

public class insert_job_post extends AppCompatActivity {

    private EditText job_name;
    private EditText job_title;
    private EditText job_description;
    private EditText job_skills;
    private EditText job_hours;
    private EditText job_email;
    private EditText job_phone;
    private EditText job_salary;

    private Button btn_post_job;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_job_post);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        InsertJob();
    }

    private void InsertJob() {
        job_name = findViewById(R.id.job_name);
        job_title = findViewById(R.id.job_title);
        job_description = findViewById(R.id.job_descreption);
        job_skills = findViewById(R.id.job_skills);
        job_hours = findViewById(R.id.job_hours);
        job_email = findViewById(R.id.job_email);
        job_phone = findViewById(R.id.job_phone);
        job_salary = findViewById(R.id.job_salary);

        btn_post_job = findViewById(R.id.btn_job_post);

        btn_post_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = job_name.getText().toString().trim();
                String title = job_title.getText().toString().trim();
                String description = job_description.getText().toString().trim();
                String skills = job_skills.getText().toString().trim();
                String hours = job_hours.getText().toString().trim();
                String email = job_email.getText().toString().trim();
                String phone = job_phone.getText().toString().trim();
                String salary = job_salary.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(title) || TextUtils.isEmpty(description) || TextUtils.isEmpty(skills)
                        || TextUtils.isEmpty(hours) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)
                        || TextUtils.isEmpty(salary)) {
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser == null) {
                    // If the user is not authenticated, redirect to the login or registration screen
                    startActivity(new Intent(insert_job_post.this, Login.class));
                    finish();
                    return;
                }

                String userId = currentUser.getUid();
                String jobId = mFirestore.collection("JobPost").document().getId(); // Generate a unique ID for the job post
                String date = DateFormat.getDateInstance().format(new Date());

                Data data = new Data(name, title, date, description, skills, email, salary);

                // Create a new job post document under the user's ID within the JobPost collection
                DocumentReference jobPostRef = mFirestore.collection("JobPost").document(userId).collection("UserJobs").document(jobId);
                jobPostRef.set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Successfully posted the job", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), postJob.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Failed to post the job", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}

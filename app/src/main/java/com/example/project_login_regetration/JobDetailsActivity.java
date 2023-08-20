package com.example.project_login_regetration;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.project_login_regetration.Model.Data;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

//userEmailAddress


public class JobDetailsActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 123;
    private Uri selectedImageUri;
    private Data clickedJob;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        // Initialize UI elements
        TextView jobTitleTextView = findViewById(R.id.job_title);
        TextView jobNameTextView = findViewById(R.id.job_name);
        TextView jobDateTextView = findViewById(R.id.job_post_date);
        TextView jobDescriptionTextView = findViewById(R.id.job_descreption);
        TextView jobSkillsTextView = findViewById(R.id.job_skills);
        TextView jobEmailTextView = findViewById(R.id.job_email);
        TextView jobSalaryTextView = findViewById(R.id.job_salary);

        // Retrieve job data from intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("jobData")) {
            clickedJob = intent.getParcelableExtra("jobData");

            jobTitleTextView.setText("Title: " + clickedJob.getTitle());
            jobNameTextView.setText("Company Name: " + clickedJob.getName());
            jobDateTextView.setText("Date: " + clickedJob.getDate());
            jobDescriptionTextView.setText("Description: " + clickedJob.getDescription());
            jobSkillsTextView.setText("Skills: " + clickedJob.getSkills());
            jobEmailTextView.setText("Email: " + clickedJob.getEmail());
            jobSalaryTextView.setText("Salary: " + clickedJob.getSalary());

            // Set other job details here
        }

        // Handle "Drop Your CV" button click
        Button dropCvButton = findViewById(R.id.drop_cv_button);
        dropCvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGalleryPermissionAndOpen();
            }
        });

        // Initialize the Gmail API client


    }


    // Modify the onActivityResult() method to pass the job poster's email to sendEmailWithCV()
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();

            if (clickedJob != null) {
                uploadCVToFirestore(selectedImageUri, clickedJob.getEmail());

                // Send email with the CV as an attachment to the job poster's email
                sendEmailWithCV(selectedImageUri, clickedJob.getEmail());
            }
        }
    }





    // Inside the sendEmailWithCV method
    private void sendEmailWithCV(Uri cvUri, String recipientEmail) {
        // Get the job poster's email from the clickedJob object
        String jobPosterEmail = clickedJob.getEmail();


        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{jobPosterEmail}); // Set recipient email
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Application with CV");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Please find my CV attached.");
        emailIntent.putExtra(Intent.EXTRA_STREAM, cvUri);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send Email"));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Could not send email.", Toast.LENGTH_SHORT).show();
        }
    }













    private void checkGalleryPermissionAndOpen() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_REQUEST_CODE);
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "Permission denied, cannot access gallery.", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void uploadCVToFirestore(Uri cvUri, String jobPosterEmail) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Use the job poster's email as company ID
        String companyId = jobPosterEmail;

        // Get references to Firestore and Firebase Storage
        CollectionReference cvCollection = db.collection("CV");
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        // Upload the CV to Firebase Storage using a new auto-generated document ID as the filename
        String newCvDocumentId = cvCollection.document().getId(); // Generate a new document ID
        StorageReference cvReference = storageReference.child("CV").child(companyId).child(newCvDocumentId + ".jpg");

        // Upload the CV to Firebase Storage
        cvReference.putFile(cvUri).addOnSuccessListener(taskSnapshot -> {
            // Get the download URL for the uploaded CV
            cvReference.getDownloadUrl().addOnSuccessListener(uri -> {
                // Save the download URL in the Firestore document
                Map<String, Object> cvData = new HashMap<>();
                cvData.put("cvUrl", uri.toString());

                // Create a new document with the auto-generated ID and set the CV data
                cvCollection.document(newCvDocumentId)
                        .set(cvData)
                        .addOnSuccessListener(aVoid -> {
                            // Handle success
                            Log.d("Upload CV", "CV URL saved in Firestore successfully");
                            // Display a success message to the user
                            Toast.makeText(JobDetailsActivity.this, "Your CV uploaded", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            // Handle failure
                            Log.e("Upload CV", "Error saving CV data in Firestore: " + e.getMessage());
                            // Display a failure message to the user
                            Toast.makeText(JobDetailsActivity.this, "CV upload failed", Toast.LENGTH_SHORT).show();
                        });
            });
        }).addOnFailureListener(e -> {
            // Handle upload failure
            Log.e("Upload CV", "Upload failed: " + e.getMessage());
            // Display a failure message to the user
            Toast.makeText(JobDetailsActivity.this, "CV upload failed", Toast.LENGTH_SHORT).show();
        });
    }




}


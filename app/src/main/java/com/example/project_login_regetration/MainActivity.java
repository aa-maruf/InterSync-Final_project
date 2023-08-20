package com.example.project_login_regetration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//activity_main

        findViewById(R.id.btn_allJob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedUserEmail = "user@example.com"; // Replace with the actual selected email
                Intent intent = new Intent(MainActivity.this, allJob.class);
                intent.putExtra("userEmail", selectedUserEmail);
                startActivity(intent);
            }
        });
    }
}

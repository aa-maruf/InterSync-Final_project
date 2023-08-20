package com.example.project_login_regetration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class starting_page extends AppCompatActivity {

    Button starting_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_page);

        starting_btn = findViewById(R.id.btn_getStarted);
        starting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserORCompany.class);//UserORCompany
                startActivity(intent);
                finish();
            }
        });


    }
}
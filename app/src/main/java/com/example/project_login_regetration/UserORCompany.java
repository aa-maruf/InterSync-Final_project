package com.example.project_login_regetration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class UserORCompany extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_orcompany);

        ImageButton companySectionButton = findViewById(R.id.comapny_section);
        companySectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the Registration activity when the button is clicked
                Intent intent = new Intent(UserORCompany.this, Login.class);
                startActivity(intent);
            }
        });


        ImageButton internSectionButton = findViewById(R.id.intern_section);
        internSectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the Registration activity when the button is clicked
                Intent intent = new Intent(UserORCompany.this, User_Login.class);
                startActivity(intent);
            }
        });
    }
}

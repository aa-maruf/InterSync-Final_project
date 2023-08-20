package com.example.project_login_regetration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class forgot_password extends AppCompatActivity {

    //Declaration
    Button btnContinue;
    EditText edtEmail;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    String strEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //Initializaton

        btnContinue = findViewById(R.id.fogot_pass_button);
        edtEmail = findViewById(R.id.forgotpass_email);

        mAuth = FirebaseAuth.getInstance();

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail = edtEmail.getText().toString().trim();
                if (!TextUtils.isEmpty(strEmail)) {
                    ResetPassword();
                } else {
                    edtEmail.setError("Email field can't be empty");
                }
            }
        });

    }

        private void ResetPassword() {
            btnContinue.setVisibility(View.INVISIBLE);
            mAuth.sendPasswordResetEmail(strEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(forgot_password.this, "Reset Password link has been sent to your registerd Email", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(forgot_password.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(forgot_password.this, "Error :- " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            btnContinue.setVisibility(View.VISIBLE);
                        }
                    });





    }
}
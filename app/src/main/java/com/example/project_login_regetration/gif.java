package com.example.project_login_regetration;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

public class gif extends AppCompatActivity {

    private GifImageView gifImageView;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        gifImageView = findViewById(R.id.gif_image_view);


        //if touches then gif will be gone
//        gifImageView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // Start a new activity when the user touches the GIF
//                Intent intent = new Intent(gif.this, starting_page.class);
//                startActivity(intent);
//                return false; // Return false to allow other touch events to be processed
//            }
//        });


        //gif timer code

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(gif.this, starting_page.class);
                startActivity(intent);
                finish();
            }
        },1000);



    }
}

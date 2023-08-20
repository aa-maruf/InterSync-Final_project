package com.example.project_login_regetration;


import android.widget.ImageView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Navigation_drawer extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    ImageView imageMenu;


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_navigation_drawer);
//
//
//
//        // Navagation Drawar------------------------------
//        drawerLayout = findViewById(R.id.drawer_layout);
//        navigationView = findViewById(R.id.nav_View);
//        imageMenu = findViewById(R.id.imageMenu);
//
//        toggle = new ActionBarDrawerToggle(Navigation_drawer.this, drawerLayout, R.string.open, R.string.close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        // Drawar click event
//        // Drawer item Click event ------
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                if (item.getItemId() == R.id.mHome) {
//                    Toast.makeText(Navigation_drawer.this, "Clicked", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(Navigation_drawer.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                    drawerLayout.closeDrawers();
//                } else if (item.getItemId() == R.id.mShare) {
//                    Toast.makeText(Navigation_drawer.this, "Facebook", Toast.LENGTH_SHORT).show();
//                    drawerLayout.closeDrawers();
//                }
//                // ----changed
//                return false;
//            }
//        });
//        //------------------------------
//
//        // ------------------------
//        // App Bar Click Event
//        imageMenu = findViewById(R.id.imageMenu);
//
//        imageMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Code Here
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
//
//
//        // ------------------------
//    }


}
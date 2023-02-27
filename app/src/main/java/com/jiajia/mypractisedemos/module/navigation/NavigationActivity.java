package com.jiajia.mypractisedemos.module.navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.widget.Button;

import com.jiajia.mypractisedemos.R;

public class NavigationActivity extends AppCompatActivity {

    private FragmentContainerView mNavHost;
    private Button btnHome;
    private Button btnUser;

    // Navigation相关的
    private NavHostFragment navHostFragment;
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        mNavHost = findViewById(R.id.a_main_nav_host);
//        btnHome = findViewById(R.id.nav_bottom_home);
//        btnUser = findViewById(R.id.nav_bottom_user);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.a_main_nav_host);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

//        btnHome.setOnClickListener(v -> navController.navigate(R.id.homeFragment));
//
//        btnUser.setOnClickListener(v -> navController.navigate(R.id.userFragment));

    }
}
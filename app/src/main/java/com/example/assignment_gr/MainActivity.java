package com.example.assignment_gr;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    ImageView btnHome, btnPlace, btnsetting, btnDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        btnHome = findViewById(R.id.home);
        btnPlace = findViewById(R.id.place);
        btnsetting = findViewById(R.id.setting);

        btnHome.setOnClickListener(this::onHome);
        btnsetting.setOnClickListener(this::onSetting);


    }

    public void onHome(View v)
    {
        loadFragment(new HomeFragment());
    }
    public void onSetting(View v)
    {
        loadFragment(new SettingFragment());
    }

    public void loadFragment(Fragment fragment)
    {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
package com.example.demoapp.Log_Reg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.demoapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class Login extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton google;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //INICIALIZANDO VARIABLES
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        google = findViewById(R.id.fab_google);

        //LOGIN & REGISTER TABS
        tabLayout.addTab(tabLayout.newTab().setText("Iniciar Sesión"));
        tabLayout.addTab(tabLayout.newTab().setText("Registrarse"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //ANIMACIONES
        anim = AnimationUtils.loadAnimation(this,R.anim.fade);
        google.setTranslationY(300);
        tabLayout.setTranslationX(300);
        viewPager.setTranslationX(300);
        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        tabLayout.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        viewPager.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        google.startAnimation(anim);
        tabLayout.startAnimation(anim);
        viewPager.startAnimation(anim);
    }
}
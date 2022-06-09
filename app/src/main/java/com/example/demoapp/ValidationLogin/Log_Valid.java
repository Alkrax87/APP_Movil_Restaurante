package com.example.demoapp.ValidationLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.demoapp.Admin.AdminMenu;
import com.example.demoapp.R;
import com.example.demoapp.User.UserMenu;

public class Log_Valid extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY=5500;

    TextView msg;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_valid);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String email = getIntent().getStringExtra("n1");
        String rol = getIntent().getStringExtra("rol");

        msg = findViewById(R.id.msg);
        msg.animate().alpha(1).setDuration(1000).setStartDelay(3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (rol.equals("admin")){
                    mediaPlayer = MediaPlayer.create(Log_Valid.this,R.raw.sound);
                    mediaPlayer.start();
                    Intent intent = new Intent(Log_Valid.this, AdminMenu.class);
                    intent.putExtra("n1",email);
                    startActivity(intent);
                    finish();
                } else if(rol.equals("user")){
                    mediaPlayer = MediaPlayer.create(Log_Valid.this,R.raw.sound);
                    mediaPlayer.start();
                    Intent intent = new Intent(Log_Valid.this, UserMenu.class);
                    intent.putExtra("n1",email);
                    startActivity(intent);
                    finish();
                }
            }
        },SPLASH_SCREEN_DELAY);
    }
}
package com.example.demoapp.Items_Food_User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.demoapp.Admin.AdminMenu;
import com.example.demoapp.Items_Food.AddFood;
import com.example.demoapp.R;
import com.example.demoapp.User.UserMenu;

public class Buy extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY=6000;

    TextView msg;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        msg = findViewById(R.id.msg);
        String value = getIntent().getStringExtra("item");
        msg.setText("Compra de "+ value +" realizada con exito!");
        msg.animate().alpha(1).setDuration(1000).setStartDelay(3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mediaPlayer = MediaPlayer.create(Buy.this,R.raw.sound2);
                mediaPlayer.start();
                String email = getIntent().getStringExtra("n1");
                Intent intent = new Intent(Buy.this, UserMenu.class);
                intent.putExtra("n1",email);
                intent.putExtra("page","4");
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN_DELAY);
    }
}
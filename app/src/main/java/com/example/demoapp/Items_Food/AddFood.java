package com.example.demoapp.Items_Food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.demoapp.Admin.AdminMenu;
import com.example.demoapp.Items_Food_User.Buy;
import com.example.demoapp.R;

public class AddFood extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY=5500;

    TextView msg;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        msg = findViewById(R.id.msg);
        String value = getIntent().getStringExtra("item");
        msg.setText("Elemento: "+value+" fue agregado correctamente.");
        msg.animate().alpha(1).setDuration(1000).setStartDelay(3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mediaPlayer = MediaPlayer.create(AddFood.this,R.raw.sound2);
                mediaPlayer.start();
                String email = getIntent().getStringExtra("n1");
                Intent intent = new Intent(AddFood.this, AdminMenu.class);
                intent.putExtra("n1",email);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN_DELAY);
    }
}
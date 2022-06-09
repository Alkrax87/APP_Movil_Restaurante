package com.example.demoapp.Items_Food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.demoapp.Admin.AdminMenu;
import com.example.demoapp.R;
import com.example.demoapp.ValidationLogin.Log_Valid;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class DeleteFood extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY=5500;

    TextView msg;
    FirebaseFirestore mFireStore;
    private StorageReference storageReference;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_food);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        msg = findViewById(R.id.msg);
        mFireStore = FirebaseFirestore.getInstance();
        //==========================================================================================
        //STORAGE DELETE
        String storageitem = getIntent().getStringExtra("storage");
        storageReference = FirebaseStorage.getInstance().getReference().child("images/"+storageitem);
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        });

        //==========================================================================================
        //ITEMS DELETE
        String fooditem = getIntent().getStringExtra("title");
        mFireStore.collection("food").whereEqualTo("titulo",""+fooditem).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        Log.d("users",document.getId() + " => " + document.getData().get("nombre"));
                        String id_item = document.getId();
                        msg.setText("Elemento: "+document.getData().get("titulo").toString()+" ha sido eliminado correctamente.");
                        mFireStore.collection("food").document(id_item).delete();
                    }
                } else {
                    Log.w("users","Error", task.getException());
                }
            }
        });

        msg.animate().alpha(1).setDuration(1000).setStartDelay(3000);

        mFireStore.collection("dashboard").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){

                        String user = document.getData().get("nUsers").toString();
                        String foods = document.getData().get("nPlatos").toString();
                        String total = document.getData().get("total").toString();
                        String order = document.getData().get("nPedidos").toString();

                        int nfood = Integer.parseInt(foods);
                        nfood = nfood-1;

                        Map<String,Object> map2 = new HashMap<>();
                        map2.put("nUsers",user);
                        map2.put("nPlatos",nfood);
                        map2.put("total",total);
                        map2.put("nPedidos",order);

                        mFireStore.collection("dashboard").document("dashboardinfo").set(map2);


                    }
                } else {
                    Log.w("users","Error", task.getException());
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mediaPlayer = MediaPlayer.create(DeleteFood.this,R.raw.sound2);
                mediaPlayer.start();
                String email = getIntent().getStringExtra("n1");
                Intent intent = new Intent(DeleteFood.this, AdminMenu.class);
                intent.putExtra("n1",email);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN_DELAY);
    }
}
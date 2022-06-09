package com.example.demoapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.demoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class EditProfileAdmin extends AppCompatActivity {

    EditText nombre,apellido,telefono;
    FirebaseFirestore mFireStore;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        nombre = findViewById(R.id.inputNombre);
        apellido = findViewById(R.id.inputApellido);
        telefono = findViewById(R.id.inputTelefono);
        mFireStore = FirebaseFirestore.getInstance();
        String email = getIntent().getStringExtra("n1");
        mFireStore.collection("users").whereEqualTo("email",""+email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        nombre.setText(""+document.getData().get("nombre").toString());
                        apellido.setText(""+document.getData().get("apellido").toString());
                        telefono.setText(""+document.getData().get("telefono").toString());
                    }
                } else {
                    Log.w("users","Error", task.getException());
                }
            }
        });
    }

    public void editar(View view) {
        String email = getIntent().getStringExtra("n1");
        mFireStore.collection("users").whereEqualTo("email",""+email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        String id = document.getId();
                        String rol = document.getData().get("rol").toString();
                        String email = document.getData().get("email").toString();
                        editUser(id,rol,email);
                    }
                } else {
                    Log.w("users","Error", task.getException());
                }
            }
        });
    }

    private void editUser(String id,String rol,String email) {
        String nombreedit = nombre.getText().toString();
        String apellidoedit = apellido.getText().toString();
        String telefonoedit = telefono.getText().toString();

        Map<String,Object> map = new HashMap<>();
        map.put("nombre",nombreedit);
        map.put("apellido",apellidoedit);
        map.put("telefono",telefonoedit);
        map.put("rol",rol);
        map.put("email",email);

        mFireStore.collection("users").document(id).set(map);

        nextActivity();
    }

    private void nextActivity() {
        mediaPlayer = MediaPlayer.create(this,R.raw.sound);
        mediaPlayer.start();
        String email = getIntent().getStringExtra("n1");
        Intent intent = new Intent(this, AdminMenu.class);
        intent.putExtra("n1",email);
        intent.putExtra("page","5");
        startActivity(intent);
    }

    public void back(View view) {
        String email = getIntent().getStringExtra("n1");
        Intent intent = new Intent(this, AdminMenu.class);
        intent.putExtra("n1",email);
        intent.putExtra("page","5");
        startActivity(intent);
    }
}
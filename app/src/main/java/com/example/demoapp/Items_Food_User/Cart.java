package com.example.demoapp.Items_Food_User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoapp.Admin.AdminMenu;
import com.example.demoapp.R;
import com.example.demoapp.User.UserMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Cart extends AppCompatActivity {

    TextView tituloCompra,precioCompra,descripcionCompra;
    FirebaseFirestore mFireStore;
    ImageView img;
    CardView cart;
    Button btn;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btn = findViewById(R.id.btnLogOut2);
        cart = findViewById(R.id.cardView4);
        mFireStore = FirebaseFirestore.getInstance();
        String title = getIntent().getStringExtra("title");
        tituloCompra = findViewById(R.id.txtTitulo);
        String price = getIntent().getStringExtra("price");
        precioCompra = findViewById(R.id.txtPrice);
        String descr = getIntent().getStringExtra("description");
        descripcionCompra = findViewById(R.id.txtDescripcionCompra);
        String imgItem = getIntent().getStringExtra("img");
        storageReference = FirebaseStorage.getInstance().getReference().child("images/"+imgItem);
        img = findViewById(R.id.imgCompra);
        try {
            final File localfole = File.createTempFile(""+imgItem,"jpeg");
            storageReference.getFile(localfole).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localfole.getAbsolutePath());
                    img.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        tituloCompra.setText(title);
        precioCompra.setText("s/. "+price);
        descripcionCompra.setText(descr);


        //ANIMACIONES
        precioCompra.setAlpha(0);
        tituloCompra.setAlpha(0);
        descripcionCompra.setAlpha(0);
        cart.setAlpha(0);
        btn.setAlpha(0);

        precioCompra.setTranslationX(100);
        tituloCompra.setTranslationY(100);
        descripcionCompra.setTranslationY(100);
        btn.setTranslationY(300);

        precioCompra.animate().alpha(1).translationX(0).setDuration(1000).setStartDelay(300);
        tituloCompra.animate().alpha(1).translationY(0).setDuration(1000).setStartDelay(300);
        descripcionCompra.animate().alpha(1).translationY(0).setDuration(1000).setStartDelay(300);
        cart.animate().alpha(1).setDuration(1000).setStartDelay(1000);
        btn.animate().alpha(1).translationY(0).setDuration(1000).setStartDelay(500);


    }

    public void buy(View view) {
        String email = getIntent().getStringExtra("n1");
        mFireStore.collection("users").whereEqualTo("email",""+email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        String nombre = document.getData().get("nombre").toString();
                        String apellido = document.getData().get("apellido").toString();
                        String telefono = document.getData().get("telefono").toString();
                        String cliente  = nombre+" "+apellido+" | Telefono: "+telefono;

                        String titulo = tituloCompra.getText().toString();
                        String precio = getIntent().getStringExtra("price");
                        String img = getIntent().getStringExtra("img");
                        String user = getIntent().getStringExtra("n1");
                        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
                        String time = date.format(new Date());

                        Map<String,Object> map = new HashMap<>();
                        map.put("titulo",titulo);
                        map.put("precio",precio);
                        map.put("user",user);
                        map.put("time",time);
                        map.put("img",img);
                        map.put("cliente",cliente);


                        mFireStore.collection("orders").document().set(map);

                        dashboard();
                    }
                } else {
                    Log.w("users","Error", task.getException());
                }
            }
        });
    }

    private void dashboard() {
        mFireStore.collection("dashboard").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){

                        String user = document.getData().get("nUsers").toString();
                        String foods = document.getData().get("nPlatos").toString();
                        String total = document.getData().get("total").toString();
                        String order = document.getData().get("nPedidos").toString();
                        String precio = getIntent().getStringExtra("price");

                        int nprecio = Integer.parseInt(precio);
                        int ntotal = Integer.parseInt(total);
                        ntotal = ntotal + nprecio;
                        int norder = Integer.parseInt(order);
                        norder = norder+1;

                        Map<String,Object> map2 = new HashMap<>();
                        map2.put("nUsers",user);
                        map2.put("nPlatos",foods);
                        map2.put("total",ntotal);
                        map2.put("nPedidos",norder);

                        mFireStore.collection("dashboard").document("dashboardinfo").set(map2);


                    }
                } else {
                    Log.w("users","Error", task.getException());
                }
            }
        });

        nextActivity();
    }

    private void nextActivity() {
        Intent intent = new Intent(Cart.this,Buy.class);
        String email = getIntent().getStringExtra("n1");
        intent.putExtra("n1",email);
        intent.putExtra("item",getIntent().getStringExtra("title"));
        startActivity(intent);
    }

    public void back(View view) {
        String email = getIntent().getStringExtra("n1");
        Intent intent = new Intent(this, UserMenu.class);
        intent.putExtra("n1",email);
        startActivity(intent);
    }
}
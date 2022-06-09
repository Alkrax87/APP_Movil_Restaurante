package com.example.demoapp.Items_Food;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.demoapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {

    Context context;
    ArrayList<FoodModel> foodArrayList;
    String value;
    private StorageReference storageReference;

    public FoodAdapter(Context context, ArrayList<FoodModel> foodArrayList,String value) {
        this.context = context;
        this.foodArrayList = foodArrayList;
        this.value = value;
    }

    @NonNull
    @Override
    public FoodAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.food_admin_item,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.MyViewHolder holder, int position) {

        FoodModel food = foodArrayList.get(position);

        holder.titulo.setText(food.titulo);
        holder.descripcion.setText(food.descripcion);
        holder.precio.setText("s/. "+food.precio);
        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = value;
                Intent intent = new Intent(context, DeleteFood.class);
                intent.putExtra("n1",email);
                intent.putExtra("title",""+food.titulo);
                intent.putExtra("storage",""+food.img);
                context.startActivity(intent);
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference().child("images/"+food.img);
        try {
            final File localFile = File.createTempFile(""+food.img,"jpeg");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    holder.imagen.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo,descripcion,precio;
        Button eliminar;
        ImageView imagen;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.txtTitle);
            descripcion = itemView.findViewById(R.id.txtDescription);
            precio = itemView.findViewById(R.id.txtPrice);
            eliminar = itemView.findViewById(R.id.btnDelete);
            imagen = itemView.findViewById(R.id.imgItem);
        }
    }
}

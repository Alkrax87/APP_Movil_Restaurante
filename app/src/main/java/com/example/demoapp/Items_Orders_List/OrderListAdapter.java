package com.example.demoapp.Items_Orders_List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.Items_Orders.OrderAdapter;
import com.example.demoapp.Items_Orders.OrderModel;
import com.example.demoapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder>{

    Context context;
    ArrayList<OrderListModel> orderArrayList;
    private StorageReference storageReference;

    public OrderListAdapter(Context context, ArrayList<OrderListModel> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
    }

    @NonNull
    @Override
    public OrderListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.orders_item_admin,parent,false);

        return new OrderListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListAdapter.MyViewHolder holder, int position) {

        OrderListModel order = orderArrayList.get(position);

        holder.titulo.setText(order.titulo);
        holder.precio.setText("s/. "+order.precio);
        holder.fecha.setText(order.time);
        holder.cliente.setText(order.cliente);

        storageReference = FirebaseStorage.getInstance().getReference().child("images/"+order.img);
        try {
            final File localFile = File.createTempFile(""+order.img,"jpeg");
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
        return orderArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo,precio,fecha,cliente;
        ImageView imagen;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.txtTitle);
            precio = itemView.findViewById(R.id.txtPrice);
            fecha = itemView.findViewById(R.id.txtDate);
            cliente = itemView.findViewById(R.id.txtClient);
            imagen = itemView.findViewById(R.id.imgItem);
        }
    }
}

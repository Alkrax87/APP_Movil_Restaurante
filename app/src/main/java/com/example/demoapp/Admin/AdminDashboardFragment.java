package com.example.demoapp.Admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.demoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AdminDashboardFragment extends Fragment {

    FirebaseFirestore mFireStore;
    TextView user,foods,totalm,order;
    CardView pedidos,total,users,food;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.dashboard_admin_fragment,container,false);

        user = root.findViewById(R.id.dashusers);
        foods = root.findViewById(R.id.dashfood);
        totalm = root.findViewById(R.id.dashtotal);
        order = root.findViewById(R.id.dashorder);
        mFireStore = FirebaseFirestore.getInstance();
        mFireStore.collection("dashboard").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        user.setText(""+document.getData().get("nUsers").toString());
                        foods.setText(""+document.getData().get("nPlatos").toString());
                        totalm.setText("s/. "+document.getData().get("total").toString());
                        order.setText(""+document.getData().get("nPedidos").toString());
                    }
                } else {
                    Log.w("users","Error", task.getException());
                }
            }
        });

        pedidos = root.findViewById(R.id.pedidos);
        total = root.findViewById(R.id.total);
        users = root.findViewById(R.id.users);
        food = root.findViewById(R.id.food);

        pedidos.setAlpha(0);
        total.setAlpha(0);
        users.setAlpha(0);
        food.setAlpha(0);
        pedidos.setTranslationY(200);
        total.setTranslationY(200);
        users.setTranslationY(200);
        food.setTranslationY(200);
        pedidos.animate().alpha(1).translationY(0).setDuration(1000).setStartDelay(300);
        total.animate().alpha(1).translationY(0).setDuration(1000).setStartDelay(300);
        users.animate().alpha(1).translationY(0).setDuration(1000).setStartDelay(300);
        food.animate().alpha(1).translationY(0).setDuration(1000).setStartDelay(300);


        return root;
    }
}

package com.example.demoapp.Admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.Items_Food.FoodAdapter;
import com.example.demoapp.Items_Food.FoodModel;
import com.example.demoapp.Items_Food.CategoriesAdapter;
import com.example.demoapp.Items_Food.CategoriesModel;
import com.example.demoapp.Items_Food_User.FoodUModel;
import com.example.demoapp.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AdminHomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private CategoriesAdapter categoriesAdapter;

    FirebaseFirestore mFireStore;
    ArrayList<FoodModel> foodModels;
    FoodAdapter foodAdapter;

    TextView cate,list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.home_admin_fragment,container,false);

        mFireStore = FirebaseFirestore.getInstance();
        cate = root.findViewById(R.id.textView10);
        list = root.findViewById(R.id.textView12);

        ArrayList<CategoriesModel> item = new ArrayList<>();
        item.add(new CategoriesModel(R.drawable.icon,"Peruana"));
        item.add(new CategoriesModel(R.drawable.icon,"Mariscos"));
        item.add(new CategoriesModel(R.drawable.icon,"Bebidas"));
        item.add(new CategoriesModel(R.drawable.icon,"Sandwiches"));
        item.add(new CategoriesModel(R.drawable.icon,"Postres"));

        recyclerView = root.findViewById(R.id.rv_1);
        categoriesAdapter = new CategoriesAdapter(item);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(categoriesAdapter);

        //ANIMACION
        recyclerView.setAlpha(0);
        recyclerView.setTranslationX(500);
        recyclerView.animate().alpha(1).translationX(0).setDuration(1300).setStartDelay(500);


        //==========================================================================================
        //RECYCLER VIEW FOOD ITEMS
        RecyclerView rv_food = root.findViewById(R.id.rv_2);
        rv_food.setHasFixedSize(true);
        rv_food.setLayoutManager(new LinearLayoutManager(getActivity()));
        foodModels = new ArrayList<FoodModel>();
        foodAdapter = new FoodAdapter(getActivity(), foodModels,getActivity().getIntent().getStringExtra("n1"));
        rv_food.setAdapter(foodAdapter);
        EventChangeListener();

        //ANIMACION
        rv_food.setAlpha(0);
        rv_food.setTranslationY(500);
        rv_food.animate().alpha(1).translationY(0).setDuration(1300).setStartDelay(500);

        cate.setAlpha(0);
        list.setAlpha(0);
        cate.setTranslationX(-100);
        list.setTranslationX(-100);
        cate.animate().alpha(1).translationX(0).setDuration(1000).setStartDelay(300);
        list.animate().alpha(1).translationX(0).setDuration(1000).setStartDelay(300);

        return root;
    }

    private void EventChangeListener() {
        mFireStore.collection("food").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Log.e("FireStore error:",error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    if (dc.getType() == DocumentChange.Type.ADDED){
                        foodModels.add(dc.getDocument().toObject(FoodModel.class));
                        Collections.sort(foodModels, new Comparator<FoodModel>() {
                            @Override
                            public int compare(FoodModel foodModel, FoodModel t1) {
                                return foodModel.getTitulo().compareTo(t1.getTitulo());
                            }
                        });
                    }
                    foodAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}

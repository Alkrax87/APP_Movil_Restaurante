package com.example.demoapp.Admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.Items_Orders.OrderAdapter;
import com.example.demoapp.Items_Orders.OrderModel;
import com.example.demoapp.Items_Orders_List.OrderListAdapter;
import com.example.demoapp.Items_Orders_List.OrderListModel;
import com.example.demoapp.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AdminOrdersListFragment extends Fragment {

    FirebaseFirestore mFireStore;
    RecyclerView recyclerView;
    ArrayList<OrderListModel> orderArrayList;
    OrderListAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.orders_admin_list_fragment,container,false);

        mFireStore = FirebaseFirestore.getInstance();
        recyclerView = root.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderArrayList = new ArrayList<OrderListModel>();
        myAdapter = new OrderListAdapter(getActivity(),orderArrayList);
        recyclerView.setAdapter(myAdapter);
        EventChangeListener();

        recyclerView.setAlpha(0);
        recyclerView.setTranslationY(300);
        recyclerView.animate().alpha(1).translationY(0).setDuration(1000).setStartDelay(500);

        return root;
    }

    private void EventChangeListener() {

        mFireStore.collection("orders").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null){
                    Log.e("FireStore error:",error.getMessage());
                    return;
                }
                if (error == null){
                }

                for (DocumentChange dc : value.getDocumentChanges()){
                    if (dc.getType() == DocumentChange.Type.ADDED){
                        orderArrayList.add(dc.getDocument().toObject(OrderListModel.class));
                        Collections.sort(orderArrayList, new Comparator<OrderListModel>() {
                            @Override
                            public int compare(OrderListModel orderListModel, OrderListModel t1) {
                                return t1.getTime().compareTo(orderListModel.getTime());
                            }
                        });
                    }
                    myAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}

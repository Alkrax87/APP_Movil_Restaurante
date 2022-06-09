package com.example.demoapp.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demoapp.Admin.EditProfileAdmin;
import com.example.demoapp.Log_Reg.Login;
import com.example.demoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class UserProfileFragment extends Fragment {

    TextView name,email,phone;
    FirebaseFirestore mFireStore;
    Button logout,edit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profile_user_fragment,container,false);

        //DATOS DEL USUARIO
        name = root.findViewById(R.id.txtName);
        email = root.findViewById(R.id.txtEmail);
        phone = root.findViewById(R.id.txtPhone);
        mFireStore = FirebaseFirestore.getInstance();
        String valid = getActivity().getIntent().getStringExtra("n1");
        mFireStore.collection("users").whereEqualTo("email",""+valid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        name.setText(""+document.getData().get("nombre").toString()+" "+document.getData().get("apellido").toString());
                        phone.setText(""+document.getData().get("telefono").toString());
                        email.setText(""+document.getData().get("email").toString());
                    }
                } else {
                    Log.w("users","Error", task.getException());
                }
            }
        });

        //LOGOUT
        logout = root.findViewById(R.id.btnLogOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();
            }
        });

        //EDIT
        edit = root.findViewById(R.id.btnEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfileUser.class);
                intent.putExtra("n1",getActivity().getIntent().getStringExtra("n1"));
                startActivity(intent);
            }
        });

        name.setAlpha(0);
        phone.setAlpha(0);
        email.setAlpha(0);
        logout.setAlpha(0);
        name.setTranslationY(150);
        phone.setTranslationY(150);
        email.setTranslationY(150);
        name.animate().alpha(1).translationY(0).setDuration(1500).setStartDelay(500);
        phone.animate().alpha(1).translationY(0).setDuration(1500).setStartDelay(500);
        email.animate().alpha(1).translationY(0).setDuration(1500).setStartDelay(500);
        logout.animate().alpha(1).setDuration(1000).setStartDelay(500);

        return root;
    }
}

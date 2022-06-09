package com.example.demoapp.Log_Reg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.demoapp.R;
import com.example.demoapp.ValidationRegister.Reg_Error;
import com.example.demoapp.ValidationRegister.Reg_Valid;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SignUpFragment extends Fragment {

    EditText inputNombre, inputApellido,inputTelefono, inputEmail, inputPassword;
    Button btnRegister;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore mFireStore;
    CardView cardView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment,container,false);

        //INICIALIZANDO VARIABLES
        inputNombre = root.findViewById(R.id.Register_input_name);
        inputApellido = root.findViewById(R.id.register_input_lastname);
        inputTelefono = root.findViewById(R.id.register_input_phone);
        inputEmail = root.findViewById(R.id.register_input_email);
        inputPassword = root.findViewById(R.id.register_input_password);
        btnRegister = root.findViewById(R.id.btn_register);
        cardView = root.findViewById(R.id.lotiecontainer);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mFireStore = FirebaseFirestore.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registroNewUser();
            }
        });

        return root;
    }

    private void registroNewUser() {
        String name = inputNombre.getText().toString();
        String lastname = inputApellido.getText().toString();
        String phone = inputTelefono.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if (name.isEmpty()) {
            inputNombre.setError("Este campo no puede estar vacio.");
        } else if (lastname.isEmpty()) {
            inputApellido.setError("Este campo no puede estar vacio.");
        } else if (phone.isEmpty() || phone.length() < 9) {
            inputTelefono.setError("Ingrese un numero de telefono válido.");
        } else if (!email.matches(emailPattern)){
            inputEmail.setError("Ingrese un Email válido");
        } else if (password.isEmpty() || password.length() < 6) {
            inputPassword.setError("Ingrese una contraseña de más de 6 caracteres");
        } else {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        registroDatos();
                    } else {
                        startActivity(new Intent(getActivity(), Reg_Error.class));
                    }
                }
            });
        }
    }

    private void registroDatos() {
        String nombre = inputNombre.getText().toString();
        String apellido = inputApellido.getText().toString();
        String telefono = inputTelefono.getText().toString();
        String email = inputEmail.getText().toString();
        String rol = "user";

        Map<String,Object> map = new HashMap<>();
        map.put("nombre",nombre);
        map.put("apellido",apellido);
        map.put("telefono",telefono);
        map.put("email",email);
        map.put("rol",rol);

        mFireStore.collection("users").document().set(map);

        dashboard();
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

                        int nusers = Integer.parseInt(user);
                        nusers = nusers+1;

                        Map<String,Object> map2 = new HashMap<>();
                        map2.put("nUsers",nusers);
                        map2.put("nPlatos",foods);
                        map2.put("total",total);
                        map2.put("nPedidos",order);

                        mFireStore.collection("dashboard").document("dashboardinfo").set(map2);


                    }
                } else {
                    Log.w("users","Error", task.getException());
                }
            }
        });

        startActivity(new Intent(getActivity(), Reg_Valid.class));
    }
}

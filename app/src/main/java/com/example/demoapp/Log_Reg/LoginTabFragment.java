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
import androidx.fragment.app.Fragment;

import com.example.demoapp.R;
import com.example.demoapp.ValidationLogin.Log_Error;
import com.example.demoapp.ValidationLogin.Log_Valid;
import com.example.demoapp.ValidationRegister.Reg_Error;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.TimeUnit;

public class LoginTabFragment extends Fragment {

    EditText inputEmail, inputPassword;
    Button btnLogin;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore mFireStore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container,false);

        //INICIALIZAMOS VARIABLES
        inputEmail = root.findViewById(R.id.login_input_email);
        inputPassword = root.findViewById(R.id.login_input_password);
        btnLogin = root.findViewById(R.id.btn_login);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mFireStore = FirebaseFirestore.getInstance();

        //LOGIN ACTION
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                if (!email.matches(emailPattern)){
                    inputEmail.setError("Ingrese un Email válido");
                } else if (password.isEmpty() || password.length() < 6){
                    inputPassword.setError("Ingresa una contraseña de más de 6 caracteres");
                } else {
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                validarRol();
                            } else {
                                startActivity(new Intent(getActivity(), Log_Error.class));
                            }
                        }
                    });
                }
            }
        });

        return root;
    }

    private void validarRol() {
        String valid = inputEmail.getText().toString();
        mFireStore.collection("users").whereEqualTo("email",""+valid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        String rol = document.getData().get("rol").toString();
                        if (rol.equals("admin")){
                            Intent intent = new Intent(getActivity(),Log_Valid.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            String email = inputEmail.getText().toString();
                            intent.putExtra("n1",email);
                            intent.putExtra("rol","admin");
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getActivity(),Log_Valid.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            String email = inputEmail.getText().toString();
                            intent.putExtra("n1",email);
                            intent.putExtra("rol","user");
                            startActivity(intent);
                        }
                    }
                } else {
                    Log.w("users","Error", task.getException());
                }
            }
        });
    }
}

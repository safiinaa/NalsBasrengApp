package com.example.nalsbasreng;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.odysseyorraapp.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    Button btnregis;
    EditText etreg_nama , etteg_email, etreg_sandi;
    TextView tv_memilikiakun;

    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        btnregis = findViewById(R.id.btn_regis);
        etreg_nama = findViewById(R.id.etreg_nama);
        etteg_email = findViewById(R.id.etreg_email);
        etreg_sandi = findViewById(R.id.etreg_sandi);
        tv_memilikiakun = findViewById(R.id.tv_memilikiakun);

        tv_memilikiakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, com.example.nalsbasreng.LoginActivity.class));
            }
        });

       btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, com.example.nalsbasreng.LoginActivity.class));

                createUser();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void createUser() {

        String userName = etreg_nama.getText().toString();
        String userEmail = etteg_email.getText().toString();
        String userPassword = etreg_sandi.getText().toString();

        if (TextUtils.isEmpty(userName)){
            Toast.makeText(this, "Nama belum diisi!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Email belum diisi!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Kata sandi belum diisi!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPassword.length() < 6 ){
            Toast.makeText(this, "Panjang sandi harus lebih dari 6 huruf", Toast.LENGTH_SHORT).show();
            return;
        }

        // Membuat AKun
        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            UserModel userModel = new UserModel(userName, userEmail, userPassword);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Admin").child(id).setValue(userModel);

                            Toast.makeText(RegisterActivity.this, "Akun berhasil dibuat.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Error:"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    }
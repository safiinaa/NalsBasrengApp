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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button btnlog;
    EditText etteg_email, etreg_sandi;
    TextView tv_tidak_memilikiakun;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        btnlog = findViewById(R.id.btn_log);
        etteg_email = findViewById(R.id.etlog_email);
        etreg_sandi = findViewById(R.id.etlog_sandi);
        tv_tidak_memilikiakun = findViewById(R.id.tv_tidak_memilikiakun);

        tv_tidak_memilikiakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, com.example.nalsbasreng.RegisterActivity.class));
            }
        });

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, com.example.nalsbasreng.MainActivity.class));
                loginUser();

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loginUser() {
        String userEmail = etteg_email.getText().toString();
        String userPassword = etreg_sandi.getText().toString();

        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Email tidak ditemukan!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Kata sandi salah!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPassword.length() < 6 ){
            Toast.makeText(this, "Panjang sandi harus lebih dari 6 huruf", Toast.LENGTH_SHORT).show();
            return;
        }

        // Masuk Akun
        auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Berhasil masuk!", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(LoginActivity.this, "Error:"+task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
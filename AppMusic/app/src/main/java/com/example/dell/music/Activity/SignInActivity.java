package com.example.dell.music.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dell.music.Activity.SignUpAcitivity;
import com.example.dell.music.DoApi.DoGetUserAccount;
import com.example.dell.music.Host;
import com.example.dell.music.R;

public class SignInActivity extends AppCompatActivity {

    EditText edtEmail, edtPass;
    Button btnSignIn;
    TextView txtvSignUp, txtvSignInCanhbao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();
        anhXa();

        txtvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpAcitivity.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInputInfo()) {
                    new DoGetUserAccount(SignInActivity.this).execute(Host.LAN_MY_PHONE + Host.apiUserAccount + "?email="
                            + edtEmail.getText().toString() + "&password="
                            + edtPass.getText().toString());
                }
            }
        });
    }

    private void anhXa() {
        edtEmail = findViewById(R.id.edtSignInEmail);
        edtPass = findViewById(R.id.edtSignInPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        txtvSignUp = findViewById(R.id.txtvSignUp);
        txtvSignInCanhbao = findViewById(R.id.txtvSignInCanhBao);
    }

    private boolean checkInputInfo() {
        if (edtEmail.getText().toString().isEmpty() ||
                edtPass.getText().toString().isEmpty()) {
            txtvSignInCanhbao.setText("Sai tên tài khoản hoặc mật khẩu");
            return false;
        }
        txtvSignInCanhbao.setText("");
        return true;
    }
}

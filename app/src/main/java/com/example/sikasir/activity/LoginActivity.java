package com.example.sikasir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sikasir.R;
import com.example.sikasir.database.UserHelper;
import com.example.sikasir.entity.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    public static final String EXTRA_USER = "extra_user";

    EditText edtUsername, edtPassword;
    Button btnLogin;
    UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userHelper = new UserHelper(this);

        edtUsername = findViewById(R.id.ipt_username);
        edtPassword = findViewById(R.id.ipt_password);
        btnLogin = findViewById(R.id.btn_login);

        TextView txtUserLevel = findViewById(R.id.txt_user_level);

        if (getIntent().getStringExtra(EXTRA_USER).equals("kasir")) {
            txtUserLevel.setText(R.string.kasir);
        } else {
            txtUserLevel.setText(R.string.admin);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String username = edtUsername.getText().toString();
                    String password = edtPassword.getText().toString();

                    User currentUser = userHelper.Authenticate(new User(null, username, null, password), getApplicationContext());

                    if (currentUser != null) {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                Intent intent = new Intent();
                                if (userHelper.userLevel.equals(getIntent().getStringExtra(EXTRA_USER))) {
                                    intent = new Intent(LoginActivity.this, LandingActivity.class);
                                    intent.putExtra("KEY_ID", userHelper.idUser);
                                    intent.putExtra("KEY_USERNAME", userHelper.nameUser);
                                    intent.putExtra("KEY_USERLEVEL", userHelper.userLevel);
                                    intent.putExtra("KEY_PASSWORD", userHelper.passwordUser);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), getString(R.string.error_wrong_user_level), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, 3000);
                    }
                }
            }
        });
    }

    public boolean validate() {
        boolean valid;

        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        if (username.isEmpty()) {
            valid = false;
            Toast.makeText(getApplicationContext(), "Username Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        } else {
            valid = true;
        }

        if (password.isEmpty()) {
            valid = false;
            Toast.makeText(getApplicationContext(), "Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        } else {
            valid = true;
        }
        return valid;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

}

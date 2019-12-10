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
import android.widget.ProgressBar;
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
    ProgressBar pgLoading;
    UserHelper userHelper;

    private static final String EXTRA_USERNAME = "EXTRA_USERNAME";
    private static final String EXTRA_PASSWORD = "EXTRA_PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userHelper = new UserHelper(this);

        edtUsername = findViewById(R.id.ipt_username);
        edtPassword = findViewById(R.id.ipt_password);
        btnLogin = findViewById(R.id.btn_login);
        pgLoading = findViewById(R.id.pb_loading);

        if (savedInstanceState != null) {
            edtUsername.setText(savedInstanceState.getString(EXTRA_USERNAME));
            edtPassword.setText(savedInstanceState.getString(EXTRA_PASSWORD));
        }

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
                    pgLoading.setVisibility(View.VISIBLE);
                    btnLogin.setEnabled(false);
                    String username = edtUsername.getText().toString();
                    String password = edtPassword.getText().toString();

                    User currentUser = userHelper.Authenticate(new User(null, username, null, password), getApplicationContext());

                    if (currentUser != null) {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                Intent intent;
                                pgLoading.setVisibility(View.INVISIBLE);
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
                                    btnLogin.setEnabled(true);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_USERNAME, edtUsername.getText().toString());
        outState.putString(EXTRA_PASSWORD, edtPassword.getText().toString());
    }

}

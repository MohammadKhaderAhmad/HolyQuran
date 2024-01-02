package com.example.holyquran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    public static final String ForUserName = "ForUserName";
    public static final String ForPass = "ForPass";
    public static final String FLAG = "FLAG";

    private boolean flag = false;

    private EditText edtSignInUsername;
    private EditText edtSignInPassword;
    private CheckBox checkboxRememberMe;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private TextView txtSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        setupViews();
        setupSharedPrefs();
        checkPrefs();

    }

    private void setupViews() {
        edtSignInUsername = findViewById(R.id.edtSignInUsername);
        edtSignInPassword = findViewById(R.id.edtSignInPassword);
        checkboxRememberMe = findViewById(R.id.checkboxRememberMe);
        txtSignUp = findViewById(R.id.txtSignUp);
    }

    private void setupSharedPrefs() {
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }





    private void checkPrefs() {
        flag = prefs.getBoolean(FLAG, false);

        if(flag) {
            String username = prefs.getString(ForUserName, "");
            String password = prefs.getString(ForPass, "");
            edtSignInUsername.setText(username);
            edtSignInPassword.setText(password);
            checkboxRememberMe.setChecked(true);
        }

    }


    public void btnLoginOnClick(View view) {
        String username = edtSignInUsername.getText().toString();
        String password = edtSignInPassword.getText().toString();


        boolean isValid = true;

        if (username.isEmpty()) {
            edtSignInUsername.setError("Please, fill out this field");
            isValid = false;
        }

        if (password.isEmpty()) {
            edtSignInPassword.setError("Please, fill out this field");
            isValid = false;
        }

        String savedFullName = prefs.getString(Constants.USERNAME, "");
        String savedPassword = prefs.getString(Constants.PASS, "");

        if (!username.equals(savedFullName) || !password.equals(savedPassword)) {
            if(!username.equals(savedFullName)){
                edtSignInUsername.setError("Username is incorrect");
                isValid = false;
            }
            if(!password.equals(savedPassword)){
                edtSignInPassword.setError("password is incorrect");
                isValid = false;

            }
        }

        if (!isValid) {
            return;
        }

        if (checkboxRememberMe.isChecked()) {
            if(!flag){
                editor.putString(ForUserName, username);
                editor.putString(ForPass, password);
                editor.putBoolean(FLAG, true);
                editor.commit();
            }}
        Intent intent = new Intent(this, ChooseActivity.class);
        startActivity(intent);
        finish();

    }

    public void onSignUpClicked(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

}

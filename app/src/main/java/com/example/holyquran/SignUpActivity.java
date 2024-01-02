package com.example.holyquran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    public static final String EMAIL = "EMAIL";
    public static final String GENDER = "GENDER";
    public static final String MOBILE = "MOBILE";
    public static final String COPASSWORD = "COPASSWORD";
    public static final String FLAGG = "FLAGG";
    private boolean flag = false;

    private EditText edtSignUpFullName;
    private EditText edtSignUpEmail;
    private AutoCompleteTextView genderAutoComplete;
    private EditText edtSignUpMobile;
    private EditText edtSignUpPassword;
    private EditText edtSignUpConfirmPassword;
    private TextView txtSignIn;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setupViews();

        String[] genderOptions = getResources().getStringArray(R.array.gender_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, genderOptions);
        genderAutoComplete.setAdapter(adapter);

        setupSharedPrefs();
        checkPrefs();



    }

    private void setupViews() {
        edtSignUpFullName = findViewById(R.id.edtSignUpFullName);
        edtSignUpEmail = findViewById(R.id.edtSignUpEmail);
        genderAutoComplete = findViewById(R.id.edtSignUpGender);
        edtSignUpMobile = findViewById(R.id.edtSignUpMobile);
        edtSignUpPassword = findViewById(R.id.edtSignUpPassword);
        edtSignUpConfirmPassword = findViewById(R.id.edtSignUpConfirmPassword);
        txtSignIn = findViewById(R.id.txtSignIn);
    }

    private void setupSharedPrefs() {
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }
    private void checkPrefs() {
        flag = prefs.getBoolean(FLAGG, false);

        if(flag){
            String fullName = prefs.getString(Constants.USERNAME,  "");
            String email = prefs.getString(EMAIL, "");
            String gender = prefs.getString(GENDER, "");
            String mobile = prefs.getString(MOBILE, "");
            String password = prefs.getString(Constants.PASS, "");
            String coPassword = prefs.getString(COPASSWORD, "");

            edtSignUpFullName.setText(fullName);
            edtSignUpEmail.setText(email);
            genderAutoComplete.setText(gender, false);
            edtSignUpMobile.setText(mobile);
            edtSignUpPassword.setText(password);
            edtSignUpConfirmPassword.setText(coPassword);
        }
    }

    public void btnSignUpClick(View view) {
        String fullName = edtSignUpFullName.getText().toString().trim();
        String email = edtSignUpEmail.getText().toString();
        String gender = genderAutoComplete.getText().toString();
        String mobile = edtSignUpMobile.getText().toString();
        String password = edtSignUpPassword.getText().toString().trim();
        String coPassword = edtSignUpConfirmPassword.getText().toString();

        boolean isValid = true;

        if (fullName.isEmpty()) {
            edtSignUpFullName.setError("Please, fill out this field");
            isValid = false;
        }
        if (email.isEmpty()) {
            edtSignUpEmail.setError("Please, fill out this field");
            isValid = false;
        }
        if (mobile.isEmpty()) {
            edtSignUpMobile.setError("Please, fill out this field");
            isValid = false;
        }
        if (password.isEmpty()) {
            edtSignUpPassword.setError("Please, fill out this field");
            isValid = false;
        }
        if (coPassword.isEmpty()) {
            edtSignUpConfirmPassword.setError("Please, fill out this field");
            isValid = false;
        } else if (!password.equals(coPassword)) {
            edtSignUpConfirmPassword.setError("Passwords do not match");
            isValid = false;
        }

        if (!isValid) {
            return;
        }


            if (!flag) {
                editor.putString(Constants.USERNAME, fullName);
                editor.putString(EMAIL, email);
                editor.putString(GENDER, gender);
                editor.putString(MOBILE, mobile);
                editor.putString(Constants.PASS, password);
                editor.putString(COPASSWORD, coPassword);
                editor.putBoolean(FLAGG, true);
                editor.commit();
            }


        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    public void onSignInClicked(View view) {
        Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStop() {
        super.onStop();
        String fullName = edtSignUpFullName.getText().toString();
        String email = edtSignUpEmail.getText().toString();
        String gender = genderAutoComplete.getText().toString();
        String mobile = edtSignUpMobile.getText().toString();
        String password = edtSignUpPassword.getText().toString();
        String coPassword = edtSignUpConfirmPassword.getText().toString();

        editor.putString(Constants.USERNAME, fullName);
        editor.putString(EMAIL, email);
        editor.putString(GENDER, gender);
        editor.putString(MOBILE, mobile);
        editor.putString(Constants.PASS, password);
        editor.putString(COPASSWORD, coPassword);
        editor.putBoolean(FLAGG, true);
        editor.commit();



    }
}

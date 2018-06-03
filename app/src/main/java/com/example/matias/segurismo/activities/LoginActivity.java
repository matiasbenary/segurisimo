package com.example.matias.segurismo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.design.widget.Snackbar;

import com.example.matias.segurismo.R;
import com.example.matias.segurismo.helpers.InputValidation;
import com.example.matias.segurismo.sql.DatabaseHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = LoginActivity.this;

    private EditText editTextUser;
    private EditText editTextPassword;

    private TextView textViewUser;
    private TextView textViewPassword;

    private Button buttonLogin;
    private Button buttonRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {

        editTextUser = findViewById(R.id.editTextUser);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewUser = findViewById(R.id.textViewUser);
        textViewPassword = findViewById(R.id.textViewPassword);

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

    }
    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);

    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin:
                verifyFromSQLite();
                break;
            case R.id.buttonRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(editTextUser, textViewUser, "no es texto")) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(editTextUser, textViewUser, "no es un email valido")) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editTextPassword, textViewPassword, "no es texto")) {
            return;
        }

        Snackbar.make(getWindow().getDecorView().getRootView() , editTextUser.getText(), Snackbar.LENGTH_LONG).show();
        if (databaseHelper.checkUser(editTextUser.getText().toString().trim()
                , editTextPassword.getText().toString().trim())) {


            Intent accountsIntent = new Intent(activity, UsersListActivity.class);
            accountsIntent.putExtra("EMAIL", editTextUser.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);


        } else {

            Snackbar.make(getWindow().getDecorView().getRootView() , "Error de usuario", Snackbar.LENGTH_LONG).show();

        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        editTextUser.setText(null);
        editTextPassword.setText(null);
    }
}
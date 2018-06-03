package com.example.matias.segurismo.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.matias.segurismo.R;
import com.example.matias.segurismo.User;
import com.example.matias.segurismo.helpers.InputValidation;
import com.example.matias.segurismo.sql.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;

    private EditText editTextName;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextEmail;

    private TextView textViewName;
    private TextView textViewPassword;
    private TextView textViewConfirmPassword;
    private TextView textViewEmail;

    private Button buttonLogin;
    private Button buttonRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initListeners();
        initObjects();


    }

    private void initViews() {
        editTextName = findViewById(R.id.editTextName);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        textViewName = findViewById(R.id.textViewName);
        textViewPassword = findViewById(R.id.textViewPassword);
        textViewConfirmPassword = findViewById(R.id.textViewConfirmPassword);
        textViewEmail = findViewById(R.id.textViewEmail);

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
        user = new User();
    }
    /**
     *
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonRegister:
                postDataToSQLite();
                break;

            case R.id.buttonLogin:
                finish();
                break;
        }
    }


    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
//        Snackbar.make(getWindow().getDecorView().getRootView() , "Oki", Snackbar.LENGTH_LONG).show();

        if (!inputValidation.isInputEditTextFilled(editTextName, textViewName, "error")) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editTextEmail, textViewEmail, "error")) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(editTextEmail, textViewEmail, "error")) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editTextPassword, textViewPassword, "error")) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(editTextPassword, editTextConfirmPassword,
                textViewConfirmPassword, "error")) {
            return;
        }

        if (!databaseHelper.checkUser(editTextEmail.getText().toString().trim())) {

            user.setName(editTextName.getText().toString().trim());
            user.setEmail(editTextEmail.getText().toString().trim());
            user.setPassword(editTextPassword.getText().toString().trim());

            databaseHelper.addUser(user);


//            if (!databaseHelper.checkUser("matiasbenay@gmail.com")) {
//            Snackbar.make(getWindow().getDecorView().getRootView() , "Hola", Snackbar.LENGTH_LONG).show();
//            user.setName("Matias");
//            user.setEmail("matiasbenay@gmail.com");
//            user.setPassword("mbenary123");
//
//            databaseHelper.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(getWindow().getDecorView().getRootView() , "Oki", Snackbar.LENGTH_LONG).show();
            emptyInputEditText();


        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(getWindow().getDecorView().getRootView() , "Ya existe este usuario", Snackbar.LENGTH_LONG).show();
        }



    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        editTextName.setText(null);
        editTextEmail.setText(null);
        editTextPassword.setText(null);
        editTextConfirmPassword.setText(null);
    }

}

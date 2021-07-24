package com.example.mobicow;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout rlayout;

    private RelativeLayout relative;
    private final AppCompatActivity activity = RegisterActivity.this;


    private TextView name;
    private TextView email;
    private TextView pass;
    private TextView cpass;

    private EditText editname;
    private EditText editemail;
    private EditText editpass;
    private EditText editcpass;

    private Button btn_register;


    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;


    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.bgheader);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rlayout = findViewById(R.id.rlayout);
        animation = AnimationUtils.loadAnimation(this, R.anim.uptodowndiagonal);
        rlayout.setAnimation(animation);

        initViews();
        initListeners();
        initObjects();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * This method is to initialize views
     */
    private void initViews() {
        relative = (RelativeLayout) findViewById(R.id.relative);

        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        pass = (TextView) findViewById(R.id.pass);
        cpass = (TextView) findViewById(R.id.cpass);

        editname = (EditText) findViewById(R.id.editname);
        editemail = (EditText) findViewById(R.id.editemail);
        editpass = (EditText) findViewById(R.id.editpass);
        editcpass = (EditText) findViewById(R.id.editcpass);

        btn_register = (Button) findViewById(R.id.btn_register);


    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        btn_register.setOnClickListener(this);


    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();

    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_register:
                postDataToSQLite();
                break;


        }
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(editname, name, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editemail, email, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(editemail, email, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editpass, pass, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(editpass, editcpass,
                cpass, getString(R.string.error_password_match))) {
            return;
        }

        if (!databaseHelper.checkUser(editemail.getText().toString().trim())) {

            user.setName(editname.getText().toString().trim());
            user.setEmail(editemail.getText().toString().trim());
            user.setPassword(editpass.getText().toString().trim());

            databaseHelper.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(relative, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
            Intent accountsIntent = new Intent(this, LoginActivity.class);
            startActivity(accountsIntent);


        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(relative, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }


    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        editname.setText(null);
        editemail.setText(null);
        editpass.setText(null);
        editcpass.setText(null);
    }
}

package com.example.mobicow;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ForgetActivity extends AppCompatActivity  implements View.OnClickListener {
    private RelativeLayout relative;

    private TextView email;


    private EditText editemail;
    String text;



    private Button btn_forget;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        initViews();
        initListeners();
        initObjects();
        Bundle bundle =getIntent().getExtras();
        text= bundle.getString("role");

    }

    private void initViews() {

        relative = (RelativeLayout) findViewById(R.id.relative);
        email = (TextView) findViewById(R.id.email);
        editemail = (EditText) findViewById(R.id.editemail);
        btn_forget = (Button) findViewById(R.id.btn_forget);


    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        btn_forget.setOnClickListener(this);


    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(this);
        inputValidation = new InputValidation(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_forget:
                verifyFromSQLite();
                break;
        }

    }
    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if(text.equals("Farmer")) {
            if (!inputValidation.isInputEditTextFilled(editemail, email, getString(R.string.error_message_email))) {
                return;
            }
            if (!inputValidation.isInputEditTextEmail(editemail, email, getString(R.string.error_message_email))) {
                return;
            }


            if (databaseHelper.checkUser(editemail.getText().toString().trim())) {
                Intent i = new Intent(this, ConfirmPassActivity.class);
                i.putExtra("EMAIL", editemail.getText().toString().trim());
                emptyInputEditText();
                Bundle b = new Bundle();
                b.putString("role", text);
                i.putExtras(b);
                startActivity(i);


            } else {
                // Snack Bar to show success message that record is wrong
                Snackbar.make(relative, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
            }
        }
        else if(text.equals("Admin")){
            if (!inputValidation.isInputEditTextFilled(editemail, email, getString(R.string.error_message_email))) {
                return;
            }
            if (!inputValidation.isInputEditTextEmail(editemail, email, getString(R.string.error_message_email))) {
                return;
            }


            if (databaseHelper.checkAdmin(editemail.getText().toString().trim())) {
                Intent i = new Intent(this, ConfirmPassActivity.class);
                i.putExtra("EMAIL", editemail.getText().toString().trim());
                emptyInputEditText();
                Bundle b = new Bundle();
                b.putString("role", text);
                i.putExtras(b);
                startActivity(i);


            } else {
                // Snack Bar to show success message that record is wrong
                Snackbar.make(relative, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
            }
        }
    }
    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        editemail.setText(null);

    }
}

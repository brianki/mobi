package com.example.mobicow;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton btregister;
    private TextView tvlogin;
    private final AppCompatActivity activity = LoginActivity.this;

    private RelativeLayout relative;

    private TextView email;
     private TextView tvforgot;
    private TextView pass;

    private EditText editemail;
    private EditText editpass;


    private Button btn_login;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;



    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btregister= findViewById(R.id.btregister);
        tvlogin = findViewById(R.id.tvlogin);
        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.loginas,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);





        initViews();
        initListeners();
        initObjects();

    }





    private void initViews() {

      relative= (RelativeLayout) findViewById(R.id.relative);

        email = (TextView) findViewById(R.id.email);
        pass = (TextView) findViewById(R.id.pass);

        editemail = (EditText) findViewById(R.id.editemail);
        editpass = (EditText) findViewById(R.id.editpass);

        btn_login = (Button) findViewById(R.id.btn_login);
        tvforgot=(TextView) findViewById(R.id.tvforgot);



    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        btn_login.setOnClickListener(this);
        btregister.setOnClickListener(this);
        tvforgot.setOnClickListener(this);

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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                verifyFromSQLite();
                break;
            case R.id. btregister:
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                Pair[] pairs= new Pair[1];
                pairs[0]=new Pair<View,String>(tvlogin,"tvlogin");
                ActivityOptions activityOptions= ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
                startActivity(intent);
                break;
            case R.id.tvforgot:

               Intent i = new Intent(getApplicationContext(), ForgetActivity.class);
               Bundle b = new Bundle();
               b.putString("role", spinner.getSelectedItem().toString());
               i.putExtras(b);
               startActivity(i);

                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if(spinner.getSelectedItem().toString().equals("Admin")){
        if (!inputValidation.isInputEditTextFilled(editemail, email, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(editemail, email, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editpass, pass, getString(R.string.error_message_email))) {
            return;
        }

        if (databaseHelper.checkAdmin(editemail.getText().toString().trim()
                , editpass.getText().toString().trim())) {


            Intent accountsIntent = new Intent(activity, MainActivity.class);
            accountsIntent.putExtra("EMAIL", editemail.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);


        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(relative, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
        }
        else if(spinner.getSelectedItem().toString().equals("Farmer")){
            if (!inputValidation.isInputEditTextFilled(editemail, email, getString(R.string.error_message_email))) {
                return;
            }
            if (!inputValidation.isInputEditTextEmail(editemail, email, getString(R.string.error_message_email))) {
                return;
            }
            if (!inputValidation.isInputEditTextFilled(editpass, pass, getString(R.string.error_message_email))) {
                return;
            }

            if (databaseHelper.checkUser(editemail.getText().toString().trim()
                    , editpass.getText().toString().trim())) {


                Intent accountsIntent = new Intent(activity, FarmerActivity.class);
                accountsIntent.putExtra("EMAIL", editemail.getText().toString().trim());
                emptyInputEditText();
                Bundle b = new Bundle();
                b.putString("role", editemail.getText().toString());
                accountsIntent.putExtras(b);
                startActivity(accountsIntent);


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
        editpass.setText(null);
    }
}

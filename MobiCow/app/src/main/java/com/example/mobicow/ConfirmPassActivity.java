package com.example.mobicow;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmPassActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout relative;

    private TextView cpass;
    private TextView pass;

    private EditText editpass;
    private EditText editcpass;
    private Button btn_cpass;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    String email, text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pass);

        initViews();
        initListeners();
        initObjects();
        Intent intent = getIntent();
        email =intent.getStringExtra("EMAIL");
        Bundle bundle =getIntent().getExtras();
        text= bundle.getString("role");

    }





    private void initViews() {

        relative= (RelativeLayout) findViewById(R.id.relative);

        cpass = (TextView) findViewById(R.id.cpass);
        pass = (TextView) findViewById(R.id.pass);

        editcpass = (EditText) findViewById(R.id.editcpass);
        editpass = (EditText) findViewById(R.id.editpass);

        btn_cpass = (Button) findViewById(R.id.btn_cpass);



    }


    private void initListeners() {
        btn_cpass.setOnClickListener(this);


    }


    private void initObjects() {
        databaseHelper = new DatabaseHelper(this);
        inputValidation = new InputValidation(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cpass:
                updatePassword();
                break;


        }
    }
    private  void updatePassword(){
        String value1 = editpass.getText().toString().trim();
        String value2 =editcpass.getText().toString().trim();
        if (value1.isEmpty() && value2.isEmpty()){
            Toast.makeText(this,"Fill all fields",Toast.LENGTH_LONG).show();
            return;
        }
        if (!value1.contentEquals(value2)){
            Toast.makeText(this,"Password doesn't match",Toast.LENGTH_LONG).show();
            return;
        }
        if(text.equals("Farmer")) {
            if (!databaseHelper.checkUser(email)) {
                Snackbar.make(relative, "email doesn't exist", Snackbar.LENGTH_LONG).show();
                return;
            } else {
                databaseHelper.updatePassword(email, value1);
                Toast.makeText(this, "Password reset successful", Toast.LENGTH_LONG).show();
                emptyInputEditText();

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
        if(text.equals("Admin")){
            if (!databaseHelper.checkAdmin(email)){
                Snackbar.make(relative,"email doesn't exist",Snackbar.LENGTH_LONG).show();
                return;
            }
            else {
                databaseHelper.updateAdminPassword(email,value1);
                Toast.makeText(this,"Password reset successful",Toast.LENGTH_LONG).show();
                emptyInputEditText();

                Intent intent= new Intent(this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }

    }
    private void emptyInputEditText() {
        editcpass.setText(null);
        editpass.setText(null);
    }
}

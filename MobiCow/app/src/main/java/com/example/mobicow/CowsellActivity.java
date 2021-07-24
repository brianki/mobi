package com.example.mobicow;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;

public class CowsellActivity extends AppCompatActivity {

    EditText editcost, editmilk,editbreed, editage;
    Button btn_sell;
    ImageView camera;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    RelativeLayout relative;

    private User user;
    final int REQUEST_CODE_GALLERY = 999;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       editage= findViewById(R.id.editage);
       editbreed= findViewById(R.id.editbreed);
       editmilk = findViewById(R.id.editmilk);
        editcost = findViewById(R.id.editcost);
       btn_sell = findViewById(R.id.btn_sell);
       camera = findViewById(R.id.camera);
        initObjects();

        //select image by on imageview click
      camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //read external storage permission to select image from gallery
                //runtime permission for devices android 6.0 and above
                ActivityCompat.requestPermissions(
                        CowsellActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        //add record to sqlite
       btn_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {

                    case R.id.btn_register:
                        postDataToSQLite();
                        break;


                }

            }
        });
        private void postDataToSQLite() {
            if (!inputValidation.isInputEditTextFilled(editage, age, getString(R.string.error_message_name))) {
                return;
            }
            if (!inputValidation.isInputEditTextFilled(editbreed, breed, getString(R.string.error_message_email))) {
                return;
            }
            if (!inputValidation.isInputEditTextEmail(editmilk, email, getString(R.string.error_message_email))) {
                return;
            }
            if (!inputValidation.isInputEditTextFilled(editcost, cost, getString(R.string.error_message_password))) {
                return;
            }
            if (!inputValidation.isInputEditTextMatches(editpass, editcpass,
                    cpass, getString(R.string.error_password_match))) {
                return;
            }

            if (!databaseHelper.checkCow(editbreed.getText().toString().trim())) {

                user.setAge(editage.getText().toString().trim());
                user.setBreed(editbreed.getText().toString().trim());
                user.setMilk(editpass.getText().toString().trim());
                user.setCost(editcost.getText().toString().trim());

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



    }
    private void initObjects() {
        inputValidation = new InputValidation(this);
        databaseHelper = new DatabaseHelper(this);
        user = new User();

    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //gallery intent
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(this, "Don't have permission to access file location", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
/**
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON) //enable image guidlines
                    .setAspectRatio(1,1)// image will be square
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result =CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                //set image choosed from gallery to image view
                mImageView.setImageURI(resultUri);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }**/

        super.onActivityResult(requestCode, resultCode, data);
    }
    private void emptyInputEditText() {
        editage.setText(null);
        editbreed.setText(null);
        editmilk.setText(null);
        editcost.setText(null);
    }
}

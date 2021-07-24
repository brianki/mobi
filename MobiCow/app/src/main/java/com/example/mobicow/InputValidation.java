package com.example.mobicow;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class InputValidation {
    private Context context;



    public InputValidation(Context context) {
        this.context = context;
    }


    public boolean isInputEditTextFilled(EditText editText, TextView textView, String message) {
        String value = editText.getText().toString().trim();
        if (value.isEmpty()) {
            textView.setError(message);
            hideKeyboardFrom(editText);
            return false;
        } else {
            textView.setError(null);
        }

        return true;
    }



    public boolean isInputEditTextEmail(EditText editText, TextView textView, String message) {
        String value = editText.getText().toString().trim();
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            textView.setError(message);
            hideKeyboardFrom(editText);
            return false;
        } else {
            textView.setError(null);
        }
        return true;
    }

    public boolean isInputEditTextMatches(EditText editText1, EditText editText2, TextView textView, String message) {
        String value1 = editText1.getText().toString().trim();
        String value2 = editText2.getText().toString().trim();
        if (!value1.contentEquals(value2)) {
            textView.setError(message);
            hideKeyboardFrom(editText2);
            return false;
        } else {
            textView.setError(null);
        }
        return true;
    }

    /**
     * method to Hide keyboard
     *
     * @param view
     */
    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}

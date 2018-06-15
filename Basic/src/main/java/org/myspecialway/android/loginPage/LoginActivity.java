package org.myspecialway.android.loginPage;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.myspecialway.android.R;


public class LoginActivity extends AppCompatActivity {

    private  EditText mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView =  findViewById(R.id.layout_email);

        mPasswordView = mLoginFormView.findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
    }


    private void attemptLogin() {

        mEmailView.setError(null);
        mPasswordView.setError(null);

        String password = mPasswordView.getText().toString();
        String username = mEmailView.getText().toString();

        /// pass the user to the next activity
        /**
         *  TODO : Use Login class to save JWT token
         */

        //LoginClient.login(username/);

        /**
         *  TODO : on success pass the user to the next activity
         */

//       Intent intent = new Intent(this, ListExamplesActivity.class);
//       startActivity(intent);
//       finish();


            /**
             *  TODO : login fail show the error to the user
             */

        }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}


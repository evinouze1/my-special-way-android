package org.myspecialway.android.loginPage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.myspecialway.android.ListExamplesActivity;
import org.myspecialway.android.R;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private LoginAccessToken loginAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = findViewById(R.id.layout_email);

        mPasswordView = findViewById(R.id.password);
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
        String password = mPasswordView.getText().toString();
        String username = mEmailView.getText().toString();
        login(username, password);


    }


    /**
     * login success pass the user to the next screen
     */
    private void loginSusses() {
        Intent intent = new Intent(this, ListExamplesActivity.class);
        startActivity(intent);
        finish();
    }


    /**
     * login fail
     * <p>
     * TODO: need to defined error string
     */
    private void loginFail() {
        mEmailView.setError(getString(R.string.error_incorrect_password));
        mPasswordView.setError(getString(R.string.error_incorrect_password));
        View focusView = mEmailView;
        focusView.requestFocus();
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    public void login(String username, String password) {
        //Adding logs
        HttpLoggingInterceptor interceptor = getInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        //Creating retrofit client
        Retrofit.Builder builder = getRetrofitBuilder(client);
        Retrofit retrofit = builder.build();

        //Creating user login infra
        IUserLogin userLogin = retrofit.create(IUserLogin.class);

        //Populating parameters to json
        JsonObject object = new JsonObject();
        object.addProperty("username", username);
        object.addProperty("password", password);

        //Creating retrofit call
        Call<LoginAccessToken> call = userLogin.userLoginRequest(object, "application/json");

        //Placing call in execution queue
        call.enqueue(new Callback<LoginAccessToken>() {

            //Implementing callback
            @Override
            public void onResponse(Call<LoginAccessToken> call, Response<LoginAccessToken> response) {
                System.out.println("onResponse");
                if (response.isSuccessful()) {
                    loginAccessToken = response.body();
                    loginSusses();

                } else {
                    loginFail();
                }
            }

            @Override
            public void onFailure(Call<LoginAccessToken> call, Throwable t) {
                //TODO - Maoz - implement what happens when failing to send the request
            }
        });
    }

    @NonNull
    private Retrofit.Builder getRetrofitBuilder(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(LoginConstants.MSW_SERVER_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create());
    }

    @NonNull
    private HttpLoggingInterceptor getInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                System.out.println(message);
            }
        });
    }


    public LoginAccessToken getLoginAccessToken() {
        return loginAccessToken;
    }

}


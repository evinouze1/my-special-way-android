package org.myspecialway.android.loginPage;

import com.google.gson.JsonObject;

import org.myspecialway.android.loginPage.LoginAccessToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IUserLogin {

    @POST("login")
    Call<LoginAccessToken> userLoginRequest(@Body JsonObject body, @Header("Content-Type") String header);
}

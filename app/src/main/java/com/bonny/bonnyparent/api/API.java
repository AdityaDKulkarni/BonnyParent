package com.bonny.bonnyparent.api;

import com.bonny.bonnyparent.models.ParentModel;
import com.bonny.bonnyparent.models.TokenModel;
import com.bonny.bonnyparent.models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * @author Aditya Kulkarni
 */

public interface API {

    @POST("auth/api/login/")
    @FormUrlEncoded
    Call<TokenModel> getToken(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("auth/api/user/")
    Call<UserModel> getUser(
            @Header("Authorization") String token
    );

    @GET("api/parent/")
    Call<List<ParentModel>> getParentDetails(
            @Header("Authorization") String token
    );

}

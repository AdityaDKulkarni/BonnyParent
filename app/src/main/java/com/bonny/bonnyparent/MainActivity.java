package com.bonny.bonnyparent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bonny.bonnyparent.api.API;
import com.bonny.bonnyparent.config.RetrofitConfig;
import com.bonny.bonnyparent.models.ParentModel;
import com.bonny.bonnyparent.models.TokenModel;
import com.bonny.bonnyparent.models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();
    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        api = RetrofitConfig.config();

        login();
    }

    private void login(){

        Call<TokenModel> call = api.getToken("yd", "Pass@123");
        call.enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                Log.e(TAG, response.body().getKey());
                getUser(response.body().getKey());
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getUser(final String token){
        Call<UserModel> call = api.getUser("Token " + token);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                Log.e(TAG, response.body().getFirst_name());
                getParentDetails(response.body().getPk(), token);
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getParentDetails(final int id, String token){
        Call<List<ParentModel>> call = api.getParentDetails("Token " + token);
        call.enqueue(new Callback<List<ParentModel>>() {
            @Override
            public void onResponse(Call<List<ParentModel>> call, Response<List<ParentModel>> response) {
                for(int i = 0; i < response.body().size(); i++){
                    if(response.body().get(i).getUser() == id){
                        Log.e(TAG, response.body().get(i).getEmail());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ParentModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}

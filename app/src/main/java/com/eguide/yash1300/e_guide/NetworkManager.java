package com.eguide.yash1300.e_guide;

import android.content.Context;
import android.widget.Toast;

import com.eguide.yash1300.e_guide.Listeners.LoginResultListener;
import com.eguide.yash1300.e_guide.Listeners.StudentRegisterListener;
import com.eguide.yash1300.e_guide.NetworkAPIs.AuthAPI;
import com.eguide.yash1300.e_guide.NetworkAPIs.StudentAPI;
import com.eguide.yash1300.e_guide.NetworkAPIs.TeacherAPI;
import com.eguide.yash1300.e_guide.NetworkModels.BasicResponse;
import com.eguide.yash1300.e_guide.NetworkModels.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {

    private static NetworkManager networkManager = null;

    Retrofit authBuilder = new Retrofit.Builder().baseUrl(GlobalConstants.BASE_URL_AUTH).addConverterFactory(GsonConverterFactory.create()).build();
    Retrofit studentBuilder = new Retrofit.Builder().baseUrl(GlobalConstants.BASE_URL_STUDENT).addConverterFactory(GsonConverterFactory.create()).build();
    Retrofit teacherBuilder = new Retrofit.Builder().baseUrl(GlobalConstants.BASE_URL_TEACHER).addConverterFactory(GsonConverterFactory.create()).build();

    AuthAPI authAPI = authBuilder.create(AuthAPI.class);
    StudentAPI studentAPI = studentBuilder.create(StudentAPI.class);
    TeacherAPI teacherAPI = teacherBuilder.create(TeacherAPI.class);

    public static NetworkManager getInstance() {
        if (networkManager == null)
            networkManager = new NetworkManager();
        return networkManager;
    }

    public void studentLogin(String email, String password, final LoginResultListener loginResultListener) {
        Call<LoginResponse> loginResponseCall = authAPI.studentLogin(email, password);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Boolean success = response.body().getSuccess();
                String message = response.body().getMessage();
                if (!success) {
                    loginResultListener.onFailure(message);
                    return;
                }
                String token = response.body().getToken();
                loginResultListener.onSuccess(token);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                loginResultListener.onFailure(GlobalConstants.NETWORK_ERROR);
            }
        });
    }

    public void teacherLogin(String email, String password, final LoginResultListener loginResultListener) {
        Call<LoginResponse> loginResponseCall = authAPI.teacherLogin(email, password);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Boolean success = response.body().getSuccess();
                String message = response.body().getMessage();
                if (!success) {
                    loginResultListener.onFailure(message);
                    return;
                }
                String token = response.body().getToken();
                loginResultListener.onSuccess(token);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                loginResultListener.onFailure(GlobalConstants.NETWORK_ERROR);
            }
        });
    }

    public void studentRegister(String name, String username, String email, String password, String contact, final StudentRegisterListener studentRegisterListener) {
        Call<BasicResponse> studentRegisterCall = authAPI.studentRegister(name, username, email, password, contact);
        studentRegisterCall.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                Boolean success = response.body().getSuccess();
                String message = response.body().getMessage();
                if (!success) {
                    studentRegisterListener.onFailure(message);
                    return;
                }
                studentRegisterListener.onSuccess(message);
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                t.printStackTrace();
                studentRegisterListener.onFailure(GlobalConstants.NETWORK_ERROR);
            }
        });
    }

}

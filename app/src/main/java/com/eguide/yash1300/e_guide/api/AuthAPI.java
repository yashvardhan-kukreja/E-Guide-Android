package com.eguide.yash1300.e_guide.api;

import com.eguide.yash1300.e_guide.responses.BasicResponse;
import com.eguide.yash1300.e_guide.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthAPI {

    @POST("student/login")
    @FormUrlEncoded
    Call<LoginResponse> studentLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("teacher/login")
    @FormUrlEncoded
    Call<LoginResponse> teacherLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("student/register")
    @FormUrlEncoded
    Call<BasicResponse> studentRegister(
            @Field("name") String name,
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("contact") String contact
    );

    @POST("teacher/register")
    @FormUrlEncoded
    Call<BasicResponse> teacherRegister(
            @Field("name") String name,
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("contact") String contact,
            @Field("skills") String[] skills
    );

    @POST("teacher/check")
    @FormUrlEncoded
    Call<BasicResponse> teacherCheck(
            @Field("email") String email,
            @Field("username") String username
    );

}

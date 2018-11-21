package com.eguide.yash1300.e_guide.api;

import com.eguide.yash1300.e_guide.responses.BasicResponse;
import com.eguide.yash1300.e_guide.responses.teacher.TeacherDetailsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TeacherAPI {

    @GET("fetchDetails")
    Call<TeacherDetailsResponse> fetchDetails(
            @Header("x-access-token") String token
    );

    @POST("addSkills")
    @FormUrlEncoded
    Call<BasicResponse> addSkills(
            @Header("x-access-token") String token,
            @Field("skills") String[] skills
    );

}

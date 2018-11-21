package com.eguide.yash1300.e_guide.api;

import com.eguide.yash1300.e_guide.responses.BasicResponse;
import com.eguide.yash1300.e_guide.responses.teacher.TeacherFetchAllSkillsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UntokenTeacherAPI {

    @GET("fetchAllSkills")
    Call<TeacherFetchAllSkillsResponse> fetchAllSkills();

    @POST("addSkills")
    @FormUrlEncoded
    Call<BasicResponse> addSkills(
            @Field("teacher_email") String teacher_email,
            @Field("skills") String[] skills
    );


}

package com.eguide.yash1300.e_guide.api;

import com.eguide.yash1300.e_guide.responses.BasicResponse;
import com.eguide.yash1300.e_guide.responses.student.StudentDetailsResponse;
import com.eguide.yash1300.e_guide.responses.student.StudentFetchTeachersResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface StudentAPI {

    @GET
    Call<StudentDetailsResponse> fetchStudentDetails(
            @Header("x-access-token") String token
    );

    @GET
    Call<StudentFetchTeachersResponse> fetchAllTeachers(
            @Header("x-access-token") String token
    );

    @POST
    @FormUrlEncoded
    Call<BasicResponse> favorTeacher(
            @Header("x-access-token") String token,
            @Field("skill_id") String skillId,
            @Field("teacher_id") String teacherId
    );
}

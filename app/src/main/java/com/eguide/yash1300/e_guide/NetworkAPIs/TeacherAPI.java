package com.eguide.yash1300.e_guide.NetworkAPIs;

import com.eguide.yash1300.e_guide.NetworkResponses.TeacherDetailsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TeacherAPI {

    @GET("fetchDetails")
    Call<TeacherDetailsResponse> fetchDetails(
            @Header("x-access-token") String token
    );



}

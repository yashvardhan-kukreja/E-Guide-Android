package com.eguide.yash1300.e_guide.responses.student;

import com.eguide.yash1300.e_guide.models.FavoriteModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentFetchFavoriteTeachersResponse {

    @SerializedName("success")
    @Expose
    Boolean success;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("favTeachers")
    @Expose
    List<FavoriteModel> favTeachers;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FavoriteModel> getFavTeachers() {
        return favTeachers;
    }

    public void setFavTeachers(List<FavoriteModel> favTeachers) {
        this.favTeachers = favTeachers;
    }
}

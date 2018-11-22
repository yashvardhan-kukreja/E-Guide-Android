package com.eguide.yash1300.e_guide.responses.teacher;

import com.eguide.yash1300.e_guide.models.FavoriteModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeacherFetchFavoredStudentsResponse {

    @SerializedName("success")
    @Expose
    Boolean success;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("favStudents")
    @Expose
    List<FavoriteModel> favStudents;

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

    public List<FavoriteModel> getFavStudents() {
        return favStudents;
    }

    public void setFavStudents(List<FavoriteModel> favStudents) {
        this.favStudents = favStudents;
    }
}

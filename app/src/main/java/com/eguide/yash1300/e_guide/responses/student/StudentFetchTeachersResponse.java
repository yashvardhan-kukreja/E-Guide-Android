package com.eguide.yash1300.e_guide.responses.student;

import com.eguide.yash1300.e_guide.models.TeacherModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentFetchTeachersResponse {

    @SerializedName("success")
    @Expose
    Boolean success;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("teachers")
    @Expose
    List<TeacherModel> teachers;

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

    public List<TeacherModel> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeacherModel> teachers) {
        this.teachers = teachers;
    }
}

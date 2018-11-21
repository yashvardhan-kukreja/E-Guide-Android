package com.eguide.yash1300.e_guide.responses.teacher;

import com.eguide.yash1300.e_guide.models.TeacherModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherDetailsResponse {

    @SerializedName("success")
    @Expose
    Boolean success;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("teacher")
    @Expose
    TeacherModel teacher;

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

    public TeacherModel getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherModel teacher) {
        this.teacher = teacher;
    }
}

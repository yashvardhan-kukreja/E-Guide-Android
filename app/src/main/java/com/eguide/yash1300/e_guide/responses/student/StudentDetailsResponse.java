package com.eguide.yash1300.e_guide.responses.student;

import com.eguide.yash1300.e_guide.models.StudentModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentDetailsResponse {

    @SerializedName("success")
    @Expose
    Boolean success;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("student")
    @Expose
    StudentModel student;

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

    public StudentModel getStudent() {
        return student;
    }

    public void setStudent(StudentModel student) {
        this.student = student;
    }
}

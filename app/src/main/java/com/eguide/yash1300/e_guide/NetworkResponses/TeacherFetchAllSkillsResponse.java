package com.eguide.yash1300.e_guide.NetworkResponses;

import com.eguide.yash1300.e_guide.Models.SkillModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeacherFetchAllSkillsResponse {

    @SerializedName("success")
    @Expose
    Boolean success;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("skills")
    @Expose
    List<SkillModel> skills;

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

    public List<SkillModel> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillModel> skills) {
        this.skills = skills;
    }
}

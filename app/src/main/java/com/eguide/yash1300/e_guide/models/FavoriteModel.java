package com.eguide.yash1300.e_guide.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavoriteModel {

    @SerializedName("_id")
    @Expose
    String id;

    @SerializedName("favoredByStudent")
    @Expose
    StudentModel student;

    @SerializedName("favoriteTeacher")
    @Expose
    TeacherModel teacher;

    @SerializedName("skill")
    @Expose
    SkillModel skill;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StudentModel getStudent() {
        return student;
    }

    public void setStudent(StudentModel student) {
        this.student = student;
    }

    public TeacherModel getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherModel teacher) {
        this.teacher = teacher;
    }

    public SkillModel getSkill() {
        return skill;
    }

    public void setSkill(SkillModel skill) {
        this.skill = skill;
    }
}

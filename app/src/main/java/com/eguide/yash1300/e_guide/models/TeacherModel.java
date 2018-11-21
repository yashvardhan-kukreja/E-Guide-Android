package com.eguide.yash1300.e_guide.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeacherModel {

    @SerializedName("_id")
    @Expose
    String id;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("username")
    @Expose
    String username;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("password")
    @Expose
    String password;

    @SerializedName("contact")
    @Expose
    String contact;

    @SerializedName("students")
    @Expose
    List<FavoriteModel> students;

    @SerializedName("skills")
    @Expose
    List<SkillModel> skills;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<FavoriteModel> getStudents() {
        return students;
    }

    public void setStudents(List<FavoriteModel> students) {
        this.students = students;
    }

    public List<SkillModel> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillModel> skills) {
        this.skills = skills;
    }
}

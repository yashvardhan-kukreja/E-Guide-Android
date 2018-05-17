package com.eguide.yash1300.e_guide.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SkillModel {

    @SerializedName("_id")
    @Expose
    String id;

    @SerializedName("name")
    @Expose
    String name;

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
}

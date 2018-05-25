package com.eguide.yash1300.e_guide.Listeners;

import com.eguide.yash1300.e_guide.Models.SkillModel;

import java.util.List;

public interface TeacherFetchAllSkillsListener {

    void onSuccess (List<SkillModel>skills);
    void onFailure (String message);

}

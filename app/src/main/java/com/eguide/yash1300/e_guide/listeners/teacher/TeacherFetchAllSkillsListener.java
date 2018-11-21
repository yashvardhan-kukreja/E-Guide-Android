package com.eguide.yash1300.e_guide.listeners.teacher;

import com.eguide.yash1300.e_guide.models.SkillModel;

import java.util.List;

public interface TeacherFetchAllSkillsListener {

    void onSuccess (List<SkillModel>skills);
    void onFailure (String message);

}

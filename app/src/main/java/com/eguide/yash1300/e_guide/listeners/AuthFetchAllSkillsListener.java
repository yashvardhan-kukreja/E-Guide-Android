package com.eguide.yash1300.e_guide.listeners;

import com.eguide.yash1300.e_guide.models.SkillModel;

import java.util.List;

public interface AuthFetchAllSkillsListener {

    void onSuccess(List<SkillModel> skillModels);
    void onFailure(String message);

}

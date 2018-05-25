package com.eguide.yash1300.e_guide.Listeners;

import com.eguide.yash1300.e_guide.Models.SkillModel;

public interface SignUpSkillUpdateListener {

    void includeSkill(String skill_id);
    void excludeSkill(String skill_id);

}

package com.eguide.yash1300.e_guide.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eguide.yash1300.e_guide.R;
import com.eguide.yash1300.e_guide.models.SkillModel;
import com.eguide.yash1300.e_guide.models.TeacherModel;

import java.util.List;

public class StudentAllTeachersAdapter extends RecyclerView.Adapter<StudentAllTeachersAdapter.ViewHolder> {

    List<TeacherModel> teachers;
    Context context;
    String token;

    public StudentAllTeachersAdapter(List<TeacherModel> teachers, Context context, String token) {
        this.teachers = teachers;
        this.context = context;
        this.token = token;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.adapter_student_all_teachers, parent, false);
        return (new ViewHolder(rootView));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = teachers.get(position).getName();
        String email = teachers.get(position).getEmail();
        String contact = teachers.get(position).getContact();

        List<SkillModel> skills = teachers.get(position).getSkills();

        StringBuilder skillBuilder = new StringBuilder();

        for (SkillModel skill: skills)
            skillBuilder.append("- " + skill.getName() + "\n");

        holder.name.setText(name);
        holder.skill.setText(skillBuilder.toString());
        holder.email.setText(email);
        holder.contact.setText(contact);


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_favor_teacher, null, false);
        RecyclerView favorTeacherSkills = dialogView.findViewById(R.id.favor_teacher_skill_list);
        favorTeacherSkills.setLayoutManager(new LinearLayoutManager(context));
        favorTeacherSkills.setAdapter(new StudentFavorTeacherSkillAdapter(context, token, skills, teachers.get(position).getId()));

        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();



        holder.favorTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, skill, email, contact;
        ImageView favorTeacher;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.adapter_fav_teacher_name);
            skill = itemView.findViewById(R.id.adapter_fav_teacher_skill);
            email = itemView.findViewById(R.id.adapter_fav_teacher_email);
            contact = itemView.findViewById(R.id.adapter_fav_teacher_contact);
            favorTeacher = itemView.findViewById(R.id.favor_teacher);

        }
    }
}

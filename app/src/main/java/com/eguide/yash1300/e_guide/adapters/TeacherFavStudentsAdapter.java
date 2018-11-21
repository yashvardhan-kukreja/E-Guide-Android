package com.eguide.yash1300.e_guide.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eguide.yash1300.e_guide.models.FavoriteModel;
import com.eguide.yash1300.e_guide.R;

import java.util.List;

public class TeacherFavStudentsAdapter extends RecyclerView.Adapter<TeacherFavStudentsAdapter.ViewHolder> {

    Context context;
    List<FavoriteModel> favoriteStudents;

    public TeacherFavStudentsAdapter(Context context, List<FavoriteModel> favorites) {
        this.context = context;
        this.favoriteStudents = favorites;
    }

    @NonNull
    @Override
    public TeacherFavStudentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.adapter_teacher_fav_students, parent, false);
        return (new ViewHolder(rootView));
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherFavStudentsAdapter.ViewHolder holder, int position) {
        FavoriteModel currentFavorite = favoriteStudents.get(position);
        holder.name.setText(currentFavorite.getStudent().getName());
        holder.skill.setText(currentFavorite.getSkill().getName());
        holder.email.setText(currentFavorite.getStudent().getEmail());
        holder.contact.setText(currentFavorite.getStudent().getContact());

    }

    @Override
    public int getItemCount() {
        return favoriteStudents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, skill, email, contact;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.adapter_fav_student_name);
            skill = itemView.findViewById(R.id.adapter_fav_student_skill);
            email = itemView.findViewById(R.id.adapter_fav_student_email);
            contact = itemView.findViewById(R.id.adapter_fav_student_contact);
        }
    }

}

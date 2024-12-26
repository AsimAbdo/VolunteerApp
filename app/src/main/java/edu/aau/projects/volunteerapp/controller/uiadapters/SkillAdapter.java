package edu.aau.projects.volunteerapp.controller.uiadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.databinding.CustomSkillItemBinding;
import edu.aau.projects.volunteerapp.utils.UiUtils;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.SkillHolder> {

    List<String> skills;

    public SkillAdapter() {
    }

    public SkillAdapter(List<String> skills) {
        this.skills = skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
        notifyDataSetChanged();
    }


    public List<String> getSkills() {
        return skills;
    }

    @NonNull
    @Override
    public SkillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_skill_item,null);
        return new SkillHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return skills == null? 0 : skills.size();
    }

    public boolean addSkill(String skill) {
        boolean done = true;
        if (!skills.contains(skill))
            skills.add(skill);
        else
            done = false;
        notifyDataSetChanged();
        return done;
    }

    public class SkillHolder extends RecyclerView.ViewHolder {
        CustomSkillItemBinding bin;
        public SkillHolder(@NonNull View itemView) {
            super(itemView);
            bin = CustomSkillItemBinding.bind(itemView);
        }

        public void onBind(int position) {
            String skill = skills.get(position);
            bin.skillTvTitle.setText(skill);
            bin.skillIvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bin.skillTvTitle.isEnabled()){
                        if (UiUtils.verifyFields(bin.skillTvTitle)){
                            String skill = bin.skillTvTitle.getText().toString();
                            skills.remove(position);
                            skills.add(position, skill);
                            notifyDataSetChanged();
                        }
                        bin.skillIvEdit.setImageResource(R.drawable.ic_edit);
                        bin.skillIvDelete.setVisibility(View.VISIBLE);
                    }
                    else {
                        bin.skillIvEdit.setImageResource(R.drawable.ic_check);
                        bin.skillIvDelete.setVisibility(View.GONE);
                    }
                    bin.skillTvTitle.setEnabled(!bin.skillTvTitle.isEnabled());
                }
            });

            bin.skillIvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    skills.remove(bin.skillTvTitle.getText().toString());
                    notifyDataSetChanged();
                }
            });
        }
    }
}

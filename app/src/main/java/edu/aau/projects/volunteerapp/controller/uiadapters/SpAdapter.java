package edu.aau.projects.volunteerapp.controller.uiadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import edu.aau.projects.volunteerapp.R;
import edu.aau.projects.volunteerapp.databinding.CustomSpItemBinding;

public class SpAdapter extends BaseAdapter {
    List<String> texts;
    private Context context;

    public SpAdapter(List<String> texts, Context context) {
        this.texts = texts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return texts == null? 0 : texts.size();
    }

    @Override
    public String getItem(int position) {
        return texts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null)
            v = LayoutInflater.from(context).inflate(R.layout.custom_sp_item, null);
        CustomSpItemBinding bin = CustomSpItemBinding.bind(v);
        bin.cSpTvTitle.setText(texts.get(position));
        bin.cSpIvImage.setVisibility(View.GONE);
        return null;
    }
}

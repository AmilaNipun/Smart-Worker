package com.example.SmartWorker.Model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SmartWorker.R;

public class MyJobsHolder extends RecyclerView.ViewHolder {

    public ImageView MimageView;
    public TextView MjTitle,MJobLocation,MJobDueDate,MJobCategory,MJobBudget,MContactnum;
    public View v;


    public MyJobsHolder(@NonNull View itemView) {
        super(itemView);


        MimageView = itemView.findViewById(R.id.jobImage_myjobs);
        MjTitle = itemView.findViewById(R.id.job_title_single_myjobs);
        MJobLocation = itemView.findViewById(R.id.addJobLocation_myjobs);
        MJobDueDate = itemView.findViewById(R.id.dueDate_myjobs);
        MJobCategory = itemView.findViewById(R.id.jobCategory_myjobs);
        MJobBudget = itemView.findViewById(R.id.jobBudget_myjobs);
        MContactnum = itemView.findViewById(R.id.textviewcontactnum_myjobs);
            v = itemView;


    }
}

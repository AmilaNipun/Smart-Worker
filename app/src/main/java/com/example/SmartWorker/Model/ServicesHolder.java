package com.example.SmartWorker.Model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SmartWorker.R;

public class ServicesHolder extends RecyclerView.ViewHolder {

    public ImageView imageViewServices;
    public TextView sTopic,sDescription,sLocation,sContact;
    public View v;

    public ServicesHolder(@NonNull View itemView) {
        super(itemView);

        imageViewServices = itemView.findViewById(R.id.serviceImage);
        sTopic = itemView.findViewById(R.id.service_titile_single);
        sDescription = itemView.findViewById(R.id.serviceDescription_single);
        sLocation = itemView.findViewById(R.id.serviceLocation_single);
        sContact = itemView.findViewById(R.id.serviceContact_single);
        v = itemView;
    }
}

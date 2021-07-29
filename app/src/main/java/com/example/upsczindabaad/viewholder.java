package com.example.upsczindabaad;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class viewholder extends RecyclerView.ViewHolder {

    CardView grpCard;
    TextView grpname;
    ImageView grpImage;

    public viewholder(@NonNull View itemView) {
        super(itemView);

        grpCard=(CardView)itemView.findViewById(R.id.cardgrp);
        grpname=(TextView)itemView.findViewById(R.id.nameGrp);
        grpImage=(ImageView)itemView.findViewById(R.id.imageGrp);
    }
}

package com.example.upsczindabaad;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class viewholder extends RecyclerView.ViewHolder {

    TextView grpname;
    ImageView grpImage;

    public viewholder(@NonNull View itemView) {
        super(itemView);

        grpname=(TextView)itemView.findViewById(R.id.nameGrp);
        grpImage=(ImageView)itemView.findViewById(R.id.imageGrp);
    }
}

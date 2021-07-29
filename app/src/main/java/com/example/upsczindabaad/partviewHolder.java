package com.example.upsczindabaad;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class partviewHolder extends RecyclerView.ViewHolder {
    TextView grppname;
    ImageView grppImage;

    public partviewHolder(@NonNull View itemView) {
        super(itemView);


        grppImage=itemView.findViewById(R.id.participantsIMage);
        grppname=itemView.findViewById(R.id.particpantsName);
    }
}


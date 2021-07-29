package com.example.upsczindabaad.posts;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upsczindabaad.R;

public class allPostViewHolder extends RecyclerView.ViewHolder {

    ImageView playap,imageap,like,comment,profilepic;
    TextView username;


    public allPostViewHolder(@NonNull View itemView) {
        super(itemView);

        playap=itemView.findViewById(R.id.playap);
        imageap=itemView.findViewById(R.id.imageap);
        like=itemView.findViewById(R.id.aplike);
        comment=itemView.findViewById(R.id.commentap);
        profilepic=itemView.findViewById(R.id.apuserimage);
        username=itemView.findViewById(R.id.apusername);
    }
}

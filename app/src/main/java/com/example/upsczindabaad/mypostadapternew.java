package com.example.upsczindabaad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class mypostadapternew extends RecyclerView.Adapter<mypostadapternew.viewHolder> {

    ArrayList<mypostmodel> data;
    Context context;
    public mypostadapternew(ArrayList<mypostmodel> data, Context context) {
        this.data = data;
        this.context = context;
    }



    @NonNull
    @Override
    public mypostadapternew.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypostsingllerowimage, null, false);
        return new mypostadapternew.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull mypostadapternew.viewHolder holder, int position) {
       // Toasty.success(context,data.get(position).getImageurl()).show();
        Glide.with(context)
                .load(data.get(position).getImageurl())
                .into(holder.postImage);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder {


       // CardView grpCard;

        ImageView postImage;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            //grpCard = itemView.findViewById(R.id.singleCard);
            postImage = (ImageView) itemView.findViewById(R.id.imagempimage);
           // groupName = (TextView) itemView.findViewById(R.id.singleusername);
        }

    }
}

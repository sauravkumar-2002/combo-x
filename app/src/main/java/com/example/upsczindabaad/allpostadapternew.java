package com.example.upsczindabaad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class allpostadapternew extends RecyclerView.Adapter<allpostadapternew.viewHolder> {
    ArrayList<allpostmodelrecv> data;
    Context context;
    public allpostadapternew(ArrayList<allpostmodelrecv> data, Context context) {
        this.data = data;
        this.context = context;
    }



    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allpostsinglerowimage, null, false);
        return new allpostadapternew.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Glide.with(context)
                .load(data.get(position).getPostimage())
                .into(holder.postImage);
        Glide.with(context)
                .load(data.get(position).getProfname())
                .into(holder.profpic);
        holder.name.setText(data.get(position).getProfname());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder {


        // CardView grpCard;

        ImageView postImage;
        ImageView profpic;
        TextView name;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            profpic=itemView.findViewById(R.id.apimageuserimage);
            name=itemView.findViewById(R.id.apimageusername);
            //grpCard = itemView.findViewById(R.id.singleCard);
            postImage = (ImageView) itemView.findViewById(R.id.imageapimage);
            // groupName = (TextView) itemView.findViewById(R.id.singleusername);
        }

    }
}

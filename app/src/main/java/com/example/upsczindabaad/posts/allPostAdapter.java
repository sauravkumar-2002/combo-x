package com.example.upsczindabaad.posts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upsczindabaad.R;

import java.util.ArrayList;

public class allPostAdapter extends RecyclerView.Adapter<allPostViewHolder> {

    ArrayList<allPostModel> data;
    Context context;

    public allPostAdapter(ArrayList<allPostModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public allPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.allpostsinglerow,null,false);
       return new allPostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull allPostViewHolder holder, int position) {

        holder.username.setText(data.get(position).getUsernameap());
        holder.profilepic.setImageResource(data.get(position).getUserimageap());
        holder.imageap.setImageResource(data.get(position).getImageap());


        if(data.get(position).getVori()==1){
            holder.imageap.setAlpha(0.55f);
            holder.playap.setVisibility(View.VISIBLE);

        }else{

        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

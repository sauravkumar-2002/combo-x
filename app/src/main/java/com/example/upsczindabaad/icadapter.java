package com.example.upsczindabaad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class icadapter extends RecyclerView.Adapter<icadapter.myviewholder> {
ArrayList<icmodel>mlist;
Context context;
    public  icadapter(ArrayList<icmodel>mlist,Context context){
        this.context=context;
        this.mlist=mlist;
    }
    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,null,false);
        return  new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        icmodel model=mlist.get(position);
        holder.tv.setText(model.getTv());
        holder.iv.setImageResource(R.drawable.prof);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
             iv=itemView.findViewById(R.id.singleprof);
            tv=itemView.findViewById(R.id.singleusername);
        }
    }
}

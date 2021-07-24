package com.example.upsczindabaad;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
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
        holder.iv.setImageResource(model.getImageUrl());

        holder.singleCardView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(context,chtwnd.class);
                Pair[] pairs=new Pair[2];
                pairs[0]=new Pair<View,String>(holder.tv,"ChatpersonName");
                pairs[1]=new Pair<View,String>(holder.iv,"ChatpersonImage");

                intent.putExtra("name",model.getTv());
                intent.putExtra("image",model.getImageUrl());

                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation((Activity) context,pairs);
                context.startActivity(intent,options.toBundle());

            }
        });

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;
        CardView singleCardView;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
             iv=itemView.findViewById(R.id.singleprof);
            tv=itemView.findViewById(R.id.singleusername);
            singleCardView=itemView.findViewById(R.id.singleCard);
        }
    }
}

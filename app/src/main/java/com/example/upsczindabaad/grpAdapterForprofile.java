package com.example.upsczindabaad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class grpAdapterForprofile extends RecyclerView.Adapter<viewholder> {

    public grpAdapterForprofile(ArrayList<grpChatModel> datalist) {
        this.datalist = datalist;
    }

    ArrayList<grpChatModel>datalist;


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.myprofile_grp_singlerow,null,false);
        return  new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        holder.grpname.setText(datalist.get(position).getGrpName());
        holder.grpImage.setImageResource(datalist.get(position).getGrpImage());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
}

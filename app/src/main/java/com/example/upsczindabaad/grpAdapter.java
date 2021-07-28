package com.example.upsczindabaad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class grpAdapter extends RecyclerView.Adapter<grpAdapter.viewHolder> {


    ArrayList<grpChatModel> data;

    public grpAdapter(ArrayList<grpChatModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,null,false);
        return  new grpAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
holder.groupName.setText(data.get(position).getGrpName());
holder.groupImage.setImageResource(data.get(position).getGrpImage());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }




    public class viewHolder extends RecyclerView.ViewHolder {

        TextView groupName;
        ImageView groupImage;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            groupImage=(ImageView)itemView.findViewById(R.id.singleprof);
            groupName=(TextView)itemView.findViewById(R.id.singleusername);
        }
    }
}

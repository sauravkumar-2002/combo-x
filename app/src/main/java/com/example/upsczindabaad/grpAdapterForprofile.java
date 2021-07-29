package com.example.upsczindabaad;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class grpAdapterForprofile extends RecyclerView.Adapter<viewholder> {


    ArrayList<grpChatModel>datalist;
    Context context;

    public grpAdapterForprofile(ArrayList<grpChatModel> datalist, Context context) {
        this.datalist = datalist;
        this.context = context;
    }

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



        holder.grpCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str="https://firebasestorage.googleapis.com/v0/b/upsczindabaad-6d9bf.appspot.com/o/image%2Fprof.png?alt=media&token=6166f093-4a90-4dce-8034-539a9dce792f";

                Intent intent=new Intent(context,chtwnd.class);
                intent.putExtra("name",datalist.get(position).getGrpName());
                intent.putExtra("image",str);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
}

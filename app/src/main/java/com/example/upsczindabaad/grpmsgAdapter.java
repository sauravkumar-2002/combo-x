package com.example.upsczindabaad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class grpmsgAdapter extends RecyclerView.Adapter {

    ArrayList<ic_model1> grplist;
    Context context;

    public grpmsgAdapter(ArrayList<ic_model1> grplist, Context context) {
        this.grplist = grplist;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
       return grplist.get(position).getViewtype();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new grpmsgAdapter.vh2(LayoutInflater.from(context).inflate(R.layout.grpsinglerow2, parent, false));
        } else {
            return new grpmsgAdapter.vh1(LayoutInflater.from(context).inflate(R.layout.grpsinglerow1, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof grpmsgAdapter.vh2) {
            // ((mvh2)holder).name2.setText(llist.get(position).getName());
            ((vh2) holder).msg2.setText(grplist.get(position).getMsg());
        } else {
            //((mvh1)holder).name1.setText(llist.get(position).getName());
            ((vh1) holder).msg.setText(grplist.get(position).getMsg());
            ((vh1) holder).name.setText(grplist.get(position).getName());
        }

    }

    @Override
    public int getItemCount() {
        return grplist.size();
    }


    public class vh1 extends RecyclerView.ViewHolder{
        TextView msg,name;

        public vh1(@NonNull View itemView) {
            super(itemView);

            msg=itemView.findViewById(R.id.grp1msg);
            name=itemView.findViewById(R.id.msgername);
        }
    }


    public class vh2 extends RecyclerView.ViewHolder{

        TextView msg2;

        public vh2(@NonNull View itemView) {
            super(itemView);

            msg2=itemView.findViewById(R.id.grp2msg);
        }
    }
}

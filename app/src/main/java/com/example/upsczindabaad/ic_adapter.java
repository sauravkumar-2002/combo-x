package com.example.upsczindabaad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ic_adapter extends RecyclerView.Adapter {
    ArrayList<ic_model1> llist;
    Context context;

    public ic_adapter(ArrayList<ic_model1> llist, Context context) {
        this.llist = llist;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {

        return llist.get(position).getViewtype();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // View view=LayoutInflater.from(context).inflate(R.layout.ic_singlerow1,parent,false);
        //return  new mvh1(view);
        if (viewType == 0) {
            return new mvh2(LayoutInflater.from(context).inflate(R.layout.ic_singlerow2, parent, false));
        } else {
            return new mvh1(LayoutInflater.from(context).inflate(R.layout.ic_singlerow1, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof mvh2) {
            // ((mvh2)holder).name2.setText(llist.get(position).getName());
            ((mvh2) holder).msg2.setText(llist.get(position).getMsg());
        } else {
            //((mvh1)holder).name1.setText(llist.get(position).getName());
            ((mvh1) holder).msg1.setText(llist.get(position).getMsg());
        }
    }

    @Override
    public int getItemCount() {
        return llist.size();
    }

    public class mvh1 extends RecyclerView.ViewHolder {
        TextView name1, msg1;

        public mvh1(@NonNull View itemView) {
            super(itemView);
            // name1=itemView.findViewById(R.id.ic_singlerow1name);
            msg1 = itemView.findViewById(R.id.ic_singlerow1msg);
        }
    }

    public class mvh2 extends RecyclerView.ViewHolder {
        TextView name2, msg2;

        public mvh2(@NonNull View itemView) {
            super(itemView);
            // name2=itemView.findViewById(R.id.ic_singlerow2name);
            msg2 = itemView.findViewById(R.id.ic_singlerow2msg);
        }
    }
}


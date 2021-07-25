package com.example.upsczindabaad;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ic_adapter extends RecyclerView.Adapter {
    ArrayList<ic_model1> data;
    Context context;
    String retrievedusername;
    TextView retrivedname;
    public ic_adapter(ArrayList<ic_model1> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       String name=data.get(viewType).getName();

      return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class mvh1 extends RecyclerView.ViewHolder{
        TextView name1,msg1;
        public mvh1(@NonNull View itemView) {
            super(itemView);
            name1=itemView.findViewById(R.id.ic_singlerow1name);
            msg1=itemView.findViewById(R.id.ic_singlerow1msg);
        }
    }
    public class mvh2 extends RecyclerView.ViewHolder{
        TextView name2,msg2;
        public mvh2(@NonNull View itemView) {
            super(itemView);
            name2=itemView.findViewById(R.id.ic_singlerow2name);
            msg2=itemView.findViewById(R.id.ic_singlerow2msg);
        }
    }
}

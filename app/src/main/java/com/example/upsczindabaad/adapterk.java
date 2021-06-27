package com.example.upsczindabaad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapterk extends RecyclerView.Adapter {
    ArrayList<modelk>m1k;

    public adapterk(ArrayList<modelk> m1k) {
        this.m1k = m1k;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==0) {
            return new mvh1(LayoutInflater.from(parent.getContext()).inflate(R.layout.single1k, parent, false));
        }
        else {
            return new mvh2(LayoutInflater.from(parent.getContext()).inflate(R.layout.single2k, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof mvh1){
            ((mvh1) holder).tk1.setText(m1k.get(position).getS1());

        }
        else{
            ((mvh2) holder).tk2.setText(m1k.get(position).getS1());

        }
    }

    @Override
    public int getItemCount() {
        return m1k.size();
    }
    public class mvh1 extends RecyclerView.ViewHolder {
        TextView tk1;
        public mvh1(@NonNull View itemView) {

            super(itemView);
            tk1=itemView.findViewById(R.id.singl1k);
        }
    }
public class mvh2 extends RecyclerView.ViewHolder{
    TextView tk2;
    public mvh2(@NonNull View itemView) {
        super(itemView);
        tk2=itemView.findViewById(R.id.singl2k);
    }
}
}

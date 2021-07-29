package com.example.upsczindabaad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class partadapter extends RecyclerView.Adapter<partviewHolder> {


    ArrayList<partmodel>data;
    Context context;

    public partadapter(ArrayList<partmodel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public partviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.edtgrpsinglerow,null,false);
        return  new partviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull partviewHolder holder, int position) {
holder.grppname.setText(data.get(position).getName());
       // Toasty.success(context,data.get(position).getName()).show();

String image=data.get(position).getImage();

     //    Toasty.success(context,image).show();


if(image.equals("notuploaded")){
    Glide.with(context).load("https://firebasestorage.googleapis.com/v0/b/upsczindabaad-6d9bf.appspot.com/o/image%2Fprof.png?alt=media&token=6166f093-4a90-4dce-8034-539a9dce792f").into(holder.grppImage);
}else{

    Glide.with(context).load(image).into(holder.grppImage);
}
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

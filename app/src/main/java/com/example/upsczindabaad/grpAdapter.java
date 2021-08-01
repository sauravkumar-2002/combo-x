package com.example.upsczindabaad;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class grpAdapter extends RecyclerView.Adapter<grpAdapter.viewHolder> {
    String str;

    ArrayList<grpChatModel> data;
    Context context;

    public grpAdapter(ArrayList<grpChatModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow, null, false);
        return new grpAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.groupName.setText(data.get(position).getGrpName());
     //   Log.v("ghjk",holder.groupName.getText().toString());
       // holder.groupImage.setImageResource();
    // holder.groupImage.setImageResource(R.drawable.grp);


           //Toasty.success(context,data.get(position).getGrpImage()).show();
if(data.get(position).getGrpImage().equals("notuploadeimg")){
    holder.groupImage.setImageResource(R.drawable.grp);
    str="https://firebasestorage.googleapis.com/v0/b/upsczindabaad-6d9bf.appspot.com/o/grup.jpeg?alt=media&token=275f272e-5ae6-4fe8-8ab6-e882e4860329";
}
else{
    str=data.get(position).getGrpImage();
    Glide.with(context)
            .load(data.get(position).getGrpImage())
            .into(holder.groupImage);
}



        holder.grpCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  Toasty.success(context,"hello").show();
                //str = "https://firebasestorage.googleapis.com/v0/b/upsczindabaad-6d9bf.appspot.com/o/image%2Fprof.png?alt=media&token=6166f093-4a90-4dce-8034-539a9dce792f";
                Intent intent = new Intent(context, chtwndGrp.class);
                intent.putExtra("name", data.get(position).getGrpName());
                intent.putExtra("image", str);
                intent.putExtra("invite", data.get(position).getGrpInviteCode());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {


        CardView grpCard;
        TextView groupName;
        ImageView groupImage;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            grpCard = itemView.findViewById(R.id.singleCard);
            groupImage =  itemView.findViewById(R.id.singleprof);
            groupName =  itemView.findViewById(R.id.singleusername);
        }
    }
}

package com.example.upsczindabaad;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.upsczindabaad.posts.allPostModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link myPost#newInstance} factory method to
 * create an instance of this fragment.
 */
public class myPost extends Fragment {
FloatingActionButton fb;
ImageView profpic;
TextView username1;
RecyclerView recyclerView;
   mypostadapternew mypostadapternew;
    ArrayList<mypostmodel> glist;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public myPost() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment myPost.
     */
    // TODO: Rename and change types and number of parameters
    public static myPost newInstance(String param1, String param2) {
        myPost fragment = new myPost();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View v=inflater.inflate(R.layout.fragment_my_post, container, false);

       fb=v.findViewById(R.id.imagepost);
       recyclerView=v.findViewById(R.id.mypostrecv);
       username1=v.findViewById(R.id.usernamemypost);
       profpic=v.findViewById(R.id.userimagemypost);

        glist = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       mypostadapternew = new mypostadapternew(glist,getContext());
       recyclerView.setAdapter(mypostadapternew);
showpost();

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), postimageupload.class);
                startActivity(intent);
            }

        });

        // Inflate the layout for this fragment
        return v;
    }

    private void showpost() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        DatabaseReference refg=FirebaseDatabase.getInstance().getReference("List of Users").child(uid);
        refg.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userdatamodel userdatamodel=snapshot.getValue(com.example.upsczindabaad.userdatamodel.class);
               String s1=userdatamodel.getUsername();
               String s2=userdatamodel.getPimage();
               username1.setText(s1);
                Glide.with(getContext())
                        .load(s2)
                        .into(profpic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("posts").child(uid);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                glist.clear();

                for(DataSnapshot s:snapshot.getChildren()){
                    postuploadmodel postuploadmodel=s.getValue(com.example.upsczindabaad.postuploadmodel.class);
                    mypostmodel mp=new mypostmodel();
                    mp.setImageurl(postuploadmodel.getPost());
                   // Toasty.success(getContext(),postuploadmodel.getPost()).show();
                    glist.add(mp);
                }
                mypostadapternew.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
package com.example.upsczindabaad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link allPost#newInstance} factory method to
 * create an instance of this fragment.
 */
public class allPost extends Fragment {
RecyclerView recyclerView;
    allpostadapternew allpostadapternew;
    ArrayList<allpostmodelrecv> glist;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public allPost() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment allPost.
     */
    // TODO: Rename and change types and number of parameters
    public static allPost newInstance(String param1, String param2) {
        allPost fragment = new allPost();
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
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_all_post, container, false);
        recyclerView=v.findViewById(R.id.rvallpost);
        glist = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        allpostadapternew = new allpostadapternew(glist,getContext());
        recyclerView.setAdapter(allpostadapternew);
showpostall();
     //   allPostAdapter = new allPostAdapter(data, getContext());
       // rvallpost.setAdapter(allPostAdapter);


        return v;


    }

    private void showpostall() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("allposts");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                glist.clear();

                for(DataSnapshot s:snapshot.getChildren()){
                    allpostuploadmodel postuploadmodel=s.getValue(com.example.upsczindabaad.allpostuploadmodel.class);
                   allpostmodelrecv  mp=new allpostmodelrecv();
                    mp.setPostimage(postuploadmodel.getPost());
                    mp.setProfimage(postuploadmodel.getUseruploadedpic());
                    mp.setProfname(postuploadmodel.getUsername());
                    // Toasty.success(getContext(),postuploadmodel.getPost()).show();
                    glist.add(mp);
                }
                allpostadapternew.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //   allPostAdapter.notifyDataSetChanged();


    }

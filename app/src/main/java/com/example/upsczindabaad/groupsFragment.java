package com.example.upsczindabaad;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link groupsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class groupsFragment extends Fragment {

    TextView usernameGrp;
    ImageView imageGrp;
    RecyclerView recViewGrp;
    userdatamodel umd3;
    grpAdapter grpAdapter;
    ArrayList<grpChatModel> glist;
    icadapter icadapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public groupsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment groupsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static groupsFragment newInstance(String param1, String param2) {
        groupsFragment fragment = new groupsFragment();
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

      //  setUserName();


        View v = inflater.inflate(R.layout.fragment_groups, container, false);

        recViewGrp = v.findViewById(R.id.icrecviewGrp);


        glist = new ArrayList<>();


        recViewGrp.setLayoutManager(new LinearLayoutManager(getContext()));
        grpAdapter = new grpAdapter(glist,getContext());
        recViewGrp.setAdapter(grpAdapter);


        showAllGrp();




        return v;

    }

    private void showAllGrp() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("userowngroup").child(uid);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                glist.clear();

                for (DataSnapshot d : snapshot.getChildren()) {
                    modeltotalgroup md = d.getValue(modeltotalgroup.class);
                    String groupName = md.getGroupName();


                    grpChatModel grp = new grpChatModel();
                    grp.setGrpName(groupName);
                    grp.setGrpImage(R.drawable.arturo);

                    glist.add(grp);


                }
                grpAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void setUserName() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String uid = user.getUid();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("List of Users").child(uid);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                umd3 = new userdatamodel();
                umd3 = snapshot.getValue(userdatamodel.class);
                String userimage = umd3.getPimage();
                String username = umd3.getUsername();
                if (userimage.equals("notuploaded")) {
                    imageGrp.setImageResource(R.drawable.prof);
                    // Toasty.success(getApplicationContext(), "Default image has been setted").show();
                } else {
                    Glide.with(getContext())
                            .load(userimage)
                            .into(imageGrp);
                    //Toasty.success(getApplicationContext(), "Updated").show();
                }

                usernameGrp.setText(username);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}
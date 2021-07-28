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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link individual_chats#newInstance} factory method to
 * create an instance of this fragment.
 */
public class individual_chats extends Fragment {

    TextView icusername;
    ImageView icprofilepic, ie1;
    RecyclerView icrecv;
    userdatamodel umd2;
    ArrayList<icmodel> list;
    icadapter icadapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public individual_chats() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment individual_chats.
     */
    // TODO: Rename and change types and number of parameters
    public static individual_chats newInstance(String param1, String param2) {
        individual_chats fragment = new individual_chats();
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
        View v= inflater.inflate(R.layout.fragment_individual_chats, container, false);

        icusername = v.findViewById(R.id.icusername);
        icprofilepic = v.findViewById(R.id.icprofilepic);
        icrecv = v.findViewById(R.id.icrecview);
        icrecv.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();

        icadapter = new icadapter(list, getContext());
        icrecv.setAdapter(icadapter);


        updateStatusUser();


        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("List of Users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    userdatamodel umd3 = dataSnapshot.getValue(userdatamodel.class);
                    if (umd3.getUid().equals(uid)) {
                        continue;
                    }
                    String individual_chat = umd3.getUsername();
                    icmodel icm = new icmodel();
                    icm.setTv(individual_chat);
                    String st = umd3.getPimage();

                    if (st.equals("notuploaded")) {
                        icm.setImageUrl("https://firebasestorage.googleapis.com/v0/b/upsczindabaad-6d9bf.appspot.com/o/image%2Fprof.png?alt=media&token=6166f093-4a90-4dce-8034-539a9dce792f");
                    } else {
                        icm.setImageUrl(st);
                    }
                    list.add(icm);
                }
                icadapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        setUserName();

        icprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), myprofile.class);
                startActivity(intent);
            }
        });

        icusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), myprofile.class);
                startActivity(intent);
            }
        });


return v;

    }

    private void updateStatusUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User Status").child(uid);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = simpleDateFormat.format(calendar.getTime());

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");
        String time = simpleDateFormat1.format(calendar.getTime());


        updatestatus updatestatus = new updatestatus();
        updatestatus.setChatting("Not chatting");
        updatestatus.setStatus("online");
        reference.setValue(updatestatus);


    }


    private void setUserName() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String uid = user.getUid();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("List of Users").child(uid);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                umd2 = new userdatamodel();
                umd2 = snapshot.getValue(userdatamodel.class);
                String userimage = umd2.getPimage();
                String username = umd2.getUsername();
                if (userimage.equals("notuploaded")) {
                    icprofilepic.setImageResource(R.drawable.prof);
                    // Toasty.success(getApplicationContext(), "Default image has been setted").show();
                } else {
                    Glide.with(getContext())
                            .load(userimage)
                            .into(icprofilepic);
                    //Toasty.success(getApplicationContext(), "Updated").show();
                }

                icusername.setText(username);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}
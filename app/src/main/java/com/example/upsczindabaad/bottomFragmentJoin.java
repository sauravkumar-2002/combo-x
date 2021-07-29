package com.example.upsczindabaad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link bottomFragmentJoin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class bottomFragmentJoin extends BottomSheetDialogFragment {

    int ans = 0;  //1-grp exists  2-grp exists not memeber 3--exists and member 0-grp doest exists
    EditText invitecode;
    int res = 0;  // 1-memeber 0-not member
    Button joinGrp;
    modelgrupdetails modelgrupdetails1;

    String s1, invitecode1, sname = "kkkk", spic;
    String uidf;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public bottomFragmentJoin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment bottomFragmentJoin.
     */
    // TODO: Rename and change types and number of parameters
    public static bottomFragmentJoin newInstance(String param1, String param2) {
        bottomFragmentJoin fragment = new bottomFragmentJoin();
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
        View v = inflater.inflate(R.layout.fragment_bottom_join, container, false);
        joinGrp = v.findViewById(R.id.joinGrp);
        invitecode = v.findViewById(R.id.inviteCode);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uidf = user.getUid();


        joinGrp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                invitecode1 = invitecode.getText().toString().trim();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("totalgroup");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ans = 0;
                        res = 0;
                        for (DataSnapshot s : snapshot.getChildren()) {
                            modelx modelx = s.getValue(com.example.upsczindabaad.modelx.class);
                            if (modelx.getInvitecode().equals(invitecode1)) {
                                ans = 1;
                                break;
                            }

                        }

                        if (ans == 1) {

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("grup").child(invitecode1).child("members");
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    for (DataSnapshot sn : snapshot.getChildren()) {
                                        modelgrupparticipants modelgrupparticipants = sn.getValue(com.example.upsczindabaad.modelgrupparticipants.class);
                                        String uid = modelgrupparticipants.getUid();
                                        if (uid.equals(uidf)) {
                                            res = 1;
                                            break;

                                        }

                                    }

                                    if (res == 1)
                                        Toasty.error(getContext(), "you are already a member").show();

                                    else {

                                        DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("grup").child(invitecode1).child("groupdetails");
                                        reference1.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                modelgrupdetails modelgrupdetails=snapshot.getValue(com.example.upsczindabaad.modelgrupdetails.class);

                                                String grpname=modelgrupdetails.getGroupname();
                                                String  grpImage=modelgrupdetails.getGruppic();
                                             //   Log.v("sandy",grpImage);

                                                DatabaseReference reference2=FirebaseDatabase.getInstance().getReference("userowngroup").child(uidf);
                                                modeluserowngrup modeluserowngrup=new modeluserowngrup();
                                                modeluserowngrup.setGroupName(grpname);
                                                modeluserowngrup.setGrouppic(grpImage);
                                                modeluserowngrup.setInvitecode(invitecode1);
                                                reference2.push().setValue(modeluserowngrup);


                                                DatabaseReference reference3=FirebaseDatabase.getInstance().getReference("grup").child(invitecode1).child("members");
                                                reference3.push().child("uid").setValue(uidf);

                                                Toasty.success(getContext(),"You are Successfully Added to "+grpname).show();

                                                Intent intent=new Intent(getContext(),tabbedWindow.class);
                                                intent.putExtra("check",1);
                                                startActivity(intent);


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });


                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        } else Toasty.error(getContext(), "grp not exist").show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

        return v;


    }

    private void retrivedata() {


    }
}

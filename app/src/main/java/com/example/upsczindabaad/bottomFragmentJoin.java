package com.example.upsczindabaad;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    EditText invitecode;
    Button joinGrp;
    int ans;
    String s1;
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
       View v=inflater.inflate(R.layout.fragment_bottom_join,container,false);
       joinGrp=v.findViewById(R.id.joinGrp);
       invitecode=v.findViewById(R.id.inviteCode);

       joinGrp.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               /*
ans=0;
               FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
               String uid=user.getUid();
     String invitecode1=invitecode.getText().toString().trim();
     DatabaseReference reft=FirebaseDatabase.getInstance().getReference("totalgroup");
     reft.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             for(DataSnapshot s:snapshot.getChildren()){
                modeltotalgroup modeltotalgroup=s.getValue(com.example.upsczindabaad.modeltotalgroup.class);
                 s1=modeltotalgroup.getInvitecode();
               //  Log.v("hhhs1",s1);


                 if(s1.equals(invitecode1)){
                     DatabaseReference reft=FirebaseDatabase.getInstance().getReference("grup");

                 }
             }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });

    // Toasty.success(getActivity(),"ans is"+s1).show();





*/


               String invitecode1=invitecode.getText().toString().trim();

               DatabaseReference reference= FirebaseDatabase.getInstance().getReference("totalgroup");
               reference.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {

                       for(DataSnapshot s: snapshot.getChildren()){
                           ans=0;
                           modeltotalgroup modeltotalgroup=s.getValue(com.example.upsczindabaad.modeltotalgroup.class);
                           s1=modeltotalgroup.getInvitecode();

                           if(s1.equals(invitecode1)){

                               DatabaseReference redf=FirebaseDatabase.getInstance().getReference("grup").child(invitecode1).child("members");
                               redf.addValueEventListener(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot snapshot) {

                                     //  Toasty.success(getActivity(),s1).show();

                                       FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                                       uidf=user.getUid();

                                       for (DataSnapshot s:snapshot.getChildren()){

                                           modelgrupparticipants modelgrupparticipants=s.getValue(com.example.upsczindabaad.modelgrupparticipants.class);
                                           String s2=modelgrupparticipants.getUid();

                                          // Toasty.success(getActivity(),s2+"\n"+uidf).show();

                                           if(s2.equals(uidf)){
                                               ans=1;

                                             //  Toasty.success(getActivity(),s2+ans).show();

                                               break;
                                           }
                                           else{
                                               ans=2;
                                           }
                                       }

                                       if(ans==2){
                                           Toasty.success(getActivity(),"joined").show();
                                           dismiss();

                                           DatabaseReference red=FirebaseDatabase.getInstance().getReference("grup").child(invitecode1);
                                           modelgrupparticipants modelgrupparticipants=new modelgrupparticipants();
                                           modelgrupparticipants.setUid(uidf);
                                           red.child("members").push().setValue(modelgrupparticipants);


                                           DatabaseReference rety=FirebaseDatabase.getInstance().getReference("grup").child(invitecode1).child("groupdetails");
                                           rety.addValueEventListener(new ValueEventListener() {
                                               @Override
                                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                   modelgrupdetails modelgrupdetails=snapshot.getValue(com.example.upsczindabaad.modelgrupdetails.class);





                                                   DatabaseReference ret23=FirebaseDatabase.getInstance().getReference("userowngroup").child(uidf);
                                                   modeluserowngrup md=new modeluserowngrup();
                                                   md.setInvitecode(invitecode1);
                                                   md.
                                                           ret23.push().setValue(md);

                                                   Intent intent=new Intent(getActivity(),groupchat.class);
                                                   startActivity(intent);

                                               }

                                               @Override
                                               public void onCancelled(@NonNull DatabaseError error) {

                                               }
                                           });




                                       }

                                       else if(ans==1){
                                           Toasty.info(getActivity(), "You Are Already in This Group").show();
                                       }
                                       else {
                                           Toasty.error(getActivity(),"Enter Correct Invite Code").show();
                                       }


                                   }

                                   @Override
                                   public void onCancelled(@NonNull DatabaseError error) {

                                   }
                               });
break;
                           }
                           else{
                               continue;
                           }


                       }

                     //  Toasty.success(getActivity(),s1+ans).show();


                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });



           }
       });
        return  v;




    }
}
/*ans=1;
        Toasty.success(getActivity(),"ans is"+s1+"  "+invitecode1+ans).show();
        break;

 */
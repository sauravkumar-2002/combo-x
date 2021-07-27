package com.example.upsczindabaad;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link bottomFragmentJoin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class bottomFragmentJoin extends BottomSheetDialogFragment {

    EditText invitecode;
    Button joinGrp;

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
               String invite=invitecode.getText().toString().trim();

               DatabaseReference reference= FirebaseDatabase.getInstance().getReference("groups").child(invite);

               FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
               String uid=user.getUid();
               reference.push().setValue(uid);

           }
       });
        return  v;




    }
}
package com.example.upsczindabaad.posts;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.upsczindabaad.R;
import com.example.upsczindabaad.postaudioupload;
import com.example.upsczindabaad.postimageupload;
import com.example.upsczindabaad.postvideoupload;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link myPost#newInstance} factory method to
 * create an instance of this fragment.
 */
public class myPost extends Fragment {
FloatingActionButton imageup,videoup,audioup;

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
        videoup=v.findViewById(R.id.videopost);
        imageup=v.findViewById(R.id.imagepost);
        audioup=v.findViewById(R.id.audiopost);



        videoup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), postvideoupload.class);
                startActivity(intent);
            }
        });
        imageup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), postimageupload.class);
                startActivity(intent);
            }
        });
        audioup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), postaudioupload.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return v;
    }
}
package com.example.upsczindabaad.posts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upsczindabaad.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link allPost#newInstance} factory method to
 * create an instance of this fragment.
 */
public class allPost extends Fragment {

    RecyclerView rvallpost;
    ArrayList<allPostModel> data;
    allPostAdapter allPostAdapter;

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

        rvallpost = v.findViewById(R.id.rvallpost);
        rvallpost.setLayoutManager(new LinearLayoutManager(getContext()));
        data = new ArrayList<>();

        allPostAdapter = new allPostAdapter(data, getContext());
        rvallpost.setAdapter(allPostAdapter);

        getAllpost();
        return v;


    }

    private void getAllpost() {
        allPostModel md=new allPostModel();
        md.setUsernameap("sandeep");
        md.setUserimageap(R.drawable.berlin);
        md.setImageap(R.drawable.test);
        md.setVori(2);
      //  md.setAlpha(1);
        data.add(md);

        allPostModel md1=new allPostModel();
        md1.setUsernameap("saurav");
        md1.setUserimageap(R.drawable.arturo);
        md1.setImageap(R.drawable.test2);
        md1.setVori(1);
       // md1.setAlpha(1);
        data.add(md1);


        allPostModel md2=new allPostModel();
        md2.setUsernameap("sneha");
        md2.setUserimageap(R.drawable.tokyo);
        md2.setImageap(R.drawable.test3);
        md2.setVori(2);
       // md2.setAlpha(0.55f);
        data.add(md2);

        allPostModel md3=new allPostModel();
        md3.setUsernameap("shubham");
        md3.setUserimageap(R.drawable.denver);
        md3.setImageap(R.drawable.test4);
        md3.setVori(1);
       // md3.setAlpha(0.55f);
        data.add(md3);

        allPostAdapter.notifyDataSetChanged();


    }
}
package com.axel.akcosmetics.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.axel.akcosmetics.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SliderProductDetailFullItemFragment extends Fragment
{

    public static final String IMAGE_KEY = "IMAGE_KEY";

    public SliderProductDetailFullItemFragment() {
        // Required empty public constructor
    }


    public static SliderProductDetailFullItemFragment newInstance(String imgName) {

        Bundle args = new Bundle();
        args.putString(IMAGE_KEY, imgName);

        SliderProductDetailFullItemFragment fragment = new SliderProductDetailFullItemFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.item_slider_fragment_product_detail_full, container, false);

        Bundle args = getArguments();
        if(args == null) throw  new AssertionError();
        String imgName = args.getString(IMAGE_KEY);
        if(imgName == null) throw  new AssertionError();

        int imgId = getActivity().getResources()
                .getIdentifier(imgName,"drawable", getActivity().getPackageName());
        ImageView ivProductDetailImage = rootView.findViewById(R.id.iv_product_detail_image);
        ivProductDetailImage.setImageResource(imgId);

        return rootView;
    }

}

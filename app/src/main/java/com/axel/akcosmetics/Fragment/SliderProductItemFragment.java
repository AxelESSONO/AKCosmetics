package com.axel.akcosmetics.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.axel.akcosmetics.Buyers.ProductDetailActivity;
import com.axel.akcosmetics.Model.Products;
import com.axel.akcosmetics.R;
import com.axel.akcosmetics.Util.UtilityClass;
import com.like.LikeButton;
import com.like.OnLikeListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class SliderProductItemFragment extends Fragment implements OnLikeListener {


    public static final String PRODUCT_KEY = "product_key";

    private LikeButton btnSliderFavourite;

    public SliderProductItemFragment() {
        // Required empty public constructor
    }

    public static SliderProductItemFragment newInstance(Products product) {

        Bundle args = new Bundle();
        args.putParcelable(PRODUCT_KEY, product);
        SliderProductItemFragment fragment = new SliderProductItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.item_slider_fragment_product, container, false);

        Bundle args = getArguments();
        if (args == null) throw new AssertionError();
        final Products product = args.getParcelable(PRODUCT_KEY);
        if (product == null) throw new AssertionError();


        ImageView ivSliderProductImage = rootView.findViewById(R.id.iv_slider_product_image);
        int imageResource = getContext().getResources().getIdentifier( "drawable/"+ product.getPid(), null, getContext().getPackageName());

        //int imageResource = getContext().getResources().getIdentifier("drawable/" + product.getPid() , null, getPackageName());


        ivSliderProductImage.setImageResource(imageResource);

        TextView tvSliderProductHeading = rootView.findViewById(R.id.tv_slider_product_heading);
        tvSliderProductHeading.setText(product.getPname());

        TextView tvSliderProductPrevPrice = rootView.findViewById(R.id.tv_slider_product_Previous_price);
        if (product.getPreviousPrice() != 0) {
            tvSliderProductPrevPrice.setText(UtilityClass.getNumberFormat(product.getPreviousPrice()));
        } else {
            tvSliderProductPrevPrice.setVisibility(View.INVISIBLE);
        }

        TextView tvSliderProductPrice = rootView.findViewById(R.id.tv_slider_product_price);
        //tvSliderProductPrice.setText(UtilityClass.getNumberFormat(Double.valueOf(product.getPrice())));
        tvSliderProductPrice.setText(product.getPrice());

        btnSliderFavourite = rootView.findViewById(R.id.btn_slider_favourite);
        btnSliderFavourite.setOnLikeListener(this);


        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                intent.putExtra("id", product.getPid());
                intent.putExtra("name", product.getPname());
                intent.putExtra("price", product.getPrice());
                startActivity(intent);

            }
        });


        return rootView;

    }

    @Override
    public void liked(LikeButton likeButton) {
        Toast.makeText(getContext(), "Liked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void unLiked(LikeButton likeButton) {
        Toast.makeText(getContext(), "DISLIKED", Toast.LENGTH_SHORT).show();
    }



}

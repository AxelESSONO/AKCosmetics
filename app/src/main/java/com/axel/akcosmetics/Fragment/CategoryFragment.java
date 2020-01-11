package com.axel.akcosmetics.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axel.akcosmetics.Adapter.CategoryRecylerViewGridAdapter;
import com.axel.akcosmetics.Model.ProductCategory;
import com.axel.akcosmetics.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    ArrayList<ProductCategory> categoryList;

    RecyclerView recyclerViewCategory;


    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);

        categoryList = new ArrayList<>();
        ProductCategory productCategory1 = new ProductCategory("category_women_dress", "Women Dresses");
        ProductCategory productCategory2 = new ProductCategory("category_women_top", "Women Hoodies");
        ProductCategory productCategory3 = new ProductCategory("category_women_jacket", "Women Swetshirts");
        ProductCategory productCategory4 = new ProductCategory("category_men_jacket", "Women T-Shirts");
        ProductCategory productCategory5 = new ProductCategory("category_men_tshirt", "Women Skirts");
        ProductCategory productCategory6 = new ProductCategory("category_men_shoe", "Women Jeans");
        ProductCategory productCategory7 = new ProductCategory("category_men_backpack", "Men Shirts");
        ProductCategory productCategory8 = new ProductCategory("category_women_dress", "Men Jackets");
        ProductCategory productCategory9 = new ProductCategory("category_women_top", "Men T-Shirts");
        ProductCategory productCategory10 = new ProductCategory("category_women_jacket", "Men Jeans");
        ProductCategory productCategory11 = new ProductCategory("category_men_jacket", "Men Socks");
        ProductCategory productCategory12 = new ProductCategory("category_men_tshirt", "Men Backpacks");
        ProductCategory productCategory13 = new ProductCategory("category_men_shoe", "Baby Dresses");
        ProductCategory productCategory14 = new ProductCategory("category_men_backpack", "Women Sportswear");
        ProductCategory productCategory15 = new ProductCategory("category_women_dress", "Men Sportswear");
        categoryList.add(productCategory1);
        categoryList.add(productCategory2);
        categoryList.add(productCategory3);
        categoryList.add(productCategory4);
        categoryList.add(productCategory5);
        categoryList.add(productCategory6);
        categoryList.add(productCategory7);
        categoryList.add(productCategory8);
        categoryList.add(productCategory9);
        categoryList.add(productCategory10);
        categoryList.add(productCategory11);
        categoryList.add(productCategory12);
        categoryList.add(productCategory13);
        categoryList.add(productCategory14);
        categoryList.add(productCategory15);

        recyclerViewCategory = rootView.findViewById(R.id.rv_product_category_grid);
        CategoryRecylerViewGridAdapter productRecylerViewAdapter = new CategoryRecylerViewGridAdapter(categoryList, getContext());

        recyclerViewCategory.setHasFixedSize(false);
        recyclerViewCategory.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerViewCategory.setNestedScrollingEnabled(false);
        recyclerViewCategory.setAdapter(productRecylerViewAdapter);
        //END FOR RECYLER PRODUCT VIEW

        return rootView;

    }

}

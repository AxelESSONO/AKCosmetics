package com.axel.akcosmetics.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.axel.akcosmetics.Adapter.CategoryRecylerViewAdapter;
import com.axel.akcosmetics.Adapter.ProductRecylerViewAdapter;
import com.axel.akcosmetics.Model.ProductCategory;
import com.axel.akcosmetics.Model.Products;
import com.axel.akcosmetics.Model.SliderMain;
import com.axel.akcosmetics.R;
import com.axel.akcosmetics.Util.UtilityClass;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    ArrayList<SliderMain> sliderMainList;
    ArrayList<Products> productGridList;

    ViewPager vpSliderMain;
    RecyclerView recyclerViewProduct;
    ViewPager vpPopularProducts;

    private LinearLayout dotsIndicators;


    public MainFragment() {
        // Required empty public constructor
    }

    ArrayList<ProductCategory> categoryList;
    ArrayList<Products> sliderPopularProductList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        /*START OF HORIZOANTAL CATEGORY LIST*/
        categoryList = new ArrayList<>();
        ProductCategory productCategory1 = new ProductCategory("ic_category_shirt", "Female Dress1");
        ProductCategory productCategory2 = new ProductCategory("ic_category_jeans", "Female Dress2");
        ProductCategory productCategory3 = new ProductCategory("ic_category_dress", "Female Dress3");
        ProductCategory productCategory4 = new ProductCategory("ic_category_hoodie", "Female Dress4");
        ProductCategory productCategory5 = new ProductCategory("ic_category_shoe", "Female Dress5");
        ProductCategory productCategory6 = new ProductCategory("ic_category_women_shoe", "Female Dress6");
        categoryList.add(productCategory1);
        categoryList.add(productCategory2);
        categoryList.add(productCategory3);
        categoryList.add(productCategory4);
        categoryList.add(productCategory5);
        categoryList.add(productCategory6);

        RecyclerView recyclerView = rootView.findViewById(R.id.rv_product_category);
        CategoryRecylerViewAdapter adapter = new CategoryRecylerViewAdapter(categoryList, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        /*END OF HORIZOANTAL CATEGORY LIST*/


        // START VIEWPAGER POPULAR PRODUCTS SLIDER IMAGES
        sliderPopularProductList = new ArrayList<>();

        Products product1 = new Products("","","100","","","product_1","", "Round Neck Tshirt", "");
        product1.setPreviousPrice(30);
        Products product2 = new Products("","","120","","","product_2","", "Round Neck Tshirt", "");
        product2.setPreviousPrice(30);
        Products product3 = new Products("","","130","","","product_3","", "Round Neck Tshirt", "");
        product3.setPreviousPrice(30);
        Products product4 = new Products("","","140","","","product_4","", "Round Neck Tshirt", "");
        product4.setPreviousPrice(30);
        Products product5 = new Products("","","150","","","product_5","", "Round Neck Tshirt", "");

        sliderPopularProductList.add(product1);
        sliderPopularProductList.add(product2);
        sliderPopularProductList.add(product3);
        sliderPopularProductList.add(product4);
        sliderPopularProductList.add(product5);

        vpPopularProducts = rootView.findViewById(R.id.vp_popular_products);
        PagerAdapter sliderPopularProductAdapter = new SliderPopularProductAdapter(getChildFragmentManager());
        vpPopularProducts.setAdapter(sliderPopularProductAdapter);
        // END VIEWPAGER POPULAR PRODUCTS SLIDER IMAGES


        // START VIEWPAGER MAIN SLIDER IMAGES
        sliderMainList = new ArrayList<>();
        SliderMain sliderMain1 = new SliderMain("slider_1", "Up to 40% OFF Autumn Sale", "MEGA SALE");
        SliderMain sliderMain2 = new SliderMain("slider_2", "Newest outfits has just arrived", "NEW ARRIVALS");
        SliderMain sliderMain3 = new SliderMain("slider_3", "Sale Upto 30% This Week", "MEN’S COLLECTION");

        sliderMainList.add(sliderMain1);
        sliderMainList.add(sliderMain2);
        sliderMainList.add(sliderMain3);

        vpSliderMain = rootView.findViewById(R.id.vp_slider_main);
        PagerAdapter sliderMainAdapter = new SliderMainAdapter(getChildFragmentManager());
        vpSliderMain.setAdapter(sliderMainAdapter);
        // END OF VIEWPAGER SLIDER IMAGES


        //START RECYLERVIEW PRODUCT GRID
        productGridList = new ArrayList<>();
        Products gridProduct1 = new Products("Round Neck Tshirt","","100","R.drawable.bag_1","","product_7","", "", "");
        gridProduct1.setPreviousPrice(45);
        Products gridProduct2 = new Products("Round Neck Tshirt","","150","","","product_6","", "", "");
        gridProduct2.setPreviousPrice(32.454);
        Products gridProduct3 = new Products("Round Neck Tshirt","","200","","","product_5","", "", "");
        Products gridProduct4 = new Products("Round Neck Tshirt","","250","","","product_4","", "", "");
        Products gridProduct5 = new Products("Round Neck Tshirt","","300","","","product_3","", "", "");
        Products gridProduct6 = new Products("Round Neck Tshirt","","350","","","product_2","", "", "");
        Products gridProduct7 = new Products("Round Neck Tshirt","","300","","","product_3","", "", "");
        Products gridProduct8 = new Products("Round Neck Tshirt","","350","","","product_2","", "", "");
        productGridList.add(gridProduct1);
        productGridList.add(gridProduct2);
        productGridList.add(gridProduct3);
        productGridList.add(gridProduct4);
        productGridList.add(gridProduct5);
        productGridList.add(gridProduct6);
        productGridList.add(gridProduct7);
        productGridList.add(gridProduct8);
        recyclerViewProduct = rootView.findViewById(R.id.rv_p_detail_product_grid);
        ProductRecylerViewAdapter productRecylerViewAdapter = new ProductRecylerViewAdapter(productGridList, getContext());
        recyclerViewProduct.setHasFixedSize(false);
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewProduct.setNestedScrollingEnabled(false);
        recyclerViewProduct.setAdapter(productRecylerViewAdapter);
        //END FOR RECYLER PRODUCT VIEW

        /*START OF DOTS INDICATORS*/
        dotsIndicators = rootView.findViewById(R.id.layout_slider_main_dots);
        dots(0);

        vpSliderMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            { }

            @Override
            public void onPageSelected(int position)
            {
                dots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
            }
        });

        /*END OF DOTS INDICATORS*/


        return rootView;
    }


    private void dots(int current)
    {

        dotsIndicators.removeAllViews();
        for (int i = 0; i < sliderMainList.size(); i++)
        {
            TextView dot = new TextView(getContext());
            dot.setIncludeFontPadding(false);
            dot.setHeight((int) UtilityClass.convertDpToPixel(10, getContext()));
            dot.setWidth((int) UtilityClass.convertDpToPixel(10, getContext()));

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            int marginAsDp = (int) UtilityClass.convertDpToPixel(4, getContext());
            params.setMargins(marginAsDp, marginAsDp, marginAsDp, marginAsDp);
            dot.setLayoutParams(params);

            dot.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_border_bg_1));

            if (i == current)
            {
                dot.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_bg));
            }
            dotsIndicators.addView(dot);
        }
    }


    /*START OF MAIN SLIDER ADAPTER*/
    public class SliderMainAdapter extends FragmentStatePagerAdapter
    {

        public SliderMainAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            return SliderMainItemFragment.newInstance(sliderMainList.get(position));
        }

        @Override
        public int getCount()
        {
            return sliderMainList.size();
        }
    }
    /*END OF MAIN SLIDER ADAPTER*/


    /*START OF POLULAR PRODUCT SLIDER ADAPTER*/
    public class SliderPopularProductAdapter extends FragmentStatePagerAdapter
    {
        public SliderPopularProductAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            return SliderProductItemFragment.newInstance(sliderPopularProductList.get(position));
        }

        @Override
        public float getPageWidth(int position)
        {
            return .55f;
        }

        @Override
        public int getCount()
        {
            return sliderPopularProductList.size();
        }
    }
    /*END OF POLULAR PRODUCT SLIDER ADAPTER*/
}

package com.axel.akcosmetics.Buyers;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.axel.akcosmetics.Adapter.ProductRecylerViewAdapter;
import com.axel.akcosmetics.Fragment.SliderProductDetailFullItemFragment;
import com.axel.akcosmetics.Model.Products;
import com.axel.akcosmetics.R;
import com.axel.akcosmetics.Util.UtilityClass;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener, OnLikeListener {



    public ArrayList<String> productDetailSliderImgList;
    private int productSlideCount;
    private LinearLayout dotsIndicators;
    private TextView tvProductDetailHeading;

    // SPONSORED PRODUCTS GRID
    ArrayList<Products> productGridList;
    RecyclerView recyclerViewProduct;

    /*START OF MENU VERIABLE*/
    Toolbar toolbar;
    Button btnAddCart;
    /*END OF MENU VERIABLE*/


    // START OF DESCRIPTION AREA
    int firstDescLineCount;
    int firstDescLineHeight;
    int firstDescVisibleLineCount = 4;

    int firstDescListLineCount;
    int secondDescListLineHeight;
    int secondDescListVisibleLineCount = 4;
    // END OF DESCRIPTION AREA

    LikeButton btnPDetailFavourite;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        /*START OF TOOLBAR*/
        TextView toobarTitle = findViewById(R.id.toolbar_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle("");
        }
        toobarTitle.setText(this.getString(R.string.title_product_detail));
        //setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        /*END OF TOOLBAR*/


        tvProductDetailHeading = findViewById(R.id.tv_product_detail_heading);
        tvProductDetailHeading.setText(getIntent().getStringExtra("name"));

        // START OF FAVOURITE BUTTON
        btnPDetailFavourite = findViewById(R.id.btn_p_detail_favourite);
        btnPDetailFavourite.setOnLikeListener(this);

        // END OF FAVOURITE BUTTON

        /*START OF DIALOG*/
        Button btnProductDetailSize = findViewById(R.id.btn_product_detail_size);
        Button btnProductDetailColor = findViewById(R.id.btn_product_detail_color);
        Button btnProductDetailQuantity = findViewById(R.id.btn_product_detail_quantity);

        btnProductDetailSize.setOnClickListener(this);
        btnProductDetailColor.setOnClickListener(this);
        btnProductDetailQuantity.setOnClickListener(this);
        /*END OF DIALOG*/


        /*START OF ADD TO CART*/
        btnAddCart = findViewById(R.id.btn_product_detail_add_cart);
        btnAddCart.setOnClickListener(this);
        /*END OF ADD TO CART*/


        // START OF PRODUCT DESCRIPTION
        btnDescReadMore = findViewById(R.id.btn_p_detail_desc_read_more);
        btnDescReadMore.setOnClickListener(this);
        pDetailDesc = findViewById(R.id.p_detail_desc);

        //  START OF FIRST VISIBLE ON ACTIVE PRODUCT DESCRIPTION

        final TextView tvpDetailsDesc = findViewById(R.id.tv_p_detail_description);
        ((TextView) tvpDetailsDesc).post(new Runnable() {

            @Override
            public void run() {

                firstDescLineCount =((TextView) tvpDetailsDesc).getLineCount();
                firstDescLineHeight =((TextView) tvpDetailsDesc).getLineHeight();

                ViewGroup.LayoutParams params = pDetailDesc.getLayoutParams();
                params.height = firstDescLineHeight * firstDescVisibleLineCount;
                pDetailDesc.setLayoutParams(params);

            }

        });
        //  END OF FIRST VISIBLE ON ACTIVE PRODUCT DESCRIPTION


        pDetailListDesc = findViewById(R.id.p_detail_list_desc);
        btnDescListReadMore = findViewById(R.id.btn_p_detail_desc_list_read_more);
        btnDescListReadMore.setOnClickListener(this);
        final LinearLayout pDetailListDescLine = findViewById(R.id.p_detail_list_desc_line);

        //  START OF SECOND VISIBLE ON ACTIVE PRODUCT DESCRIPTION

        (pDetailListDescLine).post(new Runnable() {

            @Override
            public void run() {

                secondDescListLineHeight = pDetailListDescLine.getHeight();

                ViewGroup.LayoutParams params = pDetailListDesc.getLayoutParams();
                params.height = secondDescListLineHeight * secondDescListVisibleLineCount;
                pDetailListDesc.setLayoutParams(params);

            }

        });
        //  END OF SECOND VISIBLE ON ACTIVE PRODUCT DESCRIPTION

        // END OF PRODUCT DESCRIPTION



        // START OF SIZE/COLOR/QTY ICON RIGHT RESIZING
        UtilityClass.buttonScaleIconRight(this, btnProductDetailSize, R.drawable.ic_arrow_down_black, .3, 1);
        UtilityClass.buttonScaleIconRight(this, btnProductDetailColor, R.drawable.ic_arrow_down_black, .3, 1);
        UtilityClass.buttonScaleIconRight(this, btnProductDetailQuantity, R.drawable.ic_arrow_down_black, .3, 1);
        UtilityClass.buttonScaleIconRight(this, btnDescReadMore, R.drawable.ic_arrow_down_primary, 1, 1);
        UtilityClass.buttonScaleIconRight(this, btnDescListReadMore, R.drawable.ic_arrow_down_primary, 1, 1);
        // END OF SIZE/COLOR/QTY ICON RIGHT RESIZING


        // START PRODUCT DETAIL SLIDER
        productDetailSliderImgList = new ArrayList<>();
        productDetailSliderImgList.add("bag_1");
        productDetailSliderImgList.add("bag_2");
        productDetailSliderImgList.add("bag_3");

        productSlideCount = productDetailSliderImgList.size();

        ViewPager vpProductDetailsImageSlider = findViewById(R.id.vp_product_detail_image_slider);
        PagerAdapter pagerAdapter = new SliderProductDeatilAdapter(getSupportFragmentManager());
        vpProductDetailsImageSlider.setAdapter(pagerAdapter);

        // END PRODUCT DETAIL SLIDER


        // START DOTS INDICATORS
        dotsIndicators = findViewById(R.id.layout_product_Detail_dots);
        dots(0);

        vpProductDetailsImageSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // END OF DOTS INDICATORS


        //START RECYLERVIEW PRODUCT GRID

        productGridList = new ArrayList<Products>();

        //("","","150","","","product_5","", "Round Neck Tshirt", "")
        Products product1 = new Products("","","150","","","product_5","", "Round Neck Tshirt", "");
        Products product2 = new Products("","","150","","","product_5","", "Round Neck Tshirt", "");
        Products product3 = new Products("","","150","","","product_5","", "Round Neck Tshirt", "");
        Products product4 = new Products("","","150","","","product_5","", "Round Neck Tshirt", "");
        Products product5 = new Products("","","150","","","product_5","", "Round Neck Tshirt", "");
        Products product6 = new Products("","","150","","","product_5","", "Round Neck Tshirt", "");


        productGridList.add(product1);
        productGridList.add(product2);
        productGridList.add(product3);
        productGridList.add(product4);
        productGridList.add(product5);
        productGridList.add(product6);

        recyclerViewProduct = findViewById(R.id.rv_p_detail_product_grid);
        ProductRecylerViewAdapter productRecylerViewAdapter = new ProductRecylerViewAdapter(productGridList, this);

        recyclerViewProduct.setHasFixedSize(false);
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(this, 2));
        //ProductRecylerViewAdapter.ItemOffsetDecoration itemDecoration = new ProductRecylerViewAdapter.ItemOffsetDecoration(this, R.dimen.fab_margin_quarter);
        // recyclerViewProduct.addItemDecoration(itemDecoration);
        recyclerViewProduct.setNestedScrollingEnabled(false);

        recyclerViewProduct.setAdapter(productRecylerViewAdapter);

        //END FOR RECYLER PRODUCT VIEW

    }


    Button btnDescReadMore;
    Button btnDescListReadMore;

    LinearLayout pDetailListDesc;
    boolean pDetailListDescExpanded = false;

    LinearLayout pDetailDesc;
    boolean pDetailDescExpanded = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_product_detail_add_cart:
                Intent intent = new Intent(ProductDetailActivity.this, LoginActivity.class );
                        //LoginAttemptActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_product_detail_size:
                showDialog();
                break;
            case R.id.btn_product_detail_color:
                showDialog();
                break;
            case R.id.btn_product_detail_quantity:
                showDialog();
                break;

            case R.id.btn_p_detail_desc_read_more:

                if(!pDetailDescExpanded){
                    pDetailDescExpanded = true;
                    ViewGroup.LayoutParams params = pDetailDesc.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    pDetailDesc.setLayoutParams(params);
                    UtilityClass.buttonScaleIconRight(this, btnDescReadMore, R.drawable.ic_arrow_up_primary, 1, 1);
                    btnDescReadMore.setText(getResources().getString(R.string.read_less));
                }else{
                    pDetailDescExpanded = false;
                    ViewGroup.LayoutParams params = pDetailDesc.getLayoutParams();
                    params.height = firstDescLineHeight * firstDescVisibleLineCount;
                    pDetailDesc.setLayoutParams(params);
                    UtilityClass.buttonScaleIconRight(this, btnDescReadMore, R.drawable.ic_arrow_down_primary, 1, 1);
                    btnDescReadMore.setText(getResources().getString(R.string.read_more));
                }

                break;

            case R.id.btn_p_detail_desc_list_read_more:

                if(!pDetailListDescExpanded){
                    pDetailListDescExpanded = true;
                    ViewGroup.LayoutParams params = pDetailListDesc.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    pDetailListDesc.setLayoutParams(params);
                    UtilityClass.buttonScaleIconRight(this, btnDescListReadMore, R.drawable.ic_arrow_up_primary, 1, 1);
                    btnDescListReadMore.setText(getResources().getString(R.string.read_less));
                }else{
                    pDetailListDescExpanded = false;
                    ViewGroup.LayoutParams params = pDetailListDesc.getLayoutParams();
                    params.height = secondDescListLineHeight * secondDescListVisibleLineCount;
                    pDetailListDesc.setLayoutParams(params);

                    UtilityClass.buttonScaleIconRight(this, btnDescListReadMore, R.drawable.ic_arrow_down_primary, 1, 1);
                    btnDescListReadMore.setText(getResources().getString(R.string.read_more));
                }

                break;
        }
    }

    private void dots(int current) {

        dotsIndicators.removeAllViews();
        for (int i = 0; i < productSlideCount; i++) {
            TextView dot = new TextView(this);
            //dot.setText(Html.fromHtml("&#8226"));
            //dot.setText(".");
            dot.setIncludeFontPadding(false);
            //dot.setTextSize(35);
            dot.setHeight((int) UtilityClass.convertDpToPixel(10, ProductDetailActivity.this));
            dot.setWidth((int) UtilityClass.convertDpToPixel(10, ProductDetailActivity.this));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            int marginAsDp = (int) UtilityClass.convertDpToPixel(4, ProductDetailActivity.this);
            params.setMargins(marginAsDp, marginAsDp, marginAsDp, marginAsDp);
            dot.setLayoutParams(params);

            dot.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_border_bg_1));

            if (i == current) {
                dot.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_bg));
            }
            dotsIndicators.addView(dot);

        }

    }


    /*START OF DIALOG*/
    public void showDialog() {
        ProductDetailDialog productDetailDialog = new ProductDetailDialog();
        productDetailDialog.show(getFragmentManager(), "PRODUCT_DETAIL_DIALOG");

    }

    /*END OF DIALOG*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        final Menu m = menu;
        final MenuItem item = menu.findItem(R.id.action_cart_count);
        item.getActionView().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                /*START CART ACTIVITY*/
                Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        return true;
    }

    @Override
    public void liked(LikeButton likeButton) {
        Toast.makeText(this, "J'aime", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void unLiked(LikeButton likeButton) {
        Toast.makeText(this, "Je n'aime pas", Toast.LENGTH_SHORT).show();
    }

    /*START OF SLIDER IMAGE VIEWPAGER*/
    public class SliderProductDeatilAdapter extends FragmentStatePagerAdapter {

        public SliderProductDeatilAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SliderProductDetailFullItemFragment.newInstance(productDetailSliderImgList.get(position));
        }

        @Override
        public int getCount() {
            return productDetailSliderImgList.size();
        }

    }
    /*END OF SLIDER IMAGE VIEWPAGER*/
}
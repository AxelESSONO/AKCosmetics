package com.axel.akcosmetics.Buyers;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.axel.akcosmetics.Adapter.ProductRecylerViewAdapter;
import com.axel.akcosmetics.Model.Products;
import com.axel.akcosmetics.R;
import com.axel.akcosmetics.Util.UtilityClass;

import java.util.ArrayList;

public class ProductGridActivity extends AppCompatActivity
{

    /*START OF MENU VERIABLE*/
    TextView toobarTitle;
    Toolbar toolbar;
    ImageView toolbarLogo;
    /*END OF MENU VERIABLE*/

    ArrayList<Products> productGridList;
    RecyclerView recyclerViewProduct;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_grid);

        /*START OF TOOLBAR */
        toolbar = findViewById(R.id.toolbar);
        toobarTitle = findViewById(R.id.toolbar_title);
        toolbarLogo = findViewById(R.id.toolbar_logo);
        toolbar.setTitle("");
        toolbarLogo.setVisibility(View.VISIBLE);
        toobarTitle.setVisibility(View.GONE);
        toobarTitle.setText(this.getString(R.string.app_name));

        /*CHECKING THE TOOLBAR TITLE FROM USER FAVOURITES*/
        String toolbarTitle = getIntent().getStringExtra(UtilityClass.TOOLBAR_TITLE);
        if( toolbarTitle!= null){

            toobarTitle.setText(toolbarTitle);
            toolbarLogo.setVisibility(View.GONE);
            toobarTitle.setVisibility(View.VISIBLE);
        }
        /*CHECKING THE TOOLBAR TITLE FROM USER FAVOURITES*/

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*END OF TOOLBAR */


        //START RECYLERVIEW PRODUCT GRID
        productGridList = new ArrayList<>();
        Products gridProduct1 = new Products("Round Neck Tshirt","","150","","","product_5","", "", "");
        gridProduct1.setPreviousPrice(45);
        Products gridProduct2 = new Products("Round Neck Tshirt","","150","","","product_5","", "", "");
        gridProduct2.setPreviousPrice(32.454);
        Products gridProduct3 = new Products("Round Neck Tshirt","","150","","","product_5","", "", "");
        Products gridProduct4 = new Products("Round Neck Tshirt","","150","","","product_5","", "", "");
        Products gridProduct5 = new Products("Round Neck Tshirt","","150","","","product_5","", "", "");
        Products gridProduct6 = new Products("Round Neck Tshirt","","150","","","product_5","", "", "");
        gridProduct6.setPreviousPrice(62.86);
        Products gridProduct7 = new Products("Round Neck Tshirt","","150","","","product_5","", "", "");
        Products gridProduct8 = new Products("Round Neck Tshirt","","150","","","product_5","", "", "");
        productGridList.add(gridProduct1);
        productGridList.add(gridProduct2);
        productGridList.add(gridProduct3);
        productGridList.add(gridProduct4);
        productGridList.add(gridProduct5);
        productGridList.add(gridProduct6);
        productGridList.add(gridProduct7);
        productGridList.add(gridProduct8);

        recyclerViewProduct = findViewById(R.id.rv_product_grid);
        ProductRecylerViewAdapter productRecylerViewAdapter = new ProductRecylerViewAdapter(productGridList, this);

        recyclerViewProduct.setHasFixedSize(false);
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewProduct.setNestedScrollingEnabled(false);
        recyclerViewProduct.setAdapter(productRecylerViewAdapter);
        //END FOR RECYLER PRODUCT VIEW


    }

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
                Intent intent = new Intent(ProductGridActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        return true;
    }


}

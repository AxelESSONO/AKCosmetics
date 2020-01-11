package com.axel.akcosmetics.Buyers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.axel.akcosmetics.R;
import com.axel.akcosmetics.Util.UtilityClass;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {


    /*START OF MENU VERIABLE*/
    TextView toobarTitle;
    Toolbar toolbar;
    /*END OF MENU VERIABLE*/

    Button btnMyOrders;
    Button btnMyFavourites;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        /*START OF TOOLBAR*/
        TextView toobarTitle = findViewById(R.id.toolbar_title);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toobarTitle.setText("User");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        /*END OF TOOLBAR*/

        // START OF ORDER/FAVOURITE BUTTON
        btnMyOrders = findViewById(R.id.btn_my_orders);
        btnMyOrders.setOnClickListener(this);

        btnMyFavourites = findViewById(R.id.btn_my_favourite);
        btnMyFavourites.setOnClickListener(this);
        // END OF ORDER/FAVOURITE BUTTON

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_my_orders:
                Intent intentOrder = new Intent(ProfileActivity.this, OrderActivity.class);

                startActivity(intentOrder);
                break;
            case R.id.btn_my_favourite:
                Intent intentFav = new Intent(ProfileActivity.this, ProductGridActivity.class);
                intentFav.putExtra(UtilityClass.TOOLBAR_TITLE, getString(R.string.title_favourites));
                startActivity(intentFav);

                break;
        }
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
                Intent intent = new Intent(ProfileActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        return true;
    }

}

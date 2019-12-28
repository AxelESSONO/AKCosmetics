package com.axel.akcosmetics.Sellers;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.axel.akcosmetics.Admin.SellerProductCategoryActivity;
import com.axel.akcosmetics.Buyers.HomeActivity;
import com.axel.akcosmetics.Buyers.MainActivity;
import com.axel.akcosmetics.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import io.paperdb.Paper;

public class SellerHomeActivity extends AppCompatActivity
{

    private TextView mTextMessage;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {

            switch (item.getItemId())
            {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;

                case R.id.navigation_add:
                    Intent intentCategory = new Intent(SellerHomeActivity.this, SellerProductCategoryActivity.class);
                    startActivity(intentCategory);
                    return true;


                case R.id.navigation_logout:

                   /* final FirebaseAuth mAuth;
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();*/

                    Paper.book().destroy();
                    Intent intentMain = new Intent(SellerHomeActivity.this, MainActivity.class);
                    intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentMain);
                    finish();
                    return true;
            }

            return false;
        }
    };



     @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);

        BottomNavigationView navView = findViewById(R.id.nav_view);

       // mTextMessage = findViewById(R.id.instant_message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
      /*  AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_add,
                R.id.navigation_logout)
                .build();*/


    }

}

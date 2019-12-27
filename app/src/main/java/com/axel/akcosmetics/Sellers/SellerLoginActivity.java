package com.axel.akcosmetics.Sellers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.axel.akcosmetics.R;

public class SellerLoginActivity extends AppCompatActivity
{
    private ImageView pre;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        pre = (ImageView) findViewById(R.id.login_img_seller);

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SellerLoginActivity.this, "OUI", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.axel.akcosmetics.Buyers;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.axel.akcosmetics.Fragment.ConfirmFragment;
import com.axel.akcosmetics.Fragment.PaymentFragment;
import com.axel.akcosmetics.R;
import com.axel.akcosmetics.Fragment.ShippingFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.axel.akcosmetics.Buyers.ui.main.SectionsPagerAdapter;

public class CheckoutActivity extends AppCompatActivity implements
        ShippingFragment.OnFragmentInteractionListener,
        PaymentFragment.OnFragmentInteractionListener,
        ConfirmFragment.OnFragmentInteractionListener

{


    private String receivePrice = "";
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
         final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        mButton = (Button) findViewById(R.id.next_btn_tab);


        receivePrice = getIntent().getStringExtra("Total price");

        //Toast.makeText(this, "Le prix total est: " + receivePrice, Toast.LENGTH_SHORT).show();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // set Fragmentclass Arguments
        ShippingFragment shippingFragment = new ShippingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("totalPrice", receivePrice);
        shippingFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.view_pager, shippingFragment);
        fragmentTransaction.commit();

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("LIVRAISON"));
        tabs.addTab(tabs.newTab().setText("PAIEMENT"));
        tabs.addTab(tabs.newTab().setText("CONFIRMATION"));
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);


        tabs.setupWithViewPager(viewPager);

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                //Toast.makeText(CheckoutActivity.this, "Tab numéro: " + tab.getPosition(), Toast.LENGTH_SHORT).show();

                if(tab.getPosition() == 0)
                {
                    mButton.setText("SUIVANT");
                }
                else if(tab.getPosition() == 1)
                {
                    mButton.setText("SUIVANT");
                }
                else if(tab.getPosition() == 2)
                {
                    mButton.setText("CONFIRMER");
                }
                else { }



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        /**tabs.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

            }
        }); **/


        View.OnClickListener myButtonClickHandler = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int item = viewPager.getCurrentItem();
                //viewPager.setCurrentItem(item, true);

                if(item == 0)
                {
                    mButton.setText("SUIVANT");
                    viewPager.setCurrentItem(item+1, true);

                }
                else if(item == 1)
                {
                    viewPager.setCurrentItem(item+1, true);
                    mButton.setText("CONFIRMER");
                }
                else if(item == 2)
                {
                    viewPager.setCurrentItem(item, true);
                    mButton.setText("CONFIRMER");
                }
                else { }

                /**switch (item)
                {
                    case 0:
                    {
                        //Go to payment from shipping
                        viewPager.setCurrentItem(item+1, true);
                        break;
                    }
                    case 1:
                    {
                        //Go to Confirm from payment
                        viewPager.setCurrentItem(item+1, true);
                        mButton.setText("Confirmer");
                        break;
                    }
                    case 2:
                    {
                        Toast.makeText(CheckoutActivity.this, "Vous pouvez continuer vos achats", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    default:
                    {
                        viewPager.setCurrentItem(item, true);
                        break;
                    }
                }**/

            }
        };

        mButton.setOnClickListener(myButtonClickHandler);

    }

    @Override
    public void onFragmentInteraction(Uri uri) { }
}
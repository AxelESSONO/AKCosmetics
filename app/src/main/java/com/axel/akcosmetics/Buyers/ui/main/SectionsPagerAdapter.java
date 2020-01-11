package com.axel.akcosmetics.Buyers.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.axel.akcosmetics.Fragment.ConfirmFragment;
import com.axel.akcosmetics.Fragment.PaymentFragment;
import com.axel.akcosmetics.R;
import com.axel.akcosmetics.Fragment.ShippingFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter
{

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm)
    {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        switch (position)
        {
            case 0:
            {
                ShippingFragment shippingFragment = new ShippingFragment();
                return shippingFragment;
            }
            case 1:
            {
                PaymentFragment paymentFragment = new PaymentFragment();
                return paymentFragment;
            }
            case 2:
            {
                ConfirmFragment confirmFragment = new ConfirmFragment();
                return confirmFragment;
            }
            default:
            {

                return null;
            }


        }



        //return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }

}
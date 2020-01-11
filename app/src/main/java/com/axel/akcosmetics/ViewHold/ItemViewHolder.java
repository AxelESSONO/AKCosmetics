package com.axel.akcosmetics.ViewHold;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.axel.akcosmetics.Interface.ItemClickListner;
import com.axel.akcosmetics.R;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView txtProductName, txtProductDescription, txtProductPrice, txtProductStatus;
    public ImageView imageView;
    ItemClickListner listner;

    public ItemViewHolder(@NonNull View itemView)
    {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.product_seller_image_seller_item);
        txtProductName = (TextView) itemView.findViewById(R.id.product_seller_name_seller_item);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_seller_description_seller_item);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_seller_price_seller_item);
        txtProductStatus = (TextView) itemView.findViewById(R.id.product_state_seller_item);
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View v)
    {
        listner.onClick(v, getAdapterPosition(), false);
    }
}
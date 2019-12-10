package com.axel.akcosmetics.ViewHold;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.axel.akcosmetics.Interface.ItemClickListner;
import com.axel.akcosmetics.Model.Cart;
import com.axel.akcosmetics.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{


    public TextView txtProductName, txtProductPrice, txtProductQuantity;
    public ImageView productImage;
    private ItemClickListner itemClickListner;
    private Context context;

    public CartViewHolder(@NonNull View itemView)
    {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtProductPrice = itemView.findViewById(R.id.cart_product_price);
        txtProductQuantity = itemView.findViewById(R.id.cart_product_quantity);
        ///image of product
        productImage = itemView.findViewById(R.id.cart_product_image);
        //Picasso.get().load(itemView.).into(productImage);


    }

    @Override
    public void onClick(View v)
    {
        itemClickListner.onClick(v, getAdapterPosition(), false);
    }


    public void setItemClickListner(ItemClickListner itemClickListner)
    {
        this.itemClickListner = itemClickListner;
    }
}

package com.axel.akcosmetics.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.axel.akcosmetics.Buyers.ProductDetailActivity;
import com.axel.akcosmetics.Model.Products;
import com.axel.akcosmetics.R;
import com.axel.akcosmetics.Util.UtilityClass;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;

public class ProductRecylerViewAdapter extends RecyclerView.Adapter<ProductRecylerViewAdapter.ViewHolder> {

    private ArrayList<Products> productList;
    private Context context;

    public ProductRecylerViewAdapter(ArrayList<Products> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recylerview_product, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Products product = productList.get(position);

        int imageResource = context.getResources()
                .getIdentifier("drawable" +product.getPid(), null, context.getPackageName());
        holder.ivProductImage.setImageResource(imageResource);

        holder.tvProductHeading.setText(product.getPname());
        //holder.tvProductPrice.setText(UtilityClass.getNumberFormat(Double.valueOf(product.getPrice())));
        holder.tvProductPrice.setText(product.getPrice());


        if (product.getPreviousPrice() != 0) {
            holder.tvGridProductPrevPrice.setText(UtilityClass.getNumberFormat(product.getPreviousPrice()));
        } else {
            holder.tvGridProductPrevPrice.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, OnLikeListener {

        public ImageView ivProductImage;
        public TextView tvProductHeading;
        public TextView tvProductPrice;
        public TextView tvGridProductPrevPrice;

        public LikeButton btnFavourite;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            ivProductImage = itemView.findViewById(R.id.iv_grid_product_image);
            tvProductHeading = itemView.findViewById(R.id.tv_grid_product_heading);
            tvProductPrice = itemView.findViewById(R.id.tv_grid_product_price);
            tvGridProductPrevPrice = itemView.findViewById(R.id.tv_grid_product_Previous_price);

            btnFavourite = itemView.findViewById(R.id.btn_favourite);

            btnFavourite.setOnLikeListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            Products product = productList.get(position);
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("id", product.getPid());
            intent.putExtra("name", product.getPname());
            intent.putExtra("price", product.getPrice());
            context.startActivity(intent);
        }

        @Override
        public void liked(LikeButton likeButton) {
            Toast.makeText(context, "J'aime", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void unLiked(LikeButton likeButton) {
            Toast.makeText(context, "Je n'aime pas", Toast.LENGTH_SHORT).show();
        }
    }

    public static class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

        private int mItemOffset;

        public ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }

        public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
            this(context.getResources().getDimensionPixelSize(itemOffsetId));
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }
    }
}
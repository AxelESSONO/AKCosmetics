package com.axel.akcosmetics.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.axel.akcosmetics.Buyers.ProductGridActivity;
import com.axel.akcosmetics.Model.ProductCategory;
import com.axel.akcosmetics.R;

import java.util.ArrayList;

public class CategoryRecylerViewGridAdapter extends RecyclerView.Adapter<CategoryRecylerViewGridAdapter.ViewHolder>
{


    private ArrayList<ProductCategory> categoryList;
    private Context context;

    public CategoryRecylerViewGridAdapter(ArrayList<ProductCategory> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recylerview_grid_category, parent, false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProductCategory productCategory = categoryList.get(position);

        int imageId = context.getResources().getIdentifier(productCategory.getCategoryId(), "drawable", context.getPackageName());
        holder.ivCategoryImage.setImageResource(imageId);
        holder.tvCategoryName.setText(productCategory.getCategoryname());

    }

    @Override
    public int getItemCount()
    {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        ImageView ivCategoryImage;
        TextView tvCategoryName;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            ivCategoryImage = itemView.findViewById(R.id.iv_category_image);
            tvCategoryName = itemView.findViewById(R.id.tv_category_name);
        }
        @Override
        public void onClick(View v)
        {
            int position = getAdapterPosition();
            ProductCategory productCategory = categoryList.get(position);
            Intent intent = new Intent(context, ProductGridActivity.class);
            intent.putExtra("CATEGORYID", productCategory.getCategoryId());
            context.startActivity(intent);
        }
    }
}

package com.axel.akcosmetics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axel.akcosmetics.Model.Cart;
import com.axel.akcosmetics.ViewHold.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AdminUserProductsActivity extends AppCompatActivity
{

    private RecyclerView productsList;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference cartListRef;
    private String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);

        userId = getIntent().getStringExtra("userId");

        productsList = findViewById(R.id.product_list);
        productsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        productsList.setLayoutManager(layoutManager);

        cartListRef = FirebaseDatabase.getInstance().getReference()
                .child("Cart List").child("Admin View").child(userId).child("Products");
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef, Cart.class)
                .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull final CartViewHolder holder, int position, @NonNull Cart model)
            {

                holder.txtProductQuantity.setText("La quantité = " + model.getQuantity());
                holder.txtProductPrice.setText("Le prix est : " + model.getPrice());
                holder.txtProductName.setText(model.getPname());



                String pidTmp = getRef(position).getKey();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Products").child(pidTmp);

                ref.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        if(dataSnapshot.hasChild("image"))
                        {
                            String key = dataSnapshot.child("image").getValue().toString();
                            Picasso.get().load(key).into(holder.productImage);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {

                    }
                });




            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };

        productsList.setAdapter(adapter);
        adapter.startListening();

    }
}

package com.axel.akcosmetics.Sellers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.axel.akcosmetics.Admin.AdminCheckNewProductsActivity;
import com.axel.akcosmetics.Buyers.MainActivity;
import com.axel.akcosmetics.Model.Products;
import com.axel.akcosmetics.Prevalent.Prevalent;
import com.axel.akcosmetics.R;
import com.axel.akcosmetics.ViewHold.ItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import io.paperdb.Paper;

public class SellerHomeActivity extends AppCompatActivity
{

    private TextView mTextMessage;

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference unverifiedProductsRef;
    private String mProductPid;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {

            switch (item.getItemId())
            {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    return true;

                case R.id.navigation_add:
                    Intent intentCategory = new Intent(SellerHomeActivity.this, SellerProductCategoryActivity.class);
                    startActivity(intentCategory);
                    return true;


                case R.id.navigation_logout:

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

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        unverifiedProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        recyclerView = (RecyclerView) findViewById(R.id.seller_home_recyclerview);
        recyclerView.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        unverifiedProductsRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                collectPid((Map<String,Object>) dataSnapshot.getValue());

                FirebaseRecyclerOptions<Products> options =
                        new FirebaseRecyclerOptions.Builder<Products>()
                                .setQuery(unverifiedProductsRef.orderByChild("phoneSeller").equalTo(Prevalent.currentOnLineUser.getPhone()), Products.class)
                                .build();


                FirebaseRecyclerAdapter<Products, ItemViewHolder> adapter =
                        new FirebaseRecyclerAdapter<Products, ItemViewHolder>(options)
                        {
                            @Override
                            protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull final Products model)
                            {

                                holder.txtProductName.setText(model.getPname());
                                holder.txtProductDescription.setText(model.getDescription());

                                holder.txtProductStatus.setText("Etat:  " + model.getProductState());

                                holder.txtProductPrice.setText("Prix = " + model.getPrice() + "Fcfa");
                                Picasso.get().load(model.getImage()).into(holder.imageView);

                                holder.itemView.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        final String productId = model.getPid();
                                        CharSequence options[] = new CharSequence[]
                                                {
                                                        "Oui",
                                                        "Non"
                                                };
                                        AlertDialog.Builder builder = new AlertDialog.Builder(SellerHomeActivity.this);
                                        builder.setTitle("Désirez-vous supprimer ce produit? Etes-vous sûr(e)?");
                                        builder.setItems(options, new DialogInterface.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(DialogInterface dialog, int position)
                                            {
                                                //Click on Yes button
                                                if (position == 0)
                                                {
                                                    deleteProduct(productId);
                                                }

                                                //Click on No button
                                                if (position == 1)
                                                { }
                                            }
                                        });
                                        builder.show();
                                    }
                                });
                            }

                            @NonNull
                            @Override
                            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                                View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item_view, parent, false);
                                ItemViewHolder holder = new ItemViewHolder(view);
                                return holder;
                            }
                        };
                recyclerView.setAdapter(adapter);
                adapter.startListening();



            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });



    }

    private void collectPid(Map<String, Object> value)
    {
        ArrayList<String> productPid = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : value.entrySet())
        {

            //Get user map
            Map singleProduct = (Map) entry.getValue();
            //Get phone field and append to list
            productPid.add((String) singleProduct.get("pid"));
        }

        System.out.println(productPid.toString());
        mProductPid = productPid.toString();
    }


    private void deleteProduct(String productId)
    {
        unverifiedProductsRef.child(productId)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        Toast.makeText(SellerHomeActivity.this, "Cet article a été supprimé avec succès", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

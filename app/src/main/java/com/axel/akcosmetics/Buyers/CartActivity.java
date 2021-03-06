package com.axel.akcosmetics.Buyers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.axel.akcosmetics.Model.Cart;
import com.axel.akcosmetics.Prevalent.Prevalent;
import com.axel.akcosmetics.R;
import com.axel.akcosmetics.ViewHold.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CartActivity extends AppCompatActivity
{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button nextProcessBtn;
    private TextView txtTotalAmount;// txtMsg1;
    private TextView test;
    private ImageView imageView;
    private String productIdInCart = "";
    private Button continueBtn;

    private int overTotalPrice = 0;
    View lyt_empty_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        productIdInCart = getIntent().getStringExtra("pid");

        recyclerView = (RecyclerView) findViewById(R.id.cart_list);
        lyt_empty_cart = findViewById(R.id.lyt_empty_history);
        continueBtn = (Button) findViewById(R.id.btn_continue);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        nextProcessBtn = (Button) findViewById(R.id.next_btn);
        txtTotalAmount = (TextView) findViewById(R.id.total_price);
        //txtMsg1 = (TextView) findViewById(R.id.msg1);

        imageView = (ImageView) findViewById(R.id.cart_product_image);


        nextProcessBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                intent.putExtra("Total price", String.valueOf(overTotalPrice));
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    protected void onStart()
    {
        super.onStart();

        checkOrderState();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef.child("User View")
                .child(Prevalent.currentOnLineUser.getPhone())
                          .child("Products"),Cart.class).build();


        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull final CartViewHolder holder, int i, @NonNull final Cart cart)
            {

                holder.txtProductQuantity.setText("La quantité = " + cart.getQuantity());
                //cartViewHolder.txtProductPrice.setText("Le prix est : " + cart.getPrice());
                holder.txtProductName.setText(cart.getPname());

                int oneTypeTotalTPrice = ((Integer.valueOf(cart.getPrice()))) * Integer.valueOf(cart.getQuantity());

                holder.txtProductPrice.setText("Le prix est : " + oneTypeTotalTPrice + " Fcfa");

                overTotalPrice = overTotalPrice + oneTypeTotalTPrice;

                txtTotalAmount.setText(String.valueOf("Le prix total est: " + overTotalPrice + " Fcfa"));

               // Toast.makeText(CartActivity.this, "ooooooooooooooooo: " + txtTotalAmount.getText().toString(), Toast.LENGTH_SHORT).show();

                String pidTmp = getRef(i).getKey();
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
                    { }
                });


                holder.itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Modifier",
                                        "Supprimer"

                                };


                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Options du panier:");
                        builder.setItems(options, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                                if(which == 0)
                                {
                                    Intent intent = new Intent(CartActivity.this, ProductDetailsActivity.class);
                                    intent.putExtra("pid", cart.getPid());
                                    startActivity(intent);
                                }

                                if(which == 1)
                                {
                                    cartListRef.child("User View")
                                            .child(Prevalent.currentOnLineUser.getPhone())
                                            .child("Products")
                                            .child(cart.getPid())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>()
                                            {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task)
                                                {
                                                    if(task.isSuccessful())
                                                    {
                                                        Toast.makeText(CartActivity.this, "Produit retiré du panier.", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(CartActivity.this, HomeActivity.class);
                                                        startActivity(intent);
                                                    }
                                                }
                                            });
                                }
                            }
                        });

                        builder.show();

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

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }


    private void checkOrderState()
    {
        DatabaseReference ordersRef;
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnLineUser.getPhone());

        ordersRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    String shippingState = dataSnapshot.child("state").getValue().toString();
                    String userName = dataSnapshot.child("name").getValue().toString();

                    if(shippingState.equals("livrée"))
                    {

                        txtTotalAmount.setText("Cher " + userName + "\n Votre commande sera livrée avec succès.");
                        //recyclerView.setVisibility(View.GONE);

                        //txtMsg1.setVisibility(View.VISIBLE);
                       //txtMsg1.setText("Félicitation, votre commande est passée avec succès. Vous recevrez votre commande dans bientôt.");
                        //nextProcessBtn.setVisibility(View.GONE);

                        Toast.makeText(CartActivity.this, "Merci de votre achat", Toast.LENGTH_SHORT).show();

                    }
                    else if(shippingState.equals("Non livré"))
                    {
                        txtTotalAmount.setText("Etat de livraison: Non livré");
                        //recyclerView.setVisibility(View.GONE);
                        //txtMsg1.setVisibility(View.VISIBLE);
                        //nextProcessBtn.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            { }
        });
    }

}

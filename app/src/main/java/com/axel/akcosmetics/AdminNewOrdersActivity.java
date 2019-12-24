package com.axel.akcosmetics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.axel.akcosmetics.Model.AdminOrders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminNewOrdersActivity extends AppCompatActivity
{

    private RecyclerView orderList;
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);


        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        orderList = (RecyclerView) findViewById(R.id.orders_list);
        orderList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders> options =
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                .setQuery(ordersRef, AdminOrders.class)
                .build();

        FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, final int position, @NonNull final AdminOrders model)
                    {

                        holder.userName.setText("Nom: " + model.getName());
                        holder.userPhoneNumber.setText("Numéro de téléphone: " + model.getPhone());
                        holder.userTotalPrice.setText("Prix toltal: " + model.getTotalAmount());
                        holder.userDate.setText("Date de la commande: " + model.getDate());
                        holder.userDateTime.setText("Heure de la commande: " + model.getTime());
                        holder.userCity.setText("Ville: " + model.getCity());
                        holder.userQuarter.setText("Quartier: " + model.getQuarter());

                        holder.showOrdersBtn.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                String uId = getRef(position).getKey();

                                Intent intent = new Intent(AdminNewOrdersActivity.this, AdminUserProductsActivity.class);
                                intent.putExtra("userId", uId);
                                startActivity(intent);
                            }
                        });

                        holder.itemView.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                CharSequence options[] = new CharSequence[]
                                        {
                                                "Oui",
                                                "Non"
                                        };

                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminNewOrdersActivity.this);
                                builder.setTitle("Avez-vous expédié ce commande de ce produit?");
                                builder.setItems(options, new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        if(i == 0)
                                        {
                                            String uId = getRef(position).getKey();
                                            RemoveOrder(uId);
                                        }
                                        else
                                        {
                                            finish();
                                        }
                                    }
                                });
                                builder.show();

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                        return new AdminOrdersViewHolder(view);
                    }
                };

        orderList.setAdapter(adapter);
        adapter.startListening();

    }

    private void RemoveOrder(String uId)
    {
        ordersRef.child(uId).removeValue();
    }

    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder
    {
        public TextView userName, userPhoneNumber, userTotalPrice, userDate, userDateTime, userCity, userQuarter;
        public Button showOrdersBtn;

        public AdminOrdersViewHolder(@NonNull View itemView)
        {
            super(itemView);

            userName = itemView.findViewById(R.id.order_user_name);
            userPhoneNumber = itemView.findViewById(R.id.order_phone_number);
            userTotalPrice = itemView.findViewById(R.id.order_total_price);
            userDate = itemView.findViewById(R.id.order_date);
            userDateTime = itemView.findViewById(R.id.order_date_time);
            userCity = itemView.findViewById(R.id.order_city);
            userQuarter = itemView.findViewById(R.id.order_quarter);

            showOrdersBtn = itemView.findViewById(R.id.shom_all_products_btn);
        }
    }

}

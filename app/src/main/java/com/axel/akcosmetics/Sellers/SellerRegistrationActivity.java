package com.axel.akcosmetics.Sellers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.axel.akcosmetics.Buyers.LoginActivity;
import com.axel.akcosmetics.Buyers.MainActivity;
import com.axel.akcosmetics.Buyers.RegisterActivity;
import com.axel.akcosmetics.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import io.paperdb.Paper;

public class SellerRegistrationActivity extends AppCompatActivity
{

    private Button sellerLoginBegin, registerButton;
    private EditText nameInput, phoneInput, passwordInput, provinceInput, cityInput, quarterInput;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);

        mAuth = FirebaseAuth.getInstance();

        sellerLoginBegin = (Button) findViewById(R.id.seller_already_account_btn);
        registerButton = (Button) findViewById(R.id.seller_register_btn);
        nameInput = (EditText) findViewById(R.id.seller_name);
        phoneInput = (EditText) findViewById(R.id.seller_phone);
        passwordInput = (EditText) findViewById(R.id.seller_password);
        provinceInput = (EditText) findViewById(R.id.seller_province);
        cityInput = (EditText) findViewById(R.id.seller_city);
        quarterInput = (EditText) findViewById(R.id.seller_quarter);

        loadingBar = new ProgressDialog(this);



        //On click on seller begin TextView
        sellerLoginBegin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SellerRegistrationActivity.this, SellerLoginActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                registerSeller();
            }
        });

    }

    private void registerSeller()
    {
        String name = nameInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String password = passwordInput.getText().toString();
        String province = provinceInput.getText().toString();
        String city = cityInput.getText().toString();
        String quarter = quarterInput.getText().toString();

        if(!name.equals("") && !phone.equals("") && !password.equals("") && !province.equals("") && !city.equals("") && !quarter.equals(""))
        {
            loadingBar.setTitle("Création du compte commerçant");
            loadingBar.setMessage("Veuillez patienter un instant, nous vérifions les informations d'identification");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            Validatephonenumber(name, phone, password);
        }
        else
        {
            Toast.makeText(this, "Veuillez saisir tous les champs, s'il vous plaît.", Toast.LENGTH_SHORT).show();
        }

    }

    private void Validatephonenumber(final String name, final String phone, final String password)
    {
        //Creating DataBase
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        final String province = provinceInput.getText().toString();
        final String city = cityInput.getText().toString();
        final String quarter = quarterInput.getText().toString();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(!(dataSnapshot.child("Sellers").child(phone).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phoneSeller", phone);
                    userdataMap.put("passwordSeller", password);
                    userdataMap.put("nameSeller", name);
                    userdataMap.put("provinceSeller", province);
                    userdataMap.put("citySeller", city);
                    userdataMap.put("quarterSeller", quarter);

                    RootRef.child("Sellers").child(phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(SellerRegistrationActivity.this, "Félitation, votre compte a été créé avec succès.", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Paper.book().destroy();
                                Intent intent = new Intent(SellerRegistrationActivity.this, SellerHomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }

                            else
                            {
                                loadingBar.dismiss();
                                Toast.makeText(SellerRegistrationActivity.this, "Problème de réseau, veuillez vous connecter à internet.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(SellerRegistrationActivity.this,"Un compte existe déjà avec le numéro de téléphone: " + phone ,Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                    Toast.makeText(SellerRegistrationActivity.this,"Veuillez saisir un autre numéro",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(SellerRegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });
    }
}

package com.axel.akcosmetics.Sellers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.axel.akcosmetics.Admin.AdminCategoryActivity;
import com.axel.akcosmetics.Buyers.HomeActivity;
import com.axel.akcosmetics.Buyers.LoginActivity;
import com.axel.akcosmetics.Model.Users;
import com.axel.akcosmetics.Prevalent.Prevalent;
import com.axel.akcosmetics.Prevalent.PrevalentSeller;
import com.axel.akcosmetics.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class SellerLoginActivity extends AppCompatActivity
{
    private Button sellerLoginBegin;
    private EditText phoneInput, passwordInput;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    private String parentDbName = "Sellers";
    private CheckBox chkBoxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        mAuth = FirebaseAuth.getInstance();

        sellerLoginBegin = (Button) findViewById(R.id.login_seller_btn);
        phoneInput = (EditText) findViewById(R.id.login_seller_phone);
        passwordInput = (EditText) findViewById(R.id.login_seller_password);
        loadingBar = new ProgressDialog(this);

        chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_login_chk);
        Paper.init(this);

        sellerLoginBegin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                loginSeller();
            }
        });
    }

    private void loginSeller()
    {

        String phone = phoneInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(!phone.equals("") && !password.equals(""))
        {
            loadingBar.setTitle("Connexion au compte commerçant");
            loadingBar.setMessage("Veuillez patienter un instant, nous vérifions les informations d'identification");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToSellerAccount(phone,password);

        }
        else
        {
            Toast.makeText(this, "Veuillez saisir tous les champs, s'il vous plaît.", Toast.LENGTH_SHORT).show();
        }

    }

    private void AllowAccessToSellerAccount(final String phone, final String password)
    {
        if(chkBoxRememberMe.isChecked())
        {
            Paper.book().write(Prevalent.UserPhoneKey,phone);
            Paper.book().write(Prevalent.UserPasswordKey,password);
        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                if(dataSnapshot.child(parentDbName).child(phone).exists())
                {

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference(parentDbName);
                    DatabaseReference referenceSellers = reference.child(phone);

                    DatabaseReference referencePassword = referenceSellers.child("passwordSeller");
                    final DatabaseReference referencePhone = referenceSellers.child("phoneSeller");


                    if(parentDbName.equals("Sellers"))
                    {

                        referencePassword.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot1)
                            {
                                final String sellerPasswordOfUser = dataSnapshot1.getValue(String.class);

                                if(sellerPasswordOfUser.equals(password))
                                {
                                    referencePhone.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot2) {
                                            String sellerPhoneOfUser = dataSnapshot2.getValue(String.class);
                                            if(sellerPhoneOfUser.equals(phone))
                                            {
                                                Toast.makeText(SellerLoginActivity.this, "Vous êtes désormais connecté", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();

                                                Users userData = new Users("","", sellerPhoneOfUser, sellerPasswordOfUser,"","");
                                                Intent intent = new Intent(SellerLoginActivity.this, SellerHomeActivity.class);
                                                Prevalent.currentOnLineUser = userData;
                                                startActivity(intent);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) { }
                                    });
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //Log.w(TAG, "onCancelled", databaseError.toException());
                            }
                        });
                    }
                }
                else
                {
                    Toast.makeText(SellerLoginActivity.this, "Aucun compte n'existe au numéro: " + phone, Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(SellerLoginActivity.this, "Numéro de téléphone ou mot de passe incorrect, veuillez vérifier les informations saisies.", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            { }
        });
    }
}

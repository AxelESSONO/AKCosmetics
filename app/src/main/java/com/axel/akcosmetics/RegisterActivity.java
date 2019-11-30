package com.axel.akcosmetics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity
{

    private Button CreateAccountButton;
    private EditText InputName, InputPhoneNumber, InputPassWord;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        InputPassWord = (EditText) findViewById(R.id.register_password_input);
        loadingBar = new ProgressDialog(this);

        //Create account
        CreateAccountButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CreateAccount();
            }
        });
    }

    //Method to create account
    private void CreateAccount()
    {
        //get name, phone number and password of user
        String name = InputName.getText().toString();
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassWord.getText().toString();

        //Check name field, phone number field and password field is not empty
        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this,"Veuillez saisir votre nom, s'il vous plaît", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this,"Veuillez saisir votre numéro de téléphone, s'il vous plaît", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Veuillez saisir votre mot de passe, s'il vous plaît", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Création du compte");
            loadingBar.setMessage("Veuillez patienter un instant, nous vérifions les informations d'identification");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            Validatephonenumber(name, phone, password);
        }
    }

    private void Validatephonenumber(final String name, final String phone, final String password)
    {

        //Creating DataBase
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(!(dataSnapshot.child("Users").child("phone").exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", phone);
                    userdataMap.put("password", password);
                    userdataMap.put("name", name);

                    RootRef.child("Users").child(phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(RegisterActivity.this, "Félitation, votre compte a été créé avec succès.", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();


                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }

                            else
                            {
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Problème de réseau, veuillez vous connecter à internet.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this,"Un compte existe déjà avec le numéro de téléphone: " + phone ,Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this,"Veuillez saisir un autre numéro",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
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

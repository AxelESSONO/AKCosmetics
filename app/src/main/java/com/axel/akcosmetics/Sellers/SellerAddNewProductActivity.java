package com.axel.akcosmetics.Sellers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.axel.akcosmetics.Admin.SellerProductCategoryActivity;
import com.axel.akcosmetics.Prevalent.Prevalent;
import com.axel.akcosmetics.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class SellerAddNewProductActivity extends AppCompatActivity
{

    private TextView CategorySelected;

    private String CategoryName, Description, Price,Pname, saveCurrentDate, saveCurrentTime;
    private Button AddNewProductButton;
    private EditText InputProductName, InputProdutDescription, InputProductPrice;
    private ImageView InputProductImage;
    private Uri ImageUri;
    private StorageReference ProductImagesRef;
    private DatabaseReference productsRef, sellersRef;
    private String ProductRandomKey, downloadImageUrl;
    private static final int GalleryPick = 1;
    private ProgressDialog loadingBar;

    private String sCity, sName, sPhone, sProvince, sQuarter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_add_new_product);

        CategoryName = getIntent().getExtras().get("category").toString();
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product images");
        productsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        sellersRef = FirebaseDatabase.getInstance().getReference().child("Sellers");


        CategorySelected = (TextView) findViewById(R.id.category_selected);
        CategorySelected.setText(CategoryName);

        AddNewProductButton = (Button) findViewById(R.id.add_new_product);
        InputProductImage = (ImageView) findViewById(R.id.select_product_image);
        InputProductName = (EditText) findViewById(R.id.product_name);
        InputProdutDescription = (EditText) findViewById(R.id.product_description);
        InputProductPrice = (EditText) findViewById(R.id.product_price);
        loadingBar = new ProgressDialog(this);

        InputProductImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OpenGallery();
            }
        });

        AddNewProductButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ValidateProductData();
            }
        });

        //FirebaseAuth.getInstance().getCurrentUser().getUid(

        sellersRef.child(Prevalent.currentOnLineUser.getPhone()) // ================ ICI ================
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.exists())
                        {
                            sName = dataSnapshot.child("nameSeller").getValue().toString();
                            sCity = dataSnapshot.child("citySeller").getValue().toString();
                            sPhone = dataSnapshot.child("phoneSeller").getValue().toString();
                            sProvince = dataSnapshot.child("provinceSeller").getValue().toString();
                            sQuarter = dataSnapshot.child("quarterSeller").getValue().toString();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });
    }

    private void ValidateProductData()
    {
        Description = InputProdutDescription.getText().toString();
        Price = InputProductPrice.getText().toString();
        Pname = InputProductName.getText().toString();


        if(ImageUri == null)
        {
            Toast.makeText(this, "Une image est obligatoire", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "Veuillez ajouter la description du produit.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Price))
        {
            Toast.makeText(this, "Veuillez ajouter le prix du produit.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Pname))
        {
            Toast.makeText(this, "Veuillez ajouter le nom du produit.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation()
    {



        loadingBar.setTitle("Ajout du nouveau produit par l'administrateur");
        loadingBar.setMessage("Veuillez patienter un instant pendant que nous recevons le nouveau produit");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();



        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate =  new SimpleDateFormat("dd MM, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime =  new SimpleDateFormat("HH: mm: ss");
        saveCurrentTime = currentTime.format(calendar.getTime());

        ProductRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + ProductRandomKey + ".jpg");
        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(SellerAddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(SellerAddNewProductActivity.this, "L'image du produit a été enrégistrée avec succès", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if(!task.isSuccessful())
                        {
                            throw  task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if(task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(SellerAddNewProductActivity.this, "L'Url de l'image du produit a été enregistrée dans la base de donnée avec succès.", Toast.LENGTH_SHORT).show();
                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });

    }

    private void SaveProductInfoToDatabase()
    {

        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", ProductRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", Description);
        productMap.put("image", downloadImageUrl);
        productMap.put("category", CategoryName);
        productMap.put("price", Price);
        productMap.put("pname", Pname);

        productMap.put("citySeller", sCity );
        productMap.put("nameSeller", sName);
        productMap.put("phoneSeller", sPhone);
        productMap.put("provinceSeller", sProvince);
        productMap.put("quarterSeller", sQuarter);

        productMap.put("productState", "Pas approuvé");

        productsRef.child(ProductRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {

                            Intent intent = new Intent(SellerAddNewProductActivity.this, SellerProductCategoryActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(SellerAddNewProductActivity.this, "Produit ajouté avec succès.", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(SellerAddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();


                        }
                    }
                });

    }

    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GalleryPick && resultCode == RESULT_OK && data !=null)
        {
            ImageUri = data.getData();
            InputProductImage.setImageURI(ImageUri);
        }
    }

    private void sellerInformation()
    {

    }

}
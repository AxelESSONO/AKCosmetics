package com.axel.akcosmetics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SearchProductsActivity extends AppCompatActivity
{

    private Button searchBtn;
    private EditText inputText;
    private RecyclerView searchList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);

        inputText = (EditText) findViewById(R.id.search_product_name);
        searchBtn = (Button) findViewById(R.id.search_btn);

        searchList = (RecyclerView) findViewById(R.id.searh_list);
        searchList.setLayoutManager(new LinearLayoutManager(SearchProductsActivity.this));

    }
}

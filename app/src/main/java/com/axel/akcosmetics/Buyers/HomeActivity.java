package com.axel.akcosmetics.Buyers;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.axel.akcosmetics.Fragment.CategoryFragment;
import com.axel.akcosmetics.Fragment.MainFragment;
import com.axel.akcosmetics.R;
import com.axel.akcosmetics.Util.UtilityClass;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity
        //implements NavigationView.OnNavigationItemSelectedListener
{

   /** private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private String type = "";  **/

    //======================================================== NEW PROGRAM =======================================================

    public static final String FRAGMENT_MAIN = "fragment_main";
    public static final String FRAGMENT_MENU_CATEGORY = "fragment_menu_category";
    public static final String FRAGMENT_MENU_MAIN = "fragment_menu_main";


    /*START OF MENU VERIABLE*/
    private TextView toobarTitle;
    private ImageView toobarLogo;
    private Toolbar toolbar;
    /*END OF MENU VERIABLE*/

    /*START OF NAVIGATION DRAWER*/
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView nvMainNav;
    /*END OF NAVIGATION DRAWER*/

             // ================== On Create ==========================

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*START OF TOOLBAR */
        toolbar = findViewById(R.id.toolbar);
        toobarTitle = (TextView) findViewById(R.id.toolbar_title);
        //toobarLogo = findViewById(R.id.toolbar_logo);
        toolbar.setTitle("");
        //toobarTitle.setText(this.getString(R.string.app_name));
        //toobarTitle.setText(R.string.app_name);
        //toobarTitle.setTextColor(Integer.parseInt("#FFFFFF"));
        //toobarTitle.setText("LikeView");
        //toobarLogo.setVisibility(View.INVISIBLE);
        toobarTitle.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        /*END OF TOOLBAR */


        /*START OF MAIN FRAGMENT*/
        MainFragment cartFragment = new MainFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_fragment_container, cartFragment, FRAGMENT_MAIN)
                .commit();
        /*END OF MAIN FRAGMENT*/


        /*START OF NAVIGATION DRAWER */
        drawerLayout = findViewById(R.id.dl_navigation_drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();


        //SmoothActionBarDrawerToggle mDrawerToggle = new SmoothActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        // drawerLayout.setDrawerListener(mDrawerToggle);

        nvMainNav = findViewById(R.id.nv_main_nav);
        nvMainNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:

                        drawerLayout.closeDrawer(GravityCompat.START);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                MainFragment mainFragment = new MainFragment();
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .add(R.id.main_fragment_container, mainFragment, FRAGMENT_MENU_MAIN)
                                        .addToBackStack(null)
                                        .commit();

                            }
                        }, 0);

                        break;

                    case R.id.nav_account:
                        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        //drawerLayout.closeDrawers();
                        break;

                    case R.id.nav_category:

                        drawerLayout.closeDrawer(GravityCompat.START);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                CategoryFragment categoryFragment = new CategoryFragment();

                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .add(R.id.main_fragment_container, categoryFragment, FRAGMENT_MENU_CATEGORY)
                                        .addToBackStack(null)
                                        .commit();
                            }
                        }, 0);

                        break;

                    case R.id.nav_orders:
                        Intent intentOrder = new Intent(HomeActivity.this, OrderActivity.class);
                        startActivity(intentOrder);
                        //drawerLayout.closeDrawers();
                        break;

                    case R.id.nav_cart:
                        Intent intentCart = new Intent(HomeActivity.this, CartActivity.class);
                        startActivity(intentCart);
                        //drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_favourites:
                        Intent intentFavourites = new Intent(HomeActivity.this, ProductGridActivity.class);
                        intentFavourites.putExtra(UtilityClass.TOOLBAR_TITLE, getString(R.string.title_favourites));
                        startActivity(intentFavourites);
                        //drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_login:
                        Intent intentLogin = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intentLogin);
                        //drawerLayout.closeDrawers();
                        break;
                }

                return false;
            }
        });

        /*END OF NAVIGATION DRAWER */

    }


    public void removeAllFragments() {

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        final Menu m = menu;
        final MenuItem item = menu.findItem(R.id.action_cart_count);
        item.getActionView().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                /*START CART ACTIVITY*/
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*START FOR NAVIGATION DRAWER*/
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        /*END FOR NAVIGATION DRAWER*/

        return super.onOptionsItemSelected(item);
    }





    //======================================================== END         =======================================================


/**
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            type = getIntent().getExtras().get("Admin").toString();
        }

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        Paper.init(this) ;


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Accueil");
        setSupportActionBar(toolbar);

        /// Floating Button//////////////////////////////////////////////
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!type.equals("Admin"))
                {
                    Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                    startActivity(intent);
                }
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);


        if(!type.equals("Admin"))
        {
            userNameTextView.setText(Prevalent.currentOnLineUser.getName());
            Picasso.get().load(Prevalent.currentOnLineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);
        }


        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
       // layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(ProductsRef.orderByChild("productState").equalTo("Approuvé"), Products.class)
                .build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull final Products products)
            {
                productViewHolder.txtProductName.setText(products.getPname());
                productViewHolder.txtProductDescription.setText(products.getDescription());
                productViewHolder.txtProductPrice.setText(products.getPrice() + "Fcfa");
                Picasso.get().load(products.getImage()).into(productViewHolder.imageView);


                productViewHolder.itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {

                        if(type.equals("Admin"))
                        {
                            Intent intent = new Intent(HomeActivity.this, AdminMaintainProductsActivity.class);
                            intent.putExtra("pid", products.getPid());
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
                            intent.putExtra("pid", products.getPid());
                            startActivity(intent);
                        }


                    }
                });


            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }
        };


        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
            {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

       //if (id == R.id.action_settings)
       //{
           //return true;
      // }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart)
        {

            if(!type.equals("Admin"))
            {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        }
        else if(id == R.id.nav_search)
        {

            if(!type.equals("Admin"))
            {
                Intent intent = new Intent(HomeActivity.this, SearchProductsActivity.class);
                startActivity(intent);
            }

        }

        else if (id == R.id.nav_orders)
        {
            if(!type.equals("Admin"))
            {
                Intent OrderIntent = new Intent(HomeActivity.this, OrderActivity.class);
                startActivity(OrderIntent);

            }

        }
        else if (id == R.id.nav_categories)
        {

        }
        else if (id == R.id.nav_settings)
        {

            if(!type.equals("Admin"))
            {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        }
        else if (id == R.id.nav_logout)
        {

            if(!type.equals("Admin"))
            {
                Paper.book().destroy();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
  **/
}

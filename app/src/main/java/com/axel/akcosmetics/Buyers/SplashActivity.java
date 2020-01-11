package com.axel.akcosmetics.Buyers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.axel.akcosmetics.R;

public class SplashActivity extends AppCompatActivity
{


    private TextView EntrepriseName, EntrepriseSlogan, EntrepriseVersion;


    private Intent mainIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mainIntent = new Intent(SplashActivity.this, MainActivity.class);

        EntrepriseName = (TextView) findViewById(R.id.name_entreprise);
        EntrepriseSlogan = (TextView) findViewById(R.id.slogan_entreprise);
        EntrepriseVersion = (TextView) findViewById(R.id.app_version);

        Animation transitionAlfa = AnimationUtils.loadAnimation(this, R.anim.transition_alfa);
        EntrepriseName.startAnimation(transitionAlfa);
        EntrepriseSlogan.startAnimation(transitionAlfa);
        EntrepriseVersion.startAnimation(transitionAlfa);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, 3000);

    }












    /**Boolean isCancelled = false;
    private ProgressBar progressBar;
    long id = 0;
    String url = "";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Config.ENABLE_RTL_MODE)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        }

        progressBar = findViewById(R.id.progressBar);

        if (getIntent().hasExtra("nid"))
        {
            id = getIntent().getLongExtra("nid", 0);
            url = getIntent().getStringExtra("external_link");
        }

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {

                progressBar.setVisibility(View.GONE);

                if (!isCancelled)
                {
                    if (id == 0)
                    {
                        if (url.equals("") || url.equals("no_url"))
                        {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else
                            {
                            Intent a = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(a);

                            Intent b = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(b);

                            finish();
                        }
                    } else if (id == 1010101010)
                    {

                        Intent a = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(a);

                        finish();

                    } else
                        {

                        Intent a = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(a);

                        finish();

                    }
                }
            }
        }, Config.SPLASH_TIME);

    }
**/

}
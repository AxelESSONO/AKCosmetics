package com.axel.akcosmetics;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity
{

    private Button CreatAccountButton;
    private EditText InputName, InputPhoneNumber, InputPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CreatAccountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        InputPassWord = (EditText) findViewById(R.id.register_password_input);
    }
}

package com.example.restaurantpicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.restaurantpicker.Models.User;
import com.example.restaurantpicker.SharedPreferenceManager.SharedPrefManager;

public class UserProfile extends AppCompatActivity {

    TextView nameTextView;
    TextView emailTextView;
    TextView phoneTextView;
    Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initializeViews();
        makeVisibleUserInfo();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, UpdateProfile.class));
            }
        });
    }

    private void makeVisibleUserInfo() {
        User user = SharedPrefManager.getInstance(this).getUser();
        nameTextView.setText(user.getName());
        emailTextView.setText(user.getEmail());
        phoneTextView.setText(user.getPhone());
    }

    private void initializeViews() {
        nameTextView = findViewById(R.id.user_name_textview);
        emailTextView = findViewById(R.id.user_email_textview);
        phoneTextView = findViewById(R.id.user_phone_textview);
        updateButton = findViewById(R.id.update_button);
    }
}

package com.example.restaurantpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.restaurantpicker.Models.User;
import com.example.restaurantpicker.SharedPreferenceManager.SharedPrefManager;

public class ConfirmOrder extends AppCompatActivity {

    TextView restaurantNameTextView;
    TextView itemNameTextView;
    EditText phoneEditText;
    EditText locationEditText;

    Button confirmButton;

    String restaurantName;
    String itemID;
    String itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        initialize();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = validateInputs();

                if (!isValid) {
                    return;
                }

                //TODO: Submit orders to server

            }
        });

    }

    private boolean validateInputs() {

        String phone = phoneEditText.getText().toString();
        String location = locationEditText.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            phoneEditText.setError("Please enter phone");
            phoneEditText.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(location)) {
            locationEditText.setError("Please enter your location");
            locationEditText.requestFocus();
            return false;
        }

        return true;
    }

    private void initialize() {
        restaurantNameTextView = findViewById(R.id.restaurant_name_textview);
        itemNameTextView = findViewById(R.id.item_name_textview);

        phoneEditText = findViewById(R.id.phone_edit_text);
        locationEditText = findViewById(R.id.location_edit_text);

        confirmButton = findViewById(R.id.confirm_button);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            restaurantName = bundle.getString(Constants.RESTAURANT_NAME);
            itemID = bundle.getString(Constants.ITEM_ID);
            itemName = bundle.getString(Constants.ITEM_NAME);

            restaurantNameTextView.setText(restaurantName);
            itemNameTextView.setText(itemName);

            User user = SharedPrefManager.getInstance(this).getUser();
            phoneEditText.setText(user.getPhone());

        }

    }
}

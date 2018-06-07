package com.example.restaurantpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.restaurantpicker.Models.User;
import com.example.restaurantpicker.SharedPreferenceManager.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConfirmOrder extends AppCompatActivity {
    TextView restaurantNameTextView;
    TextView itemNameTextView;
    EditText phoneEditText;
    EditText locationEditText;
    Button confirmButton;
    NumberPicker amountPicker;
    String restaurantName;
    String itemID;
    String restaurantID;
    String itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        initializeViews();
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = validateInputs();
                if (!isValid) {
                    return;
                }
                submitOrder();
            }
        });
    }

    private void submitOrder() {
        final User user = SharedPrefManager.getInstance(this).getUser();
        final String userID = user.getId();
        final String tempPhone = phoneEditText.getText().toString().trim();
        final String tempLocation = locationEditText.getText().toString().trim();
        final String itemAmount = String.valueOf(amountPicker.getValue());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SUBMIT_ORDER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(Constants.LOGTAG, e.getLocalizedMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("item_id", itemID);
                params.put("user_id", userID);
                params.put("company_id", restaurantID);
                params.put("amount", itemAmount);
                params.put("phone", tempPhone);
                params.put("location", tempLocation);
                return params;
            }
        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest, Constants.REQUEST_TAG);
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

    private void initializeViews() {
        restaurantNameTextView = findViewById(R.id.restaurant_name_textview);
        itemNameTextView = findViewById(R.id.item_name_textview);
        phoneEditText = findViewById(R.id.phone_edit_text);
        locationEditText = findViewById(R.id.location_edit_text);
        confirmButton = findViewById(R.id.confirm_button);
        amountPicker = findViewById(R.id.amount_picker);
        amountPicker.setMaxValue(10);
        amountPicker.setMinValue(1);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            restaurantName = bundle.getString(Constants.RESTAURANT_NAME);
            itemID = bundle.getString(Constants.ITEM_ID);
            itemName = bundle.getString(Constants.ITEM_NAME);
            restaurantID = bundle.getString(Constants.RESTAURANT_ID);
            restaurantNameTextView.setText(restaurantName);
            itemNameTextView.setText(itemName);
            User user = SharedPrefManager.getInstance(this).getUser();
            phoneEditText.setText(user.getPhone());
        }
    }
}

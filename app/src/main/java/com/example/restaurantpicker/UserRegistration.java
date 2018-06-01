package com.example.restaurantpicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class UserRegistration extends AppCompatActivity {

    Button register;
    EditText editTextName, editTextEmail, editTextPassword, editTextPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        initialize();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerUser();

            }
        });
    }

    private void registerUser() {
        final String tempName = editTextName.getText().toString().trim();
        final String tempEmail = editTextEmail.getText().toString().trim();
        final String tempPassword = editTextPassword.getText().toString().trim();
        final String tempPhone = editTextPhone.getText().toString().trim();

        //data validation
        if (TextUtils.isEmpty(tempName)) {
            editTextName.setError("Please enter name");
            editTextName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(tempEmail)) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(tempEmail).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(tempPassword)) {
            editTextPassword.setError("Enter a password");
            editTextPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(tempPhone)) {
            editTextPhone.setError("Enter a phone number");
            editTextPhone.requestFocus();
            return;
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.USER_REGISTRATION_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                Log.d(Constants.LOGTAG, "on response");

                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")) {
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating user object from json
                        User user = new User(
                                userJson.getString("id"),
                                userJson.getString("name"),
                                userJson.getString("email")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);


                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), GetAllRestaurants.class));

                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        //Log.d(Constants.LOGTAG, obj.getString("message"));
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
                params.put("name", tempName);
                params.put("email", tempEmail);
                params.put("password", tempPassword);
                params.put("phone", tempPhone);
                return params;
            }
        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest, Constants.REQUEST_TAG);
    }

    private void initialize() {
        register = findViewById(R.id.buttonRegister);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPhone = findViewById(R.id.editTextPhone);
    }
}

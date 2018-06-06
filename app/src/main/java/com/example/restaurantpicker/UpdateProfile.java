package com.example.restaurantpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class UpdateProfile extends AppCompatActivity {

    EditText nameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText phoneEditText;
    Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        initializeViews();
        setUserInfoToEditText();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestToServer();
            }
        });
    }

    private void sendRequestToServer() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.UPDATE_PROFILE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                JSONObject userJson = obj.getJSONObject("user");
                                saveUserInformation(userJson);
                            } else {
                                Log.d(Constants.LOGTAG, obj.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(Constants.LOGTAG, error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = getValidPostParameters();
                return params;
            }
        };

        AppSingleton.getInstance(getApplicationContext()).
                addToRequestQueue(stringRequest, Constants.REQUEST_TAG);

    }

    private void saveUserInformation(JSONObject userJson) throws JSONException {
        User user = new User(
                userJson.getString("id"),
                userJson.getString("name"),
                userJson.getString("email"),
                userJson.getString("phone")
        );
        SharedPrefManager.getInstance(getApplicationContext()).logout();
        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
    }

    private Map<String, String> getValidPostParameters() {
        Map<String, String> params = new HashMap<>();
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String userID = SharedPrefManager.getInstance(this).getUser().getId();
        params.put("id", userID);

        if (!TextUtils.isEmpty(name)) {
            params.put("name", name);
        }
        if (!TextUtils.isEmpty(email) &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            params.put("email", email);
        }
        if (!TextUtils.isEmpty(password)) {
            params.put("password", password);
        }
        if (!TextUtils.isEmpty(phone)) {
            params.put("phone", phone);
        }
        return params;
    }


    private void initializeViews() {
        nameEditText = findViewById(R.id.editTextName);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        phoneEditText = findViewById(R.id.editTextPhone);
        updateButton = findViewById(R.id.buttonUpdate);
    }

    private void setUserInfoToEditText() {
        User user = SharedPrefManager.getInstance(this).getUser();
        nameEditText.setText(user.getName());
        emailEditText.setText(user.getEmail());
        phoneEditText.setText(user.getPhone());
    }

}

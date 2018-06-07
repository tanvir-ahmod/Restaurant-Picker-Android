package com.example.restaurantpicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.restaurantpicker.Adapter.OrderHistoryAdapter;
import com.example.restaurantpicker.Models.OrderHistory;
import com.example.restaurantpicker.Models.User;
import com.example.restaurantpicker.SharedPreferenceManager.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserProfile extends AppCompatActivity {
    TextView nameTextView;
    TextView emailTextView;
    TextView phoneTextView;
    Button updateButton;
    List<OrderHistory> orderHistoryList = new ArrayList<>();
    RecyclerView recyclerView;
    OrderHistoryAdapter orderHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initializeViews();
        initializeAdapters();
        makeVisibleUserInfo();
        getHistoryFromServer();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, UpdateProfile.class));
            }
        });
    }

    private void getHistoryFromServer() {
        String userID = SharedPrefManager.getInstance(this).getUser().getId();
        String URL = Constants.ORDER_HISTORY_URL + "?id=" + userID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                JSONArray histories = obj.getJSONArray("history");
                                for (int i = 0; i < histories.length(); i++) {
                                    JSONObject history = histories.getJSONObject(i);
                                    OrderHistory orderHistory = new OrderHistory();
                                    orderHistory.setRestaurnatName(history.getString("restaurant_name"));
                                    orderHistory.setItemName(history.getString("item_name"));
                                    orderHistory.setPrice(history.getString("price"));
                                    orderHistory.setTimestamp(history.getString("order_time"));
                                    orderHistoryList.add(orderHistory);
                                }
                            } else {
                                Log.d(Constants.LOGTAG, obj.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        orderHistoryAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(Constants.LOGTAG, "VolleyError : " + error.getMessage());
                    }
                });
        AppSingleton.getInstance(getApplicationContext())
                .addToRequestQueue(stringRequest, Constants.REQUEST_TAG);
    }

    private void initializeAdapters() {
        orderHistoryAdapter = new OrderHistoryAdapter(orderHistoryList);
        orderHistoryAdapter.setContext(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(orderHistoryAdapter);
    }

    private void makeVisibleUserInfo() {
        User user = SharedPrefManager.getInstance(this).getUser();
        nameTextView.setText(user.getName());
        emailTextView.setText(user.getEmail());
        phoneTextView.setText(user.getPhone());
    }

    private void initializeViews() {
        nameTextView = findViewById(R.id.restaurant_name_textview);
        emailTextView = findViewById(R.id.restaurant_email_textview);
        phoneTextView = findViewById(R.id.restaurant_phone_textview);
        updateButton = findViewById(R.id.update_button);
        recyclerView = findViewById(R.id.order_history_recyclerview);
    }
}

package com.example.restaurantpicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.restaurantpicker.Adapter.OrderHistoryAdapter;
import com.example.restaurantpicker.Models.OrderHistory;
import com.example.restaurantpicker.Models.User;
import com.example.restaurantpicker.SharedPreferenceManager.SharedPrefManager;

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
        insertDummyData();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, UpdateProfile.class));
            }
        });
    }

    private void insertDummyData() {
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setRestaurnatName("aaaaaaaaa");
        orderHistory.setItemName("bbbbbbb");
        orderHistory.setAmount("1");
        orderHistory.setPrice("10");
        orderHistory.setTimestamp("100");
        orderHistoryList.add(orderHistory);

        orderHistory = new OrderHistory();
        orderHistory.setRestaurnatName("cccccc");
        orderHistory.setItemName("ddddd");
        orderHistory.setAmount("2");
        orderHistory.setPrice("20");
        orderHistory.setTimestamp("300");
        orderHistoryList.add(orderHistory);

        orderHistoryAdapter.notifyDataSetChanged();
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
        nameTextView = findViewById(R.id.user_name_textview);
        emailTextView = findViewById(R.id.user_email_textview);
        phoneTextView = findViewById(R.id.user_phone_textview);
        updateButton = findViewById(R.id.update_button);
        recyclerView = findViewById(R.id.order_history_recyclerview);
    }
}

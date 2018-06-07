package com.example.restaurantpicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.restaurantpicker.Adapter.ItemAdapter;
import com.example.restaurantpicker.Models.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RestaurantItems extends AppCompatActivity {
    String restaurantID;
    private ArrayList<Item> itemData = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    TextView restaurantNameTextView;
    TextView restaurantEmailTextView;
    TextView restaurantPhoneTextView;
    ImageView restaurantImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_items);
        initializeViews();
        displayRestaurantInfo();
        itemAdapter = new ItemAdapter(itemData, this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(itemAdapter);
        getRestaurantProducts(restaurantID);
    }

    private void displayRestaurantInfo() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            restaurantID = bundle.getString(Constants.RESTAURANT_ID);
            String restaurantName = bundle.getString(Constants.RESTAURANT_NAME);
            String restaurantPhone = bundle.getString(Constants.RESTAURANT_PHONE);
            String restaurantImageURL = bundle.getString(Constants.RESTAURANT_IMAGE);
            restaurantNameTextView.setText(restaurantName);
            restaurantPhoneTextView.setText(restaurantPhone);
            Glide.with(this).load(restaurantImageURL).into(restaurantImageView);
        }
    }

    private void initializeViews() {
        restaurantNameTextView = findViewById(R.id.restaurant_name_textview);
        restaurantEmailTextView = findViewById(R.id.restaurant_email_textview);
        restaurantPhoneTextView = findViewById(R.id.restaurant_phone_textview);
        restaurantImageView = findViewById(R.id.restaurant_image);
        recyclerView = findViewById(R.id.item_recycler_view);
    }

    private void getRestaurantProducts(final String restaurantId) {
        String url = Constants.RESTAURANT_ITEM_URL + "?id=" + restaurantId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                JSONArray items = obj.getJSONArray("items");

                                for (int i = 0; i < items.length(); i++) {
                                    JSONObject item = items.getJSONObject(i);
                                    String id = item.getString("id");
                                    String name = item.getString("item_name");
                                    String price = item.getString("price");
                                    String image = item.getString("image");
                                    Item itemModel = new Item(id, name, price, image, restaurantId);
                                    itemData.add(itemModel);
                                }
                            } else {
                                Log.d(Constants.LOGTAG, obj.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        itemAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(Constants.LOGTAG, error.getMessage());
                    }
                });

        AppSingleton.getInstance(getApplicationContext())
                .addToRequestQueue(stringRequest, Constants.REQUEST_TAG);
    }
}

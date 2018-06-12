package com.example.restaurantpicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.restaurantpicker.Adapter.ItemAdapter;
import com.example.restaurantpicker.Models.Item;
import com.example.restaurantpicker.SharedPreferenceManager.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResult extends AppCompatActivity {
    private ArrayList<Item> itemData = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private String searchKey;
    private RelativeLayout restaurantInfoLayout;
    TextView searchResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_items);
        searchResultTextView = findViewById(R.id.search_result_text_view);
        searchResultTextView.setText("Search Result");

        recyclerView = findViewById(R.id.item_recycler_view);
        restaurantInfoLayout = findViewById(R.id.restaurant_info_layout);
        restaurantInfoLayout.setVisibility(View.GONE);

        itemAdapter = new ItemAdapter(itemData, this);
        itemAdapter.setVisibleRestaurantName(true); //To visible restaurant name

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(itemAdapter);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            searchKey = bundle.getString(Constants.SEARCH_KEY);
        }
        getSearchedItems(searchKey);
    }

    private void getSearchedItems(final String searchKey) {
        String url = Constants.SERVER + Constants.SEARCH_ITEM_URL + "?search_key=" + searchKey;
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
                                    String restaurantID = item.getString("companyID");
                                    String name = item.getString("item_name");
                                    String price = item.getString("price");
                                    String image = item.getString("image");
                                    String restaurantName = item.getString("restaurant_name");
                                    Item itemModel = new Item(id, name, price, image, restaurantID);
                                    itemModel.setRestaurantName(restaurantName);
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
                    }
                });
        AppSingleton.getInstance(getApplicationContext())
                .addToRequestQueue(stringRequest, Constants.REQUEST_TAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.profile) {
            startActivity(new Intent(this, UserProfile.class));
        }
        if (id == R.id.setting) {
            startActivity(new Intent(this, Setting.class));
        }
        if (id == R.id.logout) {
            SharedPrefManager.getInstance(this).logout();
            finish();
            startActivity(new Intent(this, Login.class));
        }
        return super.onOptionsItemSelected(item);
    }
}

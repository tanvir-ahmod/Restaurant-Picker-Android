package com.example.restaurantpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.restaurantpicker.Models.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RestaurantProducts extends AppCompatActivity {

    String restaurantID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_products);

        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            Toast.makeText(this, "Something went Wrong!", Toast.LENGTH_SHORT).show();
            return;
        }

        restaurantID = bundle.getString(Constants.RESTAURANT_ID);
        getRestaurantProducts(restaurantID);


    }


    private void getRestaurantProducts(final String restaurantId) {

        String url = Constants.RESTAURANT_ITEM_URL + "?id=" + restaurantId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to json object
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
                                    // restaurantsData.add(restaurantModel);
                                    //Log.d(Constants.LOGTAG,itemModel.toString());

                                }

                            } else {
                                Log.d(Constants.LOGTAG, obj.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(Constants.LOGTAG, e.getLocalizedMessage());
                        }

                        //restaurantAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(Constants.LOGTAG, error.getMessage());
                    }
                });

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest, Constants.REQUEST_TAG);

    }
}

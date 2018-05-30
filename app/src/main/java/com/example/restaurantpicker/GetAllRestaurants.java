package com.example.restaurantpicker;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.example.restaurantpicker.Adapter.RestaurantAdapter;
import com.example.restaurantpicker.Models.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetAllRestaurants extends AppCompatActivity {

    private ArrayList<Restaurant> restaurantsData = new ArrayList<>();

    RecyclerView recyclerView;
    RestaurantAdapter restaurantAdapter;

    Bitmap tempImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_restaurants);

        recyclerView = findViewById(R.id.all_restaurant_recyclerview);

        restaurantAdapter = new RestaurantAdapter(restaurantsData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(restaurantAdapter);

        getAllRestaurants();

        // Log.d(Constants.LOGTAG, restaurantsData.size() + "");

        // insertDummyData();
    }

    private void insertDummyData() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("aaaa");
        restaurant.setPhone("0000");
        restaurantsData.add(restaurant);

        restaurant = new Restaurant();
        restaurant.setName("bbbbb");
        restaurant.setPhone("11111");
        restaurantsData.add(restaurant);

        restaurant = new Restaurant();
        restaurant.setName("ccccc");
        restaurant.setPhone("22222");
        restaurantsData.add(restaurant);

        restaurantAdapter.notifyDataSetChanged();

    }

    private void getAllRestaurants() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.GET_RESTAURANT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                //Log.d(Constants.LOGTAG, obj.getString("message"));

                                JSONArray restaurants = obj.getJSONArray("restaurant");

                                for (int i = 0; i < restaurants.length(); i++) {
                                    JSONObject restaurant = restaurants.getJSONObject(i);

                                    String id = restaurant.getString("id");
                                    String name = restaurant.getString("name");
                                    String phone = restaurant.getString("phone");
                                    String image = restaurant.getString("image");
                                    //Bitmap bitmap = volleyImageLoader(Constants.RESTAURANT_IMAGE_URL + image);

                                    Restaurant restaurantModel = new Restaurant(id, name, phone, image);
                                   // restaurantModel.setRestaurantImage(bitmap);
                                    restaurantsData.add(restaurantModel);

                                }

                                //Log.d(Constants.LOGTAG, restaurantsData.size() + "");

                            } else {
                                Log.d(Constants.LOGTAG, obj.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(Constants.LOGTAG, e.getLocalizedMessage());
                        }

                        restaurantAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(Constants.LOGTAG, error.getMessage());
                    }
                }) {
            /*@Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", tempEmail);
                params.put("password", password);
                return params;
            }*/
        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest, Constants.REQUEST_TAG);

    }


    public Bitmap volleyImageLoader(final String url) {

        tempImage = null;
        //Log.e(Constants.LOGTAG, "Image URL : " + url);

        ImageLoader imageLoader = AppSingleton.getInstance(getApplicationContext()).getImageLoader();

        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(Constants.LOGTAG, "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {

                    tempImage = response.getBitmap();
                    Log.d(Constants.LOGTAG, "Image loaded for" + url);
                }
            }
        });

        return tempImage;
    }
}

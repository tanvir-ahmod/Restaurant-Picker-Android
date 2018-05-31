package com.example.restaurantpicker.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.restaurantpicker.Constants;
import com.example.restaurantpicker.R;
import com.example.restaurantpicker.Models.Restaurant;

import java.util.List;

/**
 * Created by Shoukhin on 5/30/2018.
 */
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurantList;
    private Context context;

    public RestaurantAdapter(List<Restaurant> restaurantList, Context context) {
        this.restaurantList = restaurantList;
        this.context = context;
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_restaurant_list_view, parent, false);

        return new RestaurantViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {
        holder.restaurantName.setText(restaurantList.get(position).getName());
        holder.restaurantPhone.setText(restaurantList.get(position).getPhone());

        //String url = "http://192.168.161.1/Restaurant%20Picker/images/happy.jpg";
        String imageUrl = Constants.RESTAURANT_IMAGE_URL + restaurantList.get(position).getImage();
        Glide.with(context).load(imageUrl).into(holder.restaurantImage);

        Log.d(Constants.LOGTAG, restaurantList.get(position).getImage());

        //holder.restaurantImage.setImageResource(R.drawable.ic_dots);
        //holder.restaurantImage.setImageBitmap(restaurantList.get(position).getRestaurantImage());
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        public TextView restaurantName;
        public TextView restaurantPhone;
        public ImageView restaurantImage;

        public RestaurantViewHolder(View view) {
            super(view);
            restaurantName = view.findViewById(R.id.restaurant_name_textview);
            restaurantPhone = view.findViewById(R.id.restaurant_phone_textview);
            restaurantImage = view.findViewById(R.id.restaurant_image);
        }
    }
}

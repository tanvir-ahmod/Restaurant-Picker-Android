package com.example.restaurantpicker.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.restaurantpicker.R;
import com.example.restaurantpicker.Models.Restaurant;

import java.util.List;

/**
 * Created by Shoukhin on 5/30/2018.
 */
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurantList;

    public RestaurantAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
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

        Glide.with(holder.restaurantImage.getContext()).load(restaurantList.get(position).getImage()).into(holder.restaurantImage);

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

package com.example.restaurantpicker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.restaurantpicker.ConfirmOrder;
import com.example.restaurantpicker.Constants;
import com.example.restaurantpicker.Models.Item;
import com.example.restaurantpicker.Models.User;
import com.example.restaurantpicker.R;
import com.example.restaurantpicker.SharedPreferenceManager.SharedPrefManager;

import java.util.List;

/**
 * Created by Shoukhin on 6/1/2018.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> itemList;
    private Context context;
    private boolean visibleRestaurantName = false;

    public ItemAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    //Used to visible / invisible restaurant name in recycler view
    public void setVisibleRestaurantName(boolean visibleRestaurantName) {
        this.visibleRestaurantName = visibleRestaurantName;
    }

    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_view, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, final int position) {
        holder.itemName.setText(itemList.get(position).getName());
        holder.itemPrice.setText("Price : " + itemList.get(position).getPrice() + " tk.");
        String imageURL = Constants.SERVER + itemList.get(position).getImageURL();
        Glide.with(context).load(imageURL).into(holder.itemImage);
        if (visibleRestaurantName) {
            holder.restaurantName.setVisibility(View.VISIBLE);
            holder.restaurantName.setText(itemList.get(position).getRestaurantName());
        } else
            holder.restaurantName.setVisibility(View.GONE);

        holder.submitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ConfirmOrder.class);
                intent.putExtra(Constants.ITEM_ID, itemList.get(position).getId());
                intent.putExtra(Constants.RESTAURANT_NAME, itemList.get(position).getRestaurantName());
                intent.putExtra(Constants.ITEM_NAME, itemList.get(position).getName());
                intent.putExtra(Constants.RESTAURANT_ID, itemList.get(position).getRestaurantID());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView restaurantName;
        public TextView itemName;
        public TextView itemPrice;
        public ImageView itemImage;
        public FloatingActionButton submitOrder;

        public ItemViewHolder(View view) {
            super(view);
            itemName = view.findViewById(R.id.item_name);
            itemPrice = view.findViewById(R.id.item_price);
            itemImage = view.findViewById(R.id.item_image);
            restaurantName = view.findViewById(R.id.restaurant_name);
            submitOrder = view.findViewById(R.id.submit_order_fab);
        }
    }
}

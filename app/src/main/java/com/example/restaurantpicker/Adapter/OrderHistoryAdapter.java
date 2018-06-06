package com.example.restaurantpicker.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.restaurantpicker.Models.OrderHistory;
import com.example.restaurantpicker.R;

import java.util.List;

/**
 * Created by Shoukhin on 6/6/2018.
 */

public class OrderHistoryAdapter extends
        RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {

    private List<OrderHistory> orderHistoryList;
    private Context context;

    public OrderHistoryAdapter(List<OrderHistory> orderHistoryList) {
        this.orderHistoryList = orderHistoryList;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_history_list_view, parent, false);

        return new OrderHistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {
        holder.restaurantNameTextView.setText(orderHistoryList.get(position).getRestaurnatName());
        holder.itemNameTextView.setText(orderHistoryList.get(position).getItemName());
        holder.amountTextView.setText(orderHistoryList.get(position).getAmount());
        holder.priceTextView.setText(orderHistoryList.get(position).getPrice());
        holder.orderTimeTextView.setText(orderHistoryList.get(position).getTimestamp());

    }

    @Override
    public int getItemCount() {
        return orderHistoryList.size();
    }

    public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView restaurantNameTextView;
        TextView itemNameTextView;
        TextView amountTextView;
        TextView priceTextView;
        TextView orderTimeTextView;

        public OrderHistoryViewHolder(View itemView) {
            super(itemView);
            restaurantNameTextView = itemView.findViewById(R.id.restaurant_name_textview);
            itemNameTextView = itemView.findViewById(R.id.item_name_textview);
            amountTextView = itemView.findViewById(R.id.amount_textview);
            priceTextView = itemView.findViewById(R.id.price_textview);
            orderTimeTextView = itemView.findViewById(R.id.order_date_textview);
        }
    }


}

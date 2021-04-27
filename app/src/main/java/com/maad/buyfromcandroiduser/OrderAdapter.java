package com.maad.buyfromcandroiduser;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Activity activity;
    private ArrayList<OrderModel> orders;

    public OrderAdapter(Activity activity, ArrayList<OrderModel> orders) {
        this.activity = activity;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = activity.getLayoutInflater().inflate(R.layout.order_list_item, parent
                , false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, int position) {
        holder.titleTV.setText(orders.get(position).getTitle());
        holder.quantityTV.setText(String.valueOf(orders.get(position).getQuantity()));
        Glide.with(activity).load(orders.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTV;
        public TextView quantityTV;
        public ImageView image;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.tv_title);
            quantityTV = itemView.findViewById(R.id.tv_quantity);
            image = itemView.findViewById(R.id.iv);
        }
    }
}

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

class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Activity activity;
    private ArrayList<ProductModel> products;
    private OnProductClickListener onProductClickListener;

    public ProductAdapter(Activity activity, ArrayList<ProductModel> products) {
        this.activity = activity;
        this.products = products;
    }

    public interface OnProductClickListener{
        void onProductClick(int position);
    }

    public void setOnProductClickListener(OnProductClickListener onProductClickListener) {
        this.onProductClickListener = onProductClickListener;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = activity.getLayoutInflater()
                .inflate(R.layout.product_list_item, parent, false);
        return new ProductViewHolder(view, onProductClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        holder.title.setText(products.get(position).getTitle());
        holder.price.setText(products.get(position).getPrice() + "$");
        Glide
                .with(activity)
                .load(products.get(position).getImage())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;
        public TextView price;

        public ProductViewHolder(@NonNull View itemView
                , OnProductClickListener onProductClickListener) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_product);
            title = itemView.findViewById(R.id.tv_title);
            price = itemView.findViewById(R.id.tv_price_vaue);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onProductClickListener.onProductClick(getAdapterPosition());
                }
            });

        }
    }
}

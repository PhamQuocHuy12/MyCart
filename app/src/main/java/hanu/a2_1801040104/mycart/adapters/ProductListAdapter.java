package hanu.a2_1801040104.mycart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import hanu.a2_1801040104.mycart.R;
import hanu.a2_1801040104.mycart.models.Product;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListHolder> {
    private JSONArray products;

    public ProductListAdapter(JSONArray products) {
        this.products = products;
    }

    @Override
    public int getItemCount() {
        return this.products.length();
    }

    @NonNull
    @Override
    public ProductListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =  parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View productView = layoutInflater.inflate(R.layout.product_view_layout, parent, false);
        return new ProductListHolder(productView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListHolder holder, int position) {
        JSONObject product = null;
        try {
            product = this.products.getJSONObject(position);
            holder.bind(product);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageButton btnAddToCard = holder.view.findViewById(R.id.btnAddToCart);
        btnAddToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class ProductListHolder extends RecyclerView.ViewHolder{
        View view;
        TextView tvDescription, tvPrice;
        public ProductListHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.productName);
            tvPrice = itemView.findViewById(R.id.productUnitPrice);
            view = itemView;
        }

        public void bind(JSONObject product) throws JSONException {
            tvDescription.setText(product.getString("name"));
//            tvPrice.setText(product.getInt("unitPrice"));
        }
    }
}

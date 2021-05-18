package hanu.a2_1801040104.mycart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hanu.a2_1801040104.mycart.R;
import hanu.a2_1801040104.mycart.models.CartItem;


public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartListHolder> {
    private List<CartItem> items;

    public CartListAdapter(List<CartItem> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @NonNull
    @Override
    public CartListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =  parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View cartItemView = layoutInflater.inflate(R.layout.cart_item_view_layout, parent, false);
        return new CartListHolder(cartItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListHolder holder, int position) {
        CartItem cartItem = this.items.get(position);
        holder.bind(cartItem);
    }

    public class CartListHolder extends RecyclerView.ViewHolder{
        private CartItem cartItem;
        private TextView tvName;
        private TextView tvPrice;
        private TextView tvQuantity;

        public CartListHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.cartItemDescription);
            tvPrice = itemView.findViewById(R.id.cartItemPrice);
            tvQuantity = itemView.findViewById(R.id.cartItemQuantity);
        }

        public void bind(CartItem cartItem){
            this.cartItem = cartItem;
            tvName.setText(cartItem.getName());
            tvPrice.setText(Double.toString(cartItem.getPrice()));
            tvQuantity = itemView.findViewById(R.id.cartItemQuantity);
        }
    }
}

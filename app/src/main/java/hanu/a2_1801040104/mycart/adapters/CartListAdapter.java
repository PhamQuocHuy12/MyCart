package hanu.a2_1801040104.mycart.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import hanu.a2_1801040104.mycart.R;
import hanu.a2_1801040104.mycart.activities.TotalCash;
import hanu.a2_1801040104.mycart.database.Database;
import hanu.a2_1801040104.mycart.models.CartItem;


public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartListHolder> {
    private List<CartItem> items;
    private Context context;
    private Bitmap bitmap;
    private Database database;
    private TotalCash listener;

    public CartListAdapter(List<CartItem> items, TotalCash listener) {
        this.items = items;
        this.listener =listener;
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @NonNull
    @Override
    public CartListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context =  parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View cartItemView = layoutInflater.inflate(R.layout.cart_item_view_layout, parent, false);

        return new CartListHolder(cartItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListHolder holder, int position) {
        CartItem cartItem = this.items.get(position);
        try {
            holder.bind(cartItem);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ImageButton increase = holder.itemView.findViewById(R.id.cartItemIncrease);
        ImageButton decrease = holder.itemView.findViewById(R.id.cartItemDecrease);
        database = new Database(context, null, null, 3);
        CartItem item = items.get(position);

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.inscreaseQtyBy1(cartItem.getId());

                item.setQuantity(item.getQuantity()+1);
                item.setTotal(item.getUnitPrice()*item.getQuantity());
                notifyItemChanged(position);
                listener.calculateTotalPrice();
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.decreaseQtyBy1(cartItem.getId());
                if(cartItem.getQuantity()>1){
                    item.setQuantity(item.getQuantity()-1);
                    item.setTotal(item.getUnitPrice()*item.getQuantity());
                notifyItemChanged(position);
                } else {
                    items.remove(position);
                    notifyDataSetChanged();
                }
                listener.calculateTotalPrice();
            }
        });
    }

    public class CartListHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvPrice, tvQuantity, tvTotalPrice;
        private ImageView ivThumbnail;
        public CartListHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.cartItemDescription);
            tvPrice = itemView.findViewById(R.id.cartItemPrice);
            tvQuantity = itemView.findViewById(R.id.cartItemQuantity);
            ivThumbnail = itemView.findViewById(R.id.cartItemImage);
            tvTotalPrice = itemView.findViewById(R.id.cartItemTotal);
        }

        public void bind(CartItem cartItem) throws ExecutionException, InterruptedException {
            tvName.setText(cartItem.getName());
            tvPrice.setText(String.valueOf(cartItem.getUnitPrice()));
            tvQuantity.setText(String.valueOf(cartItem.getQuantity()));
            tvTotalPrice.setText(String.valueOf(cartItem.getTotal()));

            if(cartItem.getThumbnail() != null){
            CartListAdapter.ImageDownload task = new CartListAdapter.ImageDownload();
            bitmap = task.execute(cartItem.getThumbnail()).get();
            ivThumbnail.setImageBitmap(bitmap);
            }
        }
    }

    private class ImageDownload extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                return bitmap;
            }  catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }
    }
}

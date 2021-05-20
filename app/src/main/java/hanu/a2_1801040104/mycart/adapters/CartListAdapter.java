package hanu.a2_1801040104.mycart.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        try {
            holder.bind(cartItem);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class CartListHolder extends RecyclerView.ViewHolder{
        private CartItem cartItem;
        private TextView tvName;
        private TextView tvPrice;
        private TextView tvQuantity;
        private ImageView ivThumbnail;

        public CartListHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.cartItemDescription);
            tvPrice = itemView.findViewById(R.id.cartItemPrice);
            tvQuantity = itemView.findViewById(R.id.cartItemQuantity);
            ivThumbnail = itemView.findViewById(R.id.cartItemImage);
        }

        public void bind(CartItem cartItem) throws ExecutionException, InterruptedException {
            tvName.setText(cartItem.getName());
            tvPrice.setText(String.valueOf(cartItem.getUnitPrice()));
            tvQuantity.setText(String.valueOf(cartItem.getQuantity()));

            if(cartItem.getThumbnail() != null){
            CartListAdapter.ImageDownload task = new CartListAdapter.ImageDownload();
            Bitmap bitmap = task.execute(cartItem.getThumbnail()).get();
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

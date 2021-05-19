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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import org.json.JSONException;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import hanu.a2_1801040104.mycart.R;
import hanu.a2_1801040104.mycart.models.Product;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListHolder> {
    private List<Product> products;

    public ProductListAdapter(List<Product> products) {
        this.products = products;
    }

    @Override
    public int getItemCount() {
        return this.products.size();
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
        Product product = null;
            product = this.products.get(position);
        try {
            holder.bind(product);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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
        TextView tvName, tvPrice;
        ImageView ivThumbnail;
        public ProductListHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.productName);
            tvPrice = itemView.findViewById(R.id.productUnitPrice);
            ivThumbnail = itemView.findViewById(R.id.productThumbnail);
            view = itemView;
        }
        public void bind(Product product) throws ExecutionException, InterruptedException {
            tvPrice.setText(String.valueOf(product.getUnitPrice()));
            tvName.setText(product.getName());

            ImageDownload task = new ImageDownload();
            Bitmap bitmap = task.execute(product.getThumbnail()).get();

            ivThumbnail.setImageBitmap(bitmap);
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

package hanu.a2_1801040104.mycart.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import hanu.a2_1801040104.mycart.R;
import hanu.a2_1801040104.mycart.adapters.ProductListAdapter;
import hanu.a2_1801040104.mycart.models.Product;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    RecyclerView productList;

//    ProductListAdapter productListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();

        try {
            String result = task.execute("https://mpr-cart-api.herokuapp.com/products").get();
            JSONArray productArray = new JSONArray(result);
            List<Product> products = new ArrayList<>();

            for(int i = 0; i<productArray.length(); i++){
                JSONObject productJSON = productArray.getJSONObject(i);
                int id = productJSON.getInt("id");
                String thumbnail = productJSON.getString("thumbnail");
                String name = productJSON.getString("name");
                int unitPrice = productJSON.getInt("unitPrice");
                Product product = new Product(id, thumbnail,name,unitPrice);
                products.add(product);

                productList = findViewById(R.id.productList);
                productList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                ProductListAdapter productListAdapter = new ProductListAdapter(products);
                productList.setAdapter(productListAdapter);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_cart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btnViewCart) {
            startActivity(new Intent(this, CartActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    public class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream is = urlConnection.getInputStream();
                Scanner sc = new Scanner(is);
                StringBuilder result = new StringBuilder();
                String line;
                while (sc.hasNextLine()) {
                    line = sc.nextLine();
                    result.append(line);
                }
                return result.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result == null) {
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                return;
            }
        }
    }
}
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

public class MainActivity extends AppCompatActivity {
    RecyclerView productList;

//    ProductListAdapter productListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        task.execute("https://mpr-cart-api.herokuapp.com/products");
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
            try {
                List<Product> demoList = new ArrayList<>();
                demoList.add(new Product(1, null, "product1", 15000));
                demoList.add(new Product(2, null, "product1", 15000));
                demoList.add(new Product(3, null, "product1", 15000));

                TextView tvApi = findViewById(R.id.testApi);
                tvApi.setText(result);

                JSONArray productArray = new JSONArray(result);
                productArray.getJSONObject(0);

                productList = findViewById(R.id.productList);
                productList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                ProductListAdapter productListAdapter = new ProductListAdapter(productArray);
                productList.setAdapter(productListAdapter);

            } catch (Exception e){
                throw new IllegalStateException("error");
            }
        }
    }
}
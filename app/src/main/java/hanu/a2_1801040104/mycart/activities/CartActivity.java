package hanu.a2_1801040104.mycart.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import hanu.a2_1801040104.mycart.R;
import hanu.a2_1801040104.mycart.adapters.CartListAdapter;
import hanu.a2_1801040104.mycart.database.Database;
import hanu.a2_1801040104.mycart.models.CartItem;

public class CartActivity extends AppCompatActivity {
    RecyclerView cartView;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        List<CartItem> cart = new ArrayList<>();
        database = new Database(this, null, null, 3);
        cart = database.getAll();

        List<CartItem> demoCart = new ArrayList<>();
        demoCart.add(new CartItem(1, null, "name", 5, 2000));

        cartView = findViewById(R.id.cartItemList);
        cartView.setLayoutManager(new LinearLayoutManager(this));
        CartListAdapter cartListAdapter = new CartListAdapter(cart);
        cartView.setAdapter(cartListAdapter);
    }
}
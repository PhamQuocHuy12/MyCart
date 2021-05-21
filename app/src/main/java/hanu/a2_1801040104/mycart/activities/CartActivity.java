package hanu.a2_1801040104.mycart.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hanu.a2_1801040104.mycart.R;
import hanu.a2_1801040104.mycart.adapters.CartListAdapter;
import hanu.a2_1801040104.mycart.database.Database;
import hanu.a2_1801040104.mycart.models.CartItem;

public class CartActivity extends AppCompatActivity implements TotalCash {
    RecyclerView cartView;
    Database database;
    TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        List<CartItem> cart = new ArrayList<>();
        database = new Database(this, null, null, 3);
        cart = database.getAll();

        cartView = findViewById(R.id.cartItemList);
        cartView.setLayoutManager(new LinearLayoutManager(this));
        CartListAdapter cartListAdapter = new CartListAdapter(cart, this);
        cartView.setAdapter(cartListAdapter);
        tvTotal = findViewById(R.id.totalPrice);

        calculateTotalPrice();
    }

    public void calculateTotalPrice() {
        tvTotal.setText(String.valueOf(database.sumPrice()));
    }
}
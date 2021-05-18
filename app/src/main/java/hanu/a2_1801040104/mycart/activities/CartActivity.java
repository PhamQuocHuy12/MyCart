package hanu.a2_1801040104.mycart.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import hanu.a2_1801040104.mycart.R;
import hanu.a2_1801040104.mycart.adapters.CartListAdapter;
import hanu.a2_1801040104.mycart.models.CartItem;

public class CartActivity extends AppCompatActivity {
    RecyclerView cartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        List<CartItem> demoCart = new ArrayList<>();
        demoCart.add(new CartItem(1,null, "product1", 2, 15000));
        demoCart.add(new CartItem(2,null, "product2", 2, 15000 ));
        demoCart.add(new CartItem(3,null, "product3", 2, 15000 ));

        cartView = findViewById(R.id.cartItemList);
        cartView.setLayoutManager(new LinearLayoutManager(this));
        CartListAdapter cartListAdapter = new CartListAdapter(demoCart);
        cartView.setAdapter(cartListAdapter);

    }
}
package com.birzeit.group_assignment_2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.birzeit.group_assignment_2.Models.Cart;
import com.birzeit.group_assignment_2.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BillActivity extends AppCompatActivity {

    TextView title_txt, price_txt, tax_txt, total_txt;
    private List<Double> prices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        setupViews();
        loadData();
    }

    private void setupViews() {
        title_txt = findViewById(R.id.title_txt);
        price_txt = findViewById(R.id.price_txt);
        tax_txt = findViewById(R.id.tax_txt);
        total_txt = findViewById(R.id.total_txt);
    }

        private void setTexts(){

            double total = calculate();

            price_txt.setText(total + " ₪");

            double tax = 0.0;
            tax = total * 0.14;

            DecimalFormat df2 = new DecimalFormat("#.##");

            tax_txt.setText(df2.format(tax) + " ₪");

            double totalPrice = 0.0;
            totalPrice = total + tax;

            total_txt.setText(df2.format(totalPrice) + " ₪");
        }

        private double calculate(){

            double total = 0.0;

            for (int i = 0; i < cartList.size(); i++){
                String price = cartList.get(i).getProductPrice();
                String[] str = price.split(" ");

                prices.add(Double.parseDouble(str[0]));
            }

            for (int i = 0; i < prices.size(); i++){
                total += prices.get(i);
            }
            Toast.makeText(getApplicationContext(),"Hello " + total, Toast.LENGTH_SHORT).show();

            return total;
        }


    private List<Cart> cartList = new ArrayList<>();

    private void loadData() {

        String url = "http://192.168.1.28:80/GroupAss2/getCart.php";
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ja = response.getJSONArray("result");

                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jsonObject = ja.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String productName = jsonObject.getString("productName");
                                String productPrice = jsonObject.getString("productPrice");
                                String productPhoto = jsonObject.getString("productPhoto");

                                cartList.add(new Cart(id, productName, productPrice, productPhoto));
                            }
                            title_txt.setText("There is " + String.valueOf(cartList.size()) + " products on the cart");
                            setTexts();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error");
            }
        });
        com.example.groupassigment1.MySingleton.getInstance(this).addToRequestQueue(jor);
    }

    public void backBtnAction(View view) {
        Intent intent = new Intent(this,CategoryListActivity.class);
        startActivity(intent);
    }

    public void cartBtnAction(View view) {
        Intent intent = new Intent(this,CartListActivity.class);
        startActivity(intent);
    }
}
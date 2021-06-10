package com.birzeit.group_assignment_2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.birzeit.group_assignment_2.Adapters.Adapter_ItemList;
import com.birzeit.group_assignment_2.Models.Shoes;
import com.birzeit.group_assignment_2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends AppCompatActivity {

    private List<Shoes> shoesList = new ArrayList<>();
    public RecyclerView recyclerView;
    public EditText searchView;
    Adapter_ItemList adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        setupViews();
        loadData();

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    private void filter(String text) {

        ArrayList<Shoes> filterList = new ArrayList<>();
        for (Shoes item : shoesList)
        {
            if (item.getBrand().toLowerCase().contains(text.toLowerCase()))
            {
                filterList.add(item);
            }
        }
        adapter.filteredList(filterList);
    }
    private void setupViews(){
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
    }
    public String category = "";

    private void loadData() {

        final String[] category = {getIntent().getStringExtra("Category")};
        String url = "http://192.168.1.28:80/GroupAss2/getItemList.php?cat="+ category[0];
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ja = response.getJSONArray("result");

                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jsonObject = ja.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String brand = jsonObject.getString("brand");
                                category[0] = jsonObject.getString("categoryName");
                                String price = jsonObject.getString("price");
                                String rate = jsonObject.getString("rate");
                                String photo = jsonObject.getString("photo");

                                shoesList.add(new Shoes(id,name,brand, category[0],price,rate,photo));
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(ItemListActivity.this));
                            adapter = new Adapter_ItemList(ItemListActivity.this, shoesList);
                            recyclerView.setAdapter(adapter);
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

    public void cartBtnAction(View view) {
        Intent intent = new Intent(this,CartListActivity.class);
        intent.putExtra("Category", category);
        startActivity(intent);
    }

    public void backBtnAction(View view) {
        Intent intent = new Intent(this,CategoryListActivity.class);
        intent.putExtra("Category", category);
        startActivity(intent);
    }
}
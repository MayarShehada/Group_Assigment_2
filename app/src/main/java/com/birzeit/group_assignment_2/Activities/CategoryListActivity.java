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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.birzeit.group_assignment_2.Adapters.Adapter_CategoryList;
import com.birzeit.group_assignment_2.Models.Cart;
import com.birzeit.group_assignment_2.Models.Category;
import com.birzeit.group_assignment_2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryListActivity extends AppCompatActivity {

    private List<Category> categories = new ArrayList<>();

    public RecyclerView recyclerView;
    String url = "http://192.168.1.28:80/GroupAss2/getCategoryList.php";
    Adapter_CategoryList adapter;

    TextView counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        setupViews();
        loadData();
    }

    private void setupViews(){
        recyclerView = findViewById(R.id.category);
    }

    String categoryName;
    private void loadData() {
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ja = response.getJSONArray("result");

                            for (int i = 0; i < ja.length(); i++) {

                                JSONObject jsonObject = ja.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                categoryName = jsonObject.getString("categoryName");
                                String categoryPhoto = jsonObject.getString("categoryPhoto");

                                categories.add(new Category(id, categoryName, categoryPhoto));

                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(CategoryListActivity.this));
                            adapter = new Adapter_CategoryList(CategoryListActivity.this, categories);
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
        intent.putExtra("Category", categoryName);
        startActivity(intent);
    }
}
package com.birzeit.group_assignment_2.Activities;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.birzeit.group_assignment_2.Models.Cart;
import com.birzeit.group_assignment_2.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class DetailesShoeActivity extends AppCompatActivity {

    String id = "", name = "", brand = "", categoryName = "", price = "", rate = "", photo = "", size = "", color = "";
    int size_item = 0, color_item = 0;
    TextView name_txt, brand_txt, price_txt, counter;
    TextView rate1, rate2, rate3, rate4, rate5;
    ImageView photo_img;
    Spinner size_spinner, color_spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailes_shoe);

        id = getIntent().getStringExtra("id_data");
        name = getIntent().getStringExtra("name_data");
        brand = getIntent().getStringExtra("brand_data");
        categoryName = getIntent().getStringExtra("category_data");
        price = getIntent().getStringExtra("price_data");
        rate = getIntent().getStringExtra("rate_data");
        photo = getIntent().getStringExtra("photo_data");

        setupViews();

        populateSpinner();
    }

    public void setupViews(){
        name_txt = findViewById(R.id.name_txt);
        brand_txt = findViewById(R.id.brand_txt);
        price_txt = findViewById(R.id.total_txt);
        photo_img = findViewById(R.id.photo_img);
        size_spinner = findViewById(R.id.size_spinner);
        color_spinner = findViewById(R.id.color_spinner);

        rate1 = findViewById(R.id.rate1);
        rate2 = findViewById(R.id.rate2);
        rate3 = findViewById(R.id.rate3);
        rate4 = findViewById(R.id.rate4);
        rate5 = findViewById(R.id.rate5);

        name_txt.setText(name);
        brand_txt.setText(brand);
        price_txt.setText(price);
        setRate();
        Picasso.get().load(photo).into(photo_img);
    }

    private void populateSpinner() {

        ArrayAdapter<String> color_adapt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.colors));
        color_spinner.setAdapter(color_adapt);

        ArrayList<String> sizes = new ArrayList<>();

        if(categoryName.equals("Kids Shoes")) {
            for (int i = 20; i < 35; i++) {
                sizes.add(String.valueOf(i));
            }
        }else if(categoryName.equals("Womens Shoes")) {
            for (int i = 35; i < 42; i++) {
                sizes.add(String.valueOf(i));
            }
        }else if(categoryName.equals("Mens Shoes")) {
            for (int i = 35; i < 45; i++) {
                sizes.add(String.valueOf(i));
            }
        }

        ArrayAdapter<String> size_adapt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sizes);
        size_spinner.setAdapter(size_adapt);
    }

    public void setRate(){

        int rat = Integer.parseInt(rate);

        if(rat == 1){
            rate1.setBackgroundResource(R.drawable.gold_star);
        }else if(rat == 2){
            rate1.setBackgroundResource(R.drawable.gold_star);
            rate2.setBackgroundResource(R.drawable.gold_star);
        }else if(rat == 3){
            rate1.setBackgroundResource(R.drawable.gold_star);
            rate2.setBackgroundResource(R.drawable.gold_star);
            rate3.setBackgroundResource(R.drawable.gold_star);
        }else if(rat == 4){
            rate1.setBackgroundResource(R.drawable.gold_star);
            rate2.setBackgroundResource(R.drawable.gold_star);
            rate3.setBackgroundResource(R.drawable.gold_star);
            rate4.setBackgroundResource(R.drawable.gold_star);
        }else if(rat == 5){
            rate1.setBackgroundResource(R.drawable.gold_star);
            rate2.setBackgroundResource(R.drawable.gold_star);
            rate3.setBackgroundResource(R.drawable.gold_star);
            rate4.setBackgroundResource(R.drawable.gold_star);
            rate5.setBackgroundResource(R.drawable.gold_star);
        }
    }

    public void add_to_cartOnClick(View view) {

        String restUrl = "http://192.168.1.28:80/GroupAss2/addToCart.php";
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else{
            DetailesShoeActivity.SendPostRequest runner = new SendPostRequest();
            runner.execute(restUrl);
        }

        size = size_spinner.getSelectedItem().toString();
        size_item = size_spinner.getSelectedItemPosition();

        color = color_spinner.getSelectedItem().toString();
        color_item = color_spinner.getSelectedItemPosition();

        Intent intent = new Intent(this,ItemListActivity.class);
        intent.putExtra("Category", categoryName);
        startActivity(intent);
    }

    private String processRequest(String restUrl) throws UnsupportedEncodingException {


        String data = URLEncoder.encode("productName", "UTF-8")
                + "=" + URLEncoder.encode(brand, "UTF-8");

        data += "&" + URLEncoder.encode("productPrice", "UTF-8") + "="
                + URLEncoder.encode(price, "UTF-8");

        data += "&" + URLEncoder.encode("productPhoto", "UTF-8") + "="
                + URLEncoder.encode(photo, "UTF-8");

        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL(restUrl);

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally{
            if ((reader != null)) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Show response on activity
        return text;
    }

    public void backBtnAction(View view) {
        Intent intent = new Intent(this,ItemListActivity.class);
        intent.putExtra("Category", categoryName);
        startActivity(intent);
    }

    public void cartBtnAction(View view) {
        Intent intent = new Intent(this,CartListActivity.class);
        intent.putExtra("Category", categoryName);
        startActivity(intent);
    }

    public class SendPostRequest extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                return processRequest(strings[0]);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {

        }
    }
}

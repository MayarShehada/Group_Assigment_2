package com.birzeit.group_assignment_2.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.birzeit.group_assignment_2.Activities.CartListActivity;
import com.birzeit.group_assignment_2.Models.Cart;
import com.birzeit.group_assignment_2.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Adapter_CartList extends RecyclerView.Adapter<Adapter_CartList.ViewHolder> {

    private Context context;
    private List<Cart> cartList;
    private Button delete_btn ;
    private  String id= "";

    public Adapter_CartList(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    public Adapter_CartList() {

    }

    public void filteredList(ArrayList<Cart> filterList) {
        cartList = filterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Adapter_CartList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_CartList.ViewHolder holder, int position) {

        Cart cart = cartList.get(position);
        CardView cardView = holder.cardView;

        ImageView image = cardView.findViewById(R.id.imageView);
        Picasso.get().load(cart.getProductPhoto()).into(image);

        TextView price_txt = cardView.findViewById(R.id.total_txt);
        price_txt.setText(cart.getProductPrice());

        TextView name_txt = cardView.findViewById(R.id.brand_txt);
        name_txt.setText(cart.getProductName());

        delete_btn=cardView.findViewById(R.id.delete_btn);

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String restUrl = "http://192.168.1.28:80/GroupAss2/deleteProduct.php";
                id=cart.getId();
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.INTERNET}, 123);

                } else{
                    Adapter_CartList.SendPostRequest runner = new SendPostRequest();
                    runner.execute(restUrl);
                }
                Intent intent =new Intent(context, CartListActivity.class);
                context.startActivity(intent);
            }

        });

    }

    @Override
    public int getItemCount() {

        return cartList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull CardView cardView) {
            super(cardView);
            this.cardView = cardView;
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
    private String processRequest(String restUrl) throws UnsupportedEncodingException {

        String data = URLEncoder.encode("id", "UTF-8")
                + "=" + URLEncoder.encode(id+"", "UTF-8");

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

    private class SendPostRequest extends AsyncTask<String, Void, String> {

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
            super.onPostExecute(s);
        }
    }
}

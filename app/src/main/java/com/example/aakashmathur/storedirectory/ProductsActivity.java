package com.example.aakashmathur.storedirectory;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ProductsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_products);


            String storeName = getIntent().getStringExtra("StoreName");
            String storeLocation = getIntent().getStringExtra("StoreLocation");

            //Toast.makeText(this, storeName, Toast.LENGTH_LONG).show();


            //Set Header as Store Name - Store Location:
            TextView tv = (TextView)findViewById(R.id.textView2);
            tv.setText(storeName + " - " + storeLocation);

            String fileName = getIntent().getStringExtra("ProductFileName");
            ArrayList<Product> listOfProducts = ReadProductsFromTextFile(fileName);

            ProductAdaptor adapter = new ProductAdaptor(this, R.layout.item_product, listOfProducts);
            ListView listView = (ListView)findViewById(R.id.listView2);
            listView.setAdapter(adapter);

            if(getActionBar() != null){
                getActionBar().setDisplayHomeAsUpEnabled(true);
            }

        }
        catch(Exception e){
            String message = e.getMessage();
        }
    }

    private ArrayList<Product> ReadProductsFromTextFile(String fileName){
        ArrayList<Product> al = null;
        try {
            al = new ArrayList<Product>();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(getAssets().open(fileName), "UTF-8"));
            String nextLine = null;
            while((nextLine = br.readLine()) != null){
                Product product = new Product();
                product.name = nextLine;
                product.aisle = br.readLine();

                al.add(product);
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "From File Not Found Exception.",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "From IO Exception.",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return al;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_products, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void viewLayoutClick(View view){
        Toast.makeText(this,"Button view layout clicked.", Toast.LENGTH_LONG).show();
    }
}

package com.example.aakashmathur.storedirectory;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class ProductsActivity extends ActionBarActivity {

    private ArrayList<Product> listOfProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_products);


            String storeName = getIntent().getStringExtra("StoreName");
            String storeLocation = getIntent().getStringExtra("StoreLocation");

            //Set Header as Store Name - Store Location:
            TextView tv = (TextView)findViewById(R.id.textView2);
            tv.setText(storeName + " - " + storeLocation);

            String fileName = getIntent().getStringExtra("ProductFileName");
            listOfProducts = ReadProductsFromTextFile(fileName);

            Collections.sort(listOfProducts);

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
                Product product = new Product(nextLine, br.readLine(), false);
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

    public void checkBoxClicked(View senderView){

        View view = (View)senderView.getParent();
        TextView tv = (TextView)view.findViewById(R.id.textViewProduct);
        String storeSelected = (String) tv.getText();

        tv = (TextView)view.findViewById(R.id.textViewAisle);
        String aisleSelected = (String) tv.getText();

        for(Product p : listOfProducts){
            if( (p.name.equals(storeSelected)) && (p.aisle.equals(aisleSelected))) {
                p.selected = !p.selected;
            }
        }

        //Toast.makeText(this, storeSelected, Toast.LENGTH_LONG).show();


        //adapter.notifyDataSetChanged();

        ListView listView = (ListView)findViewById(R.id.listView2);

        ProductAdaptor a = ((ProductAdaptor)listView.getAdapter());
        ArrayList<Product> sortedList = sort();
        a.clear();
        a.addAll(sortedList);
        a.notifyDataSetChanged();
        listView.invalidateViews();
        listView.refreshDrawableState();

    }

    public ArrayList<Product> sort(){

        ArrayList<Product> selectedProducts = new ArrayList<>();
        ArrayList<Product> unselectedProducts = new ArrayList<>();

        for(Product p: listOfProducts){
            if(p.selected){
                selectedProducts.add(new Product(p.name, p.aisle, true));
            }
            else{
                unselectedProducts.add(new Product(p.name, p.aisle, false));
            }
        }

        Collections.sort(selectedProducts);
        Collections.sort(unselectedProducts);

        int count = listOfProducts.size();
        ArrayList<Product> sortedListOfProducts = new ArrayList<Product>();
        count = sortedListOfProducts.size();
        sortedListOfProducts.addAll(selectedProducts);
        count = sortedListOfProducts.size();
        sortedListOfProducts.addAll(unselectedProducts);
        count = sortedListOfProducts.size();

        return sortedListOfProducts;
    }

}

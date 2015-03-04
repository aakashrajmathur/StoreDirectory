//Suggestion for App name - Quick Shopper, credit Akshata Mathur (21st Feb 2015)


package com.example.aakashmathur.storedirectory;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class StoresActivity extends ActionBarActivity {

    Intent intent = null;
    ArrayList<Store> listOfStores = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);

        intent = new Intent(this, ProductsActivity.class);


        listOfStores = ReadStoresFromTextFile("StoresMetaInfo.txt");

//        final ArrayList<String> listOfStoreNames = new ArrayList<>(listOfStores.size());
//
//        for(Store s: listOfStores){
//            listOfStoreNames.add(s.storeName);
//        }
//
//        ArrayAdapter<String> adapter =
//            //new ArrayAdapter<String>( this, android.R.layout.simple_list_item_2, listOfStores );
//            new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listOfStoreNames);
        ListView listView = (ListView)findViewById(R.id.listView);

        StoreAdaptor sa = new StoreAdaptor(this, R.layout.item_store, listOfStores);

        listView.setAdapter(sa);


        intent = new Intent(this, ProductsActivity.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position + " which is : " + listOfStores.get(position), Toast.LENGTH_LONG)
                        .show();
                intent.putExtra("StoreName", listOfStores.get(position).storeName);
                intent.putExtra("StoreLocation", listOfStores.get(position).storeLocation);
                intent.putExtra("ProductFileName", listOfStores.get(position).productFileName);
                startActivity(intent);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stores, menu);
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

        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Store> ReadStoresFromTextFile(String fileName){
        ArrayList<Store> al = null;
        try {
            al = new ArrayList<Store>();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(getAssets().open(fileName), "UTF-8"));
            String nextLine = null;

            while((nextLine = br.readLine()) != null) {

                Store store = new Store();
                store.storeName = nextLine;
                store.storeLocation = br.readLine();
                store.addressLine1 = br.readLine();
                store.addressLine2 = br.readLine();
                store.gPSCoordinatesLat = br.readLine();
                store.gPSCoordinatesLong = br.readLine();
                store.productFileName = br.readLine();
                store.mapFileNameLine1 = br.readLine();
                store.mapFileNameLine2 = br.readLine();
                store.mapFileNameLine3 = br.readLine();
                store.hoursMondayOpen = br.readLine();
                store.hoursMondayClose = br.readLine();
                store.hoursTuesdayOpen = br.readLine();
                store.hoursTuesdayClose = br.readLine();
                store.hoursWednesdayOpen = br.readLine();
                store.hoursWednesdayClose = br.readLine();
                store.hoursThursdayOpen = br.readLine();
                store.hoursThursdayClose = br.readLine();
                store.hoursFridayOpen = br.readLine();
                store.hoursFridayClose = br.readLine();
                store.hoursSaturdayOpen = br.readLine();
                store.hoursSaturdayClose = br.readLine();
                store.hoursSundayOpen = br.readLine();
                store.hoursSundayClose = br.readLine();
                store.contactNumber = br.readLine();
                store.reservedLine1 = br.readLine();
                store.reservedLine2 = br.readLine();
                store.reservedLine3 = br.readLine();
                store.reservedLine4 = br.readLine();
                store.website = br.readLine();

                al.add(store);

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
}

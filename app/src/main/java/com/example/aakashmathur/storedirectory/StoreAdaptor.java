package com.example.aakashmathur.storedirectory;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.params.HttpProtocolParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by aakashmathur on 2/14/15.
 */
public class StoreAdaptor extends ArrayAdapter<Store> {
    private Context context;
    private List<Store> stores;

    public StoreAdaptor(Context context, int resource, List<Store> stores) {
        super(context, resource, stores);
        this.context = context;
        this.stores = stores;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Store store = stores.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_store, null);

        TextView tv = (TextView)view.findViewById(R.id.textViewStoreName);
        tv.setText(store.storeName);

        tv = (TextView)view.findViewById(R.id.textViewStoreLocation);
        tv.setText(store.storeLocation);

        //textViewContact
        tv = (TextView)view.findViewById(R.id.textViewContact);
        tv.setText(store.contactNumber);

        if(store.storeName.equals("Store Name")){
            view.setBackgroundColor(Color.YELLOW);
        }
        else {
            if (position % 2 == 0) {

                if (isStoreOpenNow(stores.get(position))) {
                    view.setBackgroundColor(Color.rgb(240, 255, 240)); //GREEN);
                    ((TextView)view.findViewById(R.id.textViewHours)).setText("Open");
                } else {
                    view.setBackgroundColor(Color.rgb(255, 128, 128)); //RED);
                    ((TextView)view.findViewById(R.id.textViewHours)).setText("Closed");
                }
            } else {
                if (isStoreOpenNow(stores.get(position))) {
                    view.setBackgroundColor(Color.rgb(245, 255, 245)); //LT GREEN);
                    ((TextView)view.findViewById(R.id.textViewHours)).setText("Open");
                } else {
                    view.setBackgroundColor(Color.rgb(255, 205, 205)); //LIGHT RED
                    ((TextView)view.findViewById(R.id.textViewHours)).setText("Closed");
                }
            }
        }

        return view;
    }

    private boolean isStoreOpenNow(Store store) {

        if(store.storeName.equals("Store Name")){
            return true;
        }

        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

        Calendar calendar = Calendar.getInstance();
        weekDay = dayFormat.format(calendar.getTime());

        //Toast.makeText( context, weekDay, Toast.LENGTH_LONG).show();

        String openTime = null;
        String closeTime = null;
        if(weekDay.equals("Monday")){
            openTime = store.hoursMondayOpen;
            closeTime = store.hoursMondayClose;
        }
        else if(weekDay.equals("Tuesday")){
            openTime = store.hoursTuesdayOpen;
            closeTime = store.hoursTuesdayClose;
        }
        else if(weekDay.equals("Wednesday")){
            openTime = store.hoursWednesdayOpen;
            closeTime = store.hoursWednesdayClose;
        }
        else if(weekDay.equals("Thursday")){
            openTime = store.hoursThursdayOpen;
            closeTime = store.hoursThursdayClose;
        }
        else if(weekDay.equals("Friday")){
            openTime = store.hoursFridayOpen;
            closeTime = store.hoursFridayClose;
        }
        else if(weekDay.equals("Saturday")){
            openTime = store.hoursSaturdayOpen;
            closeTime = store.hoursSaturdayClose;
        }
        else if(weekDay.equals("Sunday")){
            openTime = store.hoursSundayOpen;
            closeTime = store.hoursSundayClose;
        }
        else{
            openTime = store.hoursSundayOpen;
            closeTime = store.hoursSundayClose;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        Date openTimeDate = null;
        Date closeTimeDate = null;
        try {
            openTimeDate = dateFormat.parse(openTime);
            closeTimeDate = dateFormat.parse(closeTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date currentTime = null;
        try {
            currentTime = dateFormat.parse(dateFormat.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if ( currentTime.before(openTimeDate) || currentTime.after(closeTimeDate))
        {
            return false;
        }
        else {
            return true;
        }
    }
}

package com.example.aakashmathur.storedirectory;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aakashmathur on 2/14/15.
 */
public class ProductAdaptor extends ArrayAdapter {

    Context context;
    List<Product> products;
    public ProductAdaptor(Context context, int resource, List<Product> objects) {
        super(context, resource, objects);

        this.context = context;
        this.products = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Product product = products.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_product, null);

        TextView tv = (TextView)view.findViewById(R.id.textViewProduct);
        tv.setText(product.name);

        tv = (TextView)view.findViewById(R.id.textViewAisle);
        tv.setText(product.aisle);

        CheckBox cb = (CheckBox)view.findViewById(R.id.checkBox1);
        cb.setChecked(product.selected);

        //Background color

        if((position % 2) == 0) {
            view.setBackgroundColor(Color.rgb(236, 240, 241));//Cloud
        }else{
            view.setBackgroundColor(Color.rgb(189, 195, 199));//Silver
        }

        return view;
    }
}

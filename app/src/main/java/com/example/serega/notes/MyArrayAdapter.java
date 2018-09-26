package com.example.serega.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.serega.notes.JSON.Item.Item;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<Item> {
    private final Context context;
    private final List<Item> item;

    public MyArrayAdapter(Context context, List<Item> item) {
        super(context, R.layout.row_layout, item);
        this.context = context;
        this.item = item;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.tvList);
        textView.setText(item.get(position).toString());

        String s = item.get(position).toString();
        if (s.startsWith(item.get(position).getName())){
            if (item.get(position).getColor().startsWith("purple")) {
                textView.setBackgroundResource(android.R.color.holo_purple);
            }
            if (item.get(position).getColor().startsWith("green")){
                textView.setBackgroundResource(android.R.color.holo_green_light);
            }
            if (item.get(position).getColor().startsWith("blue")){
                textView.setBackgroundResource(android.R.color.holo_blue_light);
            }
            if (item.get(position).getColor().startsWith("yellow")){
                textView.setBackgroundResource(android.R.color.holo_orange_light);
            }
            if (item.get(position).getColor().startsWith("gray")){
                textView.setBackgroundResource(android.R.color.darker_gray);
            }
            if (item.get(position).getColor().startsWith("red")){
                textView.setBackgroundResource(android.R.color.holo_red_light);
            }
            if (item.get(position).getColor().startsWith("orange")){
                textView.setBackgroundResource(android.R.color.holo_orange_dark);
            }
        }

        return rowView;
    }
}
package com.example.serega.notes.JSON;


import com.example.serega.notes.DB.DatabaseAdapter;
import com.example.serega.notes.JSON.Item.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class JSONReader {
    private static final String REGEX_INPUT_BOUNDARY_BEGINNING = "\\A";
    List<Item> items;

    public List<Item> read(InputStream inputStream, DatabaseAdapter adapter ) throws JSONException {
        String json = new Scanner(inputStream).useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            long itemId = object.getLong("id");
            String name = object.getString("name");
            String description = object.getString("description");
            String color = object.getString("color");
            Item item = new Item(itemId, name, description, color);
            adapter.insert(item);
        }
        items = adapter.getItems();
        return items;
    }

}

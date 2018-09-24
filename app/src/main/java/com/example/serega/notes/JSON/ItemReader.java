package com.example.serega.notes.JSON;

import com.example.serega.notes.JSON.Item.Item;
import com.example.serega.notes.JSON.Item.ItemDescription;
import com.example.serega.notes.JSON.Item.ItemName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ItemReader {
    private static final String REGEX_INPUT_BOUNDARY_BEGINNING = "\\A";

    public List<ItemName> readName(InputStream inputStream) throws JSONException {
        List<ItemName> itemsName = new ArrayList<ItemName>();
        String json1 = new Scanner(inputStream).useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next();
        JSONArray array1 = new JSONArray(json1);
        for (int i = 0; i < array1.length(); i++) {
            JSONObject object = array1.getJSONObject(i);
            String name = object.getString("name");
            itemsName.add(new ItemName(name));
        }
        return itemsName;
    }

    public List<ItemDescription> readDescription(InputStream inputStream) throws JSONException {
        List<ItemDescription> itemsDescription = new ArrayList<ItemDescription>();
        String json2 = new Scanner(inputStream).useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next();
        JSONArray array2 = new JSONArray(json2);
        for (int i = 0; i < array2.length(); i++) {
            JSONObject object = array2.getJSONObject(i);
            String description = object.getString("description");
            itemsDescription.add(new ItemDescription(description));
        }
        return itemsDescription;
    }



}

package com.example.serega.notes.JSON;

import android.content.Context;

import com.example.serega.notes.JSON.Item.Item;
import com.example.serega.notes.JSON.Item.ItemDescription;
import com.example.serega.notes.JSON.Item.ItemName;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class JSONHelper {
    private static final String FILE_NAME = "dataName.json";
    private static final String FILE_Description = "dataDescription.json";

    public static boolean exportToJSON(Context context, List<ItemName> dataListName, List<ItemDescription> dataListDescription) {

        Gson gson = new Gson();
        DataItemsName dataItems = new DataItemsName();
        DataItemDescription dataItemDescription = new DataItemDescription();
        dataItems.setItemsName(dataListName);
        dataItemDescription.setItemDescription(dataListDescription);
        String jsonString = gson.toJson(dataItems);
        String jsonStringDescription = gson.toJson(dataItemDescription);

        FileOutputStream fileOutputStreamName = null;
        FileOutputStream fileOutputStreamDescription = null;

        try {
            fileOutputStreamName = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fileOutputStreamName.write(jsonString.getBytes());
            fileOutputStreamDescription = context.openFileOutput(FILE_Description, Context.MODE_PRIVATE);
            fileOutputStreamDescription.write(jsonStringDescription.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStreamName != null && fileOutputStreamDescription != null) {
                try {
                    fileOutputStreamName.close();
                    fileOutputStreamDescription.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    public static List<ItemName> importNameFromJSON(Context context) {

        InputStreamReader streamReader = null;
        FileInputStream fileInputStream = null;
        try{
            fileInputStream = context.openFileInput(FILE_NAME);
            streamReader = new InputStreamReader(fileInputStream);
            Gson gson = new Gson();
            DataItemsName dataItems = gson.fromJson(streamReader, DataItemsName.class);
            return  dataItems.getItemsName();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public static List<ItemDescription> importDescriptionFromJSON(Context context) {

        InputStreamReader streamReader = null;
        FileInputStream fileInputStream = null;
        try{
            fileInputStream = context.openFileInput(FILE_Description);
            streamReader = new InputStreamReader(fileInputStream);
            Gson gson = new Gson();
            DataItemDescription dataItems = gson.fromJson(streamReader, DataItemDescription.class);
            return  dataItems.getItemDescription();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /*private static class DataItems {
        private List<Item> items;

        List<Item> getItems() {
            return items;
        }
        void setItems(List<Item> items) {
            this.items = items;
        }
    }*/

    private static class DataItemsName {
        private List<ItemName> itemsName;

        List<ItemName> getItemsName() {
            return itemsName;
        }
        void setItemsName(List<ItemName> itemsName) {
            this.itemsName = itemsName;
        }
    }
    private static class DataItemDescription {
        private List<ItemDescription> ItemDescription;

        List<ItemDescription> getItemDescription() {
            return ItemDescription;
        }
        void setItemDescription(List<ItemDescription> ItemDescription) {
            this.ItemDescription = ItemDescription;
        }
    }
}

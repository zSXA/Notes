package com.example.serega.notes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.serega.notes.JSON.Item.Item;
import com.example.serega.notes.JSON.Item.ItemDescription;
import com.example.serega.notes.JSON.Item.ItemName;
import com.example.serega.notes.JSON.ItemReader;
import com.example.serega.notes.JSON.JSONHelper;

import org.json.JSONException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final int MENU_CHANGE = 1;
    final int MENU_DELETE = 2;
    public static final String ACTION_NOTE = "com.example.serega.notes.Note";
    public static final String ACTION_DESCRIPTION = "com.example.serega.notes.NoteDescription";
    private static final String FILE_NAME = "dataName.json";
    //ArrayAdapter<Item> adapter;
    //List<Item> items, itemsData, itemsReadData;

    ArrayAdapter<ItemName> adapterName;
    List<ItemName> itemsName, itemsDataName;

    ArrayAdapter<ItemDescription> adapterDescription;
    List<ItemDescription> itemDescription, itemDataDescriptions;

    ListView lvList;
    TextView textView;

    AdapterView.AdapterContextMenuInfo acmi;
    Item noteItem;
    ItemName noteItemName;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ACTION_NOTE);
                startActivityForResult(intent, 1);
            }
        });

        lvList = (ListView) findViewById(R.id.lvList);
        registerForContextMenu(lvList);
        try {
            open();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapterName = new ArrayAdapter<>(this, R.layout.simple_list_item, itemsName);
        lvList.setAdapter(adapterName);
        lvList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ACTION_DESCRIPTION);
                Log.d("Log", itemsName.get(position).toString());
                Log.d("Log", itemDescription.get(position).toString());
                intent.putExtra("description", itemDescription.get(position).toString());
                startActivityForResult(intent, 2);
                pos = position;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ItemName itemName;
        ItemDescription itemsDescription;
        if (data == null) {return;}
        switch (requestCode) {
            case 1:
                String name = data.getStringExtra("name");
                if (!name.isEmpty()){
                    itemName = new ItemName(name);
                    String description = data.getStringExtra("description");
                    itemsDescription = new ItemDescription(description);
                    itemsName.add(0,itemName);
                    itemDescription.add(0, itemsDescription);
                    adapterName.notifyDataSetChanged();
                }
                break;
            case 2:
                String description1 = data.getStringExtra("description");
                Log.d("Log", description1);
                itemsDescription = new ItemDescription(description1);
                itemDescription.remove(pos);
                itemDescription.add(pos, itemsDescription);
                itemDescription.listIterator();
                break;
            case 3:
                String name2 = data.getStringExtra("name");
                itemName = new ItemName(name2);
                String description2 = data.getStringExtra("description");
                itemsDescription = new ItemDescription(description2);
                itemsName.remove(acmi.position);
                itemDescription.remove(acmi.position);
                itemsName.add(acmi.position, itemName);
                itemDescription.add(acmi.position, itemsDescription);
                itemsName.listIterator();
                itemDescription.listIterator();
                adapterName.notifyDataSetChanged();
                break;
        }
        save();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        switch (v.getId()) {
            case R.id.lvList:
                menu.add(0, MENU_CHANGE, 0, "Изменить");
                menu.add(0, MENU_DELETE, 0, "Удалить");
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        noteItemName = adapterName.getItem(acmi.position);
        switch (item.getItemId()) {

            case MENU_CHANGE:
                Intent intent = new Intent(ACTION_NOTE);
                startActivityForResult(intent, 3);
                Toast.makeText(this, "Изменить", Toast.LENGTH_SHORT).show();
                break;
            case MENU_DELETE:
                adapterName.remove(adapterName.getItem(acmi.position));
                Toast.makeText(this, "Удалено", Toast.LENGTH_SHORT).show();
                adapterName.notifyDataSetChanged();
        }
        save();
        return super.onContextItemSelected(item);
    }

    public void save(){

        boolean result = JSONHelper.exportToJSON(this, itemsName, itemDescription);
    }

    public void open() throws JSONException {
        itemsDataName = itemsName;
        itemsDataName = JSONHelper.importNameFromJSON(this);
        itemDescription = JSONHelper.importDescriptionFromJSON(this);
        if(itemsDataName!=null){
            itemsName = itemsDataName;
            adapterName = new ArrayAdapter<>(this, R.layout.simple_list_item, itemsName);
            lvList.setAdapter(adapterName);
            Toast.makeText(this, "Данные восстановлены", Toast.LENGTH_SHORT).show();
        }
        else{
            readItemsName();
            readItemsDescription();
        }
    }
    private void readItemsName() throws JSONException {
        InputStream inputStream = getResources().openRawResource(R.raw.items_notes);
        itemsName = new ItemReader().readName(inputStream);
    }

    private void readItemsDescription() throws JSONException {
        InputStream inputStream = getResources().openRawResource(R.raw.items_notes);
        itemDescription = new ItemReader().readDescription(inputStream);
    }

    private void readItem(Context context) throws JSONException{
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = context.openFileInput(FILE_NAME);
            itemsName = new ItemReader().readName(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /*public void onChange(String s){

        adapter.remove(rss);
        adapter.insert(s, acmi.position);
        adapter.notifyDataSetChanged();
    }*/
}

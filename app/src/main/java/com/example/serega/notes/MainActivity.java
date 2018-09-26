package com.example.serega.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.serega.notes.DB.DatabaseAdapter;
import com.example.serega.notes.JSON.Item.Item;
import com.example.serega.notes.JSON.JSONReader;

import org.json.JSONException;

import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final int MENU_CHANGE = 1;
    final int MENU_DELETE = 2;
    public static final String ACTION_NOTE = "com.example.serega.notes.Note";
    private long itemId=0;

    MyArrayAdapter myArrayAdapter;
    DatabaseAdapter adapter;

    List<Item> items;
    ListView lvList;

    AdapterView.AdapterContextMenuInfo acmi;

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
                startActivity(intent);
            }
        });

        lvList = (ListView) findViewById(R.id.lvList);
        registerForContextMenu(lvList);
        lvList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = myArrayAdapter.getItem(position);
                if(item!=null) {
                    Intent intent = new Intent(getApplicationContext(), NoteDescription.class);
                    intent.putExtra("id", item.getId());
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        Item item1;
        switch (item.getItemId()) {
            case MENU_CHANGE:
                item1 = myArrayAdapter.getItem(acmi.position);
                Intent intent = new Intent(getApplicationContext(), Note.class);
                intent.putExtra("id", item1.getId());
                startActivity(intent);
                break;
            case MENU_DELETE:
                item1 = myArrayAdapter.getItem(acmi.position);
                itemId = item1.getId();
                adapter.open();
                adapter.delete(itemId);
                adapter.close();
                onResume();
        }
        return super.onContextItemSelected(item);
    }

    private void readItems() throws JSONException {
        InputStream inputStream = getResources().openRawResource(R.raw.items);
        items = new JSONReader().read(inputStream, adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new DatabaseAdapter(this);
        adapter.open();
        items = adapter.getItems();
        if (items.isEmpty()){
            try {
                readItems();
            } catch (JSONException e) {
                Toast.makeText(this,"Ошибка при чтении списка.", Toast.LENGTH_LONG).show();
            }
        }
        myArrayAdapter = new MyArrayAdapter(this, items);
        lvList.setAdapter(myArrayAdapter);
        adapter.close();
    }
}

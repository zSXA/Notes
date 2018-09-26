package com.example.serega.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.serega.notes.DB.DatabaseAdapter;
import com.example.serega.notes.JSON.Item.Item;

public class NoteDescription extends AppCompatActivity {

    private DatabaseAdapter adapter;
    EditText etNote_description;
    private long itemId;
    private String name, color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_description);

        etNote_description = (EditText) findViewById(R.id.etNote_description);
        adapter = new DatabaseAdapter(this);

        Bundle extras = getIntent().getExtras();
        itemId = extras.getLong("id");
        adapter.open();
        Item item = adapter.getItem(itemId);
        etNote_description.setText(item.getDescription());
        name = item.getName();
        color = item.getColor();
        adapter.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_desc_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        save();
        return super.onOptionsItemSelected(item);
    }
    public void save(){

        String description = etNote_description.getText().toString();
        Item item = new Item(itemId, name, description, color);

        adapter.open();
        adapter.update(item);
        goHome();
    }

    private void goHome(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}

package com.example.serega.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class Note extends AppCompatActivity {

    private EditText etName_note, etDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etName_note = (EditText) findViewById(R.id.etName_note);
        etDescription = (EditText) findViewById(R.id.etDescription);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        intent.putExtra("name", etName_note.getText().toString());
        intent.putExtra("description", etDescription.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
        return super.onOptionsItemSelected(item);
    }
}

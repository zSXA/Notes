package com.example.serega.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;

public class NoteDescription extends AppCompatActivity {

    EditText etNote_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_description);
        etNote_description = (EditText) findViewById(R.id.etNote_description);
        Intent intent = getIntent();
        String description = intent.getStringExtra("description");
        etNote_description.setText(description);
        Log.d("NoteDescription", description);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_desc_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        intent.putExtra("description", etNote_description.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
        return super.onOptionsItemSelected(item);
    }
}

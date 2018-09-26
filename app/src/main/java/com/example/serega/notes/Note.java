package com.example.serega.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.serega.notes.DB.DatabaseAdapter;
import com.example.serega.notes.JSON.Item.Item;

public class Note extends AppCompatActivity {

    private EditText etName_note, etDescription;
    private DatabaseAdapter adapter;
    private long itemId = 0;
    private String color = "yellow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etName_note = (EditText) findViewById(R.id.etName_note);
        etDescription = (EditText) findViewById(R.id.etDescription);

        RadioButton purpleRadioButton = (RadioButton)findViewById(R.id.radio_purble);
        purpleRadioButton.setOnClickListener(radioButtonClickListener);

        RadioButton greenRadioButton = (RadioButton)findViewById(R.id.radio_green);
        greenRadioButton.setOnClickListener(radioButtonClickListener);

        RadioButton blueRadioButton = (RadioButton)findViewById(R.id.radio_blue);
        blueRadioButton.setOnClickListener(radioButtonClickListener);

        RadioButton yellowRadioButton = (RadioButton)findViewById(R.id.radio_yellow);
        yellowRadioButton.setOnClickListener(radioButtonClickListener);

        RadioButton brownRadioButton = (RadioButton)findViewById(R.id.radio_brown);
        brownRadioButton.setOnClickListener(radioButtonClickListener);

        RadioButton redRadioButton = (RadioButton)findViewById(R.id.radio_red);
        redRadioButton.setOnClickListener(radioButtonClickListener);

        RadioButton orangeRadioButton = (RadioButton)findViewById(R.id.radio_orange);
        orangeRadioButton.setOnClickListener(radioButtonClickListener);

        adapter = new DatabaseAdapter(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            itemId = extras.getLong("id");
        }
        if (itemId > 0) {
            adapter.open();
            Item item = adapter.getItem(itemId);
            etName_note.setText(item.getName());
            etDescription.setText(String.valueOf(item.getDescription()));
            adapter.close();
        }

    }

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton)v;
            switch (rb.getId()) {
                case R.id.radio_purble:
                    color = "purple";
                    break;
                case R.id.radio_green:
                    color = "green";
                    break;
                case R.id.radio_blue:
                    color = "blue";
                    break;
                case R.id.radio_yellow:
                    color = "yellow";
                    break;
                case R.id.radio_brown:
                    color = "gray";
                    break;
                case R.id.radio_red:
                    color = "red";
                    break;
                case R.id.radio_orange:
                    color = "orange";
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        save();
        return super.onOptionsItemSelected(item);
    }
    public void save(){

        String name = etName_note.getText().toString();
        String description = etDescription.getText().toString();
        Item item = new Item(itemId, name, description, color);

        adapter.open();
        if (itemId > 0) {
            adapter.update(item);
        } else {
            adapter.insert(item);
        }
        adapter.close();
        goHome();
    }

    private void goHome(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}

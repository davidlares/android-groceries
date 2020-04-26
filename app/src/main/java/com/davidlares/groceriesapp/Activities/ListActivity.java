package com.davidlares.groceriesapp.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.davidlares.groceriesapp.Data.DatabaseHandler;
import com.davidlares.groceriesapp.Model.Grocery;
import com.davidlares.groceriesapp.UI.RecyclerViewAdapter;
import com.davidlares.groceriesapp.Util.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.davidlares.groceriesapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Grocery> groceryList;
    private List<Grocery> listItems;
    private DatabaseHandler db;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private EditText groceryItem;
    private EditText quantity;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                createPopDialog();
            }
        });

        db = new DatabaseHandler(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        groceryList = new ArrayList<>();
        listItems = new ArrayList<>();

        // get items
        groceryList = db.getAllGroceries();

        for(Grocery c : groceryList) {
            Grocery grocery = new Grocery();
            grocery.setName(c.getName());
            grocery.setQuantity("Qty: " + c.getQuantity());
            grocery.setId(c.getId());
            grocery.setDateAdded("Added on: " + c.getDateAdded());
            listItems.add(grocery);
        }

        recyclerViewAdapter = new RecyclerViewAdapter(this, listItems);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private void createPopDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);
        groceryItem = (EditText) view.findViewById(R.id.groceryItem);
        quantity = (EditText) view.findViewById(R.id.groceryQty);
        saveButton = (Button) view.findViewById(R.id.saveButton);

        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGrocerytoDB(view);
            }
        });


    }

    private void saveGrocerytoDB(View view) {
        Grocery grocery = new Grocery();
        String newGrocery = groceryItem.getText().toString();
        String newGroceryQuantity = quantity.getText().toString();
        grocery.setName(newGrocery);
        grocery.setQuantity(newGroceryQuantity);
        // saving to DB
        db.addGrocery(grocery);
        Snackbar.make(view, "Item saved", Snackbar.LENGTH_LONG).show();
        Log.d("Count", String.valueOf(db.getGroceriesCount()));
        // delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(ListActivity.this, ListActivity.class));
                finish();
            }
        }, 1000);

    }


}

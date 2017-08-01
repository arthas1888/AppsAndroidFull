package com.example.a68.listapplication;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ListaActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] frutas = new String[]{"Banana", "Manzana",
                "Pera", "Melon", "Patilla", "Piña", "Sandia",
                "Kiwi", "Mandarina", "Naranja"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, frutas);
        setListAdapter(adapter);
    }
}

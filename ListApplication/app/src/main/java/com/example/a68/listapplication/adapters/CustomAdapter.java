package com.example.a68.listapplication.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.a68.listapplication.R;
import com.example.a68.listapplication.models.Persona;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter{


    private final ArrayList<Persona> personas;

    public CustomAdapter(@NonNull Context context,
                         @NonNull ArrayList<Persona> objects) {
        super(context, android.R.layout.simple_expandable_list_item_2,
                objects);
        personas = objects;
    }

    @Override
    public int getCount() {
        return personas.size();
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater
                    .inflate(android.R.layout.simple_expandable_list_item_2,
                            parent, false);
        }
        TextView textView1 = (TextView) convertView.findViewById(android.R.id.text1);
        TextView textView2 = (TextView) convertView.findViewById(android.R.id.text2);

        Persona persona = personas.get(position);
        textView1.setText(persona.nombre + " " + persona.apellido);
        textView2.setText("Edad: " + persona.edad);

        return convertView;

    }
}

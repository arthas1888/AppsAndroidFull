package com.example.a68.accesscpapplication;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.a68.accesscpapplication.MainActivity.AUTHORITY;
import static com.example.a68.accesscpapplication.MainActivity.TABLE_NAME;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Logger.addLogAdapter(new AndroidLogAdapter());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int _id = extras.getInt("id");
            String uri = "content://" + AUTHORITY + "/" + TABLE_NAME + "/" + _id;
            Cursor c = getContentResolver().query(Uri.parse(uri), null, null, null, null);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                String name = c.getString(c.getColumnIndex("nombre"));
                Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
                getSupportActionBar().setTitle(name);
            }
        }
        getDataFromServer();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getDataFromServer() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://centrisserv.centris.co/api/Venta/ConsultarVentaPorFechaAsync/";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject("{\"i\":{\"p\":\"Centris APP\",\"c\":\"1\",\"g\":\"1\",\"s\":\"1\",\"i\":\"es-co\",\"v\":\"1.0.0\",\"u\":\"gustavosantis@gmail.com\",\"f\":\"20160101121212\",\"m\":\"\",\"x\":\"\",\"y\":\"\"},\"p\":{\"m\":\"01\",\"a\":\"2017\"}}");
            Logger.json(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

// Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.json(response.toString());
                        // Display the first 500 characters of the response string.
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "error " + error.getMessage());
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}

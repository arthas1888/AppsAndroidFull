package com.example.a68.accesscpapplication;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.Toast;

import com.example.a68.accesscpapplication.adapters.MyCursorRecyclerViewAdapter;
import com.example.a68.accesscpapplication.models.Place;

public class MainActivity extends AppCompatActivity
        implements MyCursorRecyclerViewAdapter.OnListPlaceInteractionListener,
        LoaderManager.LoaderCallbacks<Cursor>,
        SearchView.OnQueryTextListener {

    private static final String TABLE_NAME = "Place";
    private static final String AUTHORITY = "com.example.a68.httpapplication.MyContentProvider";
    private static final Uri GET_ALL_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
    private MyCursorRecyclerViewAdapter adapter;


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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // EXPLICIT INTENT EXAMPLE
        //grantUriPermission("com.example.read", GET_ALL_URI, Intent.FLAG_GRANT_READ_URI_PERMISSION);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyCursorRecyclerViewAdapter.OnListPlaceInteractionListener mListener = this;
        adapter = new MyCursorRecyclerViewAdapter(mListener);
        recyclerView.setAdapter(adapter);

        getSupportLoaderManager().initLoader(0, null, this);

        /*Cursor cursor = getContentResolver().query(GET_ALL_URI,
                null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            MyCursorRecyclerViewAdapter.OnListPlaceInteractionListener mListener = this;
            adapter = new MyCursorRecyclerViewAdapter(cursor, mListener);
            recyclerView.setAdapter(adapter);

            cursor.moveToFirst();
            Toast.makeText(this, cursor.getString(cursor.getColumnIndex("nombre")),
                    Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));

            searchView.setIconifiedByDefault(false);
            searchView.setSubmitButtonEnabled(false);
            searchView.setOnQueryTextListener(this);
        } else {
            Log.e("ERROR", "searchView is null");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemInteraction(int id) {
        Log.d("MAIN", "id: " + id);
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri baseUri = GET_ALL_URI;
        String selection = null;
        String[] selectionArgs = null;
        if (args != null) {
            selection = args.getString("selection");
            selectionArgs = args.getStringArray("selectionArgs");
        }
        return new CursorLoader(this, baseUri, null,
                selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (adapter != null) {
            adapter.updateList(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.updateList(null);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("onQueryTextChange", newText);
        if (!newText.isEmpty()) {

            String selection = "ciudad like ?";
            String[] selectionArgs = new String[]{"%" + newText + "%"};
            Bundle bundle = new Bundle();
            bundle.putString("selection", selection);
            bundle.putStringArray("selectionArgs", selectionArgs);

            getSupportLoaderManager().restartLoader(0, bundle, this);
        } else {
            getSupportLoaderManager().restartLoader(0, null, this);
        }
        return false;
    }
}

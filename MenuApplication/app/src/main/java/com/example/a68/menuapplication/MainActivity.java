package com.example.a68.menuapplication;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a68.menuapplication.adapters.ImageAdapter;

public class MainActivity extends AppCompatActivity {

    private static final int VIEW_IMAGE = 1;
    private static final int WALL_PAPER = 2;
    private GridView gridView;

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
        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(new ImageAdapter(this));
        registerForContextMenu(gridView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Opciones");
        AdapterView.AdapterContextMenuInfo cmi =
                (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.add(VIEW_IMAGE, cmi.position, 0, "Ver imagen");
        menu.add(WALL_PAPER, cmi.position, 1, "Seleccionar como fondo de pantalla");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Integer resourceId = (Integer)
                gridView.getItemAtPosition(item.getItemId());
        //ImageView imageView = (ImageView) findViewById(resourceId);
        int pos = (int) gridView.getItemIdAtPosition(item.getItemId());
        ImageView imageView = (ImageView) gridView.getChildAt(pos);
        //ImageView view = (ImageView) gridView.getSelectedView();
        switch (item.getGroupId()) {
            case VIEW_IMAGE:
                Intent intent = new Intent(MainActivity.this, PreviewActivity.class);
                intent.putExtra("image", resourceId);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(this, imageView, "image");
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
                break;
            case WALL_PAPER:
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                break;
            case R.id.action_exit:
                exit();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Alerta")
                .setMessage("Desea salir de la app?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("NO", null);
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Cerrando la app..", Toast.LENGTH_SHORT).show();
    }
}

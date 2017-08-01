package com.example.a68.cameraapplication;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 0;
    private static final int RC_HANDLE_CAMERA_PERM = 11;
    private static final String TAG = MainActivity.class.getSimpleName();

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
                openCam();
            }
        });

        saveData();
    }

    private void saveData() {
        SharedPreferences preferences = this.getSharedPreferences("Shared",
                CONTEXT_IGNORE_SECURITY);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", "data prueba");
        editor.apply();

        String dataSaved = preferences.getString("name", "");
        Toast.makeText(this, dataSaved, Toast.LENGTH_SHORT).show();
    }

    private void openCam() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        int rc = ActivityCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA);
        if (android.os.Build.VERSION.SDK_INT >
                Build.VERSION_CODES.LOLLIPOP &&
                rc != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        }
    }

    private void requestPermission() {
        final String[] permissions = new String[]{
                Manifest.permission.CAMERA};

        ActivityCompat.requestPermissions(this, permissions,
                RC_HANDLE_CAMERA_PERM);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != RC_HANDLE_CAMERA_PERM){
            Log.e(TAG, "requestCode not found");
            return;
        }

        if (permissions[0].equals(Manifest.permission.CAMERA) &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "Permisos concedidos");
        }else{
            Toast.makeText(this, "Permisos no concedidos por el usuario", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        ImageView mImageView = (ImageView) findViewById(R.id.image_view);
        mImageView.setImageBitmap(imageBitmap);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

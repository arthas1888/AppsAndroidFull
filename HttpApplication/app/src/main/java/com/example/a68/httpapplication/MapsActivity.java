package com.example.a68.httpapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.example.a68.httpapplication.models.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Place place;
    private NotificationManager mNotificationManager;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(MapsActivity.this, MainActivity.class);


        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(PendingIntent
                                .getActivity(MapsActivity.this, 0, intent, 0));
        mNotificationManager.notify(0, mBuilder.build());

        notificationExpText();
    }


    private void notificationExpText() {
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.ic_launcher);
        Notification.Builder builder = createNotification();
        builder
                .setContentTitle("BigText title")
                .setContentText("Contenido reducido")
                .setSubText("Info")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon);
        Notification notification = new Notification.BigTextStyle(builder)
                .bigText("Esto es UNA notificacion")
                .setBigContentTitle("Android ATC")
                .setSummaryText(getResources().getString(R.string.summary_text))
                .build();

        mNotificationManager.notify(4, notification);
    }

    private Notification.Builder createNotification() {
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentText("Notificaciones");
        return builder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            place = (Place) extras.getSerializable("place");
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(place.lat, place.lon);
        mMap.addMarker(new MarkerOptions().position(sydney)
                .title("Informacion"


                ).snippet("Nombre: " + place.nombre + "\n" +
                        "Ciudad: " + place.ciudad + "\n" +
                        "Direccion: " + place.direccion + "\n" +
                        "Pais: " + place.pais + "\n" +
                        "Celular: " + place.celular + "\n" +
                        "Korarios: " + place.horarios));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17));
    }
}

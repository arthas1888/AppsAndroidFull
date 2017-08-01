package com.example.a68.httpapplication.utilities;


import android.net.Uri;

import com.example.a68.httpapplication.providers.MyContentProvider;

import static com.example.a68.httpapplication.databases.MyDataBase.TABLE_NAME;

public class Uris {

    public static final Uri GET_ALL_URI = Uri.parse("content://" + MyContentProvider.AUTHORITY + "/" + TABLE_NAME);
    public static final Uri BULK_INSERT_URI = Uri.parse("content://" + MyContentProvider.AUTHORITY + "/" + TABLE_NAME + "/bulk-insert");

}

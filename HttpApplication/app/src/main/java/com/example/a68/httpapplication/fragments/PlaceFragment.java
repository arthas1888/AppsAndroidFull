package com.example.a68.httpapplication.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a68.httpapplication.MainActivity;
import com.example.a68.httpapplication.R;
import com.example.a68.httpapplication.adapters.MyCursorRecyclerViewAdapter;
import com.example.a68.httpapplication.adapters.MyPlaceRecyclerViewAdapter;
import com.example.a68.httpapplication.databases.MyDataBase;
import com.example.a68.httpapplication.models.Place;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import static com.example.a68.httpapplication.databases.MyDataBase.COLUMN_CIUDAD;
import static com.example.a68.httpapplication.databases.MyDataBase.COLUMN_NAME;
import static com.example.a68.httpapplication.utilities.Uris.BULK_INSERT_URI;
import static com.example.a68.httpapplication.utilities.Uris.GET_ALL_URI;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PlaceFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private ArrayList<Place> places;
    private MyCursorRecyclerViewAdapter adapter;
    private MyDataBase dataBase;
    private Cursor cursor;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlaceFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PlaceFragment newInstance(int columnCount) {
        PlaceFragment fragment = new PlaceFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBase = new MyDataBase(getActivity());
        places = new ArrayList<>();
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        //cursor = dataBase.getAll();
        cursor = getActivity().
                getContentResolver().query(GET_ALL_URI,
                null, null, null, null);
        if (cursor == null || !cursor.moveToFirst())
            ((MainActivity)getActivity()).request();
        //else
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new MyCursorRecyclerViewAdapter(cursor, mListener);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateList(JSONArray response) {

        Date currentLocalTime = new Date();
        /*places =  new Gson().fromJson(response.toString(),
                new TypeToken<ArrayList<Place>>(){}.getType());

        dataBase.addAll(places);*/


        try {
            //dataBase.addJArray(response);
            getActivity().
                    getContentResolver().bulkInsert(BULK_INSERT_URI, getValuesFromJArray(response));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        long millis = new Date().getTime() - currentLocalTime.getTime();
        Log.e("CURRENT TIME", "duration secs: "
                + ((double)millis/1000.0) + ", millis: " + millis);
        //cursor = dataBase.getAll();
        cursor = getActivity().
                getContentResolver().query(GET_ALL_URI,
                null, null, null, null);
        adapter.notifyDataSetChanged();
        adapter.updateList(cursor);

//        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(getActivity(),
//                android.R.layout.simple_list_item_2, cursor, new String[]{
//                COLUMN_NAME, COLUMN_CIUDAD}, new int[]{android.R.id.text1, android.R.id.text2}, 1);
    }

    public ContentValues[] getValuesFromJArray(JSONArray jsonArray) throws JSONException {
        ArrayList<ContentValues> contentValues = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            ContentValues cv = new ContentValues();
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            Iterator<String> keysIterator = jsonObj.keys();
            while (keysIterator.hasNext())
            {
                String key = keysIterator.next();
                String value = jsonObj.getString(key);
                if (value == null) value = "";
                /*if (i == 0)
                    Logger.d("key: " + key + ", value: " + value);*/
                if (key.equals("point") || key.equals("telefono")
                        || key.equals("is_active")) continue;
                cv.put(key, value);
            }
            contentValues.add(cv);

            //db.insert(TABLE_NAME, null, cv);
        }
        return contentValues.toArray(new ContentValues[contentValues.size()]);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Place item);
    }
}

package com.example.a68.httpapplication.adapters;

import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a68.httpapplication.R;
import com.example.a68.httpapplication.fragments.PlaceFragment.OnListFragmentInteractionListener;
import com.example.a68.httpapplication.models.Place;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.a68.httpapplication.databases.MyDataBase.COLUMN_CIUDAD;
import static com.example.a68.httpapplication.databases.MyDataBase.COLUMN_NAME;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Place} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCursorRecyclerViewAdapter extends RecyclerView.Adapter<MyCursorRecyclerViewAdapter.ViewHolder> {

    private Cursor mValues;
    private final OnListFragmentInteractionListener mListener;
    private Place place;

    public MyCursorRecyclerViewAdapter(Cursor items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_place, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        mValues.moveToPosition(position);
        holder.mIdView.setText(mValues.getString(mValues.getColumnIndex(COLUMN_NAME)));
        holder.mContentView.setText(mValues.getString(mValues.getColumnIndex(COLUMN_CIUDAD)));
        try {
            holder.mItem = getObj(mValues);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.getCount();
    }

    public void updateList(Cursor nuevoCursor) {
        if (nuevoCursor != null) {
            mValues = nuevoCursor;
            notifyDataSetChanged();
        }
    }

    private Place getObj(Cursor cursor) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        for (String columnName : cursor.getColumnNames()) {
            int typeColumn = cursor.getType(cursor.getColumnIndex(columnName));
            if (typeColumn == Cursor.FIELD_TYPE_STRING) {
                String entityStr = cursor.getString(cursor.getColumnIndex(columnName));
                if (entityStr.equals("null")) entityStr = "0";
                jsonObject.put(columnName, entityStr);
            } else if (typeColumn == Cursor.FIELD_TYPE_INTEGER) {
                Integer entityInt = cursor.getInt(cursor.getColumnIndex(columnName));
                jsonObject.put(columnName, entityInt);
            } else if (typeColumn == Cursor.FIELD_TYPE_FLOAT) {
                Double entityDouble = cursor.getDouble(cursor.getColumnIndex(columnName));
                jsonObject.put(columnName, entityDouble);
            }

        }
        Gson gson = new Gson();
        return gson.fromJson(jsonObject.toString(), new TypeToken<Place>() {
        }.getType());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Place mItem;
        public CardView cardview;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            cardview = (CardView) view.findViewById(R.id.cardview);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}

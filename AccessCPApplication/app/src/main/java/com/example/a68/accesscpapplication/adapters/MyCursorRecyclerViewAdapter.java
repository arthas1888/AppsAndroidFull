package com.example.a68.accesscpapplication.adapters;

import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a68.accesscpapplication.R;
import com.example.a68.accesscpapplication.models.Place;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Place} and makes a call to the
 * specified {@link OnListPlaceInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCursorRecyclerViewAdapter extends RecyclerView.Adapter<MyCursorRecyclerViewAdapter.ViewHolder> {

    public interface OnListPlaceInteractionListener{
        void onItemInteraction(int id);
    }

    private Cursor mValues;
    private final OnListPlaceInteractionListener mListener;
    private Place place;

    public MyCursorRecyclerViewAdapter(OnListPlaceInteractionListener listener) {
        mListener = listener;
    }

    public MyCursorRecyclerViewAdapter(Cursor items, OnListPlaceInteractionListener listener) {
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
        holder.mIdView.setText(mValues.getString(mValues.getColumnIndex("nombre")));
        holder.mContentView.setText(mValues.getString(mValues.getColumnIndex("ciudad")));
        final int _id = mValues.getInt(mValues.getColumnIndex("_id"));
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onItemInteraction(_id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mValues != null){
            return mValues.getCount();
        }
        return 0;
    }

    public void updateList(Cursor nuevoCursor) {
        if (nuevoCursor != null) {
            mValues = nuevoCursor;
            notifyDataSetChanged();
        }
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

package com.example.a68.recyclerlistapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a68.recyclerlistapplication.R;
import com.example.a68.recyclerlistapplication.fragments.ListaFragment;

/**
 * Created by 68 on 08/07/2017.
 */

public class CustomRecyclerAdapter
        extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {
    private String[] mDataset;
    private ListaFragment.OnItemListener listener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public View view;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.text_desc);
            view = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CustomRecyclerAdapter(String[] myDataset,
                                 ListaFragment.OnItemListener listener) {
        mDataset = myDataset;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CustomRecyclerAdapter.ViewHolder
    onCreateViewHolder(ViewGroup parent,
                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_recycler, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset[position]);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onClick(mDataset[position]);
                }
                Log.d("", "Click");
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
package com.example.a68.recyclerlistapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a68.recyclerlistapplication.R;
import com.example.a68.recyclerlistapplication.adapters.CustomRecyclerAdapter;

/**
 * Created by 68 on 08/07/2017.
 **/

public class ListaFragment extends Fragment {

    private OnItemListener listener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lista_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        String[] dataset = {"Lollipop", "Honeycumb", "KitKat", "Icecream", "Marshmallow"};
        CustomRecyclerAdapter customRecyclerAdapter =
                new CustomRecyclerAdapter(dataset, listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(customRecyclerAdapter);
        return view;
    }

    public interface OnItemListener{
        void onClick(String item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnItemListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}

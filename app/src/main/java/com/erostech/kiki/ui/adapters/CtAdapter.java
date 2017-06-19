package com.erostech.kiki.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erostech.kiki.R;
import com.erostech.kiki.ui.viewholders.CtViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erosgarciaponte on 16.06.17.
 */

public class CtAdapter extends RecyclerView.Adapter<CtViewHolder> {
    private List<Country> mList;

    public CtAdapter() {
        mList = new ArrayList<>();
    }

    @Override
    public CtViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        CtViewHolder viewHolder = new CtViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CtViewHolder viewHolder, int position) {
        Country country = mList.get(position);
        viewHolder.bind(country);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }
}

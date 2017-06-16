package com.erostech.kiki.ui.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.erostech.kiki.R;
import com.erostech.kiki.models.Country;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by erosgarciaponte on 16.06.17.
 */

public class CtViewHolder extends RecyclerView.ViewHolder {
    private ProgressBar mProgress;
    private ImageView mImage;
    private TextView mName;
    private TextView mRegion;

    public CtViewHolder(View itemView) {
        super(itemView);
        mProgress = (ProgressBar) itemView.findViewById(R.id.progress_view);
        mImage = (ImageView) itemView.findViewById(R.id.flag_view);
        mName = (TextView) itemView.findViewById(R.id.name_view);
        mRegion = (TextView) itemView.findViewById(R.id.region_view);
    }

    public void bind(Country country) {
        if (country.getFlag() != null && !country.getFlag().trim().isEmpty()) {
            Picasso.with(itemView.getContext()).load(country.getFlag()).centerCrop().into(mImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                }
            });
        }
    }
}

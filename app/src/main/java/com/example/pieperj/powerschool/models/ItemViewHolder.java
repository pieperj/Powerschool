package com.example.pieperj.powerschool.models;

import android.view.View;
import android.widget.TextView;

import com.example.pieperj.powerschool.R;

import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public final TextView textView;

    public ItemViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.layout.notification_recycler_view_item);
        //textView = (TextView) itemView.findViewById(R.layout.notification_recycler_view_item);

    }

}

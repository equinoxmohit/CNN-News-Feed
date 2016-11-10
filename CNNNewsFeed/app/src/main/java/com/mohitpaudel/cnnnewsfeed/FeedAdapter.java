package com.mohitpaudel.cnnnewsfeed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;



public class FeedAdapter extends ArrayAdapter {

    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<FeedEntry> newsFeed;

    public FeedAdapter(Context context, int resource, List<FeedEntry> newsFeed) {
        super(context, resource);
        this.newsFeed = newsFeed;
        this.layoutResource=resource;
        this.layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return newsFeed.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null)
        {
            convertView=layoutInflater.inflate(layoutResource,parent,false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else
        {
            viewHolder=(ViewHolder) convertView.getTag();
        }

        FeedEntry currentRecord=newsFeed.get(position);

        viewHolder.tvTitle.setText(currentRecord.getTitle());
        viewHolder.tvPubDate.setText(currentRecord.getPubDate());
        viewHolder.tvDescription.setText(currentRecord.getDescription());
        viewHolder.tvLink.setText(currentRecord.getLink());

        return convertView;
    }

    public class ViewHolder
    {
        final TextView tvTitle,tvDescription,tvPubDate,tvLink;

        public ViewHolder(View view)
        {
            tvTitle=(TextView) view.findViewById(R.id.tvTitle);
            tvDescription=(TextView) view.findViewById(R.id.tvDescription);
            tvPubDate=(TextView) view.findViewById(R.id.tvPubDate);
            tvLink=(TextView) view.findViewById(R.id.tvLink);
        }

    }



}


package com.example.android.booklisting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vikash on 17/6/17.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_display_book, parent, false);
        }
        Book currentposition = getItem(position);

        String author_name = currentposition.getAuthors();
        String date = currentposition.getDate();
        String title = currentposition.getTitle();

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title);
        titleTextView.setText(title);

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        dateTextView.setText(date);

        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author_name);
        authorTextView.setText(author_name);

        final String url = currentposition.getLink();

        RelativeLayout rl = (RelativeLayout) listItemView.findViewById(R.id.activity_display_book);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                getContext().startActivity(i);
            }
        });

        return listItemView;
    }
}

package com.example.android.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class MonkeyAdapter extends ArrayAdapter<Monkey> {

    /**
     * constructs a new MonkeyAdapter
     *
     * @param context of the app
     * @param monkeys which is the list of monkeys for the app
     */

    public MonkeyAdapter(Context context, List<Monkey> monkeys) {
        super(context, 0, monkeys);

    }

    //Override GetView
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        //This looks for which view to display. If outside view, not shown

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // get the Monkey for this position

        final Monkey currentMonkey = getItem(position);

        // Find the TextView with view ID date
        TextView dateView = (TextView) listItemView.findViewById(R.id.date_text_view);

        /**
         * Now we set the date TextView to display the date.
         * Under normal circumstances we would get a Date object from a Long value from the JSON.
         * Here, however, we get a String, not formatted to seconds or milliseconds for easy date formatting.
         * Of course we could turn a String to a Date back to a String. But for this app it doesn't
         * seem necessary.
         */

        String date = currentMonkey.getDate();
        String dateoutput = date.substring(0, 10);
        dateView.setText(dateoutput);

        // Find the TextView with the section name
        TextView sectionView = (TextView) listItemView.findViewById(R.id.section_text_view);

        // Set the section name TextView to display the section name for each article
        sectionView.setText(currentMonkey.getSection());

        // Find the TextView with the article title
        TextView titleView = (TextView) listItemView.findViewById(R.id.title_text_view);

        // Set the section name TextView to display the section name for each article
        titleView.setText(currentMonkey.getTitle());


        /**
         * Create OnClickListener to go to a URL for the article
         */

        listItemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Uri intentUri = Uri.parse(currentMonkey.getUrl());
                                                Intent uriIntent = new Intent(Intent.ACTION_VIEW, intentUri);
                                                getContext().startActivity(uriIntent);
                                            }
                                        }
        );

        /**
         * Here's the return statement, sending the whole list item view with 3 TextViews
         * to be shown in the ListView
         */

        return listItemView;


    }
}
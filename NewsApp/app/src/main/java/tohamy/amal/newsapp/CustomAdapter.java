package tohamy.amal.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<News> {
    public CustomAdapter(@NonNull Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_news_list, parent, false);
        }


        News currentPosition = getItem(position);

        TextView pillarName = listItemView.findViewById(R.id.pillar_name_text_view);
        pillarName.setText(currentPosition.getPillarName());

        TextView news = listItemView.findViewById(R.id.news_text_view);
        news.setText(currentPosition.getNewsText());


        String date = currentPosition.getDate().substring(0, 10);
        //Date date = new Date(currentPosition.getDate());
        //String formattedDate = formatDate(date);
        TextView dateTextView = listItemView.findViewById(R.id.date_text_view);
        dateTextView.setText(date);

        String time = currentPosition.getDate().substring(12, 16);
        TextView timeTextView = listItemView.findViewById(R.id.time_text_view);
        //String formattedTime = formatTime(time);
        timeTextView.setText(time);

        return listItemView;
    }

}

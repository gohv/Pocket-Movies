package xyz.georgihristov.pocketmovies;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


class NavigationAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final int layoutResourceId;
    private final String[] data;

    public NavigationAdapter(Context context, String[] data) {
        super(context, R.layout.nav_drawer, data);
        this.context = context;
        this.layoutResourceId = R.layout.nav_drawer;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = ((Activity) context).getLayoutInflater();


        View v = inflater.inflate(layoutResourceId, parent, false);

        TextView textView = (TextView) v.findViewById(R.id.navDrawerTextView);

        String choice = data[position];


        textView.setText(choice);

        return v;
    }
}

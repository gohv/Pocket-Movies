package xyz.georgihristov.pocketmovies;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class NavigationAdapter extends ArrayAdapter<String> {

    private Context context;
    private int layoutResourceId;
    private String[] data;

    public NavigationAdapter(Context context,int layoutResourceId, String[] data) {
        super(context, layoutResourceId,data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {


        LayoutInflater inflater = ((Activity) context).getLayoutInflater();



        View v = inflater.inflate(layoutResourceId, parent, false);

        TextView textView = (TextView) v.findViewById(R.id.navDrawerTextView);

        String choice = data[position];


        textView.setText(choice);

        return v;
    }
}

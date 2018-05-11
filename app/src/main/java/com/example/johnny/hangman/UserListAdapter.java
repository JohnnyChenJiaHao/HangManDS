package com.example.johnny.hangman;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sammy on 08-05-2018.
 */

//This class is inspired/borrowed by https://github.com/mitchtabian/ListViews
public class UserListAdapter extends ArrayAdapter<User>{
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    public UserListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<User> objects) {
        super(context, resource, objects);

        mContext = context;
        mResource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        String student_Id = getItem(position).getStudent_Id();
        String score = getItem(position).getScore();
        String numberOfTries = getItem(position).getNumberOfTries();
        String time_used = getItem(position).getTime_used();

        User user = new User(student_Id, score, numberOfTries, time_used);

        final View result;

        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.student_Id = (TextView) convertView.findViewById(R.id.student_Id);
            holder.score = (TextView) convertView.findViewById(R.id.score);
            holder.numberOfTries = (TextView) convertView.findViewById(R.id.numberoftries);
            holder.time_used = (TextView) convertView.findViewById(R.id.time_used);


            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;

        holder.student_Id.setText(user.getStudent_Id());
        holder.score.setText(user.getScore());
        holder.numberOfTries.setText(user.getNumberOfTries());
        holder.time_used.setText(user.getTime_used());

        return convertView;
    }

    private static class ViewHolder{
        TextView student_Id;
        TextView score;
        TextView time_used;
        TextView numberOfTries;
    }

}

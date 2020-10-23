package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PersonAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Person> personsList;
    private LayoutInflater layoutInflater;

    public PersonAdapter(Context context, ArrayList<Person> personsList){
        this.context = context;
        this.personsList = personsList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return personsList.size();
    }

    @Nullable
    @Override
    public Person getItem(int position) {
        return personsList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       ViewHolder holder;

       if(convertView == null){
           convertView = layoutInflater.inflate(R.layout.person_display, null);
           holder = new ViewHolder();

           holder.tvPersonID = convertView.findViewById(R.id.tvPersonID);
           holder.tvLastname = convertView.findViewById(R.id.tvLastname);
           holder.tvFirstname = convertView.findViewById(R.id.tvFirstname);

           convertView.setTag(holder);
       } else {
           holder = (ViewHolder) convertView.getTag();
       }

        holder.tvPersonID.setText(String.valueOf(personsList.get(position).getPersonID()));
        holder.tvLastname.setText(personsList.get(position).getLastname());
        holder.tvFirstname.setText(personsList.get(position).getFirstname());

        return convertView;
    }

    private static class ViewHolder{
        TextView tvLastname;
        TextView tvFirstname;
        TextView tvPersonID;
    }
}

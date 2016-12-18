package com.edulectronics.tinycircuit.Views.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edulectronics.tinycircuit.Models.Factories.ScenarioFactory;
import com.edulectronics.tinycircuit.Models.Scenarios.IScenario;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.Views.CircuitActivity;
import com.edulectronics.tinycircuit.Views.ExerciseMenuActivity;

/**
 * Created by Wilmer on 18-12-2016.
 */

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private View view;
    private String[] items;

    public ListViewAdapter(Context context, View view, String[] items) {
        this.context = context;
        this.view = view;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater layoutInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.menu_button, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.menuButtonText);
        textView.setText(items[position]);
        textView.setOnClickListener(getOnClickListener());
        return convertView;
    }

    @Override
    public int getCount(){
        return items.length;
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public Object getItem(int position)
    {
        return items[position];
    }

    private View.OnClickListener getOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView) view;
                if(textView.getText() == context.getResources().getString(R.string.exercise)){
                    exerciseMenuStart();
                } else {
                    freePlayStart();
                }
            }
        };
    }

    private void freePlayStart() {
        ScenarioFactory factory = new ScenarioFactory();
        IScenario scenario = factory.getScenario("freeplay");

        Intent intent = new Intent(context, CircuitActivity.class);
        intent.putExtra("scenario", scenario );
        context.startActivity(intent);
    }

    private void exerciseMenuStart(){
        Intent exercise = new Intent(context, ExerciseMenuActivity.class);
        context.startActivity(exercise);
    }
}

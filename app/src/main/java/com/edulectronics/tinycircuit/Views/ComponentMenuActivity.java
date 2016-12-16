package com.edulectronics.tinycircuit.Views;

import android.content.res.TypedArray;
import android.view.Menu;
import android.widget.ExpandableListView;

import com.edulectronics.tinycircuit.Controllers.LevelController;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.MenuItem;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.Views.Adapters.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bernd on 16/12/2016.
 */

public class ComponentMenuActivity {
    private LevelController levelController;
    private CircuitActivity circuitActivity;
    private ArrayList<MenuItem> headers;
    private HashMap<MenuItem, List<MenuItem>> children;

    public ComponentMenuActivity(LevelController levelController, CircuitActivity circuitActivity) {
        this.circuitActivity = circuitActivity;
        this.levelController = levelController;
    }

    public ComponentMenuActivity(CircuitActivity circuitActivity) {
        this.circuitActivity = circuitActivity;
    }

    public void createMenu() {
        ExpandableListView expandableList = (ExpandableListView) circuitActivity.findViewById(R.id.expandablelist);
        makeLists();

        ExpandableListAdapter adapter = new ExpandableListAdapter(
                circuitActivity, headers, children, expandableList
        );
        expandableList.setAdapter(adapter);
        expandableList.setOnChildClickListener(circuitActivity.onChildClick());
        expandableList.setOnGroupClickListener(circuitActivity.onGroupClick());
    }

    /*
    * Makes the groups and children
    * TODO only show available components and make it readable code
    * */
    private void makeLists() {
        headers = new ArrayList<>();
        String[] items = circuitActivity.getResources().getStringArray(R.array.menuitems);
        children = new HashMap<>();
        /*Temporary! Should work different!*/
        int[] textures = {R.drawable.battery, R.drawable.lightbulb_on, R.drawable.resistor};

        TypedArray typedArray = circuitActivity.getResources().obtainTypedArray(R.array.categories);
        int length = typedArray.length();
        String[][] headings = new String[length][];

        for (int i = 0; i < length; i++){
            int id = typedArray.getResourceId(i, 0);
            headings[i] = circuitActivity.getResources().getStringArray(id);
        }
        typedArray.recycle();

        for(int i = 0; i < items.length; i++){
            MenuItem item = new MenuItem();
            item.setIconName(items[i]);
            item.setIconImage(textures[i]);
            headers.add(item);

            List<MenuItem> heading = new ArrayList();
            for(int j = 1; j < headings[i].length; j++){
                MenuItem subitem = new MenuItem();
                subitem.setIconName(headings[i][j]);
                subitem.setIconImage(textures[i]);
                heading.add(subitem);
            }
            children.put(headers.get(i), heading);
        }
    }
}

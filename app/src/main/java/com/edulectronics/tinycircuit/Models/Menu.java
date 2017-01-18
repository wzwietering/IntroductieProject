package com.edulectronics.tinycircuit.Models;

import android.content.Context;
import android.content.res.TypedArray;

import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bernd on 11/01/2017.
 */

public class Menu {
    private List<MenuItem> headers;
    private HashMap<MenuItem, List<MenuItem>> children;

    public Menu(Context context) {
        headers = new ArrayList<>();
        String[] items = context.getResources().getStringArray(R.array.menuitems);
        children = new HashMap<>();
        /*Temporary! Should work different!*/
        int[] textures = {
                0,
                R.drawable.battery,
                R.drawable.lightbulb_on,
                R.drawable.resistor,
                R.drawable.switch_on
        };

        TypedArray typedArray = context.getResources().obtainTypedArray(R.array.categories);
        int length = typedArray.length();
        String[][] headings = new String[length][];

        for (int i = 0; i < length; i++) {
            int id = typedArray.getResourceId(i, 0);
            headings[i] = context.getResources().getStringArray(id);
        }
        typedArray.recycle();

        for (int i = 0; i < items.length; i++) {
            MenuItem item = new MenuItem();
            item.setIconName(items[i]);
            item.setIconImage(textures[i]);
            headers.add(item);

            List<MenuItem> heading = new ArrayList();
            for (int j = 1; j < headings[i].length; j++) {
                MenuItem subitem = new MenuItem();
                subitem.setIconName(headings[i][j]);
                subitem.setIconImage(textures[i]);
                heading.add(subitem);
            }
            children.put(headers.get(i), heading);
        }
    }

    public List<MenuItem> getHeaders() {
        return headers;
    }

    public HashMap<MenuItem, List<MenuItem>> getChildren() {
        return children;
    }
}

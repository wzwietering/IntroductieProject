package com.edulectronics.tinycircuit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.GridView;

import com.edulectronics.tinycircuit.Circuit.CircuitController;
import com.edulectronics.tinycircuit.Models.ExpandedMenu;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.ui.adapters.CircuitAdapter;
import com.edulectronics.tinycircuit.ui.adapters.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CircuitActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private List<ExpandedMenu> headers;
    private HashMap<ExpandedMenu, List<String>> children;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        GridView circuit = (GridView) findViewById(R.id.circuit);

        /*Prevents scrolling, stuff can still be rendered outside screen*/
        circuit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE){
                    return true;
                }
                return false;
            }
        });

        Intent intent = getIntent();
        CircuitController controller =
                (CircuitController) intent.getSerializableExtra("Controller");
        circuit.setNumColumns(controller.circuit.width);
        circuit.setAdapter(new CircuitAdapter(this, controller));

        makeMenu();
    }

    /*This code adds a menu to the side*/
    private void makeMenu(){
        ExpandableListView expandableList = (ExpandableListView) findViewById(R.id.expandablelist);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main);
        prepareListData();

        ExpandableListAdapter adapter = new ExpandableListAdapter(
                this, headers, children, expandableList);
        expandableList.setAdapter(adapter);

        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view,
                                        int groupPosition, int childPosition, long rowID) {
                /*Handle click event of child over here*/
                return false;
            }
        });
        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view,
                                        int groupPosition, long rowID) {
                /*Handle click event of group over here*/
                return false;
            }
        });
    }

    /*Makes the groups and children*/
    private void prepareListData() {
        headers = new ArrayList<>();
        children = new HashMap<>();
        String[] items = getResources().getStringArray(R.array.menuitems);

        ExpandedMenu item1 = new ExpandedMenu();
        item1.setIconName(items[0]);
        item1.setIconImage(R.mipmap.battery);
        headers.add(item1);

        ExpandedMenu item2 = new ExpandedMenu();
        item2.setIconName(items[1]);
        item2.setIconImage(R.mipmap.lightbulb_on);
        headers.add(item2);

        ExpandedMenu item3 = new ExpandedMenu();
        item3.setIconName(items[2]);
        item3.setIconImage(R.mipmap.resistor);
        headers.add(item3);

        /*Add child data*/
        List<String> heading1 = new ArrayList();
        heading1.add("Batterij");

        List<String> heading2 = new ArrayList();
        heading2.add("Gloeilamp");

        List<String> heading3 = new ArrayList();
        heading3.add("Weerstand");

        children.put(headers.get(0), heading1);
        children.put(headers.get(1), heading2);
        children.put(headers.get(2), heading3);
    }
}

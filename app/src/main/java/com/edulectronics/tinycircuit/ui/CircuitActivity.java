package com.edulectronics.tinycircuit.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.GridView;

import com.edulectronics.tinycircuit.Circuit.CircuitController;
import com.edulectronics.tinycircuit.Models.MenuItem;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.ui.adapters.CircuitAdapter;
import com.edulectronics.tinycircuit.ui.adapters.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.ui.Draggables.DragController;
import com.edulectronics.tinycircuit.ui.Draggables.DragLayer;
import com.edulectronics.tinycircuit.ui.Draggables.Interfaces.DragSource;

public class CircuitActivity extends Activity
        implements  View.OnClickListener,
        View.OnTouchListener, View.OnLongClickListener //  , AdapterView.OnItemClickListener
{
    private DragController mDragController;   // Object that handles a drag-drop sequence. It interacts with DragSource and DropTarget objects.
    private DragLayer mDragLayer;             // The ViewGroup within which an object can be dragged.
	private List<MenuItem> headers;
    private HashMap<MenuItem, List<MenuItem>> children;

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
        circuit.setAdapter(new com.edulectronics.tinycircuit.ui.adapters.CircuitAdapter(this, controller));

        controller.addComponent(new Lightbulb(), 1);

        mDragController = new DragController(this);
        mDragLayer = (DragLayer) findViewById(R.id.drag_layer);
        mDragLayer.setDragController (mDragController);
        mDragLayer.setGridView (circuit);

        mDragController.setDragListener (mDragLayer);

        makeMenu();
    }

    /*This code adds a menu to the side*/
    private void makeMenu(){
        ExpandableListView expandableList = (ExpandableListView) findViewById(R.id.expandablelist);
        makeLists();

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
    private void makeLists() {
        headers = new ArrayList<>();
        children = new HashMap<>();
        String[] items = getResources().getStringArray(R.array.menuitems);
        /*Temporary! Should work different!*/
        int[] textures = {R.mipmap.battery, R.mipmap.lightbulb_on, R.mipmap.resistor};

        TypedArray typedArray = getResources().obtainTypedArray(R.array.categories);
        int length = typedArray.length();
        String[][] headings = new String[length][];

        for (int i = 0; i < length; i++){
            int id = typedArray.getResourceId(i, 0);
            headings[i] = getResources().getStringArray(id);
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

    public void onClick(View v)
    {
    }
    public boolean onTouch (View v, MotionEvent ev) {
        // If we are configured to start only on a long click, we are not going to handle any events here.

        boolean handledHere = false;

        final int action = ev.getAction();

        // In the situation where a long click is not needed to initiate a drag, simply start on the down event.
        if (action == MotionEvent.ACTION_DOWN) {
            handledHere = startDrag(v);
            if (handledHere) v.performClick();
        }

        return handledHere;
    }

    public boolean onLongClick(View v)
    {
        // Make sure the drag was started by a long press as opposed to a long click.
        // (Note: I got this from the Workspace object in the Android Launcher code.
        //  I think it is here to ensure that the device is still in touch mode as we start the drag operation.)
        if (!v.isInTouchMode()) {
            return false;
        }
        return startDrag (v);
    }

    public boolean startDrag (View v)
    {
        DragSource dragSource = (DragSource) v;

        mDragController.startDrag (v, dragSource, dragSource);

        return true;
    }
}

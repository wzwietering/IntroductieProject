package com.edulectronics.tinycircuit.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edulectronics.tinycircuit.Circuit.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Factories.ComponentFactory;
import com.edulectronics.tinycircuit.Models.MenuItem;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.ui.Adapters.CircuitAdapter;
import com.edulectronics.tinycircuit.ui.Adapters.ExpandableListAdapter;
import com.edulectronics.tinycircuit.ui.Draggables.DragController;
import com.edulectronics.tinycircuit.ui.Draggables.DragLayer;
import com.edulectronics.tinycircuit.ui.Draggables.GridCell;
import com.edulectronics.tinycircuit.ui.Draggables.Interfaces.DragSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CircuitActivity extends Activity
        implements  View.OnClickListener,
        View.OnTouchListener, View.OnLongClickListener //  , AdapterView.OnItemClickListener
{
    private DragController mDragController;   // Object that handles a drag-drop sequence. It interacts with DragSource and DropTarget objects.
    private DragLayer mDragLayer;             // The ViewGroup within which an object can be dragged.
	private List<MenuItem> headers;
    private HashMap<MenuItem, List<MenuItem>> children;
    private CircuitController circuitController;

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
        this.circuitController =
                (CircuitController) intent.getSerializableExtra("Controller");
        circuit.setNumColumns(circuitController.circuit.width);
        circuit.setAdapter(new CircuitAdapter(this, circuitController));

        circuitController.addComponent(new Lightbulb(), 1);

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

        expandableList.setOnChildClickListener(onChildClick());
        expandableList.setOnGroupClickListener(onGroupClick());
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

    public void onClickAddComponent(String text)
    {
        Component component = ComponentFactory.CreateComponent(text);
        addNewComponent(component);
        NavigationView view = (NavigationView) findViewById(R.id.navigationview);
        ((DrawerLayout) findViewById(R.id.activity_main)).closeDrawer(view);
    }

    public void addNewComponent(Component component)
    {
        FrameLayout componentHolder = (FrameLayout)findViewById (R.id.component_source_frame);
        componentHolder.setVisibility(View.VISIBLE);

        if (componentHolder != null) {
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams (ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.FILL_PARENT,
                    Gravity.CENTER);
            GridCell newView = new GridCell (this, circuitController);
            newView.setComponent(component);
            componentHolder.addView(newView, lp);
            newView.mCellNumber = -1;

            // Have this activity listen to touch and click events for the view.
            newView.setOnClickListener(this);
            newView.setOnLongClickListener(this);
            newView.setOnTouchListener (this);
        }
    }

    public ExpandableListView.OnGroupClickListener onGroupClick(){
        return new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        };
    }

    public ExpandableListView.OnChildClickListener onChildClick(){
        return new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                LinearLayout group = (LinearLayout) ((LinearLayout)v).getChildAt(0);
                View child = group.getChildAt(1);
                String text = (String) ((TextView)child).getText();
                onClickAddComponent(text);
                return false;
            }
        };
    }
}

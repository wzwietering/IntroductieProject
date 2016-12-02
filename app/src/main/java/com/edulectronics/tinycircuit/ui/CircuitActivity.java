package com.edulectronics.tinycircuit.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;

import com.edulectronics.tinycircuit.Circuit.CircuitController;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Lightbulb;
import com.edulectronics.tinycircuit.Models.Factories.ComponentFactory;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.ui.Adapters.CircuitAdapter;
import com.edulectronics.tinycircuit.ui.Draggables.DragController;
import com.edulectronics.tinycircuit.ui.Draggables.DragLayer;
import com.edulectronics.tinycircuit.ui.Draggables.GridCell;
import com.edulectronics.tinycircuit.ui.Draggables.Interfaces.DragSource;

public class CircuitActivity extends Activity
        implements  View.OnClickListener,
        View.OnTouchListener, View.OnLongClickListener //  , AdapterView.OnItemClickListener
{
    private DragController mDragController;   // Object that handles a drag-drop sequence. It interacts with DragSource and DropTarget objects.
    private DragLayer mDragLayer;             // The ViewGroup within which an object can be dragged.
    private GridCell lastNewCell;
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
        ListView menu = (ListView) findViewById(R.id.menu);
        String[] items = getResources().getStringArray(R.array.items);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, items);
        menu.setAdapter(adapter);

        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long itemId){
                /*Not implemented, should select desired item*/
            }
        });
    }

    public void onClick(View v)
    {
    }

    public boolean onTouch(View v, MotionEvent ev) {
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
        return startDrag(v);
    }

    public boolean startDrag(View v)
    {
        DragSource dragSource = (DragSource)v;

        mDragController.startDrag(v, dragSource, dragSource);

        return true;
    }

    public void onClickAddComponent(View v)
    {
        Component component = ComponentFactory.CreateComponent("Lightbulb");
        addNewComponent(component);
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
}

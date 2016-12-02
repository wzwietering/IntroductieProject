package com.edulectronics.tinycircuit.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.edulectronics.tinycircuit.Circuit.CircuitController;
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

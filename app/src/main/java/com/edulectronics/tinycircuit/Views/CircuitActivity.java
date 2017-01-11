package com.edulectronics.tinycircuit.Views;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Controllers.ConnectionController;
import com.edulectronics.tinycircuit.Controllers.LevelController;
import com.edulectronics.tinycircuit.Controllers.MessageController;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connection;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.Models.Factories.ComponentFactory;
import com.edulectronics.tinycircuit.Models.Menu;
import com.edulectronics.tinycircuit.Models.MessageArgs;
import com.edulectronics.tinycircuit.Models.MessageTypes;
import com.edulectronics.tinycircuit.Models.Scenarios.IScenario;
import com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios.FreePlayScenario;
import com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios.Scenario2;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.Views.Adapters.CircuitAdapter;
import com.edulectronics.tinycircuit.Views.Adapters.ExpandableListAdapter;
import com.edulectronics.tinycircuit.Views.Draggables.DeleteZone;
import com.edulectronics.tinycircuit.Views.Draggables.DragController;
import com.edulectronics.tinycircuit.Views.Draggables.DragLayer;
import com.edulectronics.tinycircuit.Views.Draggables.GridCell;
import com.edulectronics.tinycircuit.Views.Draggables.Interfaces.IDragSource;

import static com.edulectronics.tinycircuit.Models.MessageTypes.Explanation;

public class CircuitActivity extends Activity implements View.OnClickListener, View.OnTouchListener, View.OnLongClickListener {
    private DragController mDragController; // Object that handles a drag-drop sequence. It interacts with DragSource and DropTarget objects.
    private CircuitController circuitController;
    private ConnectionController connectionController;
    private LevelController levelController;
    private DragLayer mDragLayer; // The ViewGroup within which an object can be dragged.
    private Menu menu;
    private MessageController messageController = new MessageController(getFragmentManager());
    private boolean isInWireMode = false;
    private int cellSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_circuit);

        cellSize = getResources().getInteger(R.integer.cell_size);
        setControllers();
        initializeView();
    }

    private void initializeView() {
        createMenuButton();
        createDragControls(createCircuit());
        createMenu();
        createDrawView();
        showMessages();
    }

    private void showMessages() {
        if (levelController.getScenario().getClass() != FreePlayScenario.class) {
            messageController.displayMessage(new MessageArgs(levelController.getScenario().getPrompt(), Explanation));
        }
    }

    private void createMenuButton() {
        ImageView hamburger = (ImageView) findViewById(R.id.hamburger);
        hamburger.setImageResource(R.drawable.ic_hamburger);
    }

    private void createDrawView() {
        WireView wireView = (WireView) findViewById(R.id.draw_view);
        wireView.setControllers(circuitController);
    }

    private GridView createCircuit() {
        GridView circuitGrid = (GridView) findViewById(R.id.circuit);

        /*Prevents scrolling, stuff can still be rendered outside screen*/
        circuitGrid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    return true;
                }
                return false;
            }
        });

        circuitGrid.setNumColumns(circuitController.circuit.width);
        circuitGrid.setAdapter(new CircuitAdapter(this, circuitController));
        return circuitGrid;
    }

    private void setControllers() {
        Point size = getDisplaySize();
        connectionController = new ConnectionController((WireView) findViewById(R.id.draw_view), cellSize, cellSize);
        levelController = new LevelController(getIntent().getStringExtra("scenario"));
        // Width or height divided by cellsize fits the maxiumum amount of cells inside the screen
        circuitController = new CircuitController(size.x / cellSize, size.y / cellSize, levelController.getAvailableComponents());
        DeleteZone deleteZone = (DeleteZone) findViewById(R.id.delete_zone_view);
        deleteZone.setCircuitController(circuitController);
    }

    private Point getDisplaySize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    private void createDragControls(GridView grid) {
        mDragController = new DragController(this);
        mDragLayer = (DragLayer) findViewById(R.id.drag_layer);
        mDragLayer.setDragController(mDragController);
        mDragLayer.setGridView(grid);
        mDragController.setDragListener(mDragLayer);
    }

    /*This code adds a menu to the side*/
    private void createMenu() {
        menu = new Menu(this);
        ExpandableListView expandableList = (ExpandableListView) findViewById(R.id.expandablelist);
        ExpandableListAdapter adapter = new ExpandableListAdapter(
                this, menu.getHeaders(), menu.getChildren(), expandableList
        );
        expandableList.setAdapter(adapter);
        expandableList.setOnChildClickListener(onChildClick());
        expandableList.setOnGroupClickListener(onGroupClick());
    }

    public void onClick(View v) {
        if (!isInWireMode) {
            // Let clicked component handle the tap.
            if (circuitController.handleClick(((GridCell) v).mCellNumber)) {
                ((GridCell) v).resetImage();
            }
        }
    }

    public boolean onTouch(View v, MotionEvent ev) {
        boolean handledHere = false;
        final int action = ev.getAction();

        if (isInWireMode) {
            //Check if wire is touched, but only on the down. This is necessary to prevent line
            //removal on the up event.
            if(action == MotionEvent.ACTION_DOWN) {
                for (Connection connection : circuitController.getAllConnections()) {
                    if (connection.isTouched(ev)) {
                        Connector connector = new Connector();
                        connector.disconnect(connection.pointA, connection.pointB);
                        connectionController.redraw();
                    }
                }
            }

            Component component = ((GridCell) v).getComponent();
            if(component != null && action == MotionEvent.ACTION_DOWN) {
                connectionController.makeWire(component, ev);

                if (levelController.getScenario().isCompleted(circuitController.circuit)) {
                    scenarioCompleted();
                }
            }
        }

        return handledHere;
    }

    private void scenarioCompleted() {
        messageController.displayMessage(new MessageArgs(
                R.string.scenario_complete,
                MessageTypes.ScenarioComplete,
                true));
    }

    public boolean onLongClick(View v) {
        // Make sure the drag was started by a long press as opposed to a long click.
        // (Note: I got this from the Workspace object in the Android Launcher code.
        //  I think it is here to ensure that the device is still in touch mode as we start the drag operation.)
        if (!v.isInTouchMode()) {
            return false;
        }

        return startDrag(v);
    }

    public boolean startDrag(View v) {
        IDragSource dragSource = (IDragSource) v;
        mDragController.startDrag(v, dragSource, dragSource);
        return true;
    }

    public void onClickAddComponent(String text) {
        Component component = ComponentFactory.CreateComponent(text, 5.0);

        FrameLayout componentHolder = (FrameLayout) findViewById(R.id.component_source_frame);
        componentHolder.setVisibility(View.VISIBLE);

        if (componentHolder != null) {
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams (LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT,
                    Gravity.CENTER);
            GridCell newView = new GridCell(this);
            newView.setComponent(component);
            componentHolder.addView(newView, lp);
            newView.mCellNumber = -1;

            // Have this activity listen to touch and click events for the view.
            newView.setOnClickListener(this);
            newView.setOnLongClickListener(this);
            newView.setOnTouchListener(this);
        }

        NavigationView view = (NavigationView) findViewById(R.id.navigationview);
        ((DrawerLayout) findViewById(R.id.activity_main)).closeDrawer(view);
    }

    public ExpandableListView.OnGroupClickListener onGroupClick() {
        return new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (groupPosition == 0) {
                    LinearLayout group = (LinearLayout) v;
                    toggleMode(group);
                    NavigationView view = (NavigationView) findViewById(R.id.navigationview);
                    ((DrawerLayout) findViewById(R.id.activity_main)).closeDrawer(view);
                }
                return false;
            }
        };
    }

    public ExpandableListView.OnChildClickListener onChildClick() {
        return new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                LinearLayout group = (LinearLayout) ((LinearLayout) v).getChildAt(0);
                View child = group.getChildAt(1);
                String text = (String) ((TextView) child).getText();
                onClickAddComponent(text);
                return false;
            }
        };
    }

    public void toggleMode(LinearLayout linearLayout) {
        isInWireMode = !isInWireMode;
        if (isInWireMode) {
            linearLayout.setBackgroundResource(R.color.wiremode_on);
        } else {
            linearLayout.setBackgroundResource(R.color.wiremode_off);
        }
    }

    public void openMenu(View v) {
        ((DrawerLayout) findViewById(R.id.activity_main)).openDrawer(Gravity.LEFT);
    }

    public void startNextScenario() {
        // TODO: Implemented by scenariocontroller.
        // Obviously this is very very bad code. I know. I will fix it when we have a scenario-
        // controller.
        levelController.setScenario(new Scenario2(this.circuitController.circuit));
    }

    public void run(View view) {
        circuitController.run();
        ((GridView) findViewById(R.id.circuit)).invalidateViews();
    }
}

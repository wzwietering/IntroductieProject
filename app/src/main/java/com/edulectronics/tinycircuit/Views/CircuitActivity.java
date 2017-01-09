package com.edulectronics.tinycircuit.Views;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Controllers.MessageController;
import com.edulectronics.tinycircuit.Controllers.WireController;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connection;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.Models.MenuItem;
import com.edulectronics.tinycircuit.Models.MessageArgs;
import com.edulectronics.tinycircuit.Models.MessageTypes;
import com.edulectronics.tinycircuit.Models.Scenarios.IScenario;
import com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios.FreePlayScenario;
import com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios.Scenario2;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.Views.Adapters.CircuitAdapter;
import com.edulectronics.tinycircuit.Views.Adapters.ExpandableListAdapter;
import com.edulectronics.tinycircuit.Views.Draggables.DragController;
import com.edulectronics.tinycircuit.Views.Draggables.DragLayer;
import com.edulectronics.tinycircuit.Views.Draggables.GridCell;
import com.edulectronics.tinycircuit.Views.Draggables.Interfaces.IDragSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static com.edulectronics.tinycircuit.Models.MessageTypes.Explanation;

public class CircuitActivity extends Activity
        implements View.OnClickListener, View.OnTouchListener, View.OnLongClickListener { //  , AdapterView.OnItemClickListener
    private DragController mDragController;   // Object that handles a drag-drop sequence. It interacts with DragSource and DropTarget objects.
    private DragLayer mDragLayer;             // The ViewGroup within which an object can be dragged.
    private List<MenuItem> headers;
    private HashMap<MenuItem, List<MenuItem>> children;
    private CircuitController circuitController;
    private WireController wireController;
    private IScenario scenario;
    private Set<Component> availableComponents;
    private MessageController messageController = new MessageController(getFragmentManager());
    private boolean isInWireMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_circuit);

        int cellSize = getResources().getInteger(R.integer.cell_size);
        wireController = new WireController((WireView) findViewById(R.id.draw_view), cellSize, cellSize);

        ImageView hamburger = (ImageView) findViewById(R.id.hamburger);
        hamburger.setImageResource(R.drawable.ic_hamburger);

        scenario = (IScenario) getIntent().getSerializableExtra("scenario");
        availableComponents = scenario.getAvailableComponents();
        getController();
        GridView grid = setCircuit();
        createDragControls(grid);
        createMenu();
        createDrawView();

        if (scenario.getClass() != FreePlayScenario.class) {
            messageController.displayMessage(new MessageArgs(scenario.getPrompt(), Explanation));
        }
    }

    private void createDrawView() {
        WireView wireView = (WireView) findViewById(R.id.draw_view);
        wireView.setControllers(circuitController);
    }

    private GridView setCircuit() {
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
        circuitGrid.setAdapter(new CircuitAdapter(this));
        return circuitGrid;
    }

    private void getController() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int cellSize = getResources().getInteger(R.integer.cell_size);
        //Width or height divided by cellsize fits the maxiumum amount of cells inside the screen

        circuitController = CircuitController.getInstance();
        circuitController.setProperties(size.x / cellSize, size.y / cellSize, scenario.loadComponents());
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
        ExpandableListView expandableList = (ExpandableListView) findViewById(R.id.expandablelist);
        makeLists();

        ExpandableListAdapter adapter = new ExpandableListAdapter(
                this, headers, children, expandableList
        );
        expandableList.setAdapter(adapter);
        expandableList.setOnChildClickListener(onChildClick());
        expandableList.setOnGroupClickListener(onGroupClick());
    }

    /*Makes the groups and children*/
    private void makeLists() {
        headers = new ArrayList<>();
        String[] items = getResources().getStringArray(R.array.menuitems);
        children = new HashMap<>();
        /*Temporary! Should work different!*/
        int[] textures = {0, R.drawable.battery, R.drawable.lightbulb_on, R.drawable.resistor, R.drawable.switch_on};

        TypedArray typedArray = getResources().obtainTypedArray(R.array.categories);
        int length = typedArray.length();
        String[][] headings = new String[length][];

        for (int i = 0; i < length; i++) {
            int id = typedArray.getResourceId(i, 0);
            headings[i] = getResources().getStringArray(id);
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
                subitem.setIconImage(textures[i + j - 1]);
                heading.add(subitem);
            }
            children.put(headers.get(i), heading);
        }
    }

    public void onClick(View v) {
        if (!isInWireMode) {
            // Let clicked component handle the tap.
            if (CircuitController.getInstance().handleClick(((GridCell) v).mCellNumber)) {
                ((GridCell) v).resetImage();
            }
        }
    }

    public boolean onTouch(View v, MotionEvent ev) {
        boolean handledHere = false;
        final int action = ev.getAction();

        // In the situation where a long click is not needed to initiate a drag, simply start on the down event.
        if (isInWireMode) {
            //Check if wire is touched, but only on the down. This is necessary to prevent line
            //removal on the up event.
            if(action == MotionEvent.ACTION_DOWN) {
                for (Connection connection : circuitController.getAllConnections()) {
                    if (connection.isTouched(ev)) {
                        Connector connector = new Connector();
                        connector.disconnect(connection.pointA, connection.pointB);
                        wireController.redraw();
                    }
                }
            }

            Component component = ((GridCell) v).getComponent();
            if(component != null && action == MotionEvent.ACTION_DOWN) {
                wireController.makeWire(component, ev);

                if (scenario.isCompleted(circuitController.circuit)) {
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
        circuitController.addNewComponent(text, this);
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
        this.scenario = new Scenario2(this.circuitController.circuit);
    }

    public void run(View view) {
        circuitController.run();
        ((GridView)findViewById(R.id.circuit)).invalidateViews();
    }
}

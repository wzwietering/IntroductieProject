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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.edulectronics.tinycircuit.Views.Adapters.ListAdapter;
import com.edulectronics.tinycircuit.Views.Draggables.DragController;
import com.edulectronics.tinycircuit.Views.Draggables.DragLayer;
import com.edulectronics.tinycircuit.Views.Draggables.GridCell;
import com.edulectronics.tinycircuit.Views.Draggables.Interfaces.IDragSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.edulectronics.tinycircuit.Models.MessageTypes.Explanation;

public class CircuitActivity extends Activity
        implements View.OnClickListener, View.OnTouchListener, View.OnLongClickListener { //  , AdapterView.OnItemClickListener
    private DragController mDragController;   // Object that handles a drag-drop sequence. It interacts with DragSource and DropTarget objects.
    private DragLayer mDragLayer;             // The ViewGroup within which an object can be dragged.
    private List<MenuItem> componentlist;
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
        ListView listView = (ListView) findViewById(R.id.list);
        makeLists();

        ListAdapter adapter = new ListAdapter(
                this, componentlist, listView
        );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClick());
    }

    /*Makes the list of components*/
    private void makeLists() {
        componentlist = new ArrayList<>();
        int componentCount = availableComponents.size();
        if (componentCount == 0){
            componentCount = 4;
        }

        String[] items = getResources().getStringArray(R.array.menuitems);
        int[] textures = {R.drawable.battery, R.drawable.lightbulb_on, R.drawable.resistor, R.drawable.switch_on};

        for (int i = 0; i < componentCount; i++) {
            MenuItem item = new MenuItem();
            item.setIconName(items[i]);
            item.setIconImage(textures[i]);
            componentlist.add(item);
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
            Component component = ((GridCell) v).getComponent();
            if(action == MotionEvent.ACTION_DOWN) {
                for (Connection connection : circuitController.getAllConnections()) {
                    if (connection.isTouched(ev) && component == null) {
                        Connector connector = new Connector();
                        connector.disconnect(connection.pointA, connection.pointB);
                        wireController.redraw();
                    }
                }
            }

            if(component != null && action == MotionEvent.ACTION_DOWN) {
                wireController.makeWire(component, ev);
            }
        }
        checkScenarioComplete();

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

    public ListView.OnItemClickListener onItemClick() {
        return new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout group = (LinearLayout) view;
                TextView textView = (TextView) group.findViewById(R.id.submenu);
                String text = (String) textView.getText();
                onClickAddComponent(text);
            }
        };
    }

    public void toggleMode(View view) {
        isInWireMode = !isInWireMode;
        if (isInWireMode) {
            view.setBackgroundResource(R.color.wiremode_on);
        } else {
            view.setBackgroundResource(R.color.background);
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

    public void run(View view){
        circuitController.run();
        ((GridView)findViewById(R.id.circuit)).invalidateViews();
        checkScenarioComplete();
    }

    private void checkScenarioComplete(){
        if (scenario.isCompleted(circuitController.circuit)) {
            scenarioCompleted();
        }
    }
}

package com.edulectronics.tinycircuit.Views;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Controllers.ConnectionController;
import com.edulectronics.tinycircuit.Controllers.LevelController;
import com.edulectronics.tinycircuit.Controllers.MessageController;
import com.edulectronics.tinycircuit.DataStorage.VariableHandler;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connection;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.Models.Factories.ComponentFactory;
import com.edulectronics.tinycircuit.Models.MenuItem;
import com.edulectronics.tinycircuit.Models.MessageArgs;
import com.edulectronics.tinycircuit.Models.MessageTypes;
import com.edulectronics.tinycircuit.Models.Scenarios.DesignScenario;
import com.edulectronics.tinycircuit.Models.Scenarios.IScenario;
import com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios.FreePlayScenario;
import com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios.Scenario2;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.Views.Adapters.CircuitAdapter;
import com.edulectronics.tinycircuit.Views.Draggables.DeleteZone;
import com.edulectronics.tinycircuit.Views.Adapters.ListAdapter;
import com.edulectronics.tinycircuit.Views.Draggables.DragController;
import com.edulectronics.tinycircuit.Views.Draggables.DragLayer;
import com.edulectronics.tinycircuit.Views.Draggables.GridCell;
import com.edulectronics.tinycircuit.Views.Draggables.Interfaces.IDragSource;
import java.util.List;
import java.util.Random;

import static com.edulectronics.tinycircuit.Models.MessageTypes.Explanation;
import java.util.ArrayList;

public class CircuitActivity extends Activity implements View.OnClickListener, View.OnTouchListener, View.OnLongClickListener { //  , AdapterView.OnItemClickListener
    private DragController mDragController;   // Object that handles a drag-drop sequence. It interacts with DragSource and DropTarget objects.
    private DragLayer mDragLayer;             // The ViewGroup within which an object can be dragged.
    private List<MenuItem> componentlist;
    private CircuitController circuitController;
    private ConnectionController connectionController;
    private LevelController levelController;
    private MessageController messageController = new MessageController(getFragmentManager());
    private boolean isInWireMode = false;
    private int cellSize;
    private IScenario scenario;

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
            String prompt = getResources().getString(levelController.getScenario().getPrompt());
            messageController.displayMessage(new MessageArgs(prompt, Explanation));
        }
    }

    private void createMenuButton() {
        ImageView hamburger = (ImageView) findViewById(R.id.hamburger);
        hamburger.setImageResource(R.drawable.ic_hamburger);
    }

    private void createDrawView() {
        connectionController.setControllers(circuitController);
        connectionController.redrawWires();
    }

    private GridView createCircuit() {
        GridView circuitGrid = (GridView) findViewById(R.id.circuit);

        /*Prevents scrolling, stuff can still be rendered outside screen*/
        circuitGrid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return event.getAction() == MotionEvent.ACTION_MOVE;
            }
        });

        circuitGrid.setNumColumns(circuitController.circuit.width);
        circuitGrid.setAdapter(new CircuitAdapter(this, circuitController));
        return circuitGrid;
    }

    private void setControllers() {
        Point size = getDisplaySize();
        connectionController = new ConnectionController(this, cellSize, cellSize);
        levelController = new LevelController(getIntent().getStringExtra("scenario"));
        // Width or height divided by cellsize fits the maxiumum amount of cells inside the screen
        if (getIntent().getStringExtra("scenario").equals("freeplay")) {
            circuitController = new CircuitController(size.x / cellSize, size.y / cellSize);
        } else {
            circuitController = new CircuitController(size.x / cellSize, size.y / cellSize, levelController.loadComponents());
        }
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
        mDragController.setWireController(this.connectionController);
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
        int componentCount = levelController.getAvailableComponents().size();

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
            if (circuitController.handleClick(((GridCell) v).mCellNumber)) {
                ((GridCell) v).resetImage();
            }
        }
    }

    public boolean onTouch(View v, MotionEvent ev) {
        final int action = ev.getAction();

        // In the situation where a long click is not needed to initiate a drag, simply start on the down event.
        if (isInWireMode) {
            //Check if wire is touched, but only on the down. This is necessary to prevent line
            //removal on the up event.
            Component component = ((GridCell) v).getComponent();
            if(action == MotionEvent.ACTION_DOWN) {
                for (Connection connection : circuitController.getAllConnections()) {
                    if (connection.isTouched(ev) && component == null) {
                        Connector.disconnect(connection.pointA, connection.pointB);
                        connectionController.redrawWires();
                    }
                }
            }

            if(component != null && action == MotionEvent.ACTION_DOWN) {
                connectionController.makeWire(component, ev);
            }
        }
        return false;
    }

    private void scenarioCompleted() {
        VariableHandler variableHandler = new VariableHandler(getApplicationContext());
        variableHandler.saveProgress(levelController.getScenarioID());
        givePositiveFeedback();
        // after callback

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
        Component component = ComponentFactory.CreateComponent(text);

        FrameLayout componentHolder = (FrameLayout) findViewById(R.id.component_source_frame);
        componentHolder.setVisibility(View.VISIBLE);

        if (componentHolder != null) {
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams (LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT,
                    Gravity.CENTER);
            GridCell newView = new GridCell(circuitController, this);
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
        levelController.goToNextLevel();
        this.showMessages();
        // Reset the menu. There might be more components for the user to use.
        this.createMenu();
    }

    public void run(View view) {
        // Reset the circuit before animating it; so all wires start out white, and lightbulbs whole.
        connectionController.redrawWires();
        ((GridView)findViewById(R.id.circuit)).invalidateViews();

        // TODO: get the delay back from the circuitcontroller (which gets it from wirecontroller)
        // to delay the scenario complete check until the whole circuit has been animated.
        int delay = circuitController.run(this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkScenarioComplete();
            }
        }, delay == 0 ? delay : delay + 1000);
    }

    private void checkScenarioComplete(){
        //The boolean is used to give the user only negative feedback when they press the run button.
        //Giving negative feedback when this method runs using the onTouch method is a nightmare,
        //because you will get negative messages all the time.
        if (levelController.levelIsCompleted(circuitController.circuit)) {
            this.scenarioCompleted();
        } else if (levelController.getScenario().getClass() != FreePlayScenario.class) {
            giveNegativeFeedback();
        }
    }

    //Create a negative feedback message
    private void giveNegativeFeedback(){
        String[] negativeFeedback = getResources().getStringArray(R.array.negative_feedback);
        String feedback = getResources().getString(((DesignScenario) levelController.getScenario()).getHint());
        messageController.displayMessage(new MessageArgs(
                giveFeedback(negativeFeedback) + " " + feedback,
                MessageTypes.Mistake));
    }

    //Create a positive feedback message
    private void givePositiveFeedback(){
        String[] positiveFeedback = getResources().getStringArray(R.array.positive_feedback);
        messageController.displayMessage(new MessageArgs(
                giveFeedback(positiveFeedback),
                MessageTypes.ScenarioComplete,
                true));
    }

    //This methods returns a random string from an array. It is used to give the user more
    //interesting feedback, because the string is not always the same.
    private String giveFeedback(String[] options){
        Random random = new Random();
        return options[random.nextInt(options.length)];
    }

    public CircuitController getCircuitController(){
        return circuitController;
    }
}

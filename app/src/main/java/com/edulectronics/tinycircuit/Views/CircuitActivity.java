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
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.edulectronics.tinycircuit.Controllers.CircuitController;
import com.edulectronics.tinycircuit.Controllers.ConnectionController;
import com.edulectronics.tinycircuit.Controllers.LevelController;
import com.edulectronics.tinycircuit.Controllers.MediaController;
import com.edulectronics.tinycircuit.Controllers.MessageController;
import com.edulectronics.tinycircuit.DataStorage.VariableHandler;
import com.edulectronics.tinycircuit.Models.Components.Bell;
import com.edulectronics.tinycircuit.Models.Components.Component;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connection;
import com.edulectronics.tinycircuit.Models.Components.Connectors.Connector;
import com.edulectronics.tinycircuit.Models.Components.Switch;
import com.edulectronics.tinycircuit.Models.Factories.ComponentFactory;
import com.edulectronics.tinycircuit.Models.MenuItem;
import com.edulectronics.tinycircuit.Models.MessageArgs;
import com.edulectronics.tinycircuit.Models.MessageTypes;
import com.edulectronics.tinycircuit.Models.Scenarios.DesignScenario;
import com.edulectronics.tinycircuit.Models.Scenarios.ImplementedScenarios.FreePlayScenario;
import com.edulectronics.tinycircuit.R;
import com.edulectronics.tinycircuit.Views.Adapters.CircuitAdapter;
import com.edulectronics.tinycircuit.Views.Adapters.ListAdapter;
import com.edulectronics.tinycircuit.Views.Draggables.DeleteZone;
import com.edulectronics.tinycircuit.Views.Draggables.DragController;
import com.edulectronics.tinycircuit.Views.Draggables.DragLayer;
import com.edulectronics.tinycircuit.Views.Draggables.GridCell;
import com.edulectronics.tinycircuit.Views.Draggables.Interfaces.IDragSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.edulectronics.tinycircuit.Models.MessageTypes.Explanation;

public class CircuitActivity extends Activity implements View.OnClickListener, View.OnTouchListener, View.OnLongClickListener { //  , AdapterView.OnItemClickListener
    private DragController mDragController;   // Object that handles a drag-drop sequence. It interacts with DragSource and DropTarget objects.
    private List<MenuItem> componentlist;
    private CircuitController circuitController;
    private ConnectionController connectionController;
    private LevelController levelController;
    private MessageController messageController = new MessageController(getFragmentManager());
    private MediaController mediaController = new MediaController(this);
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
        showMessages(false);

        if(levelController.getScenario().getClass() == FreePlayScenario.class){
            ImageView hint = (ImageView) findViewById(R.id.Help);
            hint.setVisibility(View.GONE);
        }

        // Set invisible on start, to prevent square in the middle of the screen
        FrameLayout componentHolder = (FrameLayout) findViewById(R.id.component_source_frame);
        componentHolder.setVisibility(View.INVISIBLE);
    }

    private void showMessages(boolean allowHint) {
        if (levelController.getScenario().getClass() != FreePlayScenario.class) {
            String prompt = getResources().getString(levelController.getScenario().getPrompt());
            MessageArgs args = new MessageArgs(prompt, Explanation);
            args.allowHint = allowHint;
            messageController.displayMessage(args);
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
        connectionController = new ConnectionController(this, cellSize, cellSize);
        levelController = new LevelController(getIntent().getStringExtra("scenario"));
        // now we have the levelcontroller we can set the circuitcontroller.
        setCircuitController();
    }

    private void setCircuitController() {
        // Width or height divided by cellsize fits the maxiumum amount of cells inside the screen
        Point size = getDisplaySize();
        if (getIntent().getStringExtra("scenario").equals("freeplay")) {
            circuitController = new CircuitController(size.x / cellSize, size.y / cellSize);
        } else {
            circuitController = new CircuitController(size.x / cellSize, size.y / cellSize, levelController.loadComponents(), levelController.getScenarioID());
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
        DragLayer mDragLayer = (DragLayer) findViewById(R.id.drag_layer);
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
        int[] textures = {R.drawable.battery, R.drawable.lightbulb_on, R.drawable.resistor, R.drawable.switch_on, R.drawable.bell};

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
                Component component = circuitController.getComponent(((GridCell) v).mCellNumber);
                if(component.getClass() == Switch.class) {
                    ((GridCell) v).resetImage();
                    // Only animate the wires if the circuit was already running. Also check for
                    // scenario complete then, since it's more intuitive to do here also.
                    if(circuitController.isInRunningState) {
                        int delay = animateWires();
                        if(circuitController.isAnimating) {
                            delayedScenarioComplete(delay);
                        }
                    }
                } else if (component.getClass() == Bell.class){
                    ringBell((Bell) component);
                }
            }
        }
    }

    public boolean onTouch(View v, MotionEvent ev) {
        final int action = ev.getAction();

        // In the situation where a long click is not needed to initiate a drag, simply start on the down event.
        if (isInWireMode && ((GridCell) v).mCellNumber > -1) {
            //Check if wire is touched, but only on the down. This is necessary to prevent line
            //removal on the up event.
            Component component = ((GridCell) v).getComponent();
            if (action == MotionEvent.ACTION_DOWN) {
                for (Connection connection : circuitController.getAllConnections()) {
                    if (connection.isTouched(ev) && component == null) {
                        Connector.disconnect(connection.pointA, connection.pointB);
                        connectionController.redrawWires();
                    }
                }
            }
            // Only make a wire on the up action. The user might be making a long
            // press and we don't want to make a wire on long press (longPress is for dragging)
            if (component != null && action == MotionEvent.ACTION_UP) {
                connectionController.makeWire(component, ev);
            }

            circuitController.isInRunningState = false;
        }
        return false;
    }

    private void scenarioCompleted() {
        VariableHandler variableHandler = new VariableHandler(getApplicationContext());
        variableHandler.saveProgress(levelController.getScenarioID());
        givePositiveFeedback();
    }

    public boolean onLongClick(View v) {
        // Make sure the drag was started by a long press as opposed to a long click.
        // (Note: I got this from the Workspace object in the Android Launcher code.
        //  I think it is here to ensure that the device is still in touch mode as we start the drag operation.)
        return v.isInTouchMode() && startDrag(v);
    }

    public boolean startDrag(View v) {
        connectionController.cancelConnection();
        IDragSource dragSource = (IDragSource) v;
        mDragController.startDrag(v, dragSource, dragSource);
        return true;
    }

    public void onClickAddComponent(String text) {
        Component component = ComponentFactory.CreateComponent(text);

        FrameLayout componentHolder = (FrameLayout) findViewById(R.id.component_source_frame);
        componentHolder.setVisibility(View.VISIBLE);

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
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

        // initializeView calls showMessages, so only show the messages when there is no restart
        if (levelController.getScenario().resetCircuitOnStart()) {
            setCircuitController();
            initializeView();
        } else {
            this.showMessages(false);
        }
        // Reset the menu. There might be more components for the user to use.
        this.createMenu();
    }

    //The run method with view is necessary because the event handler of a button must be able to
    // pass a view, but we don't use the view, so this implementation is sufficient.
    public void run(View view) {
        if(!circuitController.isAnimating) {
            int delay = animateWires();
            delayedScenarioComplete(delay);
        }
    }

    //Animate the wires of the circuit
    private int animateWires(){
        // Reset the circuit before animating it; so all wires start out white, and lightbulbs whole.
        connectionController.redrawWires();
        ((GridView) findViewById(R.id.circuit)).invalidateViews();

        // get the delay back from the circuitcontroller to delay the scenario complete check
        // until the whole circuit has been animated.
        return circuitController.run(this);
    }

    //Check if the scenario is completed after a certain delay
    private void delayedScenarioComplete(final int delay){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkScenarioComplete(delay);
                circuitController.isAnimating = false;
            }
        }, delay == 0 ? delay : delay + 1000);
    }


    private void checkScenarioComplete(int delay) {
        //The boolean is used to give the user only negative feedback when they press the run button.
        //Giving negative feedback when this method runs using the onTouch method is a nightmare,
        //because you will get negative messages all the time.
        if(circuitController.isAnimating || delay == 0) {
            if (levelController.getScenario().getClass() != FreePlayScenario.class) {
                if (levelController.levelIsCompleted(circuitController.circuit, circuitController.getGraph())) {
                    this.scenarioCompleted();
                } else {
                    giveNegativeFeedback();
                }
            }
        }
    }

    public void getHelp(View view) {
        showMessages(true);
    }

    //Create a negative feedback message
    public void giveNegativeFeedback() {
        String[] negativeFeedback = getResources().getStringArray(R.array.negative_feedback);
        String feedback = getResources().getString(((DesignScenario) levelController.getScenario()).getHint());
        messageController.displayMessage(new MessageArgs(
                giveFeedback(negativeFeedback) + " " + feedback,
                MessageTypes.Mistake));
    }

    //Create a positive feedback message
    private void givePositiveFeedback() {
        String[] positiveFeedback = getResources().getStringArray(R.array.positive_feedback);
        String feedback = giveFeedback(positiveFeedback) + " " + getResources().getString(
                levelController.getScenario().getCompletePrompt());
        messageController.displayMessage(new MessageArgs(
                feedback,
                MessageTypes.ScenarioComplete,
                true));
    }

    //This methods returns a random string from an array. It is used to give the user more
    //interesting feedback, because the string is not always the same.
    private String giveFeedback(String[] options) {
        Random random = new Random();
        return options[random.nextInt(options.length)];
    }

    public CircuitController getCircuitController() {
        return circuitController;
    }

    public void ringBell(Bell bell){
        final Bell _bell = bell;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mediaController.playSound(_bell.getSound());
            }
        });
        thread.run();
    }
}

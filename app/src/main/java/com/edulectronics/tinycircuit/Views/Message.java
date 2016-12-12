package com.edulectronics.tinycircuit.Views;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.edulectronics.tinycircuit.R;

/**
 * Created by Maaike on 12-12-2016.
 */

public class Message extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        getDialog().setTitle("Test Dialog");

        Bundle args = getArguments();
        int messageId = args.getInt("message", 0);
        int titleId = args.getInt("title", 0);

        TextView explanation = (TextView) view.findViewById(R.id.scenario_explanation);
        explanation.setText(messageId);

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(titleId);

        Button dismiss = (Button) view.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }
}

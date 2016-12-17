package com.edulectronics.tinycircuit.Views;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.edulectronics.tinycircuit.Models.MessageArgs;
import com.edulectronics.tinycircuit.R;

/**
 * Created by Maaike on 12-12-2016.
 */

public class Message extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_message, container, false);
        Bundle args = getArguments();
        MessageArgs messageArgs = (MessageArgs) args.getSerializable("messageargs");
        setContent(messageArgs, view);
        setDismissButtonAction(messageArgs, view);

        return view;
    }

    private void setDismissButtonAction(final MessageArgs args, View view) {

        Button dismiss = (Button) view.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                if(args.endActivity) {
                    Message.this.getActivity().finish();
                }
                if(args.goToNextScenario) {
                    ((CircuitActivity)Message.this.getActivity()).startNextScenario();
                }
            }
        });
    }

    private void setContent(final MessageArgs args, View view) {

        TextView explanation = (TextView) view.findViewById(R.id.scenario_explanation);
        explanation.setText(args.message);

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(args.title);
    }
}

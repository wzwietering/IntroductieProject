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
    View view;
    MessageArgs messageArgs;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message, container, false);
        messageArgs = (MessageArgs) getArguments().getSerializable("messageargs");

        setContent();
        setDismissButtonAction();
        setHintButton();

        return view;
    }

    private void setDismissButtonAction() {
        Button dismiss = (Button) view.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                if(messageArgs.endActivity) {
                    Message.this.getActivity().finish();
                }
                if(messageArgs.goToNextScenario) {
                    ((CircuitActivity)Message.this.getActivity()).startNextScenario();
                }
            }
        });
    }

    private void setHintButton() {
        Button hintButton = (Button) view.findViewById(R.id.hint);
        if(messageArgs.allowHint) {
            hintButton.setVisibility(View.VISIBLE);
            hintButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dismiss();
                    ((CircuitActivity) Message.this.getActivity()).giveNegativeFeedback();
                }
            });
        } else {
            hintButton.setVisibility(View.INVISIBLE);
        }
    }

    private void setContent() {
        TextView explanation = (TextView) view.findViewById(R.id.scenario_explanation);
        explanation.setText(messageArgs.message);

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(messageArgs.title);
    }
}

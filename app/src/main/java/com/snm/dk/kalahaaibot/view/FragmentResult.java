package com.snm.dk.kalahaaibot.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.snm.dk.kalahaaibot.R;
import com.snm.dk.kalahaaibot.control.VisualControl;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentResult extends Fragment implements View.OnClickListener {


    private TextView tvResult;
    private Button btnPlayAgain;
    private FrameLayout layer, fragmentContainer;

    public FragmentResult() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_result, container, false);

        tvResult = view.findViewById(R.id.tvResult);
        btnPlayAgain = view.findViewById(R.id.btnPlayAgain);
        layer = getActivity().findViewById(R.id.layer);
        fragmentContainer = getActivity().findViewById(R.id.fragmentContainer);

        tvResult.setText("Player 1 Wins");

        btnPlayAgain.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (btnPlayAgain == v) {
            View[] views = {layer, fragmentContainer};
            VisualControl.hideDialog(getActivity(), views);
        }
    }
}

package com.jiajia.mypractisedemos.module.dialog;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiajia.mypractisedemos.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CustomDialogFragment extends Fragment {

    public static final String TAG = "CustomDialogFragment";

    public CustomDialogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_custom_dialog, container, false);

        root.findViewById(R.id.btn_close).setOnClickListener(v -> {
            ((CustomDialogActivity)getActivity()).openOrCloseFragment(false);
        });


        return root;
    }
}
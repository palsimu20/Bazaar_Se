package com.client.bazaarse.fragement;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.client.bazaarse.activity.MainActivity;
import com.client.bazaarse.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PrivacyPolicy extends Fragment {
   private View view;
   private Button button;

    public PrivacyPolicy() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_privacy_policy, container, false);
        button=view.findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        return view;
    }
}

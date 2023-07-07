package com.nielsero.driverslicenseexam;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Shows a list of candidates older than 20 years and applying for practice exam
 * for a heavy drivers license.
 */
public class ListFragment extends Fragment {
    private MainActivity mainActivity;
    private DBHelper dbHelper;
    private ArrayAdapter<Candidate> listAdapter;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mainActivity = (MainActivity) getActivity();
        dbHelper = mainActivity.getDbHelper();
        listAdapter = mainActivity.getArrayAdapter();
        listView = view.findViewById(R.id.list_view);

        listView.setAdapter(listAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int candidateID = mainActivity.getCandidateID(position);
                dbHelper.deleteCandidate(candidateID);
                Toast.makeText(mainActivity, "Candidate deleted", Toast.LENGTH_SHORT).show();
                mainActivity.updateCandidates();
                return true;
            }
        });

        return view;
    }
}
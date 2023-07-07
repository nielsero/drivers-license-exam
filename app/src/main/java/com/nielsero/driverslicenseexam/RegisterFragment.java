package com.nielsero.driverslicenseexam;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * For registration of new candidates.
 */
public class RegisterFragment extends Fragment {
    private MainActivity mainActivity;
    private DBHelper dbHelper;
    private EditText candidateName;
    private EditText candidateBI;
    private EditText candidateAge;
    private EditText candidateContact;
    private RadioButton examPractice;
    private RadioButton examTheory;
    private RadioButton licenseHeavy;
    private RadioButton licenseLight;
    private Button registerBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        mainActivity = (MainActivity) getActivity();
        dbHelper = mainActivity.getDbHelper();
        candidateName = view.findViewById(R.id.candidate_name);
        candidateBI = view.findViewById(R.id.candidate_bi);
        candidateAge = view.findViewById(R.id.candidate_age);
        candidateContact = view.findViewById(R.id.candidate_contact);
        examPractice = view.findViewById(R.id.exam_practice);
        examTheory = view.findViewById(R.id.exam_theory);
        licenseHeavy = view.findViewById(R.id.license_heavy);
        licenseLight = view.findViewById(R.id.license_light);
        registerBtn = view.findViewById(R.id.register_btn);

        examPractice.setChecked(true);
        licenseHeavy.setChecked(true);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = candidateName.getText().toString();
                String bi = candidateBI.getText().toString();
                String age = candidateAge.getText().toString();
                String contact = candidateContact.getText().toString();
                String examType;
                String licenseCategory;

                // Input validation
                if(name.trim().equals("") || bi.trim().equals("") || age.trim().equals("") || contact.trim().equals("")) {
                    Toast.makeText(getActivity(), "Invalid input (Blank text)", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(examPractice.isChecked()) {
                    examType = "practice";
                } else {
                    examType = "theory";
                }

                if(licenseHeavy.isChecked()) {
                    licenseCategory = "heavy";
                } else {
                    licenseCategory = "light";
                }

                // All input is valid, so we can insert candidate into database
                long insert = dbHelper.insertCandidate(name, bi, Integer.parseInt(age), contact, examType, licenseCategory);

                if(insert != -1) {
                    Toast.makeText(mainActivity, "Candidate registered", Toast.LENGTH_SHORT).show();
                    resetFields();
                    mainActivity.updateCandidates();
                } else {
                    Toast.makeText(mainActivity, "Error in registration", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    /**
     * Resets edit texts and radio buttons.
     */
    private void resetFields() {
        candidateName.setText("");
        candidateBI.setText("");
        candidateAge.setText("");
        candidateContact.setText("");
        examPractice.setChecked(true);
        licenseHeavy.setChecked(true);
    }
}
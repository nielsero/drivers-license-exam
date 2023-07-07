package com.nielsero.driverslicenseexam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.nielsero.driverslicenseexam.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Main screen of the application.
 */
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    private DBHelper dbHelper;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Candidate> candidates;
    private ArrayAdapter<Candidate> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        dbHelper = new DBHelper(this);
        candidates = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.simple_list_item, R.id.item, candidates);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mainBinding.viewPager.setAdapter(viewPagerAdapter);

        updateCandidates();
    }

    /**
     * Updates candidates list with current candidates in database that are older than 20 years
     * and applying for a practice exam for a heavy drivers license.
     */
    public void updateCandidates() {
        candidates.clear();
        for(Candidate c : dbHelper.getAllCandidates()) {
            if(c.getAge() > 20 && c.getExamType().equals("practice") && c.getLicenseCategory().equals("heavy")) {
                candidates.add(c);
            }
        }
        arrayAdapter.notifyDataSetChanged();
    }

    /**
     * Gets the id of candidate at specific position on the candidates list.
     * @param position position of candidate in candidates list
     * @return candidate id
     */
    public int getCandidateID(int position) {
        Candidate c = candidates.get(position);
        return c.getId();
    }

    public ArrayAdapter<Candidate> getArrayAdapter() {
        return arrayAdapter;
    }

    public DBHelper getDbHelper() {
        return dbHelper;
    }
}
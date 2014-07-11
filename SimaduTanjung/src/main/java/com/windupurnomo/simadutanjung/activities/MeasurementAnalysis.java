package com.windupurnomo.simadutanjung.activities;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;

import com.cengalabs.flatui.FlatUI;
import com.windupurnomo.simadutanjung.R;
import com.windupurnomo.simadutanjung.entities.FragmentDataShare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MeasurementAnalysis extends ActionBarActivity {
    private static float daya;
    private int theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_analysis);
        String garduNo = getIntent().getStringExtra("garduNo");
        daya = getIntent().getFloatExtra("daya", 0f);
        theme = getIntent().getIntExtra("theme", 0);
        getActionBar().setTitle("Pengukuran " + garduNo);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(theme);
        getActionBar().setBackgroundDrawable(FlatUI.getActionBarDrawable(this, theme, false, 2));
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.measurement_analysis, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private EditText bebanTrafo, persenBeban,
                ketidakseimbanganA, ketidakseimbanganB, ketidakseimbanganC, ketidakseimbanganD,
                rekomendasiAR, rekomendasiAS, rekomendasiAT,
                rekomendasiBR, rekomendasiBS, rekomendasiBT,
                rekomendasiCR, rekomendasiCS, rekomendasiCT,
                rekomendasiDR, rekomendasiDS, rekomendasiDT;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_measurement_analysis, container, false);
            initField(rootView);
            return rootView;
        }

        private void initField(View rootView){
            bebanTrafo = (EditText) rootView.findViewById(R.id.beban_trafo);
            persenBeban = (EditText) rootView.findViewById(R.id.persen_beban);

            ketidakseimbanganA = (EditText) rootView.findViewById(R.id.ketidakseimbangan_a);
            ketidakseimbanganB = (EditText) rootView.findViewById(R.id.ketidakseimbangan_b);
            ketidakseimbanganC = (EditText) rootView.findViewById(R.id.ketidakseimbangan_c);
            ketidakseimbanganD = (EditText) rootView.findViewById(R.id.ketidakseimbangan_d);

            rekomendasiAR = (EditText) rootView.findViewById(R.id.rekomendasi_a_r);
            rekomendasiAS = (EditText) rootView.findViewById(R.id.rekomendasi_a_s);
            rekomendasiAT = (EditText) rootView.findViewById(R.id.rekomendasi_a_t);

            rekomendasiBR = (EditText) rootView.findViewById(R.id.rekomendasi_b_r);
            rekomendasiBS = (EditText) rootView.findViewById(R.id.rekomendasi_b_s);
            rekomendasiBT = (EditText) rootView.findViewById(R.id.rekomendasi_b_t);

            rekomendasiCR = (EditText) rootView.findViewById(R.id.rekomendasi_c_r);
            rekomendasiCS = (EditText) rootView.findViewById(R.id.rekomendasi_c_s);
            rekomendasiCT = (EditText) rootView.findViewById(R.id.rekomendasi_c_t);

            rekomendasiDR = (EditText) rootView.findViewById(R.id.rekomendasi_d_r);
            rekomendasiDS = (EditText) rootView.findViewById(R.id.rekomendasi_d_s);
            rekomendasiDT = (EditText) rootView.findViewById(R.id.rekomendasi_d_t);


            float bebanTrafox = (FragmentDataShare.rn * FragmentDataShare.phaseR +
                    FragmentDataShare.sn * FragmentDataShare.phaseS +
                    FragmentDataShare.tn * FragmentDataShare.phaseT) / 1000;
            float persenBebanx = (bebanTrafox/daya) * 100;
            MeasurementAnalysis ma = new MeasurementAnalysis();
            float maxBebanA = ma.getMax(FragmentDataShare.phaseRBebanA,
                    FragmentDataShare.phaseSBebanA, FragmentDataShare.phaseTBebanA);
            float maxBebanB = ma.getMax(FragmentDataShare.phaseRBebanB,
                    FragmentDataShare.phaseSBebanB, FragmentDataShare.phaseTBebanB);
            float maxBebanC = ma.getMax(FragmentDataShare.phaseRBebanC,
                    FragmentDataShare.phaseSBebanC, FragmentDataShare.phaseTBebanC);
            float maxBebanD = ma.getMax(FragmentDataShare.phaseRBebanD,
                    FragmentDataShare.phaseSBebanD, FragmentDataShare.phaseTBebanD);

            float minBebanA = ma.getMin(FragmentDataShare.phaseRBebanA,
                    FragmentDataShare.phaseSBebanA, FragmentDataShare.phaseTBebanA);
            float minBebanB = ma.getMin(FragmentDataShare.phaseRBebanB,
                    FragmentDataShare.phaseSBebanB, FragmentDataShare.phaseTBebanB);
            float minBebanC = ma.getMin(FragmentDataShare.phaseRBebanC,
                    FragmentDataShare.phaseSBebanC, FragmentDataShare.phaseTBebanC);
            float minBebanD = ma.getMin(FragmentDataShare.phaseRBebanD,
                    FragmentDataShare.phaseSBebanD, FragmentDataShare.phaseTBebanD);

            float ketidakseimbanganAx = (maxBebanA - minBebanA)/maxBebanA * 100;
            float ketidakseimbanganBx = (maxBebanB - minBebanB)/maxBebanB * 100;
            float ketidakseimbanganCx = (maxBebanC - minBebanC)/maxBebanC * 100;
            float ketidakseimbanganDx = (maxBebanD - minBebanD)/maxBebanD * 100;

            float meanA = (FragmentDataShare.phaseRBebanA + FragmentDataShare.phaseSBebanA + FragmentDataShare.phaseTBebanA)/3;
            float meanB = (FragmentDataShare.phaseRBebanB + FragmentDataShare.phaseSBebanB + FragmentDataShare.phaseTBebanB)/3;
            float meanC = (FragmentDataShare.phaseRBebanC + FragmentDataShare.phaseSBebanC + FragmentDataShare.phaseTBebanC)/3;
            float meanD = (FragmentDataShare.phaseRBebanD + FragmentDataShare.phaseSBebanD + FragmentDataShare.phaseTBebanD)/3;
            float rekomendasiARx = meanA - FragmentDataShare.phaseRBebanA;
            float rekomendasiASx = meanA - FragmentDataShare.phaseSBebanA;
            float rekomendasiATx = meanA - FragmentDataShare.phaseTBebanA;

            float rekomendasiBRx = meanB - FragmentDataShare.phaseRBebanB;
            float rekomendasiBSx = meanB - FragmentDataShare.phaseSBebanB;
            float rekomendasiBTx = meanB - FragmentDataShare.phaseTBebanB;

            float rekomendasiCRx = meanC - FragmentDataShare.phaseRBebanC;
            float rekomendasiCSx = meanC - FragmentDataShare.phaseSBebanC;
            float rekomendasiCTx = meanC - FragmentDataShare.phaseTBebanC;

            float rekomendasiDRx = meanD - FragmentDataShare.phaseRBebanD;
            float rekomendasiDSx = meanD - FragmentDataShare.phaseSBebanD;
            float rekomendasiDTx = meanD - FragmentDataShare.phaseTBebanD;

            List<Float> xx = new ArrayList<Float>();

            bebanTrafo.setText(Float.toString(bebanTrafox));
            persenBeban.setText(Float.toString(persenBebanx));
            ketidakseimbanganA.setText(Float.toString(ketidakseimbanganAx));
            ketidakseimbanganB.setText(Float.toString(ketidakseimbanganBx));
            ketidakseimbanganC.setText(Float.toString(ketidakseimbanganCx));
            ketidakseimbanganD.setText(Float.toString(ketidakseimbanganDx));
            rekomendasiAR.setText(Float.toString(rekomendasiARx));
            rekomendasiAS.setText(Float.toString(rekomendasiASx));
            rekomendasiAT.setText(Float.toString(rekomendasiATx));
            
            rekomendasiBR.setText(Float.toString(rekomendasiBRx));
            rekomendasiBS.setText(Float.toString(rekomendasiBSx));
            rekomendasiBT.setText(Float.toString(rekomendasiBTx));

            rekomendasiCR.setText(Float.toString(rekomendasiCRx));
            rekomendasiCS.setText(Float.toString(rekomendasiCSx));
            rekomendasiCT.setText(Float.toString(rekomendasiCTx));

            rekomendasiDR.setText(Float.toString(rekomendasiDRx));
            rekomendasiDS.setText(Float.toString(rekomendasiDSx));
            rekomendasiDT.setText(Float.toString(rekomendasiDTx));
        }
    }

    private float getMax(float a, float b, float c){
        List<Float> nums = new ArrayList<Float>();
        nums.add(a);
        nums.add(b);
        nums.add(c);
        return Collections.max(nums);
    }

    private float getMin(float a, float b, float c){
        List<Float> nums = new ArrayList<Float>();
        nums.add(a);
        nums.add(b);
        nums.add(c);
        return Collections.min(nums);
    }

    private float getMean(Float[] nums){
        float sum = 0;
        for(float f : nums) sum += f;
        return sum/nums.length;
    }

}

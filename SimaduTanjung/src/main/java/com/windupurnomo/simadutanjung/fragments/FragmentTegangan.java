package com.windupurnomo.simadutanjung.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.windupurnomo.simadutanjung.R;
import com.windupurnomo.simadutanjung.activities.MeasurementActivity;
import com.windupurnomo.simadutanjung.entities.FragmentDataShare;

/**
 * Created by Windu Purnomo on 7/5/14.
 */
public class FragmentTegangan extends Fragment {
    public FragmentTegangan() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tegangan, container, false);
        rn = (EditText) rootView.findViewById(R.id.tegangan_rn);
        sn = (EditText) rootView.findViewById(R.id.tegangan_sn);
        tn = (EditText) rootView.findViewById(R.id.tegangan_tn);
        rs = (EditText) rootView.findViewById(R.id.tegangan_rs);
        st = (EditText) rootView.findViewById(R.id.tegangan_st);
        rt = (EditText) rootView.findViewById(R.id.tegangan_rt);

        rnA = (EditText) rootView.findViewById(R.id.tegangan_rn_a);
        snA = (EditText) rootView.findViewById(R.id.tegangan_sn_a);
        tnA = (EditText) rootView.findViewById(R.id.tegangan_tn_a);
        rsA = (EditText) rootView.findViewById(R.id.tegangan_rs_a);
        stA = (EditText) rootView.findViewById(R.id.tegangan_st_a);
        rtA = (EditText) rootView.findViewById(R.id.tegangan_rt_a);
        tiangUjungA = (EditText) rootView.findViewById(R.id.ujung_tiang_a);

        rnB = (EditText) rootView.findViewById(R.id.tegangan_rn_b);
        snB = (EditText) rootView.findViewById(R.id.tegangan_sn_b);
        tnB = (EditText) rootView.findViewById(R.id.tegangan_tn_b);
        rsB = (EditText) rootView.findViewById(R.id.tegangan_rs_b);
        stB = (EditText) rootView.findViewById(R.id.tegangan_st_b);
        rtB = (EditText) rootView.findViewById(R.id.tegangan_rt_b);
        tiangUjungB = (EditText) rootView.findViewById(R.id.ujung_tiang_b);

        rnC = (EditText) rootView.findViewById(R.id.tegangan_rn_c);
        snC = (EditText) rootView.findViewById(R.id.tegangan_sn_c);
        tnC = (EditText) rootView.findViewById(R.id.tegangan_tn_c);
        rsC = (EditText) rootView.findViewById(R.id.tegangan_rs_c);
        stC = (EditText) rootView.findViewById(R.id.tegangan_st_c);
        rtC = (EditText) rootView.findViewById(R.id.tegangan_rt_c);
        tiangUjungC = (EditText) rootView.findViewById(R.id.ujung_tiang_b);

        rnD = (EditText) rootView.findViewById(R.id.tegangan_rn_d);
        snD = (EditText) rootView.findViewById(R.id.tegangan_sn_d);
        tnD = (EditText) rootView.findViewById(R.id.tegangan_tn_d);
        rsD = (EditText) rootView.findViewById(R.id.tegangan_rs_d);
        stD = (EditText) rootView.findViewById(R.id.tegangan_st_d);
        rtD = (EditText) rootView.findViewById(R.id.tegangan_rt_d);
        tiangUjungD = (EditText) rootView.findViewById(R.id.ujung_tiang_d);


        /* Inisiasi Data */
        rn.setText(Float.toString(FragmentDataShare.rn));
        sn.setText(Float.toString(FragmentDataShare.sn));
        tn.setText(Float.toString(FragmentDataShare.tn));
        rs.setText(Float.toString(FragmentDataShare.rs));
        st.setText(Float.toString(FragmentDataShare.st));
        rt.setText(Float.toString(FragmentDataShare.rt));

        rnA.setText(Float.toString(FragmentDataShare.rnA));
        snA.setText(Float.toString(FragmentDataShare.snA));
        tnA.setText(Float.toString(FragmentDataShare.tnA));
        rsA.setText(Float.toString(FragmentDataShare.rsA));
        stA.setText(Float.toString(FragmentDataShare.stA));
        rtA.setText(Float.toString(FragmentDataShare.rtA));
        tiangUjungA.setText(FragmentDataShare.tiangUjungA);

        rnB.setText(Float.toString(FragmentDataShare.rnB));
        snB.setText(Float.toString(FragmentDataShare.snB));
        tnB.setText(Float.toString(FragmentDataShare.tnB));
        rsB.setText(Float.toString(FragmentDataShare.rsB));
        stB.setText(Float.toString(FragmentDataShare.stB));
        rtB.setText(Float.toString(FragmentDataShare.rtB));
        tiangUjungB.setText(FragmentDataShare.tiangUjungB);

        rnC.setText(Float.toString(FragmentDataShare.rnC));
        snC.setText(Float.toString(FragmentDataShare.snC));
        tnC.setText(Float.toString(FragmentDataShare.tnC));
        rsC.setText(Float.toString(FragmentDataShare.rsC));
        stC.setText(Float.toString(FragmentDataShare.stC));
        rtC.setText(Float.toString(FragmentDataShare.rtC));
        tiangUjungC.setText(FragmentDataShare.tiangUjungC);

        rnD.setText(Float.toString(FragmentDataShare.rnD));
        snD.setText(Float.toString(FragmentDataShare.snD));
        tnD.setText(Float.toString(FragmentDataShare.tnD));
        rsD.setText(Float.toString(FragmentDataShare.rsD));
        stD.setText(Float.toString(FragmentDataShare.stD));
        rtD.setText(Float.toString(FragmentDataShare.rtD));
        tiangUjungD.setText(FragmentDataShare.tiangUjungD);
        

        /* Add Listener */
        rn.addTextChangedListener(twRn);
        sn.addTextChangedListener(twSn);
        tn.addTextChangedListener(twTn);
        rs.addTextChangedListener(twRs);
        st.addTextChangedListener(twSt);
        rt.addTextChangedListener(twRt);

        rnA.addTextChangedListener(twRnA);
        snA.addTextChangedListener(twSnA);
        tnA.addTextChangedListener(twTnA);
        rsA.addTextChangedListener(twRsA);
        stA.addTextChangedListener(twStA);
        rtA.addTextChangedListener(twRtA);
        tiangUjungA.addTextChangedListener(twTiangUjungA);

        rnB.addTextChangedListener(twRnB);
        snB.addTextChangedListener(twSnB);
        tnB.addTextChangedListener(twTnB);
        rsB.addTextChangedListener(twRsB);
        stB.addTextChangedListener(twStB);
        rtB.addTextChangedListener(twRtB);
        tiangUjungB.addTextChangedListener(twTiangUjungB);

        rnC.addTextChangedListener(twRnC);
        snC.addTextChangedListener(twSnC);
        tnC.addTextChangedListener(twTnC);
        rsC.addTextChangedListener(twRsC);
        stC.addTextChangedListener(twStC);
        rtC.addTextChangedListener(twRtC);
        tiangUjungC.addTextChangedListener(twTiangUjungC);

        rnD.addTextChangedListener(twRnD);
        snD.addTextChangedListener(twSnD);
        tnD.addTextChangedListener(twTnD);
        rsD.addTextChangedListener(twRsD);
        stD.addTextChangedListener(twStD);
        rtD.addTextChangedListener(twRtD);
        tiangUjungD.addTextChangedListener(twTiangUjungD);
        

        return rootView;
    }

    private EditText rn, sn, tn, rs, st, rt,
            rnA, snA, tnA, rsA, stA, rtA, tiangUjungA,
            rnB, snB, tnB, rsB, stB, rtB, tiangUjungB,
            rnC, snC, tnC, rsC, stC, rtC, tiangUjungC,
            rnD, snD, tnD, rsD, stD, rtD, tiangUjungD;


    TextWatcher twRn = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.rn = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twSn = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.sn = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twTn = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.tn = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twRs = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                try {
                    String sss = s.toString();
                    FragmentDataShare.rs= Float.parseFloat(sss);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twSt = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.st = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twRt = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.rt = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twRnA = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.rnC = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twSnA = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.snA = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twTnA = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.tnA = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twRsA = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.rsA = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twStA = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.stA = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twRtA = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.rtA = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twTiangUjungA = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.tiangUjungA = sss;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };


    TextWatcher twRnB = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.rnB = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twSnB = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.snB = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twTnB = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.tnB = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twRsB = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.rsB = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twStB = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.stB = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twRtB = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.rtB = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twTiangUjungB = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.tiangUjungB = sss;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };








    TextWatcher twRnC = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.rnC = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twSnC = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.snC = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twTnC = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.tnC = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twRsC = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.rsC = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twStC = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.stC = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twRtC = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.rtC = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twTiangUjungC = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.tiangUjungC = sss;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twRnD = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.rnD = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twSnD = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.snD = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twTnD = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.tnD = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twRsD = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.rsD = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twStD = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.stD = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twRtD = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.rtD = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twTiangUjungD = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.tiangUjungD = sss;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };
    
}
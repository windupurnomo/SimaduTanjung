package com.windupurnomo.simadutanjung.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.windupurnomo.simadutanjung.R;
import com.windupurnomo.simadutanjung.activities.MeasurementActivity;
import com.windupurnomo.simadutanjung.entities.FragmentDataShare;

/**
 * Created by Windu Purnomo on 7/5/14.
 */
public class FragmentArus extends android.support.v4.app.Fragment{

    public FragmentArus() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_arus, container, false);
        phaseR = (EditText) rootView.findViewById(R.id.phase_r);
        phaseS = (EditText) rootView.findViewById(R.id.phase_s);
        phaseT = (EditText) rootView.findViewById(R.id.phase_t);
        netral = (EditText) rootView.findViewById(R.id.netral);

        phaseRNhA = (EditText) rootView.findViewById(R.id.phase_r_nh_a);
        phaseSNhA = (EditText) rootView.findViewById(R.id.phase_s_nh_a);
        phaseTNhA = (EditText) rootView.findViewById(R.id.phase_t_nh_a);
        netralNhA = (EditText) rootView.findViewById(R.id.netral_nh_a);

        phaseRNhB = (EditText) rootView.findViewById(R.id.phase_r_nh_b);
        phaseSNhB = (EditText) rootView.findViewById(R.id.phase_s_nh_b);
        phaseTNhB = (EditText) rootView.findViewById(R.id.phase_t_nh_b);
        netralNhB = (EditText) rootView.findViewById(R.id.netral_nh_b);

        phaseRNhC = (EditText) rootView.findViewById(R.id.phase_r_nh_c);
        phaseSNhC = (EditText) rootView.findViewById(R.id.phase_s_nh_c);
        phaseTNhC = (EditText) rootView.findViewById(R.id.phase_t_nh_c);
        netralNhC = (EditText) rootView.findViewById(R.id.netral_nh_c);

        phaseRNhD = (EditText) rootView.findViewById(R.id.phase_r_nh_d);
        phaseSNhD = (EditText) rootView.findViewById(R.id.phase_s_nh_d);
        phaseTNhD = (EditText) rootView.findViewById(R.id.phase_t_nh_d);
        netralNhD = (EditText) rootView.findViewById(R.id.netral_nh_d);

        phaseRBebanA = (EditText) rootView.findViewById(R.id.phase_r_beban_a);
        phaseSBebanA = (EditText) rootView.findViewById(R.id.phase_s_beban_a);
        phaseTBebanA = (EditText) rootView.findViewById(R.id.phase_t_beban_a);
        netralBebanA = (EditText) rootView.findViewById(R.id.netral_beban_a);

        phaseRBebanB = (EditText) rootView.findViewById(R.id.phase_r_beban_b);
        phaseSBebanB = (EditText) rootView.findViewById(R.id.phase_s_beban_b);
        phaseTBebanB = (EditText) rootView.findViewById(R.id.phase_t_beban_b);
        netralBebanB = (EditText) rootView.findViewById(R.id.netral_beban_b);

        phaseRBebanC = (EditText) rootView.findViewById(R.id.phase_r_beban_c);
        phaseSBebanC = (EditText) rootView.findViewById(R.id.phase_s_beban_c);
        phaseTBebanC = (EditText) rootView.findViewById(R.id.phase_t_beban_c);
        netralBebanC = (EditText) rootView.findViewById(R.id.netral_beban_c);

        phaseRBebanD = (EditText) rootView.findViewById(R.id.phase_r_beban_d);
        phaseSBebanD = (EditText) rootView.findViewById(R.id.phase_s_beban_d);
        phaseTBebanD = (EditText) rootView.findViewById(R.id.phase_t_beban_d);
        netralBebanD = (EditText) rootView.findViewById(R.id.netral_beban_d);
        
        /*inisialisasi data*/
        phaseR.setText(Float.toString(FragmentDataShare.phaseR));
        phaseS.setText(Float.toString(FragmentDataShare.phaseS));
        phaseT.setText(Float.toString(FragmentDataShare.phaseT));
        netral.setText(Float.toString(FragmentDataShare.netral));

        phaseRNhA.setText(Float.toString(FragmentDataShare.phaseRNhA));
        phaseSNhA.setText(Float.toString(FragmentDataShare.phaseSNhA));
        phaseTNhA.setText(Float.toString(FragmentDataShare.phaseTNhA));
        netralNhA.setText(Float.toString(FragmentDataShare.netralNhA));

        phaseRNhB.setText(Float.toString(FragmentDataShare.phaseRNhB));
        phaseSNhB.setText(Float.toString(FragmentDataShare.phaseSNhB));
        phaseTNhB.setText(Float.toString(FragmentDataShare.phaseTNhB));
        netralNhB.setText(Float.toString(FragmentDataShare.netralNhB));

        phaseRNhC.setText(Float.toString(FragmentDataShare.phaseRNhC));
        phaseSNhC.setText(Float.toString(FragmentDataShare.phaseSNhC));
        phaseTNhC.setText(Float.toString(FragmentDataShare.phaseTNhC));
        netralNhC.setText(Float.toString(FragmentDataShare.netralNhC));

        phaseRNhD.setText(Float.toString(FragmentDataShare.phaseRNhD));
        phaseSNhD.setText(Float.toString(FragmentDataShare.phaseSNhD));
        phaseTNhD.setText(Float.toString(FragmentDataShare.phaseTNhD));
        netralNhD.setText(Float.toString(FragmentDataShare.netralNhD));

        phaseRBebanA.setText(Float.toString(FragmentDataShare.phaseRBebanA));
        phaseSBebanA.setText(Float.toString(FragmentDataShare.phaseSBebanA));
        phaseTBebanA.setText(Float.toString(FragmentDataShare.phaseTBebanA));
        netralBebanA.setText(Float.toString(FragmentDataShare.netralBebanA));

        phaseRBebanB.setText(Float.toString(FragmentDataShare.phaseRBebanB));
        phaseSBebanB.setText(Float.toString(FragmentDataShare.phaseSBebanB));
        phaseTBebanB.setText(Float.toString(FragmentDataShare.phaseTBebanB));
        netralBebanB.setText(Float.toString(FragmentDataShare.netralBebanB));

        phaseRBebanC.setText(Float.toString(FragmentDataShare.phaseRBebanC));
        phaseSBebanC.setText(Float.toString(FragmentDataShare.phaseSBebanC));
        phaseTBebanC.setText(Float.toString(FragmentDataShare.phaseTBebanC));
        netralBebanC.setText(Float.toString(FragmentDataShare.netralBebanC));

        phaseRBebanD.setText(Float.toString(FragmentDataShare.phaseRBebanD));
        phaseSBebanD.setText(Float.toString(FragmentDataShare.phaseSBebanD));
        phaseTBebanD.setText(Float.toString(FragmentDataShare.phaseTBebanD));
        netralBebanD.setText(Float.toString(FragmentDataShare.netralBebanD));

        /* Add Listener*/
        phaseR.addTextChangedListener(twPhaseR);
        phaseS.addTextChangedListener(twPhaseS);
        phaseT.addTextChangedListener(twPhaseT);
        netral.addTextChangedListener(twNetral);

        phaseRNhA.addTextChangedListener(twPhaseRNhA);
        phaseSNhA.addTextChangedListener(twPhaseSNhA);
        phaseTNhA.addTextChangedListener(twPhaseTNhA);
        netralNhA.addTextChangedListener(twNetralNhA);

        phaseRNhB.addTextChangedListener(twPhaseRNhB);
        phaseSNhB.addTextChangedListener(twPhaseSNhB);
        phaseTNhB.addTextChangedListener(twPhaseTNhB);
        netralNhB.addTextChangedListener(twNetralNhB);

        phaseRNhC.addTextChangedListener(twPhaseRNhC);
        phaseSNhC.addTextChangedListener(twPhaseSNhC);
        phaseTNhC.addTextChangedListener(twPhaseTNhC);
        netralNhC.addTextChangedListener(twNetralNhC);

        phaseRNhA.addTextChangedListener(twPhaseRNhD);
        phaseSNhA.addTextChangedListener(twPhaseSNhD);
        phaseTNhA.addTextChangedListener(twPhaseTNhD);
        netralNhA.addTextChangedListener(twNetralNhD);

        phaseRBebanA.addTextChangedListener(twPhaseRBebanA);
        phaseSBebanA.addTextChangedListener(twPhaseSBebanA);
        phaseTBebanA.addTextChangedListener(twPhaseTBebanA);
        netralBebanA.addTextChangedListener(twNetralBebanA);

        phaseRBebanB.addTextChangedListener(twPhaseRBebanB);
        phaseSBebanB.addTextChangedListener(twPhaseSBebanB);
        phaseTBebanB.addTextChangedListener(twPhaseTBebanB);
        netralBebanB.addTextChangedListener(twNetralBebanB);

        phaseRBebanC.addTextChangedListener(twPhaseRBebanC);
        phaseSBebanC.addTextChangedListener(twPhaseSBebanC);
        phaseTBebanC.addTextChangedListener(twPhaseTBebanC);
        netralBebanC.addTextChangedListener(twNetralBebanC);

        phaseRBebanA.addTextChangedListener(twPhaseRBebanD);
        phaseSBebanA.addTextChangedListener(twPhaseSBebanD);
        phaseTBebanA.addTextChangedListener(twPhaseTBebanD);
        netralBebanA.addTextChangedListener(twNetralBebanD);

        return rootView;
    }

    private EditText phaseR, phaseS, phaseT, netral,
            phaseRNhA, phaseSNhA, phaseTNhA, netralNhA,
            phaseRNhB, phaseSNhB, phaseTNhB, netralNhB,
            phaseRNhC, phaseSNhC, phaseTNhC, netralNhC,
            phaseRNhD, phaseSNhD, phaseTNhD, netralNhD,
            phaseRBebanA, phaseSBebanA, phaseTBebanA, netralBebanA,
            phaseRBebanB, phaseSBebanB, phaseTBebanB, netralBebanB,
            phaseRBebanC, phaseSBebanC, phaseTBebanC, netralBebanC,
            phaseRBebanD, phaseSBebanD, phaseTBebanD, netralBebanD;

    TextWatcher twPhaseR = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try{
                String sss = s.toString();
                FragmentDataShare.phaseR = Float.parseFloat(sss);
            }catch (Exception e){

            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twPhaseS = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseS = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twPhaseT = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseT = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twNetral = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.netral = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };




    TextWatcher twPhaseRNhA = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseRNhA = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twPhaseSNhA = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseSNhA = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twPhaseTNhA = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseTNhA = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twNetralNhA = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.netralNhA = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };





    TextWatcher twPhaseRNhB = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseRNhB = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twPhaseSNhB = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseSNhB = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twPhaseTNhB = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseTNhB = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twNetralNhB = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.netralNhB = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };




    TextWatcher twPhaseRNhC = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseRNhC = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twPhaseSNhC = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseSNhC = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twPhaseTNhC = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseTNhC = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twNetralNhC = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.netralNhC = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };





    TextWatcher twPhaseRNhD = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseRNhD = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twPhaseSNhD = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseSNhD = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twPhaseTNhD = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseTNhD = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twNetralNhD = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.netralNhD = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };




    TextWatcher twPhaseRBebanA = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseRBebanA = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twPhaseSBebanA = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseSBebanA = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twPhaseTBebanA = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseTBebanA = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twNetralBebanA = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.netralBebanA = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };





    TextWatcher twPhaseRBebanB = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseRBebanB = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twPhaseSBebanB = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseSBebanB = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twPhaseTBebanB = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseTBebanB = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twNetralBebanB = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.netralBebanB = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };




    TextWatcher twPhaseRBebanC = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseRBebanC = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twPhaseSBebanC = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseSBebanC = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twPhaseTBebanC = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            String sss = s.toString();
            FragmentDataShare.phaseTBebanC = Float.parseFloat(sss);
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twNetralBebanC = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.netralBebanC = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };





    TextWatcher twPhaseRBebanD = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseRBebanD = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twPhaseSBebanD = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseSBebanD = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twPhaseTBebanD = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.phaseTBebanD = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    TextWatcher twNetralBebanD = new TextWatcher() {

        public void afterTextChanged(Editable s) {
            try {
                String sss = s.toString();
                FragmentDataShare.netralBebanD = Float.parseFloat(sss);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

}
package com.windupurnomo.simadutanjung;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.windupurnomo.simadutanjung.customui.DatePickerFragment;
import com.windupurnomo.simadutanjung.customui.TimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Editor extends ActionBarActivity {

    EditText gardu;
    EditText address;
    EditText daya;
    EditText penyulang;
    EditText tiang;
    EditText processingDate;
    EditText processingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        gardu           = (EditText)findViewById(R.id.edittext_gardu);
        address         = (EditText)findViewById(R.id.edittext_address);
        daya            = (EditText)findViewById(R.id.edittext_daya);
        penyulang       = (EditText)findViewById(R.id.edittext_penyulang);
        tiang           = (EditText)findViewById(R.id.edittext_tiang);
        processingDate  = (EditText)findViewById(R.id.edittext_date);
        processingTime  = (EditText)findViewById(R.id.edittext_time);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.editor, menu);
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

    public void showDatePicker(View v){
        DatePickerFragment newFragment = new DatePickerFragment();
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        newFragment.setArguments(args);
        newFragment.setCallBack(ondate);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void showTimePicker(View v){
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");

    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
            String date = sdf.format(new GregorianCalendar(year, monthOfYear, dayOfMonth));
            processingDate.setText(date);
        }
    };

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_editor, container, false);
            return rootView;
        }
    }

}

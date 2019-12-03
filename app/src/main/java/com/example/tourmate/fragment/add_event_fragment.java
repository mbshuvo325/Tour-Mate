package com.example.tourmate.fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.tourmate.R;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class add_event_fragment extends Fragment {

private EditText nameET,departureET,destinationET,budgetET;
private Button startDate,endDate,submitEvent;

  private String  departureDate ;
  private String  endEventDate ;

    public add_event_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_event_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameET = view.findViewById(R.id.event_name);
        departureET = view.findViewById(R.id.departure_place);
        destinationET = view.findViewById(R.id.destination_place);
        budgetET = view.findViewById(R.id.budget);
        startDate = view.findViewById(R.id.start_date);
        endDate = view.findViewById(R.id.end_date);
        submitEvent = view.findViewById(R.id.submit_event);

        ///for work

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStartDatepickerDialog();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEndDatepickerDialog();
            }
        });

        ///end work

        submitEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventName = nameET.getText().toString();
                String startPlace = departureET.getText().toString();
                String destination = destinationET.getText().toString();
                String bedget = budgetET.getText().toString();
            }
        });

    }

    private void showStartDatepickerDialog() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(getActivity(),DateStartSetListener,year,month,day);

        dpd.show();
    }
    private DatePickerDialog.OnDateSetListener DateStartSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(i,i1,i2);
            departureDate = new SimpleDateFormat("dd/mm/yyyy").format(calendar.getTime());
        }
    };


    private void showEndDatepickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(getActivity(),DateEndSetListener,year,month,day);

        dpd.show();

    }
    private DatePickerDialog.OnDateSetListener DateEndSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(i,i1,i2);
            endEventDate = new SimpleDateFormat("dd/mm/yyyy").format(calendar.getTime());
        }
    };
}

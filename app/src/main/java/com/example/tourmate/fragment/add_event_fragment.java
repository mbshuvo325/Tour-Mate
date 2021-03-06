package com.example.tourmate.fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tourmate.R;
import com.example.tourmate.pojos.TourmateEvent;
import com.example.tourmate.viewmodels.EventViewModel;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class add_event_fragment extends Fragment {

private EditText nameET,departureET,destinationET,budgetET;
private Button startDate,endDate,submitEvent,update;

  private String  departureDate ;
  private String  endEventDate ;
  private EventViewModel eventViewModel;
  private String  eventID;

    public add_event_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        Bundle bundle = getArguments();
        if (bundle != null){
             eventID = bundle.getString("id");
             eventViewModel.getEventDetails(eventID);
             eventViewModel.eventDetilsLD.observe(this, new Observer<TourmateEvent>() {
                 @Override
                 public void onChanged(TourmateEvent event) {

                     nameET.setText(event.getEventName());
                     departureET.setText(event.getDeparture());
                     destinationET.setText(event.getDestination());
                     budgetET.setText(String.valueOf(event.getEventbudget()));
                     submitEvent.setVisibility(getView().GONE);
                     update.setVisibility(getView().VISIBLE);
                     startDate.setText(event.getStartDate());
                     endDate.setText(event.getEndDate());

                 }
             });
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_event_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        nameET = view.findViewById(R.id.event_name);
        departureET = view.findViewById(R.id.departure_place);
        destinationET = view.findViewById(R.id.destination_place);
        budgetET = view.findViewById(R.id.budget);
        startDate = view.findViewById(R.id.start_date);
        endDate = view.findViewById(R.id.end_date);
        submitEvent = view.findViewById(R.id.submit_event);
        update = view.findViewById(R.id.update_event);

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
                String budget = budgetET.getText().toString();
                String firstDate = startDate.getText().toString();
                String endtDate = endDate.getText().toString();

                if (eventName.isEmpty()){
                    nameET.setError("Event Name Should Not be Empty..!");
                }
               else if (startPlace.isEmpty()){
                    departureET.setError("Starting Place Should Not be Empty..!");
                }
               else if (destination.isEmpty()){
                    destinationET.setError("Destination Should Not be Empty..!");
                }
               else if (budget.isEmpty()){
                    budgetET.setError("Budget Should Not be Empty..!");
                }
               else if (firstDate.isEmpty()){
                   startDate.setError("Date Should not empty");
                }
                else if (endtDate.isEmpty()){
                    endDate.setError("Date Should not empty");
                }
                else{
                    TourmateEvent tourmateEvent = new TourmateEvent(null,eventName,startPlace,
                            destination,Double.valueOf(budget),departureDate,endEventDate);
                    eventViewModel.saveEvent(tourmateEvent);
                    Navigation.findNavController(view).navigate(R.id.event_List);
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventName = nameET.getText().toString();
                String statPlace = departureET.getText().toString();
                String destination = destinationET.getText().toString();
                String buget = budgetET.getText().toString();
                String startDatef = startDate.getText().toString();
                String enddate = endDate.getText().toString();
                TourmateEvent tourmateEvent = new TourmateEvent(eventID,eventName,statPlace,destination,
                        Double.valueOf(buget),startDatef,enddate);

                eventViewModel.UpdateEvent(tourmateEvent);

                Navigation.findNavController(view).navigate(R.id.event_List);
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
            departureDate = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());
            startDate.setText(departureDate);

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
            endEventDate = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());
            endDate.setText(endEventDate);
        }
    };
}

package com.example.tourmate.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.tourmate.R;
import com.example.tourmate.adapter.EveentAdapter;
import com.example.tourmate.pojos.TourmateEvent;
import com.example.tourmate.viewmodels.EventViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Event_List extends Fragment {

    private EventViewModel eventViewModel;
    private EveentAdapter adapter;
    private RecyclerView eventRV;
    private TextView addEventTV;
    private TourmateEvent tourmateEvent = new TourmateEvent();


    public Event_List() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       // super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_event_memu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.add_event_id:
               Navigation.findNavController(getActivity(),R.id.nav_host_fragment)
                       .navigate(R.id.add_event_fragment);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        return inflater.inflate(R.layout.fragment_event__list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addEventTV = view.findViewById(R.id.addEvent);

        if (tourmateEvent == null){
            addEventTV.setVisibility(View.VISIBLE);
        }
        eventViewModel.eventListLD.observe(this, new Observer<List<TourmateEvent>>() {
            @Override
            public void onChanged(List<TourmateEvent> tourmateEvents) {
                eventRV = view.findViewById(R.id.eventRV);
                adapter = new EveentAdapter(getActivity(),tourmateEvents);
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                eventRV.setLayoutManager(llm);
                eventRV.setAdapter(adapter);
            }
        });

    }
}

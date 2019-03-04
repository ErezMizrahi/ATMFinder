package com.erez8.atmfinder.util;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.erez8.atmfinder.R;

public class BottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    private Spinner chooseIfHandicap;
    private Spinner chooseIfCommision;
    private Spinner chooseBankName;
    private Button done;
    private EditText cityName;
    private Filterable listener;
    private String handicap;
    private String commission;
    private String bank_name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout,container,false);
        this.chooseIfHandicap = v.findViewById(R.id.spinner_handicap);
        this.chooseBankName = v.findViewById(R.id.spinner_bank);
        this.chooseIfCommision=v.findViewById(R.id.spinner_commision);
        this.cityName = v.findViewById(R.id.city);
        setSpinners();
        v.findViewById(R.id.btn_done).setOnClickListener(this);

        return v;
    }

    private void setSpinners() {
        this.chooseBankName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { bank_name = parent.getItemAtPosition(position).toString(); }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }});
        this.chooseIfCommision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { commission = parent.getItemAtPosition(position).toString(); }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }});
        this.chooseIfHandicap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { handicap = parent.getItemAtPosition(position).toString(); }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ArrayAdapter<CharSequence> handcapSpinnerAdapter = ArrayAdapter.createFromResource(view.getContext(),R.array.handiCap,android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> commissionSpinnerAdapter = ArrayAdapter.createFromResource(view.getContext(),R.array.commision,android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> bankNameSpinnerAdapter = ArrayAdapter.createFromResource(view.getContext(),R.array.bankName,android.R.layout.simple_spinner_dropdown_item);
        this.chooseIfHandicap.setAdapter(handcapSpinnerAdapter);
        this.chooseIfCommision.setAdapter(commissionSpinnerAdapter);
        this.chooseBankName.setAdapter(bankNameSpinnerAdapter);
    }


    @Override
    public void onClick(View v) {
        String city_name = cityName.getText().toString().trim();
        this.listener.finishFilterResult(handicap,commission,bank_name,city_name);
        dismiss();
    }


    public interface Filterable{
       void finishFilterResult(String handicapCapability, String commission, String BankName, String cityName);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.listener = (Filterable) context;
        } catch (ClassCastException e) {
         throw new ClassCastException(e.getLocalizedMessage() + "implement Filterbale interface in MainActiviy");
        }
    }
}

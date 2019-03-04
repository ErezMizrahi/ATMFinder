package com.erez8.atmfinder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.erez8.atmfinder.Adapters.AtmListAdapter;
import com.erez8.atmfinder.util.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BottomSheetDialog.Filterable, AtmListAdapter.adapterOnClickListener {
    private RecyclerView recyclerView;
    private AtmListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ATMDetials> atmList;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        inflateRecyclerView(XLSXWork.getInstance().getFullATMsList());
    }


    private void init() {
        this.atmList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        findViewById(R.id.openDialog).setOnClickListener(this);
        findViewById(R.id.cashAtms).setOnClickListener(this);
        findViewById(R.id.infoAtms).setOnClickListener(this);
        this.context = this;
        this.atmList = XLSXWork.getInstance().getFullATMsList();
    }

    @Override
    public void onBackPressed() {
        // just to make sure we don't enter the splash screen again
    }

    @Override
    public void onClick(View v) {
        Log.d("onClick", "getLastKnownLocation: called.");

        switch (v.getId()) {
            case R.id.openDialog:
                BottomSheetDialog dialog = new BottomSheetDialog();
                dialog.show(getSupportFragmentManager(), "done");
                break;
            case R.id.cashAtms:
                XLSXWork.getInstance().SortListOfATMsByTtpe("משיכת", new XLSXWork.ByTypeListCallback() {
                    @Override
                    public void TypeSortCallback(List<ATMDetials> sortedList) {
                        atmList=sortedList;
                        XLSXWork.getInstance().setSortedATMsByType(sortedList);
                        inflateRecyclerView(sortedList);

                    }
                });
                break;
            case R.id.infoAtms:
                XLSXWork.getInstance().SortListOfATMsByTtpe("מכשיר מידע", new XLSXWork.ByTypeListCallback() {
                    @Override
                    public void TypeSortCallback(List<ATMDetials> sortedList) {
                        atmList=sortedList;
                        XLSXWork.getInstance().setSortedATMsByType(sortedList);
                        inflateRecyclerView(sortedList);

                    }
                });
                break;
        }


    }

    // inflate the recycler view
    private void inflateRecyclerView(List<ATMDetials> atmDetialsList) {
        adapter = new AtmListAdapter(atmDetialsList, context, this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(context,R.anim.layout_fall_down_animation);
        recyclerView.setLayoutAnimation(layoutAnimationController);
        adapter.notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void finishFilterResult(String handicapCapability, String commission, String BankName, String cityName) {

        XLSXWork.getInstance().SortListOfATMs( handicapCapability, commission, BankName, cityName, new XLSXWork.SortListCallback() {
            @Override
            public void sortCallback(List<ATMDetials> sortedList) {
                Log.d("sortedList", "sortCallback: called." + sortedList.size());
                if (sortedList.size() < 1) {
                    showAlert();
                } else {
                    atmList = sortedList;
                    inflateRecyclerView(sortedList);
                }
            }
        });
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setMessage("לצערנו, לא הצלחנו למצוא התאמה.\n אנא נסה שנית").setTitle("שגיאה").setPositiveButton("אישור", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    @Override
    public void adapterOnClick(int position) {
        Intent intent = new Intent(context,DetailsActivity.class);
        XLSXWork.getInstance().setSelectedATM(atmList.get(position));
        context.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }


}


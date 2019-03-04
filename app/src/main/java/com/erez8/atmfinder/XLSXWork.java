package com.erez8.atmfinder;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class XLSXWork {

    private static XLSXWork instance;
    private List<ATMDetials> fullATMsList;
    private List<ATMDetials> sortedATMsByType;
    private ATMDetials selectedATM;

    public static XLSXWork getInstance() {

        if (instance == null) {
            instance = new XLSXWork();
        }
        return instance;
    }

    public void setSortedATMsByType(List<ATMDetials> sortedATMsByType) {
        this.sortedATMsByType = sortedATMsByType;
    }

    void setFullATMsList(List<ATMDetials> fullATMsList) {
        this.fullATMsList = fullATMsList;
    }

    List<ATMDetials> getFullATMsList() {
        return fullATMsList;
    }

    ATMDetials getSelectedATM() {
        return selectedATM;
    }

    void setSelectedATM(ATMDetials selectedATM) {
        this.selectedATM = selectedATM;
    }


    void SortListOfATMs(String handicapCapability, String commission, String bankName, String cityName, final SortListCallback listCallback) {
        if (sortedATMsByType != null) {
            listCallback.sortCallback(runThroughTheList(sortedATMsByType,bankName,handicapCapability,commission,cityName));
        } else {
            listCallback.sortCallback(runThroughTheList(fullATMsList,bankName,handicapCapability,commission,cityName));
        }
    }

    private List<ATMDetials> runThroughTheList(List<ATMDetials> sortedATMsByType, String bankName, String handicapCapability, String commission, String cityName) {
        List<ATMDetials> retunedList = new ArrayList<>();
        for (ATMDetials atm : sortedATMsByType) {
            if (atm.getBank_Name().toLowerCase().startsWith(bankName)
                    && atm.getCity().startsWith(cityName)
                    && atm.getCommission().startsWith(commission)
                    && atm.getHandicap_Access().startsWith(handicapCapability)
               ) {
                retunedList.add(atm);
            }
        }
        return retunedList;
    }

    void SortListOfATMsByTtpe(String atmType, final ByTypeListCallback listCallback) {
        List<ATMDetials> retunedList = new ArrayList<>();
        for (ATMDetials atm : fullATMsList) {
            Log.d(TAG, "bankName: " + atmType);
            Log.d(TAG, "getBank_Name: " + atm.getATM_Type());
            if (atm.getATM_Type().startsWith(atmType)) {
                retunedList.add(atm);
            }
        }
        listCallback.TypeSortCallback(retunedList);
    }
    public interface SortListCallback {
        void sortCallback(List<ATMDetials> sortedList);
    }
    public interface ByTypeListCallback {
        void TypeSortCallback(List<ATMDetials> sortedList);
    }
}
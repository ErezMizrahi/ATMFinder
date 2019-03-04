package com.erez8.atmfinder;

import org.apache.poi.xssf.usermodel.XSSFCell;

import java.util.Iterator;

public  class ATMDetials {
    private String Bank_Code;
    private String Bank_Name;
    private String Branch_Code;
    private String Atm_Num;
    private String ATM_Address;
    private String City;
    private String Commission;
    private String ATM_Type;
    private String ATM_Address_Extra;
    private String ATM_Location;
    private String Handicap_Access;
    private String full_Add;
    private String X;
    private String Y;


    public ATMDetials(Iterator cells) {
        // getting every column to its temp variable, if hasNext is false exit the loop
        assert !cells.hasNext();
        this.Bank_Code = ((XSSFCell) cells.next()).getStringCellValue().trim();
        this.Bank_Name = ((XSSFCell) cells.next()).getStringCellValue().trim();
        this.Branch_Code = ((XSSFCell) cells.next()).getStringCellValue().trim();
        this.Atm_Num = ((XSSFCell) cells.next()).getStringCellValue().trim();
        this.ATM_Address = ((XSSFCell) cells.next()).getStringCellValue().trim();
        cells.next();
//        this.ATM_Address_Extra = ((XSSFCell) cells.next()).getStringCellValue();
        this.City = ((XSSFCell) cells.next()).getStringCellValue().trim();
        full_Add = this.ATM_Address + ", " + this.City;
        this.Commission = ((XSSFCell) cells.next()).getStringCellValue().trim();
        this.ATM_Type = ((XSSFCell) cells.next()).getStringCellValue().trim();
        this.ATM_Location = ((XSSFCell) cells.next()).getStringCellValue().trim();
        this.Handicap_Access = ((XSSFCell) cells.next()).getStringCellValue().trim();
        this.X = ((XSSFCell) cells.next()).getStringCellValue().trim();
        this.Y = ((XSSFCell) cells.next()).getStringCellValue().trim();

    }

    public String getBank_Code() {
        return Bank_Code;
    }

    public String getBank_Name() {
        return Bank_Name;
    }

    public String getBranch_Code() {
        return Branch_Code;
    }

    public String getAtm_Num() {
        return Atm_Num;
    }

    public String getATM_Address() {
        return ATM_Address;
    }

    public String getCity() {
        return City;
    }

    public String getCommission() {
        return Commission;
    }

    public String getATM_Type() {
        return ATM_Type;
    }

    public String getATM_Address_Extra() {
        return ATM_Address_Extra;
    }

    public String getATM_Location() {
        return ATM_Location;
    }

    public String getHandicap_Access() {
        return Handicap_Access;
    }

    public String getFull_Add() {
        return full_Add;
    }

    public String getX() {
        return X;
    }

    public String getY() {
        return Y;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}

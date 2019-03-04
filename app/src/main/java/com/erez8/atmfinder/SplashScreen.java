package com.erez8.atmfinder;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SplashScreen extends AppCompatActivity {
    private List<ATMDetials> atmTypeCas;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        atmTypeCas = new ArrayList<>();
        String url = "https://data.gov.il/dataset/automated-devices/resource/b9d690de-0a9c-45ef-9ced-3e5957776b26/download/-.xlsx";
        new ReadXlsxFileFromUrl().execute(url);

    }

    private  class ReadXlsxFileFromUrl extends AsyncTask<String, Void,  Void> {

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                // create url from the xlsx path
                url = new URL(strings[0]);
                // open connection to the given url
                URLConnection uc = url.openConnection();
                // create xl workbook instance from the inputstream
                XSSFWorkbook wb = new XSSFWorkbook(uc.getInputStream());
                // create reference to the first sheet of the workbook
                XSSFSheet sheet = wb.getSheetAt(0);
                XSSFRow row;
                Iterator rows = sheet.rowIterator();
                // jumping over row number one (garbage data)
                rows.next();
                while (rows.hasNext()) {
                    // start with the row after the headers
                    row = (XSSFRow) rows.next();
                    Iterator cells = row.cellIterator();

                    ATMDetials atmDetials = new ATMDetials(cells);

                        atmTypeCas.add(atmDetials);
                }
                XLSXWork.getInstance().setFullATMsList(atmTypeCas);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute( Void aVoid)
        {
            progressBar.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(SplashScreen.this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onCancelled() {
            cancel(true);
            super.onCancelled();
        }

    }


}

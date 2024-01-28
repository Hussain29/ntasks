package com.example.ntasks.rents;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.PropertyName;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Expenses implements Parcelable {

    private String propertyName;
     private String particular;
    private String expenseAmount;
    private String selectedDate;
    private String currentDate;

    public Expenses() {
        // Default constructor required for calls to DataSnapshot.getValue(Expenses.class)
    }

    public Expenses(String propertyName, String particular, String expenseAmount, String selectedDate, String currentDate) {
        this.propertyName = propertyName;
        this.particular = particular;
        this.expenseAmount = expenseAmount;
        this.selectedDate = selectedDate;
        this.currentDate = currentDate;
    }

    protected Expenses(Parcel in) {
        propertyName = in.readString();
        particular = in.readString();
        expenseAmount = in.readString();
        selectedDate = in.readString();
        currentDate = in.readString();
    }

    public static final Creator<Expenses> CREATOR = new Creator<Expenses>() {
        @Override
        public Expenses createFromParcel(Parcel in) {
            return new Expenses(in);
        }

        @Override
        public Expenses[] newArray(int size) {
            return new Expenses[size];
        }
    };

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(propertyName);
        dest.writeString(particular);
        dest.writeString(expenseAmount);
        dest.writeString(selectedDate);
        dest.writeString(currentDate);
    }



/*

    public static void writeDataToExcel(List<Expenses> expensesList, String filePath) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        FileOutputStream fileOut = null;

        try {
            fileOut = new FileOutputStream(filePath);

            // Create a sheet
            workbook.createSheet("Expenses Data");

            // Get the first sheet
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

            // Add data to the sheet
            for (int i = 0; i < expensesList.size(); i++) {
                Row row = sheet.createRow(i);

                Expenses expense = expensesList.get(i);
                createCell(row, 0, expense.getSelectedDate());
                createCell(row, 1, expense.getPropertyName());
                createCell(row, 2, expense.getParticular());
                createCell(row, 3, expense.getExpenseAmount());
            }

            // Write the workbook to the file
            workbook.write(fileOut);
            Log.d("ExcelUtils", "Data written to Excel file successfully");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ExcelUtils", "Error writing data to Excel file");
        } finally {
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static void createCell(Row row, int column, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
    }

*/
    /*@Override
    public String toString() {
        return ">" +
                  selectedDate+"\t-" +
                  propertyName +"\t-"+
                  particular +"\t-"+
                 expenseAmount +"\t" ;
    }*/

    @Override
    public String toString() {
        String paddedPropertyName = padString(propertyName, 25); // Adjust the width as needed
        String paddedParticular = padString(particular, 25); // Adjust the width as needed
        String paddedExpenseAmount = padString(expenseAmount, 15); // Adjust the width as needed

        return ">" +
                selectedDate + "\t-" +
                paddedPropertyName + "\t-" +
                paddedParticular + "\t" +
                paddedExpenseAmount + "\t";
    }

    private String padString(String input, int width) {
        if (input.length() >= width) {
            return input.substring(0, width);
        } else {
            return String.format("%-" + width + "s", input);
        }
    }

}

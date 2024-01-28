package com.example.ntasks.rents;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class PdfGenerator {

    public static void generatePdf(File file, List<Expenses> expenseList, List<Collection> collectionList) throws IOException {

        LocalDate currentDate = LocalDate.now();

        // Get the month in words (full name)
        String monthInWords = currentDate.getMonth().getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.US);

        // Get the current year
        int currentYear = currentDate.getYear();


        // Creating a PdfWriter
        PdfWriter writer = new PdfWriter(new FileOutputStream(file));

        // Creating a PdfDocument
        PdfDocument pdf = new PdfDocument(writer);

        // Creating a Document
        Document document = new Document(pdf);
document.setMargins(25,23,20,13);
        // Adding content to the PDF
        document.add(new Paragraph("REPORT FOR THE MONTH OF"+ monthInWords.toUpperCase() + " " + currentYear).setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(20).setUnderline());


        document.add(new Paragraph("\nCOLLECTIONS").setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(18));
        document.add(new Paragraph("DATE\t\t  PROPERTY NAME   TENANT NAME   RENT AMOUNT").setTextAlignment(TextAlignment.JUSTIFIED_ALL) .setFontSize(16));

        // Add each collection separately
        for (Collection collection : collectionList) {
            document.add(new Paragraph(collection.toString()).setTextAlignment(TextAlignment.JUSTIFIED_ALL));
        }

        document.add(new Paragraph("EXPENSES").setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(18));
        document.add(new Paragraph("DATE  \t\t PROPERTY NAME   PARTICULAR   EXPENSE AMOUNT").setTextAlignment(TextAlignment.JUSTIFIED_ALL).setFontSize(16));
        // Add each expense separately
        for (Expenses expense : expenseList) {
            document.add(new Paragraph(expense.toString()).setTextAlignment(TextAlignment.JUSTIFIED_ALL));
        }


        // Calculate total collections amount
        double totalCollectionsAmount = calculateTotalAmount(collectionList);
        document.add(new Paragraph("Total Collections Amount: " + totalCollectionsAmount).setTextAlignment(TextAlignment.RIGHT).setBold());
        // Perform addition operation on expense amounts
        double totalExpenseAmount = calculateTotalExpenseAmount(expenseList);
        document.add(new Paragraph("Total Expense Amount: " + totalExpenseAmount).setTextAlignment(TextAlignment.RIGHT).setBold());
        // Calculate net total (collections - expenses)
        double netTotal = totalCollectionsAmount - totalExpenseAmount;
        document.add(new Paragraph("Net Total: " + netTotal).setTextAlignment(TextAlignment.RIGHT).setBold().setFontSize(14));

        // Closing the Document
        document.close();




    }





    private static double calculateTotalAmount(List<? extends AmountCalculable> dataList) {
        double totalAmount = 0;
        for (AmountCalculable data : dataList) {
            totalAmount += data.getAmount();
        }
        return totalAmount;
    }
    private static double calculateTotalExpenseAmount(List<Expenses> expenseList) {
        double totalAmount = 0.0;
        for (Expenses expense : expenseList) {
            try {
                // Assuming expense amount is a numeric value
                double amount = Double.parseDouble(expense.getExpenseAmount());
                totalAmount += amount;
            } catch (NumberFormatException e) {
                // Handle the case where the expense amount is not a valid numeric value
            }
        }
        return totalAmount;
    }




}

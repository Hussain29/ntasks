package com.example.ntasks.rents;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfGenerator {

    public static void generatePdf(File file, String expensesData, String collectionsData) throws IOException {
        // Creating a PdfWriter
        PdfWriter writer = new PdfWriter(new FileOutputStream(file));

        // Creating a PdfDocument
        PdfDocument pdf = new PdfDocument(writer);

        // Creating a Document
        Document document = new Document(pdf);

        // Adding content to the PDF
        document.add(new Paragraph("Expenses:"));
        document.add(new Paragraph(expensesData));

        document.add(new Paragraph("\nCollections:"));
        document.add(new Paragraph(collectionsData));

        // Closing the Document
        document.close();
    }
}

package com.example.ntasks.rents;

import static com.itextpdf.kernel.geom.PageSize.A4;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ntasks.R;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ContractsActivity extends AppCompatActivity {
    Button btnsave;
    EditText et1, et2, et3, et4, et5, et6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contracts);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        et5 = findViewById(R.id.et5);
        et6 = findViewById(R.id.et6);
        btnsave = findViewById(R.id.btnsave);


        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String m1 = et1.getText().toString();
                String m2 = et2.getText().toString();
                String m3 = et3.getText().toString();
                String m4 = et4.getText().toString();
                String m5 = et5.getText().toString();
                String m6 = et6.getText().toString();*/

                String m1="16-Jan-2024";
                String m2="Mirza Shareef Baig S/O Late Mirza Ibrahim Baig , Aged about 46years Occ :\n" +
                        "Businessman.Aadhaar No :-232352983755 Resident of STR 988, 2nd Floor ,Near GHMC Park,\n" +
                        "Sanath Nagar,Balanagar,K.V Rangareddy Telangana -500018.";
                String m3="Mohammed Abdul Thiseen S/O Mohammed Abdul Samad Aged about 47 years,Occ :-\n" +
                        "Business,Redident of 13-6-823/21, Hasham Nagar, Langer House, Golconda ,Hyderabad,\n" +
                        "Telangana, 500008.";
                String m4="JISS International Corporate Services And Consultants House No 9-1-\n" +
                        "1/B/16/2, Defence Colony Langer House Golconda , Hyderabad ,Telangana 500008";
                String m5="- STR 988,Near GHMC Park, Sanath Nagar,Balanagar,K.V Rangareddy\n" +
                        "Telangana -500018";
                String m6="9,300";

                try {
                    createPdf(m1, m2, m3, m4,m5,m6);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Get the ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Set the title
        actionBar.setTitle("CONTRACTS");

        // Enable the back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        int actionBarColor = ContextCompat.getColor(this, R.color.pinkkk); // Replace with your color resource
        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle the back button click
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createPdf(String m1, String m2, String m3, String m4,String m5,String m6) throws FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, "myPDF.pdf");
        OutputStream outputStream = new FileOutputStream(file);

        // Create a PdfWriter object with the specified file path
        PdfWriter writer = new PdfWriter(file);

        // Create a PdfDocument object using the PdfWriter
        com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        // Create a Document object with the PdfDocument
        Document document = new Document(pdfDocument);
pdfDocument.setDefaultPageSize(A4);
document.setMargins(360,60,40,50);
        // Add content to the document
         Paragraph n1=new Paragraph("RENTAL AGREEMENT").setBold().setFontSize(18).setUnderline().setTextAlignment(TextAlignment.CENTER);


        document.add(n1);

        document.add(new Paragraph("This rental agreement is made and executed on this Date\t"+ m1 +"\tHyderabad,Telangana.").setFontSize(14));
        document.add(new Paragraph(m2));
        document.add(new Paragraph("(Hereinafter called the TENANT which term shall mean and include all heirs, successors, legal\n" +
                "Representatives, administrators, assigns ect. of the ONE PART)"));
        document.add(new Paragraph(m3));

        document.add(new Paragraph("Represennted By\t" + m4).setBold());
        document.add(new Paragraph("(Hereinafter called the LANDLORD which term shall mean and include all heirs, successors,\n" +
                "legal representatives,administrators, assigns etc.)"));
        document.add(new Paragraph("TENANT\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tLANDLORD"));
        document.add(new AreaBreak());
        document.add(new Paragraph("Whereas the above named landlord is the absolute owner and possessor property bearing"));
        document.add(new Paragraph("House No." + m5));
        document.add(new Paragraph("And whereas being in need of premises the Tenant approached to the landlord and requested to let-out\n" +
                "the said property on a monthly rent of Rs " + m6 + ") and the landlord agreed to\n" +
                "let-out the same on the following terms and conditions"));
        document.add(new Paragraph(" Now This Rental Agreement Withnesseth as Follows :-").setUnderline());
        document.add(new Paragraph(" 1, That the tenancy commencing from the day of the agreement for a period of eleven months.\n" +
                "2, That the Tenant shall be entitled to pay a monthly rental of Rs"+m6 +
                "which shall be paid on or before 10 of every English calendar month without arrears’ to be accumulated.\n" +
                "3, That the Tenant shall keep the let-out property in neat and clean condition without any wastage and\n" +
                "damages .And the tenant shall not make any major repair or alternations without written permission of the\n" +
                "landlord and return the premises in as it is condition.\n" +
                "5, That the Tenant shall not sublease the let-out the schedule property to any other person or persons.\n" +
                "6, That the above mentioned rent excluding the electricity consumption charges which shall be paid by\n" +
                "the Tenant.\n" +
                "7, That the tenant shall permit the landlord or his representative to inspect the let-out premises at all\n" +
                "reasonable times.\n" +
                "8, That both parties shall serva (2) three months prior notice for the termination of this rental agreement.\n" +
                "9, That this Rental Agreement can further extended with mutual consent of both the parties subject to the\n" +
                "conditions with the enhancement of rent i.e 5% on every renewal."));


        document.add(new Paragraph("10, that the Tenant have deposited a sum of Rs 18.600/- (Rupees Eighteen thousand and Six Hundred\n" +
                "only) which shall be refunded at the time of vacating the premises without any interest .That the Tenant\n" +
                "has paid running rent advance.\n" +
                "IN WITNESSES WHEREOF the Tenant & Landlord signed this Rental Agreement with their own free\n" +
                "will on this day ,month and year first mentioned above in the presence of the following witnesses."));
        document.add(new Paragraph("\n\nWITNESSES\n\n").setUnderline().setTextAlignment(TextAlignment.LEFT));
        document.add(new Paragraph("TENANT\n\n").setUnderline().setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph("LANDLORD").setUnderline().setTextAlignment(TextAlignment.RIGHT));



        document.close();
        Toast.makeText(this, "Pdf Created", Toast.LENGTH_SHORT).show();


    }
}
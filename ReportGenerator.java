//报表生成类，负责生成PDF报表。

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.FileNotFoundException;
import java.util.List;

public class ReportGenerator {

    public void generateAnnualReport(List<Account> accounts, double totalBalance) {
        String pdfPath = "year-end-report.pdf";

        try {
            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Year-end-report"));
            document.add(new Paragraph("Total number of accounts: " + accounts.size()));
            document.add(new Paragraph("Total amount: " + totalBalance));

            for (Account account : accounts) {
                document.add(new Paragraph("Account ID: " + account.getId()));
                document.add(new Paragraph("User ID: " + account.getUserId()));
                document.add(new Paragraph("User name: " + account.getUsername()));
                document.add(new Paragraph("I.D. number: " + account.getIdNumber()));
                document.add(new Paragraph("Telephone number: " + account.getPhoneNumber()));
                document.add(new Paragraph("Genders: " + account.getGender()));
                document.add(new Paragraph("Birthday: " + account.getBirthDate()));
                document.add(new Paragraph("Balance: " + account.getBalance()));
                document.add(new Paragraph("-----------------------------"));
            }

            document.close();
            System.out.println("Successful year-end report generation: " + pdfPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

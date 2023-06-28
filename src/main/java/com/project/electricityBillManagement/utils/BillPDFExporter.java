package com.project.electricityBillManagement.utils;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.project.electricityBillManagement.payload.wrapper.HistoryWrapper;

public class BillPDFExporter {

    public void generate(List<HistoryWrapper> hwList, HttpServletResponse response) throws DocumentException, IOException {
        // Creating the Object of Document
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        // Getting instance of PdfWriter
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        // Opening the created document to change it
        document.open();

        // Setting up box format
        PdfContentByte canvas = writer.getDirectContent();
        Rectangle rect = new Rectangle(document.getPageSize());
        rect.setBorder(Rectangle.BOX);
        rect.setBorderWidth(2);
        rect.setBorderColor(CMYKColor.BLACK);
        canvas.rectangle(rect);

        // Creating font for title
        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        Font fontTitle = new Font(bf, 20, Font.BOLD);

        // Creating paragraph for title
        Paragraph titleParagraph = new Paragraph("Bill", fontTitle);
        titleParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        titleParagraph.setSpacingAfter(10);
        document.add(titleParagraph);

        // Adding extra content
        Font fontContent = new Font(bf, 12);
        Paragraph extraContentParagraph = new Paragraph("Thank you for using our electricity billing system. Here is the detailed bill information:\n", fontContent);
        extraContentParagraph.setSpacingAfter(10);
        document.add(extraContentParagraph);

        // Creating a table with 9 columns
        PdfPTable table = new PdfPTable(9);
        // Setting width of the table, its columns, and spacing
        table.setWidthPercentage(100f);
        table.setWidths(new int[] {3, 3, 3, 3, 3, 3, 3, 3, 3});
        table.setSpacingBefore(10);

        // Creating font for table header
        Font fontHeader = new Font(bf, 12, Font.BOLD);

        // Adding headings in the table header cells
        PdfPCell cell = new PdfPCell(new Phrase("Bill No", fontHeader));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("From Date", fontHeader));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("To Date", fontHeader));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("End Date", fontHeader));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Units", fontHeader));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Arrears", fontHeader));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Total Amount", fontHeader));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Paid Amount", fontHeader));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Admin Id", fontHeader));
        table.addCell(cell);

        // Adding the details of each history wrapper to the table
        for (HistoryWrapper hw : hwList) {
            table.addCell(String.valueOf(hw.getBillNo()));
            table.addCell(String.valueOf(hw.getFromDate()));
            table.addCell(String.valueOf(hw.getToDate()));
            table.addCell(String.valueOf(hw.getEndDate()));
            table.addCell(String.valueOf(hw.getUnits()));
            table.addCell(String.valueOf(hw.getArrears()));
            table.addCell(String.valueOf(hw.getTotalAmount()));
            table.addCell(String.valueOf(hw.getPaidAmount()));
            table.addCell(String.valueOf(hw.getAdminId()));
        }

        // Adding the table to the document
        document.add(table);

        // Adding below the table
        Paragraph additionalSentenceParagraph = new Paragraph("If you have any questions or need further assistance, please feel free to contact our customer support.\n", fontContent);
        additionalSentenceParagraph.setSpacingBefore(10);
        document.add(additionalSentenceParagraph);

        // Closing the document
        document.close();
    }
}

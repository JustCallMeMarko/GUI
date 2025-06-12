package Backend;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.awt.Color;
import javax.swing.*;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class PdfExporter {

    public void exportToPDF(JFrame parent, ArrayList<ArrayList<String>> data, String title) {
        // Open file chooser to select save location
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save PDF");
        fileChooser.setSelectedFile(new java.io.File(title.replace(" ", "_") + ".pdf"));

        int userSelection = fileChooser.showSaveDialog(parent);
        if (userSelection != JFileChooser.APPROVE_OPTION) return;

        try {
            // Create the PDF document
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(fileChooser.getSelectedFile()));
            document.open();

            // Add title
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph titlePara = new Paragraph(title, titleFont);
            titlePara.setAlignment(Element.ALIGN_CENTER);
            titlePara.setSpacingAfter(20f);
            document.add(titlePara);

            // Add table
            String[] headers = {"Ref ID", "Item", "Qty", "Name", "Category", "School", "Section", "Date"};
            PdfPTable table = new PdfPTable(headers.length);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);

            Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(new Color(220, 220, 220));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            Font bodyFont = new Font(Font.HELVETICA, 11);
            for (ArrayList<String> row : data) {
                for (String cellText : row) {
                    PdfPCell cell = new PdfPCell(new Phrase(cellText, bodyFont));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }
            }

            document.add(table);
            document.close();

            JOptionPane.showMessageDialog(parent, "PDF saved successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Failed to create PDF: " + e.getMessage());
        }
    }
}

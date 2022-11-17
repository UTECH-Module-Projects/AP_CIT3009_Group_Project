package com.application.view.utilities;

import com.application.generic.TableList;
import com.application.models.tables.Invoice;
import com.application.models.tables.InvoiceItem;
import com.application.models.tables.Log;
import com.application.models.tables.Product;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

public class ReportGenerator {

    public static boolean personReport(String path, String type, String[][] data, String[] headers, List<TableList<Invoice, Integer>> invoices)  {
        try (PdfWriter w = new PdfWriter(path)) {
            PdfDocument pdf = new PdfDocument(w);
            pdf.setDefaultPageSize(PageSize.LETTER);
            pdf.addNewPage();

            Document doc = new Document(pdf);
            doc.setWidthPercent(100);

            for (int i = 0; i < data.length; i++) {
                setHeader(doc, type + " Report");

                Table mainTBL = new Table(new float[]{1, 4});
                mainTBL.setWidthPercent(100);

                for (int j = 0; j < headers.length; j++) {

                    if (j == 0) {
                        mainTBL.addCell(new Cell().add(headers[j]).setBold().setBorderLeft(Border.NO_BORDER).setBorderBottom(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setPaddingTop(15));
                        mainTBL.addCell(new Cell().add(data[i][j]).setBorderLeft(Border.NO_BORDER).setBorderBottom(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setPaddingTop(15));
                    } else if (j == headers.length-1){
                        mainTBL.addCell(new Cell().add(headers[j]).setBold().setBorderLeft(Border.NO_BORDER).setBorderTop(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setPaddingBottom(15));
                        mainTBL.addCell(new Cell().add(data[i][j]).setBorderLeft(Border.NO_BORDER).setBorderTop(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setPaddingBottom(15));
                    } else {
                        mainTBL.addCell(new Cell().add(headers[j]).setBold().setBorder(Border.NO_BORDER));
                        mainTBL.addCell(new Cell().add(data[i][j]).setBorder(Border.NO_BORDER));
                    }
                }
                doc.add(mainTBL);

                TableList<Invoice, Integer> invoice = invoices.get(i);
                if (invoice != null) {
                    doc.add(new Paragraph("\nInvoices\n").setFontSize(20).setBold().setUnderline());

                    Table tbl = printTable(doc, invoice.to2DArray(), invoice.getHeaders());
                    doc.add(tbl);
                }
                if (i < (data.length-1))
                    doc.add(new AreaBreak());
            }
            doc.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean invoiceReport(String path, TableList<Invoice, Integer> invoices, String[] itemHeaders) {
        try (PdfWriter w = new PdfWriter(path)) {
            PdfDocument pdf = new PdfDocument(w);
            pdf.setDefaultPageSize(PageSize.LETTER);
            pdf.addNewPage();

            Document doc = new Document(pdf);
            doc.setWidthPercent(100);

            for (int i = 0; i < invoices.size(); i++) {
                setHeader(doc, "Invoice");

                Table mainTBL = new Table(new float[]{1, 2, 1, 2});
                mainTBL.setWidthPercent(100);

                Invoice invoice = invoices.get(i);

                mainTBL.addCell(new Cell().add("Customer").setFontSize(14).setBold().setBorderLeft(Border.NO_BORDER).setBorderBottom(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setPaddingTop(15));
                mainTBL.addCell(new Cell().add(invoice.getCustomer().getName()).setBorderLeft(Border.NO_BORDER).setBorderBottom(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setPaddingTop(15));
                mainTBL.addCell(new Cell().add("Invoice #").setFontSize(14).setBold().setBorderLeft(Border.NO_BORDER).setBorderBottom(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setPaddingTop(15));
                mainTBL.addCell(new Cell().add(String.valueOf(invoice.getIdNum())).setTextAlignment(TextAlignment.RIGHT).setBorderLeft(Border.NO_BORDER).setBorderBottom(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setPaddingTop(15));

                mainTBL.addCell(new Cell().add("Employee").setFontSize(14).setBold().setBorder(Border.NO_BORDER));
                mainTBL.addCell(new Cell().add(invoice.getEmployee().getName()).setBorder(Border.NO_BORDER));
                mainTBL.addCell(new Cell().add("Invoice Date").setFontSize(14).setBold().setBorder(Border.NO_BORDER));
                mainTBL.addCell(new Cell().add(invoice.getBillDate().toString()).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));

                mainTBL.addCell(new Cell().setBorder(Border.NO_BORDER).setPaddingBottom(15));
                mainTBL.addCell(new Cell().setBorder(Border.NO_BORDER).setPaddingBottom(15));
                mainTBL.addCell(new Cell().add("Invoice Time").setFontSize(14).setBold().setBorder(Border.NO_BORDER).setPaddingBottom(15));
                mainTBL.addCell(new Cell().add(invoice.getBillTime().toString()).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).setPaddingBottom(15));
                doc.add(mainTBL);

                List<InvoiceItem> invItems = invoice.getItems();

                if (!invItems.isEmpty()) {
                    Table invItemTBL = new Table(new float[] {1, 1, 1, 1});
                    invItemTBL.setWidthPercent(100);

                    for (String header : itemHeaders) {
                        invItemTBL.addHeaderCell(new Cell().add(header).setBold().setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER));
                    }

                    for (int j = 0; j < invItems.size(); j++) {
                        String[] invItem = invItems.get(j).toArray();
                        for (String s : invItem) {
                            if (j == invItems.size() - 1)
                                invItemTBL.addCell(new Cell().add(s).setBorderTop(Border.NO_BORDER).setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER));
                            else
                                invItemTBL.addCell(new Cell().add(s).setBorder(Border.NO_BORDER));
                        }
                    }
                    double total = invoice.getTotal();
                    double discount = invoice.getDiscount();
                    double subtotal = (total / Invoice.GCT) + discount;
                    double gct = (subtotal - discount) * (Invoice.GCT - 1);

                    invItemTBL.addCell(new Cell().setBorder(Border.NO_BORDER));
                    invItemTBL.addCell(new Cell().setBorder(Border.NO_BORDER));
                    invItemTBL.addCell(new Cell().add("Subtotal").setFontSize(14).setBold().setBorder(Border.NO_BORDER).setPaddingTop(15));
                    invItemTBL.addCell(new Cell().add(String.format("$%.2f", subtotal)).setBold().setBorder(Border.NO_BORDER).setPaddingTop(15));
                    invItemTBL.addCell(new Cell().setBorder(Border.NO_BORDER));
                    invItemTBL.addCell(new Cell().setBorder(Border.NO_BORDER));
                    invItemTBL.addCell(new Cell().add("Discount").setFontSize(14).setBold().setBorder(Border.NO_BORDER));
                    invItemTBL.addCell(new Cell().add(String.format("$%.2f", discount)).setBold().setBorder(Border.NO_BORDER));
                    invItemTBL.addCell(new Cell().setBorder(Border.NO_BORDER));
                    invItemTBL.addCell(new Cell().setBorder(Border.NO_BORDER));
                    invItemTBL.addCell(new Cell().add("GCT (" + String.format("%.1f%%", (Invoice.GCT - 1) * 100) + ")").setFontSize(14).setBold().setBorder(Border.NO_BORDER));
                    invItemTBL.addCell(new Cell().add(String.format("$%.2f", gct)).setBold().setBorder(Border.NO_BORDER));
                    invItemTBL.addCell(new Cell().setBorder(Border.NO_BORDER));
                    invItemTBL.addCell(new Cell().setBorder(Border.NO_BORDER));
                    invItemTBL.addCell(new Cell().add("Total").setFontSize(14).setBold().setBorder(Border.NO_BORDER));
                    invItemTBL.addCell(new Cell().add(String.format("$%.2f", total)).setBold().setBorderTop(Border.NO_BORDER).setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER));
                    doc.add(invItemTBL);
                }
                if (i < (invoices.size()-1))
                    doc.add(new AreaBreak());
            }
            doc.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean logReport(String path, String title, TableList<Log, String> logs) {
        try (PdfWriter w = new PdfWriter(path)) {
            PdfDocument pdf = new PdfDocument(w);
            pdf.setDefaultPageSize(PageSize.LEGAL.rotate());
            pdf.addNewPage();

            Document doc = new Document(pdf);
            doc.setWidthPercent(100);

            setHeader(doc, "Logs Report");
            doc.add(new Paragraph(title).setFontSize(14));

            Table tbl = printTable(doc, logs.to2DArray(), logs.getHeaders());
            doc.add(tbl);
            doc.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean productsReport(String path, TableList<Product, String> products) {
        try (PdfWriter w = new PdfWriter(path)) {
            PdfDocument pdf = new PdfDocument(w);
            pdf.setDefaultPageSize(PageSize.LETTER.rotate());
            pdf.addNewPage();

            Document doc = new Document(pdf);
            doc.setWidthPercent(100);

            setHeader(doc, "Products Report");

            Table tbl = printTable(doc, products.to2DArray(), products.getHeaders());
            doc.add(tbl);
            doc.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean salesReport(String path, String title, String[][] data, String[] summary, String[] headers) {
        try (PdfWriter w = new PdfWriter(path)) {
            PdfDocument pdf = new PdfDocument(w);
            pdf.setDefaultPageSize(PageSize.LETTER.rotate());
            pdf.addNewPage();

            Document doc = new Document(pdf);
            doc.setWidthPercent(100);

            setHeader(doc, "Product Sales Report");
            doc.add(new Paragraph(title).setFontSize(14));

            Table tbl = printTable(doc, data, headers);

            for (String field : summary) {
                tbl.addCell(new Cell().add(field).setBold().setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER));
            }
            doc.add(tbl);

            doc.add(new Paragraph("\nRecommended Products").setFontSize(14).setBold());
            com.itextpdf.layout.element.List list = new com.itextpdf.layout.element.List();

            for (int i = 0; (i < 3 && i < data.length); i++) {
                list.add(data[i][0]);
            }
            doc.add(list);
            doc.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static Table printTable(Document doc, String[][] strings, String[] headers) {
        float[] cols = new float[headers.length];
        Arrays.fill(cols, 1);
        Table tbl = new Table(cols);
        tbl.setWidthPercent(100);

        for (String header : headers) {
            tbl.addHeaderCell(new Cell().add(header).setBold());
        }

        for (String[] row : strings) {
            for (String field : row) {
                tbl.addCell(new Cell().add(field));
            }
        }
        return tbl;
    }

    private static void setHeader(Document doc, String title) throws MalformedURLException {
        Table headerTBL = new Table(new float[]{1, 1});
        headerTBL.addCell(new Cell().add("58 Old Hope Road, Kingston 6. Kingston, Jamaica\n(876) 984-8329\ninfo@janwholesale.com").setHorizontalAlignment(HorizontalAlignment.LEFT).setFontSize(12).setBorder(Border.NO_BORDER));
        headerTBL.addCell(new Cell().add(new Image(ImageDataFactory.create("Images/banner.png")).setHorizontalAlignment(HorizontalAlignment.LEFT).setWidthPercent(50)).setBorder(Border.NO_BORDER));
        doc.add(headerTBL);
        doc.add(new Paragraph().setTextAlignment(TextAlignment.LEFT).setFontSize(30).setBold().add(title + "\n"));
    }
}

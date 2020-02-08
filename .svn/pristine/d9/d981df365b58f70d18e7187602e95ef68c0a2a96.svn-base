/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.eodlettergeneration.service;

/**
 *
 * @author chinthaka_r
 */
import java.io.FileOutputStream;
import java.io.StringReader;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker; // deprecated
import com.itextpdf.text.pdf.PdfWriter;

public class HtmlToPdf {
    // itextpdf-5.4.1.jar  http://sourceforge.net/projects/itext/files/iText/

    public void savePdf(String html) throws Exception {
        try {
            Document document = new Document(PageSize.LETTER);
            PdfWriter.getInstance(document, new FileOutputStream("c://temp//testpdf1.pdf"));
            document.open();
            document.addAuthor("Real Gagnon");
            document.addCreator("Real's HowTo");
            document.addSubject("Thanks for your support");
            document.addCreationDate();
            document.addTitle("Please read this");

            HTMLWorker htmlWorker = new HTMLWorker(document);
            htmlWorker.parse(new StringReader(html));
            document.close();
            System.out.println("Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

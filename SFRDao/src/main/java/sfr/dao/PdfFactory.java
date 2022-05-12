/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sfr.dao;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import common.model.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import sfr.model.Plan;

/**
 *
 * @author Luis D
 */
public class PdfFactory {

    public Document createRiskMatrix(OutputStream out, String title, User user, Plan plan) throws DocumentException, FileNotFoundException, BadElementException, IOException {

        HeaderFooter hf = new HeaderFooter(user.getOfficial().getName() + " " + user.getOfficial().getSurname());

        Document doc = new Document(PageSize.A4.rotate(), 36, 36, 20 + hf.getHeaderHeight(), 36 + hf.getFooterHeight());
        //, 36, 36, 20 + hf.getHeaderHeight(), 20 + hf.getFooterHeight()
        //doc.setMargins(36, 36, 20 + hf.getHeaderHeight(), 20 + hf.getFooterHeight());
        //ByteArrayOutputStream bout = new ByteArrayOutputStream();

        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(new File("D:/my.pdf")));

        writer.createXmpMetadata();
        writer.setTagged();
        writer.setPageEvent(hf);

        //Font
        Font genericFont = new Font();
        genericFont.setSize(12);
        genericFont.setColor(BaseColor.BLACK);

        //Colors
        BaseColor light_blue = new BaseColor(221, 235, 247);

        //open
        doc.open();

        PdfPTable matriz = new PdfPTable(9);
        matriz.setWidthPercentage(100);
        matriz.setWidths(new int[]{1, 1, 1, 1, 1, 5, 5, 5, 5});

        //Titulo muni
        PdfPCell muniTitle = new PdfPCell(new Phrase("MUNICIPALIDAD DE SAN PABLO DE HEREDIA", genericFont));
        muniTitle.setColspan(9);
        muniTitle.setFixedHeight(20);
        muniTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        muniTitle.setBackgroundColor(light_blue);

        matriz.addCell(muniTitle);

        //Titulo matriz
        PdfPCell matrizTitle = new PdfPCell(new Phrase("MATRIZ DE IDENTIFICACIÓN DEL RIESGO", genericFont));
        matrizTitle.setColspan(9);
        matrizTitle.setFixedHeight(20);
        matrizTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        matrizTitle.setBackgroundColor(light_blue);
        
        matriz.addCell(matrizTitle);

        //Titulo estructura
        
        //Add matriz de riesgos
        doc.add(matriz);

        //close
        doc.close();

        return doc;
    }

    private class HeaderFooter extends PdfPageEventHelper {

        protected Image logo;
        protected PdfPTable header;
        protected PdfPTable footer;
        protected float headerHeight;
        protected float footerHeight;
        protected Font titleFont;
        protected Font textFont;
        protected Font smallFont;
        protected String creator;

        public HeaderFooter(String username) throws DocumentException, BadElementException, IOException {
            this.logo = Image.getInstance("src/main/resources/images/MSPH_LOGO.png");
            this.creator = username;

            //Fonts
            this.textFont = new Font();
            this.textFont.setSize(11);
            this.textFont.setColor(BaseColor.GRAY);

            this.smallFont = new Font();
            this.smallFont.setSize(8);
            this.smallFont.setColor(BaseColor.GRAY);

            this.titleFont = new Font();
            this.titleFont.setSize(18);
            this.titleFont.setColor(BaseColor.GRAY);

            //Header
            this.setHeader();
        }

        private void setHeader() throws DocumentException {
            this.header = new PdfPTable(2);
            this.headerHeight = 80;
            this.header.setTotalWidth(600);
            this.header.setWidthPercentage(100);
            this.header.setWidths(new float[]{1, 6});
            this.header.setLockedWidth(true);

            //Image Cell
            PdfPCell img = new PdfPCell(logo);
            img.setFixedHeight(headerHeight);
            img.setVerticalAlignment(Element.ALIGN_CENTER);
            img.setBorder(Rectangle.NO_BORDER);

            header.addCell(img);

            //Text Cell
            Paragraph p = new Paragraph("Municipalidad de San Pablo de Heredia", titleFont);
            p.setAlignment(Element.ALIGN_LEFT);

            PdfPCell text = new PdfPCell(p);
            text.setVerticalAlignment(Element.ALIGN_MIDDLE);
            text.setBorder(Rectangle.NO_BORDER);

            header.addCell(text);
        }

        private void setFooter(int pageNumber) {
            this.footer = new PdfPTable(2);
            try {
                this.footer.setWidths(new int[]{24, 24});
                this.footerHeight = 20;
                this.footer.setTotalWidth(770);
                this.footer.getDefaultCell().setFixedHeight(footerHeight);
                this.footer.getDefaultCell().setBorder(Rectangle.BOTTOM);

                //Disclaimer Text
                Paragraph disclaimer = new Paragraph();
                disclaimer.setFont(smallFont);
                disclaimer.setAlignment(Element.ALIGN_LEFT);
                disclaimer.add("Este documento fue generado automáticamente por el Sistema de Factibilidad de Riesgos.\n");
                SimpleDateFormat format = new SimpleDateFormat("d 'de' MMMM 'del' yyyy 'a las' HH:mm:ss", new Locale("es", "MX"));
                disclaimer.add("Emitido por " + this.creator + " el " + format.format(new Date()));

                PdfPCell text = new PdfPCell(disclaimer);
                text.setBorder(Rectangle.BOTTOM);
                text.setVerticalAlignment(Element.ALIGN_BOTTOM);

                footer.addCell(text);

                //Page number
                footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                footer.addCell(new Phrase(String.format("Página %d", pageNumber), textFont));

            } catch (DocumentException e) {
                throw new ExceptionConverter(e);
            }
        }

        public float getHeaderHeight() {
            return this.headerHeight;
        }

        public float getFooterHeight() {
            return this.footerHeight;
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            header.writeSelectedRows(0, -1, document.left(), document.top() + ((document.topMargin() + headerHeight) / 2), writer.getDirectContent());

            this.setFooter(writer.getPageNumber());
            PdfContentByte canvas = writer.getDirectContent();
            canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
            //footer.writeSelectedRows(0, -1, document.left(), document.bottom() + ((document.bottomMargin() + footerHeight) / 2), canvas);
            footer.writeSelectedRows(0, -1, 36, 36, canvas);
            canvas.endMarkedContentSequence();
        }
    }

}

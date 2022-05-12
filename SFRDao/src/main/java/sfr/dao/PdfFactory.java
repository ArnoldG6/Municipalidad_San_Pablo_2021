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

    public Document createPlanReport(OutputStream out, User user, Plan plan, String imageFilePath) throws DocumentException, BadElementException, IOException {
        HeaderFooter hf = new HeaderFooter(user.getOfficial().getName() + " " + user.getOfficial().getSurname(), plan.getName(), imageFilePath);

        Document doc = new Document(PageSize.A4, 36, 36, 20 + hf.getHeaderHeight(), 36 + hf.getFooterHeight());

        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(new File("D:/reporte.pdf")));

        writer.createXmpMetadata();
        writer.setTagged();
        writer.setPageEvent(hf);

        //Font
        Font genericFont = new Font();
        genericFont.setSize(12);
        genericFont.setColor(BaseColor.BLACK);

        //Open
        doc.open();

        //Close
        doc.close();

        return doc;
    }

    public Document createRiskMatrix(OutputStream out, User user, Plan plan, String imageFilePath) throws DocumentException, FileNotFoundException, BadElementException, IOException {

        HeaderFooter hf = new HeaderFooter(user.getOfficial().getName() + " " + user.getOfficial().getSurname(), plan.getName(), imageFilePath);

        Document doc = new Document(PageSize.A4.rotate(), 36, 36, 20 + hf.getHeaderHeight(), 36 + hf.getFooterHeight());
        //, 36, 36, 20 + hf.getHeaderHeight(), 20 + hf.getFooterHeight()
        //doc.setMargins(36, 36, 20 + hf.getHeaderHeight(), 20 + hf.getFooterHeight());
        //ByteArrayOutputStream bout = new ByteArrayOutputStream();

        //PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(new File("D:/my.pdf")));
        PdfWriter writer = PdfWriter.getInstance(doc, out);
        writer.createXmpMetadata();
        writer.setTagged();
        writer.setPageEvent(hf);

        //Font
        Font genericFont = new Font();
        genericFont.setSize(12);
        genericFont.setColor(BaseColor.BLACK);

        //Colors
        BaseColor light_blue = new BaseColor(221, 235, 247);
        BaseColor light_green = new BaseColor(226, 239, 218);
        BaseColor light_yellow = new BaseColor(255, 242, 204);

        //open
        doc.open();

        PdfPTable matriz = new PdfPTable(7);
        matriz.setWidthPercentage(100);
        matriz.setWidths(new int[]{4, 5, 5, 7, 7, 7, 7});

        //Titulo muni
        PdfPCell muniTitle = new PdfPCell(new Phrase("MUNICIPALIDAD DE SAN PABLO DE HEREDIA", genericFont));
        muniTitle.setColspan(7);
        muniTitle.setFixedHeight(20);
        muniTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        muniTitle.setBackgroundColor(light_blue);

        matriz.addCell(muniTitle);

        //Titulo matriz
        PdfPCell matrizTitle = new PdfPCell(new Phrase("MATRIZ DE IDENTIFICACIÓN DEL RIESGO", genericFont));
        matrizTitle.setColspan(7);
        matrizTitle.setFixedHeight(20);
        matrizTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        matrizTitle.setVerticalAlignment(Element.ALIGN_TOP);
        matrizTitle.setBackgroundColor(light_blue);

        matriz.addCell(matrizTitle);

        //Titulo estructura
        PdfPCell estrucTitle = new PdfPCell(new Phrase("ESTRUCTURA DE RIESGOS", genericFont));
        estrucTitle.setColspan(3);
        estrucTitle.setFixedHeight(20);
        estrucTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        estrucTitle.setVerticalAlignment(Element.ALIGN_TOP);
        estrucTitle.setBackgroundColor(BaseColor.WHITE);

        matriz.addCell(estrucTitle);

        //Identificacion del riesgo
        PdfPCell identRiskTitle = new PdfPCell(new Phrase("IDENTIFICACIÓN DEL RIESGO", genericFont));
        identRiskTitle.setColspan(4);
        identRiskTitle.setFixedHeight(20);
        identRiskTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        identRiskTitle.setVerticalAlignment(Element.ALIGN_TOP);
        identRiskTitle.setBackgroundColor(light_green);

        matriz.addCell(identRiskTitle);

        //Niveles
        PdfPCell nivel1 = new PdfPCell(new Phrase("TIPO GENERAL", genericFont));
        nivel1.setFixedHeight(20);
        nivel1.setHorizontalAlignment(Element.ALIGN_CENTER);
        nivel1.setVerticalAlignment(Element.ALIGN_TOP);
        nivel1.setBackgroundColor(light_yellow);

        PdfPCell nivel2 = new PdfPCell(new Phrase("TIPO DE RIESGO POR ÁREA", genericFont));
        nivel2.setNoWrap(false);
        //nivel2.setFixedHeight(20);
        nivel2.setHorizontalAlignment(Element.ALIGN_CENTER);
        nivel2.setVerticalAlignment(Element.ALIGN_TOP);
        nivel2.setBackgroundColor(light_yellow);

        PdfPCell nivel3 = new PdfPCell(new Phrase("TIPO DE RIESGO POR ÁREA ESPECÍFICA", genericFont));
        nivel3.setNoWrap(false);
        //nivel3.setFixedHeight(20);
        nivel3.setHorizontalAlignment(Element.ALIGN_CENTER);
        nivel3.setVerticalAlignment(Element.ALIGN_TOP);
        nivel3.setBackgroundColor(light_yellow);

        matriz.addCell(nivel1);
        matriz.addCell(nivel2);
        matriz.addCell(nivel3);
        /*
        //Proceso Title
        PdfPCell processTitle = new PdfPCell(new Phrase("PROCESO", genericFont));
        processTitle.setFixedHeight(20);
        processTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        processTitle.setVerticalAlignment(Element.ALIGN_TOP);
        processTitle.setBackgroundColor(light_yellow);

        matriz.addCell(processTitle);

        //Objective Title
        PdfPCell objectiveTitle = new PdfPCell(new Phrase("OBJETIVO", genericFont));
        objectiveTitle.setFixedHeight(20);
        objectiveTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        objectiveTitle.setVerticalAlignment(Element.ALIGN_TOP);
        objectiveTitle.setBackgroundColor(light_yellow);

        matriz.addCell(objectiveTitle);
         */
        //Risk Title
        PdfPCell riskTitle = new PdfPCell(new Phrase("RIESGO", genericFont));
        riskTitle.setFixedHeight(20);
        riskTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        riskTitle.setVerticalAlignment(Element.ALIGN_TOP);
        riskTitle.setBackgroundColor(light_yellow);

        matriz.addCell(riskTitle);

        //Risk Description Title
        PdfPCell riskDescTitle = new PdfPCell(new Phrase("DESCRIPCIÓN DEL RIESGO", genericFont));
        riskDescTitle.setFixedHeight(20);
        riskDescTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        riskDescTitle.setVerticalAlignment(Element.ALIGN_TOP);
        riskDescTitle.setBackgroundColor(light_yellow);

        matriz.addCell(riskDescTitle);

        //Risk Factor Title
        PdfPCell riskFactorTitle = new PdfPCell(new Phrase("FACTOR DEL RIESGO (CAUSA)", genericFont));
        //riskFactorTitle.setFixedHeight(20);
        riskFactorTitle.setNoWrap(false);
        riskFactorTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        riskFactorTitle.setVerticalAlignment(Element.ALIGN_TOP);
        riskFactorTitle.setBackgroundColor(light_yellow);

        matriz.addCell(riskFactorTitle);

        //Risk Consequence Title
        PdfPCell riskConsequenceTitle = new PdfPCell(new Phrase("CONSECUENCIA", genericFont));
        riskConsequenceTitle.setFixedHeight(20);
        riskConsequenceTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        riskConsequenceTitle.setVerticalAlignment(Element.ALIGN_TOP);
        riskConsequenceTitle.setBackgroundColor(light_yellow);

        matriz.addCell(riskConsequenceTitle);

        //Add data
        if (plan.getRiskList().isEmpty()) {
            PdfPCell noRisks = new PdfPCell(new Phrase("No se han ingresaron riesgos.", genericFont));
            noRisks.setFixedHeight(40);
            noRisks.setColspan(7);
            noRisks.setHorizontalAlignment(Element.ALIGN_CENTER);
            noRisks.setVerticalAlignment(Element.ALIGN_CENTER);
            
            matriz.addCell(noRisks);

        } else {
            plan.getRiskList().forEach(risk -> {
                PdfPCell riskLVL1 = new PdfPCell(new Phrase(risk.getGeneralType(), genericFont));
                riskLVL1.setNoWrap(false);
                PdfPCell riskLVL2 = new PdfPCell(new Phrase(risk.getAreaType(), genericFont));
                riskLVL2.setNoWrap(false);
                PdfPCell riskLVL3 = new PdfPCell(new Phrase(risk.getAreaSpecificType(), genericFont));
                riskLVL3.setNoWrap(false);

                PdfPCell riskName = new PdfPCell(new Phrase(risk.getName(), genericFont));
                riskName.setNoWrap(false);
                PdfPCell riskDesc = new PdfPCell(new Phrase(risk.getDescription(), genericFont));
                riskDesc.setNoWrap(false);
                PdfPCell riskFactor = new PdfPCell(new Phrase(risk.getFactors(), genericFont));
                riskFactor.setNoWrap(false);
                PdfPCell riskConsequence = new PdfPCell(new Phrase(risk.getConsequences(), genericFont));
                riskConsequence.setNoWrap(false);

                matriz.addCell(riskLVL1);
                matriz.addCell(riskLVL2);
                matriz.addCell(riskLVL3);
                matriz.addCell(riskName);
                matriz.addCell(riskDesc);
                matriz.addCell(riskFactor);
                matriz.addCell(riskConsequence);
            });
        }
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
        protected String planName;

        public HeaderFooter(String username, String planName, String imageFilePath) throws DocumentException, BadElementException, IOException {
            this.logo = Image.getInstance(imageFilePath);
            this.creator = username;
            this.planName = planName;

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
            Paragraph p = new Paragraph();
            p.setFont(titleFont);
            p.add("Municipalidad de San Pablo de Heredia\n\n");
            p.setFont(textFont);
            p.add(planName);
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sfr.dao;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
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
import java.io.FileNotFoundException;
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

        HeaderFooter hf = new HeaderFooter(user.getOfficial().getName() + " " + user.getOfficial().getSurname(), plan.getName(), imageFilePath, false);

        Document doc = new Document(PageSize.A4, 36, 36, 36 + hf.getHeaderHeight(), 48 + hf.getFooterHeight());

        PdfWriter writer = PdfWriter.getInstance(doc, out);

        writer.createXmpMetadata();
        writer.setTagged();
        writer.setPageEvent(hf);

        //Font
        Font genericFont = new Font();
        genericFont.setSize(12);
        genericFont.setColor(BaseColor.BLACK);

        Font titleFont = new Font();
        titleFont.setSize(14);
        titleFont.setStyle(Font.BOLD);
        titleFont.setColor(BaseColor.BLACK);

        Font subTitleFont = new Font();
        subTitleFont.setSize(12);
        subTitleFont.setStyle(Font.UNDERLINE);
        subTitleFont.setColor(BaseColor.BLACK);

        Font smallFont = new Font();
        smallFont.setSize(10);
        smallFont.setColor(BaseColor.BLACK);

        //Open
        doc.open();

        //Plan title
        Paragraph title = new Paragraph(plan.getName(), titleFont);
        title.setAlignment(Element.ALIGN_JUSTIFIED);
        title.setSpacingAfter(15);

        doc.add(title);

        //Plan ID
        Paragraph id = new Paragraph();
        id.add(new Phrase("Código de identificación", subTitleFont));
        id.add(new Phrase(": ", genericFont));
        id.add(new Phrase(plan.getId(), genericFont));
        id.setAlignment(Element.ALIGN_JUSTIFIED);
        id.setSpacingAfter(15);

        doc.add(id);

        //Plan Entry Date
        SimpleDateFormat format = new SimpleDateFormat("d 'de' MMMM 'del' yyyy", new Locale("es", "MX"));
        Paragraph entryDate = new Paragraph();
        entryDate.add(new Phrase("Fecha de ingreso", subTitleFont));
        entryDate.add(new Phrase(": ", genericFont));
        entryDate.add(new Phrase(format.format(plan.getEntryDate()), genericFont));
        entryDate.setAlignment(Element.ALIGN_JUSTIFIED);
        entryDate.setSpacingAfter(15);

        doc.add(entryDate);

        //Plan Author
        Paragraph author = new Paragraph();
        author.add(new Phrase("Autor", subTitleFont));
        author.add(new Phrase(": ", genericFont));
        author.add(new Phrase(plan.getAuthorName(), genericFont));
        author.setAlignment(Element.ALIGN_JUSTIFIED);
        author.setSpacingAfter(15);

        doc.add(author);

        //Plan Tipo
        Paragraph type = new Paragraph();
        type.add(new Phrase("Tipo", subTitleFont));
        type.add(new Phrase(": ", genericFont));
        type.add(new Phrase(plan.getType(), genericFont));
        type.setAlignment(Element.ALIGN_JUSTIFIED);
        type.setSpacingAfter(15);

        doc.add(type);

        //Plan Subtipo
        Paragraph subtype = new Paragraph();
        subtype.add(new Phrase("Subtipo", subTitleFont));
        subtype.add(new Phrase(": ", genericFont));
        subtype.add(new Phrase(plan.getSubtype(), genericFont));
        subtype.setAlignment(Element.ALIGN_JUSTIFIED);
        subtype.setSpacingAfter(15);

        doc.add(subtype);

        //Plan Description
        Paragraph descTitle = new Paragraph();
        descTitle.add(new Phrase("Descripción", subTitleFont));
        descTitle.add(new Phrase(": ", genericFont));
        descTitle.setAlignment(Element.ALIGN_JUSTIFIED);
        descTitle.setSpacingAfter(15);

        doc.add(descTitle);

        Paragraph desc = new Paragraph();
        desc.add(new Phrase(plan.getDesc(), genericFont));
        desc.setAlignment(Element.ALIGN_JUSTIFIED);
        desc.setSpacingAfter(15);

        doc.add(desc);

        //Plan Risks
        Paragraph riskTitle = new Paragraph();
        riskTitle.add(new Phrase("Riesgos asociados", subTitleFont));
        riskTitle.add(new Phrase(": ", genericFont));
        riskTitle.setAlignment(Element.ALIGN_JUSTIFIED);
        riskTitle.setSpacingAfter(15);

        doc.add(riskTitle);

        if (plan.getRiskList().isEmpty()) {
            Paragraph riskData = new Paragraph();
            riskData.add(new Phrase("No se han ingresado riesgos.", genericFont));
            riskData.setAlignment(Element.ALIGN_JUSTIFIED);
            riskData.setSpacingAfter(15);

            doc.add(riskData);
        } else {
            List risks = new List(true, 20);
            //risks.setListSymbol();
            plan.getRiskList().forEach(risk -> {
                Paragraph riskData = new Paragraph();
                riskData.add(new Phrase(risk.getId() + " " + risk.getName(), genericFont));
                riskData.setAlignment(Element.ALIGN_JUSTIFIED);
                riskData.setSpacingAfter(15);

                risks.add(new ListItem(riskData));
            });
            //risks.setAutoindent(true);
            risks.setIndentationLeft(20);
            Paragraph risksData = new Paragraph();
            risksData.add(risks);
            risksData.setAlignment(Element.ALIGN_JUSTIFIED);
            risksData.setSpacingAfter(15);
            doc.add(risksData);
        }

        //Plan Incidences
        Paragraph incidenceTitle = new Paragraph();
        incidenceTitle.add(new Phrase("Incidencias ocurridas", subTitleFont));
        incidenceTitle.add(new Phrase(": ", genericFont));
        incidenceTitle.setAlignment(Element.ALIGN_JUSTIFIED);
        incidenceTitle.setSpacingAfter(15);

        doc.add(incidenceTitle);

        if (plan.getIncidenceList().isEmpty()) {
            Paragraph incidenceData = new Paragraph();
            incidenceData.add(new Phrase("No se han ingresado incidencias.", genericFont));
            incidenceData.setAlignment(Element.ALIGN_JUSTIFIED);
            incidenceData.setSpacingAfter(15);

            doc.add(incidenceData);
        } else {
            List incidences = new List(true, 20);
            //risks.setListSymbol();
            plan.getIncidenceList().forEach(incidence -> {
                Paragraph name = new Paragraph();
                name.add(new Phrase(incidence.getName(), genericFont));
                name.setAlignment(Element.ALIGN_JUSTIFIED);
                name.setSpacingAfter(15);
                ListItem item = new ListItem(name);

                List subData = new List(20);
                subData.setListSymbol(new Chunk("\u2022", titleFont));

                Paragraph date = new Paragraph();
                date.add(new Phrase("Fecha en la que se presentó", subTitleFont));
                date.add(new Phrase(": ", genericFont));
                date.add(new Phrase(format.format(incidence.getEntryDate()), genericFont));
                date.setAlignment(Element.ALIGN_JUSTIFIED);
                date.setSpacingAfter(15);
                subData.add(new ListItem(date));

                Paragraph affectation = new Paragraph();
                affectation.add(new Phrase("Porcentaje de afectación", subTitleFont));
                affectation.add(new Phrase(": ", genericFont));
                affectation.add(new Phrase(incidence.getAffectation().toString(), genericFont));
                affectation.add(new Phrase("%", genericFont));
                affectation.setAlignment(Element.ALIGN_JUSTIFIED);
                affectation.setSpacingAfter(15);

                subData.add(new ListItem(affectation));
                //subData.setIndentationLeft(20);

                incidences.add(item);
                incidences.add(subData);

            });
            //incidences.setAutoindent(true);
            incidences.setIndentationLeft(20);
            Paragraph incidencesData = new Paragraph();
            incidencesData.add(incidences);
            incidencesData.setAlignment(Element.ALIGN_JUSTIFIED);
            incidencesData.setSpacingAfter(15);

            doc.add(incidencesData);
        }

        //End of document
        Paragraph end = new Paragraph();
        end.add(new Phrase("- Fin del documento -", smallFont));
        end.setAlignment(Element.ALIGN_JUSTIFIED);
        end.setSpacingAfter(15);

        doc.add(end);

        //Close
        doc.close();

        return doc;
    }

    public Document createRiskMatrix(OutputStream out, User user, Plan plan, String imageFilePath) throws DocumentException, FileNotFoundException, BadElementException, IOException {

        HeaderFooter hf = new HeaderFooter(user.getOfficial().getName() + " " + user.getOfficial().getSurname(), plan.getName(), imageFilePath, true);

        Document doc = new Document(PageSize.A4.rotate(), 36, 36, 36 + hf.getHeaderHeight(), 36 + hf.getFooterHeight());
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
        protected Boolean landscape;

        public HeaderFooter(String username, String planName, String imageFilePath, Boolean landscape) throws DocumentException, BadElementException, IOException {
            this.logo = Image.getInstance(imageFilePath);
            this.creator = username;
            this.planName = planName;
            this.landscape = landscape;

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

            if (landscape) {
                this.header.setTotalWidth(600);
                this.header.setWidths(new float[]{1, 6});
            } else {
                this.header.setTotalWidth(523);
                this.header.setWidths(new float[]{1, 5});
            }

            this.header.setWidthPercentage(100);
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
                if (landscape) {
                    this.footer.setWidths(new int[]{24, 24});
                    this.footer.setTotalWidth(770);
                } else {
                    this.footer.setWidths(new int[]{24, 8});
                    this.footer.setTotalWidth(523);
                }
                this.footerHeight = 20;
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

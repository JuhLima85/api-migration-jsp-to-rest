package br.com.devsibre.UtilsReports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.devsibre.UtilsReports.Cantina;
import br.com.devsibre.Service.CantinaServiceImpl;

@Service
public class Cantina_reports implements Cantina_Report_Service{

	@Override
	public boolean creatPdf2(List<Cantina> cant, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		Document document = new Document(PageSize.A4, 15, 15, 45, 30);

        try {
            String filePath = context.getRealPath("/resources/reports");
            File file = new File(filePath);
            boolean exists = new File(filePath).exists();
            if (!exists) {
                new File(filePath).mkdirs();
            }

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file + "/" + "cant" + ".pdf"));
            document.open();

            Font mainFont = FontFactory.getFont("Arial", 15, BaseColor.BLACK);

            Paragraph paragraph = new Paragraph("Todos os Débitos", mainFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);
            paragraph.setSpacingAfter(10);
            document.add(paragraph);

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10);

            Font tableHeader = FontFactory.getFont("Arial", 12, BaseColor.BLACK);
            Font tableBody = FontFactory.getFont("Arial", 10, BaseColor.BLACK);

            float[] columnWidths = {2f, 2f, 2f};
            table.setWidths(columnWidths);

            PdfPCell nome = new PdfPCell(new Paragraph("Nome", tableHeader));
            nome.setBorderColor(BaseColor.BLACK);
            nome.setPaddingLeft(10);
            nome.setHorizontalAlignment(Element.ALIGN_CENTER);
            nome.setVerticalAlignment(Element.ALIGN_CENTER);
            nome.setBackgroundColor(BaseColor.WHITE);
            nome.setExtraParagraphSpace(5f);
            table.addCell(nome);

            PdfPCell descricao = new PdfPCell(new Paragraph("Descrição", tableHeader));
            descricao.setBorderColor(BaseColor.BLACK);
            descricao.setPaddingLeft(10);
            descricao.setHorizontalAlignment(Element.ALIGN_CENTER);
            descricao.setVerticalAlignment(Element.ALIGN_CENTER);
            descricao.setBackgroundColor(BaseColor.WHITE);
            descricao.setExtraParagraphSpace(5f);
            table.addCell(descricao);

            PdfPCell data = new PdfPCell(new Paragraph("Data", tableHeader));
            data.setBorderColor(BaseColor.BLACK);
            data.setPaddingLeft(10);
            data.setHorizontalAlignment(Element.ALIGN_CENTER);
            data.setVerticalAlignment(Element.ALIGN_CENTER);
            data.setBackgroundColor(BaseColor.WHITE);
            data.setExtraParagraphSpace(5f);
            table.addCell(data);

            for (Cantina cantina : cant) {
                PdfPCell nomeValue = new PdfPCell(new Paragraph(cantina.getNome(), tableBody));
                nomeValue.setBorderColor(BaseColor.BLACK);
                nomeValue.setPaddingLeft(10);
                nomeValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                nomeValue.setVerticalAlignment(Element.ALIGN_CENTER);
                nomeValue.setBackgroundColor(BaseColor.WHITE);
                nomeValue.setExtraParagraphSpace(5f);
                table.addCell(nomeValue);

                PdfPCell descricaoValue = new PdfPCell(new Paragraph(cantina.getDescricao(), tableBody));
                descricaoValue.setBorderColor(BaseColor.BLACK);
                descricaoValue.setPaddingLeft(10);
                descricaoValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                descricaoValue.setVerticalAlignment(Element.ALIGN_CENTER);
                descricaoValue.setBackgroundColor(BaseColor.WHITE);
                descricaoValue.setExtraParagraphSpace(5f);
                table.addCell(descricaoValue);

                PdfPCell dataValue = new PdfPCell(new Paragraph(cantina.getData(), tableBody));
                dataValue.setBorderColor(BaseColor.BLACK);
                dataValue.setPaddingLeft(10);
                dataValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                dataValue.setVerticalAlignment(Element.ALIGN_CENTER);
                dataValue.setBackgroundColor(BaseColor.WHITE);
                dataValue.setExtraParagraphSpace(5f);
                table.addCell(dataValue);
            }

            document.add(table);
            document.close();
            writer.close();
            return true;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CantinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(CantinaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
	}

	@Override
	public boolean createExcel2(List<Cantina> cant, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		return false;
	}

}

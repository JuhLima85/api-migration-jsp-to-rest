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

import br.com.devsibre.Model.FormularioModel;

import br.com.devsibre.ServiceImpl.FormularioServiceImpl;

@Service
public class Formulario_Report implements Formulario_Report_Service{

	@Override
	public boolean creatPdf(List<FormularioModel> cad, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		Document document = new Document(PageSize.A4, 40, 40, 10, 10);

        try {
            String filePath = context.getRealPath("/resources/reports");
            File file = new File(filePath);
            boolean exists = new File(filePath).exists();
            if (!exists) {
                new File(filePath).mkdirs();
            }

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file + "/" + "cad" + ".pdf"));
            document.open();

            Font mainFont = FontFactory.getFont("Arial", 10, BaseColor.BLACK);

            Paragraph paragraph = new Paragraph("Todos os cadastros", mainFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);
            paragraph.setSpacingAfter(10);
            document.add(paragraph);

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10);

            Font tableHeader = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
            Font tableBody = FontFactory.getFont("Arial", 8, BaseColor.BLACK);

            float[] columnWidths = {5f, 5f, 4f, 3f, 3f};
            table.setWidths(columnWidths);
            /*
            PdfPCell id = new PdfPCell(new Paragraph("ID", tableHeader));
            id.setBorderColor(BaseColor.BLACK);
            id.setPaddingLeft(10);
            id.setHorizontalAlignment(Element.ALIGN_CENTER);
            id.setVerticalAlignment(Element.ALIGN_CENTER);
            id.setBackgroundColor(BaseColor.WHITE);
            id.setExtraParagraphSpace(5f);
            table.addCell(id);
            */
            PdfPCell nome = new PdfPCell(new Paragraph("Nome", tableHeader));
            nome.setBorderColor(BaseColor.BLACK);
            nome.setPaddingLeft(10);
            nome.setHorizontalAlignment(Element.ALIGN_CENTER);
            nome.setVerticalAlignment(Element.ALIGN_CENTER);
            nome.setBackgroundColor(BaseColor.WHITE);
            nome.setExtraParagraphSpace(5f);
            table.addCell(nome);

            PdfPCell ender = new PdfPCell(new Paragraph("Endereco", tableHeader));
            ender.setBorderColor(BaseColor.BLACK);
            ender.setPaddingLeft(10);
            ender.setHorizontalAlignment(Element.ALIGN_CENTER);
            ender.setVerticalAlignment(Element.ALIGN_CENTER);
            ender.setBackgroundColor(BaseColor.WHITE);
            ender.setExtraParagraphSpace(5f);
            table.addCell(ender);

            PdfPCell fone = new PdfPCell(new Paragraph("Cidade", tableHeader));
            fone.setBorderColor(BaseColor.BLACK);
            fone.setPaddingLeft(10);
            fone.setHorizontalAlignment(Element.ALIGN_CENTER);
            fone.setVerticalAlignment(Element.ALIGN_CENTER);
            fone.setBackgroundColor(BaseColor.WHITE);
            fone.setExtraParagraphSpace(5f);
            table.addCell(fone);
             
            PdfPCell email = new PdfPCell(new Paragraph("Telefone", tableHeader));
            email.setBorderColor(BaseColor.BLACK);
            email.setPaddingLeft(10);
            email.setHorizontalAlignment(Element.ALIGN_CENTER);
            email.setVerticalAlignment(Element.ALIGN_CENTER);
            email.setBackgroundColor(BaseColor.WHITE);
            email.setExtraParagraphSpace(5f);
            table.addCell(email);
            

            PdfPCell status = new PdfPCell(new Paragraph("Status", tableHeader));
            status.setBorderColor(BaseColor.BLACK);
            status.setPaddingLeft(10);
            status.setHorizontalAlignment(Element.ALIGN_CENTER);
            status.setVerticalAlignment(Element.ALIGN_CENTER);
            status.setBackgroundColor(BaseColor.WHITE);
            status.setExtraParagraphSpace(5f);
            table.addCell(status);
            
           // String id = getId_c();	
            
            for (FormularioModel cadastro : cad) {
            	PdfPCell idValue = new PdfPCell(new Paragraph(cadastro.getNome().toString(), tableBody));
            	idValue.setBorderColor(BaseColor.BLACK);
            	idValue.setPaddingLeft(10);
            	idValue.setHorizontalAlignment(Element.ALIGN_CENTER);
            	idValue.setVerticalAlignment(Element.ALIGN_CENTER);
            	idValue.setBackgroundColor(BaseColor.WHITE);
            	idValue.setExtraParagraphSpace(5f);
                table.addCell(idValue);
                
//                PdfPCell nomeValue = new PdfPCell(new Paragraph(cadastro.getNome(), tableBody));
//                nomeValue.setBorderColor(BaseColor.BLACK);
//                nomeValue.setPaddingLeft(10);
//                nomeValue.setHorizontalAlignment(Element.ALIGN_CENTER);
//                nomeValue.setVerticalAlignment(Element.ALIGN_CENTER);
//                nomeValue.setBackgroundColor(BaseColor.WHITE);
//                nomeValue.setExtraParagraphSpace(5f);
//                table.addCell(nomeValue);

                PdfPCell enderValue = new PdfPCell(new Paragraph(cadastro.getLogradouro(), tableBody));
                enderValue.setBorderColor(BaseColor.BLACK);
                enderValue.setPaddingLeft(10);
                enderValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                enderValue.setVerticalAlignment(Element.ALIGN_CENTER);
                enderValue.setBackgroundColor(BaseColor.WHITE);
                enderValue.setExtraParagraphSpace(5f);
                table.addCell(enderValue);

                PdfPCell foneValue = new PdfPCell(new Paragraph(cadastro.getBairro(), tableBody));
                foneValue.setBorderColor(BaseColor.BLACK);
                foneValue.setPaddingLeft(10);
                foneValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                foneValue.setVerticalAlignment(Element.ALIGN_CENTER);
                foneValue.setBackgroundColor(BaseColor.WHITE);
                foneValue.setExtraParagraphSpace(5f);
                table.addCell(foneValue);
 
                PdfPCell emailValue = new PdfPCell(new Paragraph(cadastro.getFone(), tableBody));
                emailValue.setBorderColor(BaseColor.BLACK);
                emailValue.setPaddingLeft(10);
                emailValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                emailValue.setVerticalAlignment(Element.ALIGN_CENTER);
                emailValue.setBackgroundColor(BaseColor.WHITE);
                emailValue.setExtraParagraphSpace(5f);
                table.addCell(emailValue);

//                PdfPCell statusValue = new PdfPCell(new Paragraph(cadastro.getStatus(), tableBody));
//                statusValue.setBorderColor(BaseColor.BLACK);
//                statusValue.setPaddingLeft(10);
//                statusValue.setHorizontalAlignment(Element.ALIGN_CENTER);
//                statusValue.setVerticalAlignment(Element.ALIGN_CENTER);
//                statusValue.setBackgroundColor(BaseColor.WHITE);
//                statusValue.setExtraParagraphSpace(5f);
//                table.addCell(statusValue);
            }

            document.add(table);
            document.close();
            writer.close();
            return true;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FormularioServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(FormularioServiceImpl.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
	}

	@Override
	public boolean createExcel(List<FormularioModel> cad, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		return false;
	}

}

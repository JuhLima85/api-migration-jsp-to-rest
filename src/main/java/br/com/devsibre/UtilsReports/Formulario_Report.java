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

import br.com.devsibre.Domain.Entity.Formulario;
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
import br.com.devsibre.Service.FormularioServiceImpl;

@Service
public class Formulario_Report implements Formulario_Report_Service{

    @Override
    public boolean creatPdf(List<Formulario> cad, ServletContext context, HttpServletRequest request,
                            HttpServletResponse response, String membroFilter) {
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

            Paragraph paragraph;
            if ("membros".equals(membroFilter)) {
                paragraph = new Paragraph("Lista de Membros da SIBRE", mainFont);
            } else if ("naoMembros".equals(membroFilter)) {
                paragraph = new Paragraph("Lista de Não Membros da SIBRE", mainFont);
            } else {
                paragraph = new Paragraph("Todos os cadastros da SIBRE", mainFont);
            }
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

            //Determina as Colunas
            PdfPCell nome = new PdfPCell(new Paragraph("Nome", tableHeader));
            nome.setBorderColor(BaseColor.BLACK);
            nome.setPaddingLeft(10);
            nome.setHorizontalAlignment(Element.ALIGN_CENTER);
            nome.setVerticalAlignment(Element.ALIGN_CENTER);
            nome.setBackgroundColor(BaseColor.WHITE);
            nome.setExtraParagraphSpace(5f);
            table.addCell(nome);

            PdfPCell logradouro = new PdfPCell(new Paragraph("Endereco", tableHeader));
            logradouro.setBorderColor(BaseColor.BLACK);
            logradouro.setPaddingLeft(10);
            logradouro.setHorizontalAlignment(Element.ALIGN_CENTER);
            logradouro.setVerticalAlignment(Element.ALIGN_CENTER);
            logradouro.setBackgroundColor(BaseColor.WHITE);
            logradouro.setExtraParagraphSpace(5f);
            table.addCell(logradouro);

            PdfPCell fone = new PdfPCell(new Paragraph("Telefone", tableHeader));
            fone.setBorderColor(BaseColor.BLACK);
            fone.setPaddingLeft(10);
            fone.setHorizontalAlignment(Element.ALIGN_CENTER);
            fone.setVerticalAlignment(Element.ALIGN_CENTER);
            fone.setBackgroundColor(BaseColor.WHITE);
            fone.setExtraParagraphSpace(5f);
            table.addCell(fone);

            PdfPCell data = new PdfPCell(new Paragraph("Data_Nasc", tableHeader));
            data.setBorderColor(BaseColor.BLACK);
            data.setPaddingLeft(10);
            data.setHorizontalAlignment(Element.ALIGN_CENTER);
            data.setVerticalAlignment(Element.ALIGN_CENTER);
            data.setBackgroundColor(BaseColor.WHITE);
            data.setExtraParagraphSpace(5f);
            table.addCell(data);

            PdfPCell membro = new PdfPCell(new Paragraph("Membro", tableHeader));
            membro.setBorderColor(BaseColor.BLACK);
            membro.setPaddingLeft(10);
            membro.setHorizontalAlignment(Element.ALIGN_CENTER);
            membro.setVerticalAlignment(Element.ALIGN_CENTER);
            membro.setBackgroundColor(BaseColor.WHITE);
            membro.setExtraParagraphSpace(5f);
            table.addCell(membro);

            // Preenche as informações na tabela
            for (Formulario cadastro : cad) {

                PdfPCell nomeValue = new PdfPCell(new Paragraph(cadastro.getNome(), tableBody));
                nomeValue.setBorderColor(BaseColor.BLACK);
                nomeValue.setPaddingLeft(10);
                nomeValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                nomeValue.setVerticalAlignment(Element.ALIGN_CENTER);
                nomeValue.setBackgroundColor(BaseColor.WHITE);
                nomeValue.setExtraParagraphSpace(5f);
                table.addCell(nomeValue);

                PdfPCell enderValue = new PdfPCell(new Paragraph(cadastro.getLogradouro(), tableBody));
                enderValue.setBorderColor(BaseColor.BLACK);
                enderValue.setPaddingLeft(10);
                enderValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                enderValue.setVerticalAlignment(Element.ALIGN_CENTER);
                enderValue.setBackgroundColor(BaseColor.WHITE);
                enderValue.setExtraParagraphSpace(5f);
                table.addCell(enderValue);

                PdfPCell foneValue = new PdfPCell(new Paragraph(cadastro.getFone(), tableBody));
                foneValue.setBorderColor(BaseColor.BLACK);
                foneValue.setPaddingLeft(10);
                foneValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                foneValue.setVerticalAlignment(Element.ALIGN_CENTER);
                foneValue.setBackgroundColor(BaseColor.WHITE);
                foneValue.setExtraParagraphSpace(5f);
                table.addCell(foneValue);

                PdfPCell dataNascValue = new PdfPCell(new Paragraph(cadastro.getData(), tableBody));
                dataNascValue.setBorderColor(BaseColor.BLACK);
                dataNascValue.setPaddingLeft(10);
                dataNascValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                dataNascValue.setVerticalAlignment(Element.ALIGN_CENTER);
                dataNascValue.setBackgroundColor(BaseColor.WHITE);
                dataNascValue.setExtraParagraphSpace(5f);
                table.addCell(dataNascValue);

                PdfPCell membroValue = new PdfPCell(
                        new Paragraph(cadastro.isMembro() ? "Sim" : "Não", tableBody)
                );
                membroValue.setBorderColor(BaseColor.BLACK);
                membroValue.setPaddingLeft(10);
                membroValue.setHorizontalAlignment(Element.ALIGN_CENTER);
                membroValue.setVerticalAlignment(Element.ALIGN_CENTER);
                membroValue.setBackgroundColor(BaseColor.WHITE);
                membroValue.setExtraParagraphSpace(5f);
                table.addCell(membroValue);
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
    public boolean createExcel(List<Formulario> cad, ServletContext context, HttpServletRequest request,
                               HttpServletResponse response) {
        // TODO Auto-generated method stub
        return false;
    }
}

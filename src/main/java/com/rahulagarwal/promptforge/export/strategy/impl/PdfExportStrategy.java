package com.rahulagarwal.promptforge.export.strategy.impl;

import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.rahulagarwal.promptforge.codegen.entity.CodeGeneration;
import com.rahulagarwal.promptforge.export.enums.ExportType;
import com.rahulagarwal.promptforge.export.strategy.ExportStrategy;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Component
public class PdfExportStrategy implements ExportStrategy {

    @Override
    public ExportType getType() {
        return ExportType.PDF;
    }

    @Override
    public ByteArrayResource export(CodeGeneration generation) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.add(new Paragraph("PromptForge AI Code Generation", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Prompt:"));
            document.add(new Paragraph(generation.getPrompt()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Language: " + generation.getLanguage()));
            document.add(new Paragraph("Framework: " + generation.getFramework()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Generated Code"));
            document.add(new Paragraph(generation.getGeneratedCode()));
            document.close();
            return new ByteArrayResource(outputStream.toByteArray());
        } catch (Exception ex) {
            throw new RuntimeException("Unable to generate PDF.", ex);
        }
    }

    @Override
    public String getFileName(CodeGeneration generation) {
        return "generation-" + generation.getId() + ".pdf";
    }

    @Override
    public String getContentType() {
        return "application/pdf";
    }
}
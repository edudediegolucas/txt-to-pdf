package es.edudediegolucas.txttopdf.itext;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
@Slf4j
public class CreatePdf {

  private CreatePdf() {
  }

  private static CreatePdf instance;
  private static final String TMP_PDF_NAME_FILE = "temporal.pdf";
  private static final String OUTPUT_PDF_NAME_FILE = "output.pdf";

  public static CreatePdf getInstance() {
    if (instance == null) {
      instance = new CreatePdf();
    }
    return instance;
  }

  public void createPdfNotNumbered(Path pathToFile, int fontSize, int fontType) throws IOException, DocumentException {
    try (var lines = Files.lines(pathToFile)) {
      Font bodyFont = getBodyFont(fontSize, fontType);
      createPdfDocument(lines, bodyFont, OUTPUT_PDF_NAME_FILE);
    }
  }

  public void createPdfNumbered(Path pathToFile, int fontSize, int fontType) throws IOException, DocumentException {
    try (var lines = Files.lines(pathToFile)) {
      Font bodyFont = getBodyFont(fontSize, fontType);
      createPdfDocument(lines, bodyFont, TMP_PDF_NAME_FILE);
      PdfReader pdfReader = new PdfReader(TMP_PDF_NAME_FILE);
      PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(OUTPUT_PDF_NAME_FILE));
      for (int i = 1; i < pdfReader.getNumberOfPages() + 1; i++) {
        PdfContentByte content = pdfStamper.getUnderContent(i);
        content.beginText();
        content.setFontAndSize(getBaseFont(fontType), 10);
        content.setTextMatrix(545, 20);
        content.showText(String.valueOf(i));
        content.endText();
      }
      pdfStamper.close();
      pdfReader.close();
    } finally {
      Files.deleteIfExists(Path.of(TMP_PDF_NAME_FILE));
    }
  }

  private void createPdfDocument(Stream<String> lines, Font bodyFont, String fileName) throws DocumentException, FileNotFoundException {
    Document document = new Document(PageSize.A4, 40, 40, 40, 40);
    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
    writer.setLinearPageMode();
    writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);

    document.open();
    lines.forEach(line -> {
      var paragraph = new Paragraph(new Chunk(line, bodyFont));
      paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
      try {
        document.add(paragraph);
      } catch (DocumentException e) {
        log.error("Pdf creation went wrong!");
        System.exit(-1);
      }
    });
    document.close();
  }

  private Font getBodyFont(int fontSize, int fontType) {
    Font bodyFont;
    switch (fontType) {
      case 0:
        bodyFont = FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.NORMAL, BaseColor.BLACK);
        break;
      case 1:
        bodyFont = FontFactory.getFont(FontFactory.COURIER, fontSize, Font.NORMAL, BaseColor.BLACK);
        break;
      case 2:
        bodyFont = FontFactory.getFont(FontFactory.TIMES, fontSize, Font.NORMAL, BaseColor.BLACK);
        break;
      default:
        throw new IllegalArgumentException("Not a valid font type!");
    }
    return bodyFont;
  }

  private BaseFont getBaseFont(int fontType) throws DocumentException, IOException {
    BaseFont baseFont;
    switch (fontType) {
      case 0:
        baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        break;
      case 1:
        baseFont = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        break;
      case 2:
        baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        break;
      default:
        throw new IllegalArgumentException("Not a valid font type!");
    }
    return baseFont;
  }
}

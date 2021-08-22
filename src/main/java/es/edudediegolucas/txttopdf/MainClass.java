package es.edudediegolucas.txttopdf;

import com.itextpdf.text.DocumentException;
import es.edudediegolucas.txttopdf.itext.CreatePdf;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;

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
public class MainClass {

  public static void main(String... args) {
    if (args.length != 4) {
      log.error("Error: expected 4 arguments!");
      printErrorAndExit();
    }
    Path pathToFile = Path.of(args[0]);
    boolean numbered = Boolean.parseBoolean(args[1]);
    int fontSize = 0;
    int fontType = 0;
    try {
      fontSize = Integer.parseInt(args[2]);
      fontType = Integer.parseInt(args[3]);
    } catch (NumberFormatException numberFormatException) {
      log.error("Error: expected a integer!");
      printErrorAndExit();
    }
    if (fontSize < 8 || fontSize > 20) {
      log.error("Error: font_size must be greater than 8 and less than 20!");
      printErrorAndExit();
    }
    CreatePdf createPdf = CreatePdf.getInstance();
    try {
      if (numbered) {
        createPdf.createPdfNumbered(pathToFile, fontSize, fontType);
      } else {
        createPdf.createPdfNotNumbered(pathToFile, fontSize, fontType);
      }
    } catch (IOException e) {
      log.error("Error! File not found or corrupt!");
    } catch (DocumentException e) {
      log.error("Error! Pdf cannot be created!");
    } catch (IllegalArgumentException e) {
      log.error("Error! font_type is not correct!");
    }
  }

  private static void printErrorAndExit() {
    log.error("USAGE: mvn exec:java -Dexec.args=\"path/to/file.txt numbered_boolean font_size font_type\"");
    log.error("Example: mvn exec:java -Dexec.args=\"/home/user/directory/file.txt true 10 0\"");
    System.exit(-1);
  }
}

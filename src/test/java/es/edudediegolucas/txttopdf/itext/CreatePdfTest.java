package es.edudediegolucas.txttopdf.itext;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.LinkOption;
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
class CreatePdfTest {

  @Test
  void testCreatePdfInstance() {
    var createPdf = CreatePdf.getInstance();
    Assertions.assertNotNull(createPdf);
  }

  @SneakyThrows
  @Test
  void testCreatePdfNotNumbered() {
    var createPdf = CreatePdf.getInstance();
    createPdf.createPdfNotNumbered(Path.of("src/test/resources/chiquito_ipsum.txt"), 10, 0);
    Assertions.assertTrue(Files.exists(Path.of("output.pdf"), LinkOption.NOFOLLOW_LINKS));
    Files.deleteIfExists(Path.of("output.pdf"));
  }

  @SneakyThrows
  @Test
  void testCreatePdfNumbered() {
    var createPdf = CreatePdf.getInstance();
    createPdf.createPdfNumbered(Path.of("src/test/resources/chiquito_ipsum.txt"), 10, 0);
    Assertions.assertTrue(Files.exists(Path.of("output.pdf"), LinkOption.NOFOLLOW_LINKS));
    Files.deleteIfExists(Path.of("output.pdf"));
  }

  @SneakyThrows
  @Test
  void testCreatePdfNotNumberedCourier() {
    var createPdf = CreatePdf.getInstance();
    createPdf.createPdfNotNumbered(Path.of("src/test/resources/chiquito_ipsum.txt"), 10, 1);
    Assertions.assertTrue(Files.exists(Path.of("output.pdf"), LinkOption.NOFOLLOW_LINKS));
    Files.deleteIfExists(Path.of("output.pdf"));
  }

  @SneakyThrows
  @Test
  void testCreatePdfNotNumberedTimes() {
    var createPdf = CreatePdf.getInstance();
    createPdf.createPdfNotNumbered(Path.of("src/test/resources/chiquito_ipsum.txt"), 10, 2);
    Assertions.assertTrue(Files.exists(Path.of("output.pdf"), LinkOption.NOFOLLOW_LINKS));
    Files.deleteIfExists(Path.of("output.pdf"));
  }

  @SneakyThrows
  @Test
  void testCreatePdfNotNumberedNotValidFont() {
    var createPdf = CreatePdf.getInstance();
    Assertions.assertThrows(IllegalArgumentException.class, () -> createPdf.createPdfNotNumbered(Path.of("src/test/resources/chiquito_ipsum.txt"), 10, 3));
  }
}

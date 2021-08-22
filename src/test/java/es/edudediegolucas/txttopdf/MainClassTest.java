package es.edudediegolucas.txttopdf;

import com.ginsberg.junit.exit.ExpectSystemExit;
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
class MainClassTest {


  @Test
  @ExpectSystemExit
  void testMainClassWithNoArguments() {
    MainClass.main();
  }

  @Test
  @ExpectSystemExit
  void testMainClassWithFewArguments() {
    MainClass.main(new String[]{"file.txt", "true"});
  }

  @SneakyThrows
  @Test
  void testMainClass() {
    MainClass.main(new String[]{"src/test/resources/chiquito_ipsum.txt", "true", "10", "0"});
    Assertions.assertTrue(Files.exists(Path.of("output.pdf"), LinkOption.NOFOLLOW_LINKS));
    Files.deleteIfExists(Path.of("output.pdf"));
  }

  @Test
  @ExpectSystemExit
  void testMainClassWrongFontSize() {
    MainClass.main(new String[]{"src/test/resources/chiquito_ipsum.txt", "true", "0", "0"});
  }

  @Test
  @ExpectSystemExit
  void testMainClassWrongFontSizeAgain() {
    MainClass.main(new String[]{"src/test/resources/chiquito_ipsum.txt", "true", "a", "0"});
  }

  @Test
  @ExpectSystemExit
  void testMainClassWrongFontType() {
    MainClass.main(new String[]{"src/test/resources/chiquito_ipsum.txt", "true", "1", "a"});
  }
}

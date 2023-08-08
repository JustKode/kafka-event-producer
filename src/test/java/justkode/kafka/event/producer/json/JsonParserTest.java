
package justkode.kafka.event.producer.json;

import justkode.kafka.event.producer.meta.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Slf4j
class JsonParserTest {
    private InputStream getInputStream(String filename) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("parser/" + filename);

        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + filename);
        } else {
            return inputStream;
        }
    }

    private String getStringFromFile(String filename) throws IOException {
        InputStream inputStream = getInputStream(filename);
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null)
        {
            sb.append(line);
            sb.append('\n');
        }
        reader.close();

        return sb.toString();
    }

    @Test
    void testValidMetaManualMapJson() throws IOException {
        String string = getStringFromFile("validMetaManualMap.json");
        Map<String, Meta> jsonToMetaMap = JsonParser.getJsonToMetaMap(string);

        IntegerMeta idMeta = (IntegerMeta) jsonToMetaMap.get("id");
        FloatMeta scoreAMeta = (FloatMeta) jsonToMetaMap.get("score_a");
        LongMeta longMeta = (LongMeta) jsonToMetaMap.get("cost");
        DoubleMeta scoreBMeta = (DoubleMeta) jsonToMetaMap.get("score_b");
        StringMeta serialNumberMeta = (StringMeta) jsonToMetaMap.get("serial_number");

        assertThat(idMeta.getRandomValue()).isIn(1, 2, 3, 4);
        assertThat(scoreAMeta.getRandomValue()).isIn(1.0f, 2.0f, 3.0f, 4.0f);
        assertThat(longMeta.getRandomValue()).isIn(1L, 2L, 3L, 4L);
        assertThat(scoreBMeta.getRandomValue()).isIn(1.0, 2.0, 3.0, 4.0);
        assertThat(serialNumberMeta.getRandomValue()).isIn("A100000000", "B100000000", "C100000000");
    }

    @Test
    void testValidMetaNonManualMapJson() throws IOException {
        String string = getStringFromFile("validMetaNonManualMap.json");
        Map<String, Meta> jsonToMetaMap = JsonParser.getJsonToMetaMap(string);

        IntegerMeta idMeta = (IntegerMeta) jsonToMetaMap.get("id");
        FloatMeta scoreAMeta = (FloatMeta) jsonToMetaMap.get("score_a");
        LongMeta longMeta = (LongMeta) jsonToMetaMap.get("cost");
        DoubleMeta scoreBMeta = (DoubleMeta) jsonToMetaMap.get("score_b");
        StringMeta serialNumberMeta = (StringMeta) jsonToMetaMap.get("serial_number");

        assertThat(idMeta.getRandomValue()).isBetween(1, 100);
        assertThat(scoreAMeta.getRandomValue()).isBetween(1.0f, 10.0f);
        assertThat(longMeta.getRandomValue()).isBetween(1L, 10L);
        assertThat(scoreBMeta.getRandomValue()).isBetween(1.0, 10.0);
        assertThat(serialNumberMeta.getRandomValue().length()).isEqualTo(10);
    }


    @Test
    void testInvalidMetaManualMapJson() throws IOException {
        String string = getStringFromFile("invalidMetaManualMap.json");
        assertThrows(RuntimeException.class, () -> JsonParser.getJsonToMetaMap(string));
    }


    @Test
    void testInvalidMetaNonManualMapJson() throws IOException {
        String string = getStringFromFile("invalidMetaNonManualMap.json");
        assertThrows(RuntimeException.class, () -> JsonParser.getJsonToMetaMap(string));
    }
}
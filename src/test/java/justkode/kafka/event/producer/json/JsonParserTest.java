
package justkode.kafka.event.producer.json;

import justkode.kafka.event.producer.meta.Meta;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

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

        String result = "";
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
    void testValidMetaManualMapJson() {
        try {
            String string = getStringFromFile("validMetaManualMap.json");
            System.out.println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testValidMetaNonManualMapJson() {
        try {
            String string = getStringFromFile("validMetaNonManualMap.json");
            System.out.println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    void testInvalidMetaManualMapJson() {
        try {
            String string = getStringFromFile("invalidMetaManualMap.json");
            System.out.println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    void testInvalidMetaNonManualMapJson() {
        try {
            String string = getStringFromFile("invalidMetaNonManualMap.json");
            System.out.println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
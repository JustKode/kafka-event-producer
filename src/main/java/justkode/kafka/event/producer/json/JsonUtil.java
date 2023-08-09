package justkode.kafka.event.producer.json;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUtil {
    public static String getStringFromFile(String filename) throws IOException {
        InputStream inputStream = Files.newInputStream(Paths.get(filename));
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

}

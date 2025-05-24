package de.elomagic.agent;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RecordTest {

    @Test
    void testWriteTo() throws IOException, URISyntaxException {

        URL url = Paths.get("c:\\jr-agent-1.0-SNAPSHOT.jar").toUri().toURL();

        assertThat(url.toString()).startsWith("file:");

        Record record = new Record(Paths.get(url.toURI()));

        StringWriter writer = new StringWriter();

        record.writeTo(writer);

        assertThat(writer.toString()).contains(";c:/jr-agent-1.0-SNAPSHOT.jar;");


    }


}
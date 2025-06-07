package de.elomagic.agent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RecordTest {

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testWriteToOnWindows() throws IOException, URISyntaxException {
        URL url = Paths.get("c:\\jr-agent-1.0-SNAPSHOT.jar").toUri().toURL();

        assertThat(url.toString()).startsWith("file:");

        Record r = new Record(Paths.get(url.toURI()), 1234);

        StringWriter writer = new StringWriter();

        r.writeTo(writer);

        assertThat(writer.toString()).contains(";c:/jr-agent-1.0-SNAPSHOT.jar;1234;");
    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    void testWriteToNotOnWindows() throws IOException, URISyntaxException {
        URL url = Paths.get("/jr-agent-1.0-SNAPSHOT.jar").toUri().toURL();

        assertThat(url.toString()).startsWith("file:");

        Record r = new Record(Paths.get(url.toURI()), 6789);

        StringWriter writer = new StringWriter();

        r.writeTo(writer);

        assertThat(writer.toString()).contains("/jr-agent-1.0-SNAPSHOT.jar;6789;");
    }

}
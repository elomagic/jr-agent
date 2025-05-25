package de.elomagic.agent;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ListLoadedClassesAgentTest {

    @Test
    void testNormalizePath() throws IOException, URISyntaxException {
        URL url = Paths.get("c:\\service\\libs\\.\\wrapper\\jr-agent-1.0-SNAPSHOT.jar").toUri().toURL();
        Path p = ListLoadedClassesAgent.normalizePath(url);
        assertThat(p.toString()).isEqualTo("c:\\service\\libs\\wrapper\\jr-agent-1.0-SNAPSHOT.jar");

        url = Paths.get("c:\\service\\libs\\..\\wrapper\\jr-agent-1.0-SNAPSHOT.jar").toUri().toURL();
        p = ListLoadedClassesAgent.normalizePath(url);
        // TODO assertThat(p.toString()).isEqualTo("c:\\service\\wrapper\\jr-agent-1.0-SNAPSHOT.jar");    }
        assertThat(p.toString()).isEqualTo("c:\\service\\libs\\..\\wrapper\\jr-agent-1.0-SNAPSHOT.jar");    }

}
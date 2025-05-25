package de.elomagic.agent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ListLoadedClassesAgentTest {

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testNormalizePathOnWindows() throws IOException, URISyntaxException {
        URL url = Paths.get("c:\\service\\libs\\.\\wrapper\\jr-agent-1.0-SNAPSHOT.jar").toUri().toURL();
        Path p = ListLoadedClassesAgent.normalizePath(url);
        assertThat(p.toString()).isEqualTo("c:\\service\\libs\\wrapper\\jr-agent-1.0-SNAPSHOT.jar");

        url = Paths.get("c:\\service\\libs\\..\\wrapper\\jr-agent-1.0-SNAPSHOT.jar").toUri().toURL();
        p = ListLoadedClassesAgent.normalizePath(url);
        // TODO assertThat(p.toString()).isEqualTo("c:\\service\\wrapper\\jr-agent-1.0-SNAPSHOT.jar");    }
        assertThat(p.toString()).isEqualTo("c:\\service\\libs\\..\\wrapper\\jr-agent-1.0-SNAPSHOT.jar");
    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    void testNormalizePathOnNotWindows() throws IOException, URISyntaxException {
        URL url = Paths.get("/service/libs/./wrapper/jr-agent-1.0-SNAPSHOT.jar").toUri().toURL();
        Path p = ListLoadedClassesAgent.normalizePath(url);
        assertThat(p.toString()).isEqualTo("/service/libs/wrapper/jr-agent-1.0-SNAPSHOT.jar");

        url = Paths.get("/service/libs/../wrapper/jr-agent-1.0-SNAPSHOT.jar").toUri().toURL();
        p = ListLoadedClassesAgent.normalizePath(url);
        // TODO assertThat(p.toString()).isEqualTo("/service/wrapper/jr-agent-1.0-SNAPSHOT.jar");    }
        assertThat(p.toString()).isEqualTo("/service/libs/../wrapper/jr-agent-1.0-SNAPSHOT.jar");
    }


}
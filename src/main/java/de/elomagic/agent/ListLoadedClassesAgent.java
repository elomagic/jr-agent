/*
 * jr-agent
 * Copyright (c) 2025-present Carsten Rambow
 * mailto:developer AT elomagic DOT de
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.elomagic.agent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ListLoadedClassesAgent {

    private static final Path AGENT_FILE = Paths.get("./elo-agent-file.csv");

    private static final Logger LOGGER = LogManager.getLogger(ListLoadedClassesAgent.class);

    private static final Set<URL> seenJars = new HashSet<>();

    public ListLoadedClassesAgent() {
        // noop
    }

    public static void premain(String agentArgs, @NotNull Instrumentation inst) {
        LOGGER.always().log("My agent started");

        backupFile();

        inst.addTransformer((loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> {

            try {
                if (protectionDomain != null &&
                        protectionDomain.getCodeSource() != null &&
                        protectionDomain.getCodeSource().getLocation() != null) {

                    URL location = protectionDomain.getCodeSource().getLocation();

                    synchronized (seenJars) {
                        if (!seenJars.contains(location)) {
                            seenJars.add(location);

                            Record r = new Record(normalizePath(location));
                            appendToFile(r);

                            LOGGER.debug("Class loaded from JAR: {}", location);
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.warn(e.getMessage(), e);
            }

            return null;
        });
    }

    @NotNull
    static Path normalizePath(@NotNull URL location) throws URISyntaxException {
        Path file = Paths.get(location.toURI());

        String s = file.toString();

        if (s.contains("\\..\\")) {
            LOGGER.warn("Following path contains a \\..\\ and is currently not supported: {}", s);
        }

        return Paths.get(s.replace("\\.\\", "\\").replace("/./", "/"));
    }


    private static void backupFile() {

        // Backup the agent file if it exists. Name of the backup file is based on the current ISO local date time.
        try {
            if (Files.exists(AGENT_FILE)) {
                String backupFileName = "elo-agent-file-" + LocalDateTime.now().toString().replace(":", "-") + ".csv";
                Path backupFile = Paths.get(backupFileName);
                Files.copy(AGENT_FILE, backupFile);
                LOGGER.info("Backup created: {}", backupFile);
            }
        } catch (IOException e) {
            LOGGER.error("Unable to create backup of agent file: {}", e.getMessage(), e);
        }

    }

    private static void appendToFile(@NotNull Record r) {

        try (BufferedWriter writer = Files.newBufferedWriter(AGENT_FILE, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            r.writeTo(writer);
        } catch (IOException e) {
            LOGGER.error("Unable to write agent file: {}", e.getMessage(), e);
        }

    }

}

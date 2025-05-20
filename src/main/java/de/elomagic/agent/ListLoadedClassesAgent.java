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

import jakarta.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Set;

public class ListLoadedClassesAgent {

    private static final Logger LOGGER = LogManager.getLogger(ListLoadedClassesAgent.class);

    private static final Set<String> seenJars = new HashSet<>();

    public ListLoadedClassesAgent() {
        // noop
    }

    public static void premain(String agentArgs, Instrumentation inst) {
        LOGGER.always().log("My agent started");

        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(
                    ClassLoader loader,
                    String className,
                    Class<?> classBeingRedefined,
                    ProtectionDomain protectionDomain,
                    byte[] classfileBuffer) {

                try {
                    if (protectionDomain != null &&
                            protectionDomain.getCodeSource() != null &&
                            protectionDomain.getCodeSource().getLocation() != null) {

                        String location = protectionDomain.getCodeSource().getLocation().toString();

                        synchronized (seenJars) {
                            if (!seenJars.contains(location)) {
                                seenJars.add(location);

                                Record r = new Record(location);
                                appendToFile(r);

                                LOGGER.debug("Class loaded from JAR: {}", location);
                            }
                        }
                    }
                } catch (Exception e) {
                    // Fehler beim Ermitteln ignorieren
                }

                return null;
            }
        });
    }

    private static void appendToFile(@Nonnull Record r) {

        Path file = Path.of("./agent-file.csv");

        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            r.writeTo(writer);
        } catch (IOException e) {
            LOGGER.error("Unable to write agent file: {}", e.getMessage(), e);
        }

    }

}

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

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;

public class Record {

    private static final String SEPARATOR_CHAR = ";";

    public Record(@NotNull Path jar) {
        this.jar = jar;
    }

    private final Path jar;

    @NotNull
    public Path getJar() {
        return jar;
    }

    public void writeTo(@NotNull Writer writer) throws IOException {
        writer.write(Long.toString(System.currentTimeMillis()));
        writer.write(SEPARATOR_CHAR);
        writer.write(jar.toString().replace('\\', '/').replace(SEPARATOR_CHAR, "\\" + SEPARATOR_CHAR));
        writer.write(SEPARATOR_CHAR);
        writer.write(System.lineSeparator());
    }

}

/*
 * Copyright 2022 Veronica Anokhina.
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
package ru.org.sevn.mddata;

import java.io.PrintStream;
import java.nio.file.Path;

public class PlainPrinter implements Printer {
    
    private final PrintStream printStream;
    private final Path storeDir;
    
    public PlainPrinter(PrintStream ps, Path storeDir) {
        this.printStream = ps;
        this.storeDir = storeDir;
    }

    @Override
    public PrintStream getPrintStream(String tag) {
        return printStream;
    }

    @Override
    public Path getStoreDir(String tag) {
        return this.storeDir;
    }

    @Override
    public void end(String tag) {
        
    }
    
}

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

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class TagPrinter extends FileTagPrinter {
    
    private Map<String, PrintStream> map = new HashMap();
    private String ext = ".md";

    public TagPrinter(Path storeDir) {
        super(storeDir);
    }

    public TagPrinter(Path storeDir, String ext) {
        super(storeDir);
        this.ext = ext;
    }
    
    @Override
    public synchronized PrintStream getPrintStream(String tag) {
        PrintStream ps = map.get(tag);
        if (ps == null) {
            File fl = getFileByTag(tag);
            try {
                System.err.println("Open file " + fl.getCanonicalPath());
            } catch (IOException ex) {
                System.err.println("Open file " + fl.getAbsolutePath());
            }
            try {
                ps = new PrintStream (fl, "UTF-8");
                map.put(tag, ps);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return ps;
    }
    
    protected File getFileByTag(String tag) {
        return new File(storeDir.toFile(), tag + this.ext);
    }

    @Override
    public Path getStoreDir(String tag) {
        return storeDir;
    }

    @Override
    public synchronized  void end(String tag) {
        PrintStream ps = map.remove(tag);
        if (ps != null) {
            ps.close();
        }
    }
    
}

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
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 *
 * @author Veronica Anokhina
 */
public class DocPrinterBasic {
    protected final Map<String, List<ItemInfo>> tagItem = new HashMap();
    
    public <T extends DocPrinterBasic> T init(Map<String, List<ItemInfo>> tagItem) {
        this.tagItem.putAll(tagItem);
        return (T)this;
    }
    
    protected void printTitle(PrintStream out, String t) {
        out.printf("\n## %s\n\n", t);
    }
    
    protected void printItemTitle(PrintStream out, Path storeDir, ItemInfo ii, Path contentDir) {
        out.printf("```%s``` %s ", storeDir.relativize(contentDir), ii.getTitle());
    }
    
    protected void printItemUrl(PrintStream out, ItemInfo ii) {
        out.printf("[%s](%s) ", "url", ii.getUrl());
    }
    
    protected void printItemContent(PrintStream out, Path storeDir, Path content) {
        out.printf("[%s](%s)", "💾", storeDir.relativize(content));
    }
    
    protected void printItem(PrintStream out, Path storeDir, ItemInfo ii) {
        Path contentDir = Paths.get(ii.getPath()).getParent();
        printItemTitle(out, storeDir, ii, contentDir);
        if (ii.getUrl() != null && ii.getUrl().length() > 0) {
            printItemUrl(out, ii);
        }

        if (ii.getContent() != null && ii.getContent().length() > 0) {
            Path content = contentDir.resolve(ii.getContent());
            printItemContent(out, storeDir, content);
        }
        out.println("  ");
    }
    
    protected void printTag(String t, PrintStream out, Path storeDir) {

        List<ItemInfo> lii = tagItem.get(t);
        lii.sort((e1, e2) -> {
            return e1.getTitle().compareTo(e2.getTitle());
        });
        printTitle(out, t);
        lii.forEach(ii -> {
            printItem(out, storeDir, ii);
        });
    }
    
    protected void printTags(Printer printer) {
        TreeSet<String> ts = new TreeSet(this.tagItem.keySet());
        ts.forEach(t -> {
            try {
                final PrintStream out = printer.getPrintStream(t);
                final Path storeDir = printer.getStoreDir(t);
                printTag(t, out, storeDir);
            } finally {
                printer.end(t);
            }
        });
    }
    
    protected void printDates(Printer printer) {
        String t = "By dates";
        try {
            final PrintStream out = printer.getPrintStream(t);
            final Path storeDir = printer.getStoreDir(t);
            printTitle(out, t);

            this.tagItem.values().stream().flatMap(e -> e.stream()).distinct().sorted((e1, e2) -> {
                long res = (e2.getDate().getTime() - e1.getDate().getTime());
                return (res == 0) ? 0 : (res > 0) ? 1 : -1;
            }).forEach(ii -> {
                printItem(out, storeDir, ii);
            });
        } finally {
            printer.end(t);
        }
    }
    
    public void printIt(Printer printer) {
        printTags(printer);
        printDates(printer);
    }
    
}

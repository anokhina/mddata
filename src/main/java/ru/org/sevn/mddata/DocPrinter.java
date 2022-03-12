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

/**
 *
 * @author Veronica Anokhina
 */
public class DocPrinter extends DocPrinterBasic {
    
    private String lev(int lev) {
        switch(lev) {
            case 0:
                return "b";
            case 1:
                return "Kb";
            case 2:
                return "Mb";
            case 3:
                return "Gb";
            case 4:
                return "Tb";
        }
        return "" + lev;
    }
    
    private String sz(long l, int powl, int lev) {
        int pown = powl * 1024;
        if (l / pown > 0) {
            return sz(l, pown, lev + 1);
        } else {
            if (lev > 1) {
                return Math.round((((double)l) / powl) * 10) / 10.0 + " " + lev(lev - 1);
            } else {
                return l + " " + lev(lev - 1);
            }
        }
    }
    
    @Override
    protected void printItemTitle(PrintStream out, Path storeDir, ItemInfo ii, Path contentDir) {
        out.printf("\n%s  %d %s \n %s ", ii.getContent() == null ? "" : ii.getContent(), ii.getContentSize(), sz(ii.getContentSize(), 1, 1), ii.getTitle());
    }
    
    @Override
    protected void printItemUrl(PrintStream out, ItemInfo ii) {
        out.printf("[%s](%s) ", "url", ii.getUrl());
    }
    
    @Override
    protected void printItemContent(PrintStream out, Path storeDir, Path content) {
        out.printf("[%s](%s)", "💾", storeDir.relativize(content));
    }
    
}

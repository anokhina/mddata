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

public class App {
        
    public static void run(File dir, File outDir) throws Exception {
        run(dir, new TagPrinter(outDir.toPath()));
    }
    
    public static void runHtml(File dir, File outDir) throws Exception {
        run(dir, new HtmlTagPrinter(outDir.toPath()));
    }
    
    public static void run (File dir, FileTagPrinter tagPrinter) throws Exception {
        FileIndexer fi = new FileIndexer(dir);
        fi.indexIt();
        new DocPrinter().init(fi.getTagItem()).printIt(tagPrinter);
    }
}

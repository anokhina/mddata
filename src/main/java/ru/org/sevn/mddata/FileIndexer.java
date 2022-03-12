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

import com.vladsch.flexmark.util.ast.Node;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import org.json.JSONObject;

public class FileIndexer {
    private final File dirRoot;
    
    private final Map<String, List<ItemInfo>> tagItem = new HashMap();
    
    public FileIndexer(File dirRoot) {
        this.dirRoot = dirRoot;
    }
    
    public void indexIt() throws Exception {
        indexIt(this.dirRoot);
    }
    
    public Map<String, List<ItemInfo>> getTagItem() {
        return tagItem;
    }
    
    private void indexIt(final File dir) throws Exception {
        File idx = new File(dir, "index.md");
        if (idx.exists() && dir.getName().length() == 6) {
            final Path filePath = idx.toPath();
            final String src = MdFileParser.readFile(filePath);
            System.err.println("Try to parse: " + filePath.toString());
            try {
                final Node doc = MdFileParser.parse(src);
                ItemInfoBuilder iib = new ItemInfoBuilder(new ItemInfo().setPath(filePath.toString()));
                ItemInfo ii = iib.fromNode(doc);
                if (ii.getContent() != null) {
                    final File cf = new File(filePath.toFile().getParentFile(), ii.getContent());
                    if (cf.exists()) {
                        ii.setContentSize(cf.length());
                    }
                }
                iib.getObject().getTags().forEach(t -> {
                    List<ItemInfo> lii = tagItem.get(t);
                    if (lii == null) {
                        lii = new ArrayList();
                        tagItem.put(t, lii);
                    }
                    lii.add(ii);
                });
                if ( (ii.getUrl() == null || ii.getUrl().trim().length() == 0) ||
                        (ii.getContent() == null || ii.getContent().trim().length() == 0)
                        ) {
                } else {
                    MdFileParser.ast(doc);
                    JSONObject jo = new JSONObject(ii);
                    System.out.println(">>>" + jo.toString(2));
                }
                
            } catch (Exception e) {
                System.err.println("Fail to parse: " + filePath.toString());
                e.printStackTrace();
            }
        } else {
            for (final File f : dir.listFiles()) {
                if (f.isDirectory()) {
                    indexIt(f);
                }
            }
        }
    }
}

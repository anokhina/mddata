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

import com.vladsch.flexmark.html.AttributeProvider;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.IndependentAttributeProviderFactory;
import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.util.ast.Node;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Veronica Anokhina
 */
public class HtmlTagPrinter extends TagPrinter {
    
    public HtmlTagPrinter(Path storeDir) {
        super(storeDir);
    }
    
    @Override
    public synchronized  void end(String tag) {
        super.end(tag);
        File mdFile = this.getFileByTag(tag);
        if (mdFile.exists()) {
            final HtmlRenderer htmlRenderer = MdFileParser.getHtmlRenderer();
            
            File htmlFile = new File(mdFile.getParentFile(), mdFile.getName() + ".html");
            final String src;
            try {
                src = MdFileParser.readFile(mdFile.toPath());
                final Node document = MdFileParser.parse(src);
                
                System.out.println("\n---- HTML ---\n" + htmlFile.getAbsolutePath());
                final String html = htmlRenderer.render(document);
                Files.write(htmlFile.toPath(), html.getBytes(StandardCharsets.UTF_8));
            } catch (IOException ex) {
                Logger.getLogger(HtmlTagPrinter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}

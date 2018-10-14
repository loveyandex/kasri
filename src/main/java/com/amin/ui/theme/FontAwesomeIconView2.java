/**
 * Copyright (c) 2013-2016 Jens Deters http://www.jensd.de
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 *
 */
package com.amin.ui.theme;

import de.jensd.fx.glyphs.GlyphIcon;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jens Deters
 */
public class FontAwesomeIconView2 extends GlyphIcon<FontAwesomeIcon> {

    public final static String TTF_PATH = "/com/amin/ui/theme/fontawesome-webfont.ttf";

    static {
        try {
            Font.loadFont(FontAwesomeIconView2.class.getResource(TTF_PATH).openStream(), 10.0d);
        } catch (IOException ex) {
            Logger.getLogger(FontAwesomeIconView2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public FontAwesomeIconView2(FontAwesomeIcon icon) {
        super(FontAwesomeIcon.class);
        setFont(new Font("FontAwesome", DEFAULT_ICON_SIZE));
        setIcon(icon);
    }

    public FontAwesomeIconView2() {
        this(FontAwesomeIcon.ANCHOR);
    }

    @Override
    public FontAwesomeIcon getDefaultGlyph() {
        return FontAwesomeIcon.ANCHOR;
    }

    
}

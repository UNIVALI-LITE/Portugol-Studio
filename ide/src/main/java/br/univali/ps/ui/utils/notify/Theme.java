/*
 * Copyright 2017 dorkbox, llc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.univali.ps.ui.utils.notify;

import br.univali.ps.ui.swing.ColorController;
import java.awt.Color;
import java.awt.Font;


/**
 * Settings available to change the theme
 */
public
class Theme {
    final Color panel_BG;
    final Color titleText_FG;
    final Color mainText_FG;
    final Color closeX_FG;
    final Color progress_FG;

    final Font titleTextFont;
    final Font mainTextFont;


    Theme(final String titleTextFont, final String mainTextFont, boolean isDarkTheme) {
        this.titleTextFont = Font.decode(titleTextFont);
        this.mainTextFont = Font.decode(mainTextFont);

        if (isDarkTheme) {
            panel_BG = Color.DARK_GRAY;
            titleText_FG = Color.GRAY;
            mainText_FG = Color.LIGHT_GRAY;
            closeX_FG = Color.GRAY;
            progress_FG = Color.gray;
        }
        else {
            panel_BG = ColorController.COR_PRINCIPAL;
            titleText_FG = ColorController.COR_LETRA;
            mainText_FG = ColorController.COR_LETRA;
            closeX_FG = Color.LIGHT_GRAY;
            progress_FG = ColorController.AMARELO;
        }
    }

    public
    Theme(final String titleTextFont, final String mainTextFont,
          final Color panel_BG, final Color titleText_FG, final Color mainText_FG,
          final Color closeX_FG, final Color progress_FG) {
        this.titleTextFont = Font.decode(titleTextFont);
        this.mainTextFont = Font.decode(mainTextFont);

        this.panel_BG = panel_BG;
        this.titleText_FG = titleText_FG;
        this.mainText_FG = mainText_FG;
        this.closeX_FG = closeX_FG;
        this.progress_FG = progress_FG;
    }
}

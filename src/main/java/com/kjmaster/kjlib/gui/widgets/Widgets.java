package com.kjmaster.kjlib.gui.widgets;

import com.kjmaster.kjlib.gui.layout.HorizontalLayout;
import com.kjmaster.kjlib.gui.layout.PositionalLayout;
import com.kjmaster.kjlib.gui.layout.VerticalLayout;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.kjmaster.kjlib.gui.widgets.AbstractImageLabel.TYPE_IMAGELABEL;
import static com.kjmaster.kjlib.gui.widgets.AbstractLabel.TYPE_LABEL;
import static com.kjmaster.kjlib.gui.widgets.BlockRender.TYPE_BLOCKRENDER;
import static com.kjmaster.kjlib.gui.widgets.Button.TYPE_BUTTON;
import static com.kjmaster.kjlib.gui.widgets.ChoiceLabel.TYPE_CHOICELABEL;
import static com.kjmaster.kjlib.gui.widgets.ColorChoiceLabel.TYPE_COLORCHOICELABEL;
import static com.kjmaster.kjlib.gui.widgets.ColorSelector.TYPE_COLORSELECTOR;
import static com.kjmaster.kjlib.gui.widgets.EnergyBar.TYPE_ENERGYBAR;
import static com.kjmaster.kjlib.gui.widgets.FloatField.TYPE_FLOATFIELD;
import static com.kjmaster.kjlib.gui.widgets.IconHolder.TYPE_ICONHOLDER;
import static com.kjmaster.kjlib.gui.widgets.IconRender.TYPE_ICONRENDER;
import static com.kjmaster.kjlib.gui.widgets.ImageChoiceLabel.TYPE_IMAGECHOICELABEL;
import static com.kjmaster.kjlib.gui.widgets.IntegerField.TYPE_INTEGERFIELD;
import static com.kjmaster.kjlib.gui.widgets.Panel.TYPE_PANEL;
import static com.kjmaster.kjlib.gui.widgets.ScrollableLabel.TYPE_SCROLLABLELABEL;
import static com.kjmaster.kjlib.gui.widgets.Slider.TYPE_SLIDER;
import static com.kjmaster.kjlib.gui.widgets.TabbedPanel.TYPE_TABBEDPANEL;
import static com.kjmaster.kjlib.gui.widgets.TagSelector.TYPE_TAGSELECTOR;
import static com.kjmaster.kjlib.gui.widgets.TextField.TYPE_TEXTFIELD;
import static com.kjmaster.kjlib.gui.widgets.ToggleButton.TYPE_TOGGLEBUTTON;
import static com.kjmaster.kjlib.gui.widgets.WidgetList.TYPE_WIDGETLIST;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class Widgets {

    private static final Map<String, Supplier<Widget<?>>> FACTORIES = new HashMap<>();

    static {
        FACTORIES.put(TYPE_BLOCKRENDER, BlockRender::new);
        FACTORIES.put(TYPE_BUTTON, Button::new);
        FACTORIES.put(TYPE_LABEL, Label::new);
        FACTORIES.put(TYPE_CHOICELABEL, ChoiceLabel::new);
        FACTORIES.put(TYPE_COLORCHOICELABEL, ColorChoiceLabel::new);
        FACTORIES.put(TYPE_COLORSELECTOR, ColorSelector::new);
        FACTORIES.put(TYPE_ENERGYBAR, EnergyBar::new);
        FACTORIES.put(TYPE_ICONHOLDER, IconHolder::new);
        FACTORIES.put(TYPE_ICONRENDER, IconRender::new);
        FACTORIES.put(TYPE_IMAGECHOICELABEL, ImageChoiceLabel::new);
        FACTORIES.put(TYPE_IMAGELABEL, ImageLabel::new);
        FACTORIES.put(TYPE_PANEL, Panel::new);
        FACTORIES.put(TYPE_SCROLLABLELABEL, ScrollableLabel::new);
        FACTORIES.put(TYPE_SLIDER, Slider::new);
        FACTORIES.put(TYPE_TABBEDPANEL, TabbedPanel::new);
        FACTORIES.put(TYPE_TEXTFIELD, TextField::new);
        FACTORIES.put(TYPE_INTEGERFIELD, IntegerField::new);
        FACTORIES.put(TYPE_FLOATFIELD, FloatField::new);
        FACTORIES.put(TYPE_TOGGLEBUTTON, ToggleButton::new);
        FACTORIES.put(TYPE_WIDGETLIST, WidgetList::new);
        FACTORIES.put(TYPE_TAGSELECTOR, TagSelector::new);
    }

    @Nullable
    public static Widget<?> createWidget(String type) {
        Supplier<Widget<?>> function = FACTORIES.get(type);
        if (function == null) {
            return null;
        }
        return function.get();
    }

    public static Panel positional() {
        return new Panel().layout(new PositionalLayout());
    }

    public static Panel horizontal() {
        return new Panel().layout(new HorizontalLayout());
    }

    public static Panel horizontal(int margin, int spacing) {
        return new Panel().layout(new HorizontalLayout().setHorizontalMargin(margin).setSpacing(spacing));
    }

    public static Panel vertical() {
        return new Panel().layout(new VerticalLayout());
    }

    public static Panel vertical(int margin, int spacing) {
        return new Panel().layout(new VerticalLayout().setVerticalMargin(margin).setSpacing(spacing));
    }

    public static Button button(int x, int y, int w, int h, String text) {
        return new Button().hint(x, y, w, h).text(text);
    }

    public static Button button(String text) {
        return new Button().text(text);
    }

    public static TextField textfield(int x, int y, int w, int h) {
        return new TextField().hint(x, y, w, h);
    }

    public static Label label(int x, int y, int w, int h, String text) {
        return new Label().hint(x, y, w, h).text(text);
    }

    public static Label label(String text) {
        return new Label().text(text);
    }

    public static ImageChoiceLabel imageChoice(int x, int y, int w, int h) {
        return new ImageChoiceLabel().hint(x, y, w, h);
    }

    public static WidgetList list(int x, int y, int w, int h) {
        return new WidgetList().hint(x, y, w, h);
    }

    public static Slider slider(int x, int y, int w, int h) {
        return new Slider().hint(x, y, w, h);
    }
}

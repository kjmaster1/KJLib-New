package com.kjmaster.kjlib.gui.widgets;

import com.kjmaster.kjlib.gui.GuiParser;
import com.kjmaster.kjlib.gui.Scrollable;
import com.kjmaster.kjlib.gui.events.ValueEvent;
import com.kjmaster.kjlib.typed.Key;
import com.kjmaster.kjlib.typed.Type;
import com.kjmaster.kjlib.typed.TypeConvertors;
import com.kjmaster.kjlib.typed.TypedMap;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class ScrollableLabel extends AbstractLabel<ScrollableLabel> implements Scrollable {

    public static final String TYPE_SCROLLABLELABEL = "scrollablelabel";
    public static final Key<Integer> PARAM_VALUE = new Key<>("value", Type.INTEGER);

    public static final int DEFAULT_REALMIN = 0;
    public static final int DEFAULT_REALMAX = 100;
    public static final String DEFAULT_SUFFIX = "";

    private int realmin = DEFAULT_REALMIN;
    private int realmax = DEFAULT_REALMAX;
    private int first = 0;
    private String suffix = DEFAULT_SUFFIX;
    private List<ValueEvent> valueEvents = null;

    public ScrollableLabel() {
        setFirstSelected(0);
    }

    public String getSuffix() {
        return suffix;
    }

    public ScrollableLabel suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public ScrollableLabel realMaximum(int realmax) {
        this.realmax = realmax;

        if (first > realmax-realmin) {
            first = realmax-realmin;
        }
        setFirstSelected(first);

        return this;
    }

    public int getRealMaximum() {
        return realmax;
    }

    public int getRealValue() {
        return first + realmin;
    }

    public ScrollableLabel realValue(int value) {
        int f = value - realmin;
        if (f < 0) {
            f = 0;
        }
        setFirstSelected(f);
        return this;
    }

    public ScrollableLabel realMinimum(int realmin) {
        this.realmin = realmin;

        if (first < 0) {
            first = 0;
        }
        setFirstSelected(first);

        return this;
    }

    public int getRealMinimum() {
        return realmin;
    }

    @Override
    public int getMaximum() {
        return (realmax-realmin+1);
    }

    @Override
    public int getCountSelected() {
        return 1;
    }

    @Override
    public int getFirstSelected() {
        return first;
    }

    @Override
    public void setFirstSelected(int first) {
        if (this.first == first) {
            return;
        }
        this.first = first;
        text(getRealValue() + suffix);
        fireValueEvents(getRealValue());
    }

    public ScrollableLabel event(ValueEvent event) {
        if (valueEvents == null) {
            valueEvents = new ArrayList<>();
        }
        valueEvents.add(event);
        return this;
    }

    public void removeValueEvent(ValueEvent event) {
        if (valueEvents != null) {
            valueEvents.remove(event);
        }
    }

    private void fireValueEvents(int value) {
        fireChannelEvents(TypedMap.builder().put(PARAM_VALUE, value).build());
        if (valueEvents != null) {
            for (ValueEvent event : valueEvents) {
                event.valueChanged(value);
            }
        }
    }

    @Override
    public void readFromGuiCommand(GuiParser.GuiCommand command) {
        super.readFromGuiCommand(command);
        suffix = GuiParser.get(command, "suffix", DEFAULT_SUFFIX);
        realmin = GuiParser.get(command, "realmin", DEFAULT_REALMIN);
        realmax = GuiParser.get(command, "realmax", DEFAULT_REALMAX);
    }

    @Override
    public void fillGuiCommand(GuiParser.GuiCommand command) {
        super.fillGuiCommand(command);
        GuiParser.put(command, "suffix", suffix, DEFAULT_SUFFIX);
        GuiParser.put(command, "realmin", realmin, DEFAULT_REALMIN);
        GuiParser.put(command, "realmax", realmax, DEFAULT_REALMAX);
    }

    @Override
    public GuiParser.GuiCommand createGuiCommand() {
        return new GuiParser.GuiCommand(TYPE_SCROLLABLELABEL);
    }

    @Override
    public <T> void setGenericValue(T value) {
        realValue(TypeConvertors.toInt(value));
    }

    @Override
    public Object getGenericValue(Type<?> type) {
        return getRealValue();
    }
}

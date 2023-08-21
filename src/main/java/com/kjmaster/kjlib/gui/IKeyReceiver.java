package com.kjmaster.kjlib.gui;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 * Implement this interface on a Screen (gui) if you want to be certain to receive
 * keyboard events even if another interface (like JEI) takes over. This is
 * already implemented by GenericGuiContainer
 */
public interface IKeyReceiver {
    Window getWindow();

    void keyTypedFromEvent(int keyCode, int scanCode);

    // Return true if event is handled and should be canceled
    // This is called only if there is a global window open
    boolean mouseClickedFromEvent(double x, double y, int button);
    boolean mouseReleasedFromEvent(double x, double y, int button);
    boolean mouseScrolledFromEvent(double x, double y, double amount);

    void charTypedFromEvent(char codePoint);
}

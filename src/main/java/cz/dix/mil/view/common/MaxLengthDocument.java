package cz.dix.mil.view.common;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Document, typically for {@link JTextField} instances to limit number of characters that can be typed in.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class MaxLengthDocument extends PlainDocument {

    private final int limit;

    /**
     * Creates a new max length document.
     *
     * @param limit limit for maximum characters that can be typed in
     */
    public MaxLengthDocument(int limit) {
        super();
        this.limit = limit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) {
            return;
        }
        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}

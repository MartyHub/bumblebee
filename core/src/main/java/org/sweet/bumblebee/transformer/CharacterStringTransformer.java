package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.StringTransformer;
import org.sweet.bumblebee.StringTransformerException;

public class CharacterStringTransformer implements StringTransformer<Character> {

    @Override
    public Class<Character> getKey() {
        return Character.class;
    }

    @Override
    public Character convert(String s) throws StringTransformerException {
        if (s.length() != 1) {
            throw new StringTransformerException(String.format("Invalid character value <%s>", s));
        }

        return Character.valueOf(s.charAt(0));
    }

    @Override
    public String getUsage() {
        return "character";
    }
}

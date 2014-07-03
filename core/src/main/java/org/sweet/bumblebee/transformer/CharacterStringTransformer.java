package org.sweet.bumblebee.transformer;

import org.sweet.bumblebee.BumblebeeException;
import org.sweet.bumblebee.StringTransformer;

public class CharacterStringTransformer implements StringTransformer<Character> {

    @Override
    public Class<Character> getKey() {
        return Character.class;
    }

    @Override
    public Character convert(String s) throws BumblebeeException {
        if (s.length() != 1) {
            throw new BumblebeeException(String.format("Invalid character value <%s>", s));
        }

        return Character.valueOf(s.charAt(0));
    }

    @Override
    public String getUsage() {
        return "character";
    }
}

package io.github.vyaslav.kadmin.commands;

import io.github.vyaslav.kadmin.KadminCommand;

import static java.lang.String.format;


public class KadminGetPrincipalsCommand implements KadminCommand {
    private final String expression;

    public KadminGetPrincipalsCommand(String expression) {
        this.expression = expression;
    }

    public String commandString() {
        return format("getprincs %s", expression);
    }
}

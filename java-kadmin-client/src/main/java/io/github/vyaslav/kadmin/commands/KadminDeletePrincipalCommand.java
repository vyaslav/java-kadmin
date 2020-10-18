package io.github.vyaslav.kadmin.commands;

import io.github.vyaslav.kadmin.KadminCommand;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;


public class KadminDeletePrincipalCommand implements KadminCommand {
    private final String principalName;

    public KadminDeletePrincipalCommand(String principalName) {
        this.principalName = principalName;
    }

    public String commandString() {
        return format("delprinc -force %s", principalName);
    }

    public static List<KadminCommand> toDeletePrincipalCommands(List<String> principalNames) {
        return principalNames.stream()
                .map(KadminDeletePrincipalCommand::new)
                .collect(Collectors.toList());
    }
}

package io.github.vyaslav.kadmin;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class KadminCommandResultParser {
    private static Map<KadminReply, Pattern> replyPatterns = Arrays.stream(KadminReply.values())
            .collect(Collectors.toMap(
                    Function.identity(),
                    reply -> Pattern.compile(reply.getRegExp())));

    static KadminCommandResult parse(int status, String output) {
        return new KadminCommandResult(status, parseReply(status, output), output);
    }

    private static KadminReply parseReply(int status, String output) {
        if (status != 0) {
            return KadminReply.ERROR_OCCURED;
        }
        return replyPatterns.entrySet().stream()
                .filter(entry -> entry.getValue().matcher(output).matches())
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(KadminReply.NO_REPLY_FOUND);
    }
}

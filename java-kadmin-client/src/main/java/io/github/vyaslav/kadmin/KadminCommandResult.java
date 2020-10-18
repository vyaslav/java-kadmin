package io.github.vyaslav.kadmin;

public class KadminCommandResult {
    private final int exitCode;
    private final KadminReply reply;
    private final String output;

    public KadminCommandResult(int exitCode, KadminReply reply, String output) {
        this.exitCode = exitCode;
        this.reply = reply;
        this.output = output;
    }

    public String getOutput() {
        return output;
    }

    public KadminReply getReply() {
        return reply;
    }

    public int getExitCode() {
        return exitCode;
    }

}

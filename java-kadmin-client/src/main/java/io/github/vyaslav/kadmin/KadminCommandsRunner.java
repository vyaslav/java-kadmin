package io.github.vyaslav.kadmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class KadminCommandsRunner {
    final Logger log = LoggerFactory.getLogger(KadminCommandsRunner.class);
    private final String principal;
    private final String keytab;
    private final String krb5ConfigPath;

    public KadminCommandsRunner(String principal, String keytab, String krb5ConfigPath) {
        this.principal = principal;
        this.keytab = keytab;
        this.krb5ConfigPath = krb5ConfigPath;
    }

    public KadminCommandResult runCommand(KadminCommand command) throws IOException {
        try (KadminProccess kAdmin = new KadminProccess(command)) {
            int status = kAdmin.waitFor();
            String output = new BufferedReader(new InputStreamReader(kAdmin.getInputStream()))
                    .lines()
                    .collect(Collectors.joining("\n"));
            return KadminCommandResultParser.parse(status, output);
        }
    }

    private class KadminProccess implements AutoCloseable {
        private final Process kAdmin;

        public KadminProccess(KadminCommand command) throws IOException {
            ProcessBuilder processBuilder = new ProcessBuilder("kadmin",
                    "-p", principal,
                    "-kt", keytab,
                    "-q", command.commandString())
                    .directory(new File("/usr/bin"))
                    .redirectErrorStream(true);
            processBuilder.environment().put("KRB5_CONFIG", krb5ConfigPath);
            kAdmin = processBuilder.start();
            kAdmin.onExit().thenAccept(process ->
                            log.info("stopped kadmin {}, exit value: {}", process.pid(), process.exitValue()));
            log.info("Kadmin process PID: {} has started", kAdmin.pid());
        }

        public int waitFor() {
            try {
                return kAdmin.waitFor();
            } catch (InterruptedException e) {
                return kAdmin.exitValue();
            }
        }

        public InputStream getInputStream() {
            return kAdmin.getInputStream();
        }

        @Override
        public void close() throws IOException {
            if (!destroy()) {
                kAdmin.destroyForcibly();
            }
        }

        private boolean destroy() {
            kAdmin.destroy();
            try {
                return kAdmin.waitFor(10, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                return false;
            }
        }
    }
}

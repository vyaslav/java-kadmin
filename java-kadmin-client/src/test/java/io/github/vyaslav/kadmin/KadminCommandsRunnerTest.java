package io.github.vyaslav.kadmin;

import io.github.vyaslav.kadmin.commands.KadminCreatePrincipalCommand;
import io.github.vyaslav.kadmin.commands.KadminDeletePrincipalCommand;
import io.github.vyaslav.kadmin.commands.KadminGetPrincipalsCommand;
import org.junit.*;

import java.io.IOException;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Ignore
public class KadminCommandsRunnerTest {
    private static final String REALM = "MYREALM";
    private static final String PRINCIPAL = "admin/admin";
    private static KadminCommandsRunner kadminCommandsRunner;

    @BeforeClass
    public static void setup() {
        kadminCommandsRunner = new KadminCommandsRunner(PRINCIPAL + "@" + REALM, "/etc/security/keytabs/krb5.keytab", "/etc/krb5.conf");
    }

    @Test
    public void runCommand_createPrincipalCommand() throws IOException {
        createPrincipal("test", REALM);
    }

    @Test
    public void runCommand_getPrincipalsCommand() throws IOException {
        getPrincipals("test", REALM);
    }

    @Test
    public void runCommand_deletePrincipalCommand() throws IOException {
        deletePrincipal("test", REALM);
    }

    @Test
    public void runCommand_100() throws IOException {
        for (int i = 0; i < 100; i++) {
            String principalName = "test_" + i;
            createPrincipal(principalName, REALM);
            getPrincipals(principalName, REALM);
            deletePrincipal(principalName, REALM);
        }
    }

    private void createPrincipal(String principalName, String realm) throws IOException {
        KadminCommandResult result = kadminCommandsRunner.runCommand(
                new KadminCreatePrincipalCommand(principalName, "password"));
        assertEquals(result.getExitCode(), 0);
        assertEquals(result.getOutput(), "WARNING: no policy specified for " + principalName + "@" + realm + "; defaulting to no policy\n" +
                "Authenticating as principal " + PRINCIPAL + "@" + REALM + " with keytab /etc/security/keytabs/krb5.keytab.\n" +
                "Principal \"" + principalName + "@" + realm + "\" created.");
    }

    private void getPrincipals(String principalName, String realm) throws IOException {
        KadminCommandResult result = kadminCommandsRunner.runCommand(
                new KadminGetPrincipalsCommand(""));
        assertEquals(result.getExitCode(), 0);
        assertTrue(result.getOutput().contains(principalName + "@" + realm));
    }

    private void deletePrincipal(String principalName, String realm) throws IOException {
        KadminCommandResult result = kadminCommandsRunner.runCommand(
                new KadminDeletePrincipalCommand(principalName));
        assertEquals(result.getExitCode(), 0);
        assertEquals(result.getOutput(), "Authenticating as principal " + PRINCIPAL + "@" + REALM + " with keytab /etc/security/keytabs/krb5.keytab.\n" +
                "Principal \"" + principalName + "@" + realm + "\" deleted.\n" +
                "Make sure that you have removed this principal from all ACLs before reusing.");
    }
}
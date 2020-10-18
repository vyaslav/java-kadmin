package io.github.vyaslav.kadmin;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KadminCommandResultParserTest {
    private static final String REALM = "MYREALM";
    private static final String PRINCIPAL = "admin/admin";

    @Test
    public void parse_exitCodeNotZero_givesErrorOccured() {
        KadminCommandResult result = KadminCommandResultParser.parse(1, "");
        assertEquals(KadminReply.ERROR_OCCURED, result.getReply());
    }

    @Test
    public void parse_exitCodeZero_givesNoReplyFound() {
        KadminCommandResult result = KadminCommandResultParser.parse(0, "");
        assertEquals(KadminReply.NO_REPLY_FOUND, result.getReply());
    }

    @Test
    public void parse_principalCreated() {
        String output = "WARNING: no policy specified for " + PRINCIPAL + "@" + REALM + "; defaulting to no policy\n" +
                "Authenticating as principal " + PRINCIPAL + "@" + REALM + " with keytab /etc/security/keytabs/krb5.keytab.\n" +
                "Principal \"" + PRINCIPAL + "@" + REALM + "\" created.";
        KadminCommandResult result = KadminCommandResultParser.parse(0, output);
        assertEquals(KadminReply.PRINCIPAL_CREATED, result.getReply());
    }

    @Test
    public void parse_principalAlreadyDeleted() {
        String output = "Authenticating as principal " + PRINCIPAL + "@" + REALM + " with keytab /etc/security/keytabs/krb5.keytab.\n" +
                "delete_principal: Principal does not exist while deleting principal \"" + PRINCIPAL + "@" + REALM + "\"\n";
        KadminCommandResult result = KadminCommandResultParser.parse(0, output);
        assertEquals(KadminReply.PRINCIPAL_ALREADY_DELETED, result.getReply());
    }

    @Test
    public void parse_principalAlreadyCreated() {
        String output = "Authenticating as principal " + PRINCIPAL + "@" + REALM + " with keytab /etc/security/keytabs/krb5.keytab.\n" +
                "WARNING: no policy specified for " + PRINCIPAL + "@" + REALM + "; defaulting to no policy\n" +
                "add_principal: Principal or policy already exists while creating \"" + PRINCIPAL + "@" + REALM + "\".\n";
        KadminCommandResult result = KadminCommandResultParser.parse(0, output);
        assertEquals(KadminReply.PRINCIPAL_ALREADY_CREATED, result.getReply());
    }

    @Test
    public void parse_principalDeleted() {
        String output = "Authenticating as principal " + PRINCIPAL + "@" + REALM + " with keytab /etc/security/keytabs/krb5.keytab.\n" +
                "Principal \"" + PRINCIPAL + "@" + REALM + "\" deleted.\n" +
                "Make sure that you have removed this principal from all ACLs before reusing.";
        KadminCommandResult result = KadminCommandResultParser.parse(0, output);
        assertEquals(KadminReply.PRINCIPAL_DELETED, result.getReply());
    }

    @Test
    public void parse_noReplyFound() {
        String output = "Authenticating as principal " + PRINCIPAL + "@" + REALM + " with keytab /etc/security/keytabs/krb5.keytab.\n" +
                PRINCIPAL + "@" + REALM + "\n";
        KadminCommandResult result = KadminCommandResultParser.parse(0, output);
        assertEquals(KadminReply.NO_REPLY_FOUND, result.getReply());
    }
}
package io.github.vyaslav.kadmin;

import static io.github.vyaslav.kadmin.KadminStatus.ERROR;
import static io.github.vyaslav.kadmin.KadminStatus.SUCCESS;

public enum KadminReply {
    //error
    ERROR_OCCURED("(?!)", ERROR),
    PRINCIPAL_ALREADY_CREATED("(?s).*Principal or policy already exists while creating \"(.*){1}\".*", ERROR),
    PRINCIPAL_ALREADY_DELETED("(?s).*Principal does not exist while deleting principal \"(.*){1}\".*", ERROR),
    OPERATION_NOT_PERMITTED("(?s).*Operation requires (.*){1} privilege while creating \"(.*){1}\".*", ERROR),
    //success
    NO_REPLY_FOUND("(?!)", SUCCESS),
    PRINCIPAL_CREATED("(?s).*Principal \"(.*){1}\" created.*", SUCCESS),
    PRINCIPAL_DELETED("(?s).*Principal \"(.*){1}\" deleted.*", SUCCESS);

    private final String regExp;
    private final KadminStatus status;

    KadminReply(String regExp, KadminStatus status) {
        this.regExp = regExp;
        this.status = status;
    }

    public String getRegExp() {
        return regExp;
    }

    public KadminStatus getStatus() {
        return status;
    }
}

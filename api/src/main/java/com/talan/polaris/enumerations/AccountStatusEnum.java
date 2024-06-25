package com.talan.polaris.enumerations;

import static com.talan.polaris.enumerations.AccountStatusEnum.AccountStatusConstants.*;

/**
 * AccountStatusEnum.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public enum AccountStatusEnum {

    INACTIVE(INACTIVE_ACCOUNT_STATUS),
    ACTIVE(ACTIVE_ACCOUNT_STATUS),
    SUSPENDED(SUSPENDED_ACCOUNT_STATUS);

    private final String accountStatus;

    private AccountStatusEnum(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountStatus() {
        return this.accountStatus;
    }

    public abstract static class AccountStatusConstants {

        public static final String INACTIVE_ACCOUNT_STATUS      = "INACTIVE";
        public static final String ACTIVE_ACCOUNT_STATUS        = "ACTIVE";
        public static final String SUSPENDED_ACCOUNT_STATUS     = "SUSPENDED";

        private AccountStatusConstants() {

        }

    }

}

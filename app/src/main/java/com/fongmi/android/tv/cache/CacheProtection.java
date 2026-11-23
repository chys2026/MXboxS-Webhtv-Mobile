package com.silent.android.mxboxs.cache;

public record CacheProtection(boolean allowAutomaticCleanup, boolean allowManualCleanup,
                              boolean requireOwnerIdle, boolean requireAppIdle,
                              long minimumKeepBytes, long minimumKeepFiles,
                              boolean keepIfActiveSession) {
}

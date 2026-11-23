package com.silent.android.mxboxs.cache;

import java.util.List;

public record CacheCleanupPlan(CacheCleanupMode mode, List<CacheModuleId> modules) {
}

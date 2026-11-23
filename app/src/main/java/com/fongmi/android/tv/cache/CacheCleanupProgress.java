package com.silent.android.mxboxs.cache;

public record CacheCleanupProgress(CacheModuleId moduleId, int completedModules,
                                   int totalModules, long bytesBefore, long bytesAfter) {
}

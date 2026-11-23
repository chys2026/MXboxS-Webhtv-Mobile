package com.fongmi.android.tv.service;

import com.fongmi.android.tv.bean.AiConfig;
import com.fongmi.android.tv.service.AiCompletionClient.TestResult;
import com.fongmi.android.tv.service.AiCompletionClient.ModelFetchResult;
import com.fongmi.android.tv.setting.Setting;
import com.github.catvod.crawler.SpiderDebug;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * MXboxS AI 服务统一入口。
 * <p>
 * 所有 AI 智能设置项（推荐、标题解析、字幕翻译、广告识别、分组规则、看片报告、日志诊断等）
 * 都从这里取同一份 {@link AiConfig}，保证「设置一次，全局生效」。
 * <p>
 * 该入口同时提供通用的同步问答能力，让后续新增的 AI 场景不必各自拼装 HTTP 逻辑。
 */
public final class AiService {

    private static final int CONNECT_TIMEOUT_SECONDS = 10;
    private static final int READ_TIMEOUT_SECONDS = 60;
    private static final int CALL_TIMEOUT_SECONDS = 90;

    /** AI 场景标识，用于调试日志归类。 */
    public static final String SCENE_RECOMMEND = "recommend";
    public static final String SCENE_TITLE = "title-extraction";
    public static final String SCENE_SUBTITLE = "subtitle-translation";
    public static final String SCENE_AD = "ad-detection";
    public static final String SCENE_GROUP_RULE = "group-rule";
    public static final String SCENE_REPORT = "viewing-report";
    public static final String SCENE_DIAGNOSIS = "log-diagnosis";
    public static final String SCENE_CHAT = "chat";

    private AiService() {
    }

    /** 当前生效的 AI 配置（已 sanitize，可直接判 isReady）。 */
    public static AiConfig config() {
        return AiConfig.objectFrom(Setting.getAiConfig()).sanitize();
    }

    /** AI 是否已启用且配置完整。 */
    public static boolean isReady() {
        AiConfig config = config();
        return config.isEnabled() && config.isReady();
    }

    /** 保存 AI 配置，统一入口写入。 */
    public static void save(AiConfig config) {
        if (config == null) return;
        Setting.putAiConfig(config.sanitize().toJson());
    }

    /** 某场景是否可用：既要求全局 AI 就绪，也要求该场景自身开关打开。 */
    public static boolean isSceneReady(String scene) {
        if (!isReady()) return false;
        switch (scene) {
            case SCENE_TITLE:
                return Setting.isAiTitleExtraction();
            case SCENE_AD:
                return Setting.isAiAdDetection();
            case SCENE_RECOMMEND:
                return Setting.isAiRecommendation();
            default:
                return true;
        }
    }

    /** 所有 AI 场景标识，供设置页展示状态。 */
    public static List<String> scenes() {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, SCENE_RECOMMEND, SCENE_TITLE, SCENE_SUBTITLE, SCENE_AD,
                SCENE_GROUP_RULE, SCENE_REPORT, SCENE_DIAGNOSIS);
        return list;
    }

    /** 场景的中文展示名，用于设置页与提示。 */
    public static String sceneLabel(String scene) {
        switch (scene) {
            case SCENE_RECOMMEND: return "智能推荐";
            case SCENE_TITLE: return "标题解析";
            case SCENE_SUBTITLE: return "字幕翻译";
            case SCENE_AD: return "广告识别";
            case SCENE_GROUP_RULE: return "分组规则";
            case SCENE_REPORT: return "看片报告";
            case SCENE_DIAGNOSIS: return "日志诊断";
            case SCENE_CHAT: return "智能问答";
            default: return scene;
        }
    }

    /**
     * 统一同步问答：所有场景的底层调用都走这里。
     *
     * @param scene  场景标识，用于日志
     * @param prompt 完整提示词
     * @return 模型输出文本；未就绪或失败时返回空字符串
     */
    public static String ask(String scene, String prompt) {
        AiConfig config = config();
        if (!config.isEnabled() || !config.isReady()) return "";
        if (prompt == null || prompt.trim().isEmpty()) return "";
        AiCompletionClient.RequestSpec spec = AiCompletionClient.requestSpec(config, prompt);
        AiDebugLog.request("ai-service", scene, config, spec, null);
        Request request = AiCompletionClient.buildRequest(spec);
        long start = System.currentTimeMillis();
        try (Response response = client().newCall(request).execute()) {
            String body = response.body() == null ? "" : response.body().string();
            long cost = System.currentTimeMillis() - start;
            AiDebugLog.response("ai-service", scene, response.code(), cost, body, null);
            if (!response.isSuccessful()) return "";
            return AiCompletionClient.extractCompletionText(body, config);
        } catch (Throwable e) {
            AiDebugLog.error("ai-service", scene, System.currentTimeMillis() - start, e, null);
            SpiderDebug.log(e);
            return "";
        }
    }

    /** 连接测试，供设置页直接调用。 */
    public static AiCompletionClient.TestResult test(AiConfig config) {
        return AiCompletionClient.testConfig(config == null ? config() : config);
    }

    /** 拉取可用模型列表，供设置页下拉选择。 */
    public static AiCompletionClient.ModelFetchResult fetchModels(AiConfig config) {
        return AiCompletionClient.fetchModels(config == null ? config() : config);
    }

    private static OkHttpClient client() {
        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .callTimeout(CALL_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();
    }
}

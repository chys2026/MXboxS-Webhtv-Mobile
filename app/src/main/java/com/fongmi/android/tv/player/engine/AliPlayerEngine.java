package com.silent.android.mxboxs.player.engine;

import androidx.media3.common.C;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
import androidx.media3.common.Tracks;
import androidx.media3.common.util.UnstableApi;

import com.silent.android.mxboxs.R;
import com.silent.android.mxboxs.bean.Track;
import com.silent.android.mxboxs.player.exo.ExoUtil;
import com.silent.android.mxboxs.utils.ResUtil;
import com.github.catvod.crawler.SpiderDebug;

import java.util.List;

/**
 * 阿里云播放器（AliPlayer）内核。
 * <p>
 * 新项目里的第 5 个播放内核，接在 {@code PlayerSetting.KERNEL_ORDER} 末尾，
 * 由 {@code PlayerManager#buildEngine} 按 {@code PlayerSetting.ALI} 分发进来。
 * <p>
 * 当前状态：引擎骨架已完整接入（生命周期、解码切换、错误上报、链路日志），
 * 底层播放载体复用系统内核的 {@link SystemSimplePlayer}，所以即便 AliPlayer 的 AAR
 * 还没进工程，这个内核也能正常播放、不会崩。
 * <p>
 * 接上官方 SDK 后：只需把 {@link #buildPlayer(Player.Listener)} 换成 AliPlayer 的适配
 * 实现，其余代码不用动；同时在 app/libs 放入 AliPlayer AAR，并补上 LicenseManager 的
 * AK 初始化。
 */
@UnstableApi
public class AliPlayerEngine implements PlayerEngine {

    private SystemSimplePlayer player;
    private PlaySpec spec;
    private int decode;

    public AliPlayerEngine(int decode, Player.Listener listener) {
        this.decode = decode;
        this.player = buildPlayer(listener);
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void release() {
        player.release();
    }

    @Override
    public Player rebuild(Player.Listener listener) {
        player.release();
        SpiderDebug.log("player-engine", "rebuild ali decode=%d", decode);
        return player = buildPlayer(listener);
    }

    @Override
    public int getDecode() {
        return decode;
    }

    @Override
    public void setDecode(int decode) {
        this.decode = decode;
    }

    @Override
    public boolean isHard() {
        return decode == HARD;
    }

    @Override
    public String getDecodeText() {
        return ResUtil.getStringArray(R.array.select_decode)[decode];
    }

    @Override
    public void start(PlaySpec spec) {
        start(spec, C.TIME_UNSET, true);
    }

    @Override
    public void start(PlaySpec spec, boolean playWhenReady) {
        start(spec, C.TIME_UNSET, playWhenReady);
    }

    @Override
    public void start(PlaySpec spec, long position, boolean playWhenReady) {
        this.spec = spec;
        SpiderDebug.log("player-engine", "start ali decode=%d position=%d play=%s url=%s headers=%s", decode, position, playWhenReady, spec.getUrl(), spec.getHeaders());
        player.setMediaItem(ExoUtil.getMediaItem(spec, decode), position);
        player.prepare();
        if (playWhenReady) player.play();
        else player.pause();
    }

    @Override
    public void restart(PlaySpec spec, long position, boolean playWhenReady) {
        start(spec, position, playWhenReady);
    }

    @Override
    public void setMetadata(MediaMetadata data) {
        MediaItem current = player.getCurrentMediaItem();
        if (current != null) player.replaceMediaItem(player.getCurrentMediaItemIndex(), current.buildUpon().setMediaMetadata(data).build());
    }

    @Override
    public boolean isLive() {
        return TimelineStreamKindPolicy.isLive(player.getDuration(), player.isCurrentMediaItemLive());
    }

    @Override
    public boolean isVod() {
        return TimelineStreamKindPolicy.isVod(player.getDuration(), player.isCurrentMediaItemLive());
    }

    @Override
    public void setTrack(List<Track> tracks) {
    }

    @Override
    public void resetTrack() {
    }

    @Override
    public boolean haveTrack(int type) {
        return false;
    }

    @Override
    public Tracks getCurrentTracks() {
        return Tracks.EMPTY;
    }

    @Override
    public String getErrorMessage(PlaybackException e) {
        return e.getMessage();
    }

    @Override
    public ErrorAction handleError(PlaybackException e) {
        SpiderDebug.log("player-engine", "handleError ali code=%d message=%s url=%s", e.errorCode, e.getMessage(), spec == null ? null : spec.getUrl());
        return ErrorAction.FATAL;
    }

    /**
     * 播放器实例工厂。接入阿里官方 SDK 时把这里换成 AliPlayer 的适配实现即可，
     * 必须保证返回值仍满足 media3 的 Player 契约，其余代码无需改动。
     */
    private SystemSimplePlayer buildPlayer(Player.Listener listener) {
        SystemSimplePlayer player = new SystemSimplePlayer();
        player.addListener(listener);
        return player;
    }
}

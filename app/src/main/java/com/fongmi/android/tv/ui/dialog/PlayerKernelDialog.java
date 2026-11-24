package com.fongmi.android.tv.ui.dialog;

import android.content.res.Resources;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.fongmi.android.tv.R;
import com.fongmi.android.tv.setting.PlayerSetting;

import java.util.Arrays;

public final class PlayerKernelDialog {

    public interface Listener {
        void onSelected(int player);
    }

    public interface ExternalListener {
        void onExternal();
    }

    private PlayerKernelDialog() {
    }

    public static void show(FragmentActivity activity, int selected, Listener listener) {
        show(activity, selected, listener, null);
    }

    public static void show(FragmentActivity activity, int selected, Listener listener, ExternalListener external) {
        int current = PlayerSetting.sanitizePlayer(selected);
        String[] kernel = kernels(activity.getResources());
        String[] items = withExternal(kernel, external, activity.getString(R.string.player_kernel_external));
        ChoiceDialog.showSingleNoCancel(activity, R.string.player_kernel, items, availableRank(current), which -> notifySelected(kernel.length, current, which, listener, external));
    }

    public static void show(Fragment fragment, int selected, Listener listener) {
        show(fragment, selected, listener, null);
    }

    public static void show(Fragment fragment, int selected, Listener listener, ExternalListener external) {
        int current = PlayerSetting.sanitizePlayer(selected);
        String[] kernel = kernels(fragment.getResources());
        String[] items = withExternal(kernel, external, fragment.getString(R.string.player_kernel_external));
        ChoiceDialog.showSingleNoCancel(fragment, R.string.player_kernel, items, availableRank(current), which -> notifySelected(kernel.length, current, which, listener, external));
    }

    /** 内核标签，按 EXO → IJK → MPV → 系统 → 阿里 的优先级顺序排列，已剔除未集成内核。 */
    public static String[] kernels(Resources resources) {
        return compact(PlayerSetting.orderKernels(resources.getStringArray(R.array.select_player_kernel)));
    }

    /** 去掉未集成内核留下的空标签。顺序由 {@code PlayerSetting.KERNEL_ORDER} 决定，这里只做压缩。 */

    /** compact 之后的位次：未集成内核不占位，避免选中项与列表错位。 */
    private static int availableRank(int player) {
        int rank = 0;
        for (int kernel : PlayerSetting.KERNEL_ORDER) {
            if (!PlayerSetting.isPlayerAvailable(kernel)) continue;
            if (kernel == player) return rank;
            rank++;
        }
        return 0;
    }

    private static String[] compact(String[] labels) {
        int kept = 0;
        for (String label : labels) if (label != null && !label.isEmpty()) kept++;
        if (kept == labels.length) return labels;
        String[] out = new String[kept];
        int at = 0;
        for (String label : labels) if (label != null && !label.isEmpty()) out[at++] = label;
        return out;
    }

    private static String[] withExternal(String[] kernel, ExternalListener external, String label) {
        if (external == null) return kernel;
        String[] items = Arrays.copyOf(kernel, kernel.length + 1);
        items[kernel.length] = label;
        return items;
    }

    private static void notifySelected(int count, int current, int selected, Listener listener, ExternalListener external) {
        if (external != null && selected >= count) {
            external.onExternal();
            return;
        }
        int target = PlayerSetting.kernelAt(selected);
        if (target != current && listener != null) listener.onSelected(target);
    }
}

package cantwe.alldisagree.api;

import org.jetbrains.annotations.Nullable;

public interface Tab {

    @Nullable
    default Class<?> getParentScreenClass() {
        return null;
    }
}

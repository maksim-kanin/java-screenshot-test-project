package com.neuraloom.ui.screenshot;

import ru.yandex.qatools.ashot.coordinates.Coords;

import java.util.Set;

public class ScreenshotDetails {
    private String path;
    private String hash;
    private Set<Coords> ignoredAreas;
    private String[] ignoredHashes;

    public ScreenshotDetails withPath(String path) {
        this.path = path;
        return this;
    }

    public ScreenshotDetails withHash(String hash) {
        this.hash = hash;
        return this;
    }

    public ScreenshotDetails withIgnoredAreas(Set<Coords> ignoredAreas) {
        this.ignoredAreas = ignoredAreas;
        return this;
    }

    public ScreenshotDetails withIgnoredHashes(String[] ignoredHashes) {
        this.ignoredHashes = ignoredHashes;
        return this;
    }

    public String getPath() {
        return path;
    }

    public String getHash() {
        return hash;
    }

    public Set<Coords> getIgnoredAreas() {
        return ignoredAreas;
    }

    public String[] getIgnoredHashes() {
        return ignoredHashes;
    }
}

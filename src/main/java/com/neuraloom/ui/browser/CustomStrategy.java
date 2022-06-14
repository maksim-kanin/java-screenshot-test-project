package com.neuraloom.ui.browser;

import org.junit.platform.engine.ConfigurationParameters;
import org.junit.platform.engine.support.hierarchical.ParallelExecutionConfiguration;
import org.junit.platform.engine.support.hierarchical.ParallelExecutionConfigurationStrategy;

import static com.neuraloom.ui.DevConfig.DEV_CONFIG;

public class CustomStrategy implements ParallelExecutionConfiguration, ParallelExecutionConfigurationStrategy {
    @Override
    public int getParallelism() {
        return DEV_CONFIG.threadsCount();
    }

    @Override
    public int getMinimumRunnable() {
        return DEV_CONFIG.threadsCount();
    }

    @Override
    public int getMaxPoolSize() {
        return DEV_CONFIG.threadsCount();
    }

    @Override
    public int getCorePoolSize() {
        return DEV_CONFIG.threadsCount();
    }

    @Override
    public int getKeepAliveSeconds() {
        return DEV_CONFIG.threadsCount();
    }

    @Override
    public ParallelExecutionConfiguration createConfiguration(ConfigurationParameters configurationParameters) {
        return this;
    }
}

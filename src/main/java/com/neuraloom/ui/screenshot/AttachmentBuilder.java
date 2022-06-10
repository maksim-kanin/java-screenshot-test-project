package com.neuraloom.ui.screenshot;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static com.neuraloom.ui.DevConfig.DEV_CONFIG;

public class AttachmentBuilder {
    private static final String TEMPLATES_DIR = "src/main/resources/templates";
    private static final String ATTACHMENT_TEMPLATE = "screenshot-template.ftl";
    private final Map<String, Object> root = new HashMap<>();

    public AttachmentBuilder withId(String id) {
        root.put("id", id);
        return this;
    }

    public AttachmentBuilder withPath(String path) {
        root.put("path", path);
        return this;
    }

    public AttachmentBuilder withActual(String actualBase64String) {
        root.put("actual", actualBase64String);
        return this;
    }

    public AttachmentBuilder withReference(String referenceBase64String) {
        root.put("reference", referenceBase64String);
        return this;
    }

    public AttachmentBuilder withDiff(String diffBase64String) {
        root.put("diff", diffBase64String);
        return this;
    }

    public AttachmentBuilder withDiffSize(String diffSize) {
        root.put("diffSize", diffSize);
        return this;
    }

    public String build() {
        root.put("owner", DEV_CONFIG.gitHubOwner());
        root.put("repo", DEV_CONFIG.gitHubRepo());
        String result;
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
            cfg.setTemplateLoader(new FileTemplateLoader(new File(TEMPLATES_DIR)));
            Template temp = cfg.getTemplate(ATTACHMENT_TEMPLATE);
            StringWriter writer = new StringWriter();
            temp.process(root, writer);
            result = writer.toString();
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}

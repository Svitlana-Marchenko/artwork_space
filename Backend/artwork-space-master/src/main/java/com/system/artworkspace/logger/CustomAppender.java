package com.system.artworkspace.logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;
import java.util.*;

@Plugin(name="CustomAppender", category= Core.CATEGORY_NAME, elementType= Appender.ELEMENT_TYPE)
public final class CustomAppender extends AbstractAppender {

    private final List<String> loggs;

    private CustomAppender(String name, Filter filter,
                           Layout<? extends Serializable> layout) {
        super(name, filter, layout);
        loggs = new LinkedList<>();
    }

    @Override
    public void append(LogEvent event) {
        if (event.getLevel().isLessSpecificThan(Level.INFO)) {
            error("Unable to log less than WARN level.");
            return;
        }
            loggs.add((String) getLayout().toSerializable(event));

        System.out.println(loggs.remove(0));
    }

    @PluginFactory
    public static CustomAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") final Filter filter) {

        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new CustomAppender (name, filter, layout);
    }
}


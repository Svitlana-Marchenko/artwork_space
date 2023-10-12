package com.system.artworkspace.logger;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.filter.CompositeFilter;
import org.apache.logging.log4j.core.filter.MarkerFilter;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.Filter.Result;
import org.springframework.beans.factory.annotation.Value;

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
            loggs.add((String) getLayout().toSerializable(event));

        System.out.println(loggs.remove(0));
    }

    @PluginFactory
    public static CustomAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout<? extends Serializable> layout) {

        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        MarkerFilter markerFilter1 = MarkerFilter.createFilter("EXHIBITION_EVENTS", Result.ACCEPT, Filter.Result.NEUTRAL);
        MarkerFilter markerFilter2 = MarkerFilter.createFilter("CONFIDENTIAL_USER_EVENTS", Result.ACCEPT, Result.NEUTRAL);

        Filter[] filters = new Filter[] {markerFilter1, markerFilter2};
        Filter filter = CompositeFilter.createFilters(filters);

        return new CustomAppender (name, filter, layout);
    }
}


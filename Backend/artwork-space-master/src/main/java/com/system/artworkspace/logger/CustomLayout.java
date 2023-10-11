package com.system.artworkspace.logger;

import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;

@Plugin(name = "MyLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
public class CustomLayout extends AbstractStringLayout
{
    protected CustomLayout(Charset charset ) {
        super( charset );
    }

    @Override public String toSerializable( LogEvent event ) {
        StringBuilder throwable = new StringBuilder();
        if (event.getThrown() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            event.getThrown().printStackTrace(pw);
            pw.close();
            throwable.append(sw.toString());
        }
        String retValue = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:S").format(event.getTimeMillis()) + " " +
                event.getLevel().toString() + " " + event.getLoggerName() + " " + event.getMessage().getFormattedMessage() + " " + throwable;

        return retValue;
    }

    @PluginFactory
    public static CustomLayout createLayout(@PluginAttribute(value = "charset", defaultString = "UTF-8") Charset charset) {
        return new CustomLayout(charset);
    }
}
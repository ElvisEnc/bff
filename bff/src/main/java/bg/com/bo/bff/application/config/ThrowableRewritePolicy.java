package bg.com.bo.bff.application.config;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rewrite.RewritePolicy;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.MutableLogEvent;

import java.util.Arrays;

@Plugin(name = "ThrowableRewritePolicy", category = "Core", elementType = "rewritePolicy", printObject = true)
public final class ThrowableRewritePolicy implements RewritePolicy {
    @PluginFactory
    public static ThrowableRewritePolicy createPolicy() {
        return new ThrowableRewritePolicy();
    }

    @Override
    public LogEvent rewrite(LogEvent source) {
        Throwable throwable = source.getMessage().getThrowable();
        if (throwable != null) {
            StackTraceElement[] stackTrace = throwable.getStackTrace();
            if (stackTrace.length > 3)
                throwable.setStackTrace(Arrays.copyOfRange(stackTrace, 0, 3));

            MutableLogEvent mutableLogEvent = new MutableLogEvent();
            mutableLogEvent.initFrom(source);
            mutableLogEvent.setThrown(throwable);
            return mutableLogEvent;
        }
        return source;
    }
}

package bg.com.bo.bff.application.config;

import lombok.Getter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rewrite.RewritePolicy;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.MutableLogEvent;
import org.apache.logging.log4j.core.pattern.RegexReplacement;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.SimpleMessage;

import java.util.List;
import java.util.regex.Pattern;

@Plugin(name = "ObfuscationRewritePolicy", category = "Core", elementType = "rewritePolicy", printObject = true)
public final class ObfuscationRewritePolicy implements RewritePolicy {
    @PluginFactory
    public static ObfuscationRewritePolicy createPolicy() {
        return new ObfuscationRewritePolicy();
    }

    @Override
    public LogEvent rewrite(LogEvent source) {
        Message message = source.getMessage();
        String maskedMessage = message.getFormattedMessage();

        for (RegexReplacement regexReplacement : REGEX_REPLACEMENTS)
            maskedMessage = regexReplacement.format(maskedMessage);

        MutableLogEvent mutableEvent = new MutableLogEvent();
        mutableEvent.initFrom(source);
        mutableEvent.setMessage(new SimpleMessage(maskedMessage));

        return mutableEvent;
    }

    @Getter
    public static final List<RegexReplacement> REGEX_REPLACEMENTS;

    static {
        REGEX_REPLACEMENTS = List.of(
                RegexReplacement.createRegexReplacement(Pattern.compile("\\\\\"password\\\\\"\\s*:\\s*\\\\\"[^\\\\\"]*\\\\\""), "\\\\\"password\\\\\": \\\\\"****\\\\\""),
                RegexReplacement.createRegexReplacement(Pattern.compile("\\\\\"tokenBiometric\\\\\"\\s*:\\s*\\\\\"[^\\\\\"]*\\\\\""), "\\\\\"tokenBiometric\\\\\": \\\\\"****\\\\\""),
                RegexReplacement.createRegexReplacement(Pattern.compile("\\\\\"previousPassword\\\\\"\\s*:\\s*\\\\\"[^\\\\\"]*\\\\\""), "\\\\\"previousPassword\\\\\": \\\\\"****\\\\\""),
                RegexReplacement.createRegexReplacement(Pattern.compile("\\\\\"oldPassword\\\\\"\\s*:\\s*\\\\\"[^\\\\\"]*\\\\\""), "\\\\\"oldPassword\\\\\": \\\\\"****\\\\\""),
                RegexReplacement.createRegexReplacement(Pattern.compile("\\\\\"newPassword\\\\\"\\s*:\\s*\\\\\"[^\\\\\"]*\\\\\""), "\\\\\"newPassword\\\\\": \\\\\"****\\\\\""),
                RegexReplacement.createRegexReplacement(Pattern.compile("\\\\\"passwordBiometric\\\\\"\\s*:\\s*\\\\\"[^\\\\\"]*\\\\\""), "\\\\\"passwordBiometric\\\\\": \\\\\"****\\\\\""),
                RegexReplacement.createRegexReplacement(Pattern.compile("\\\\\"tokenFinger\\\\\"\\s*:\\s*\\\\\"[^\\\\\"]*\\\\\""), "\\\\\"tokenFinger\\\\\": \\\\\"****\\\\\"")
        );
    }
}

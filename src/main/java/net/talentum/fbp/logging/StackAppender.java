package net.talentum.fbp.logging;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;

/**
 * This {@link Appender} stacks logged events until
 * {@link #setAppender(Appender)} is called, when all stacked events are logged
 * to the given appender. From then on, every next event will be directly logged
 * to the stored appender.
 * 
 * @author JJurM
 */
@Plugin(name = "Stack", category = "Core", elementType = "appender", printObject = true)
public class StackAppender extends AbstractAppender {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 * 
	 * @param name
	 */
	protected StackAppender(String name) {
		super(name, null, null, true);
	}

	protected List<LogEvent> logEvents = new ArrayList<LogEvent>();
	protected Appender appender;

	@Override
	public void append(LogEvent event) {
		if (appender != null) {
			appender.append(event);
		} else {
			logEvents.add(event);
		}
	}

	/**
	 * Sets current appender to the new one. Stacked events and every new events
	 * will be logged directly to the given appender.
	 * 
	 * @param appender new {@link Appender}
	 */
	public void setAppender(Appender appender) {
		this.appender = appender;
		for (LogEvent event : logEvents) {
			appender.append(event);
		}
		logEvents.clear();
	}

}

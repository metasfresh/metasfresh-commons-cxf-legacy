package de.metas.commons.cxf;


import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.interceptor.AbstractLoggingInterceptor;
import org.apache.cxf.management.counters.CounterRepository;
import org.apache.cxf.management.jmx.InstrumentationManagerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;


/*
 * #%L
 * de.metas.procurement.webui
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Common cxf configuration.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Service
public class CxfConfiguration
{
	/**
	 * Creates the cxf bus. Note that "cxf is the bean's default name. If you give it a different name, you need to make it know to e.g. the CXFServlet.
	 *
	 * @return
	 */
	@Bean//(name = "cxf")
	public SpringBus cxf()
	{
		return new SpringBus();
	}

	@Bean//(name="jacksonJaxbJsonProvider")
	public JacksonJaxbJsonProvider jacksonJaxbJsonProvider()
	{
		final JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();
		return jacksonJaxbJsonProvider;
	}

	@Bean
	public org.apache.cxf.management.InstrumentationManager instrumentationManager(final SpringBus bus)
	{
		final InstrumentationManagerImpl instrumentationManager = new InstrumentationManagerImpl();
		instrumentationManager.setEnabled(true);
		instrumentationManager.setBus(bus);
		instrumentationManager.setUsePlatformMBeanServer(true);
		return instrumentationManager;
	}

	@Bean
	public org.apache.cxf.management.counters.CounterRepository counterRepository(final SpringBus bus)
	{
		final CounterRepository counterRepository = new CounterRepository();
		counterRepository.setBus(bus);
		return counterRepository;
	}

	/**
	 *
	 * @return
	 * @task https://metasfresh.atlassian.net/browse/FRESH-87
	 */
	@Bean
	public LoggingFeature createLoggingFeature()
	{
		final boolean prettyPrint = true;
		final boolean showBinary = true;

		// see LoggingFeature.initializeProvider()...we want to make sure that showBinary is not ignored
		final int limit = AbstractLoggingInterceptor.DEFAULT_LIMIT + 1;

		final LoggingFeature loggingFeature = new LoggingFeature(
				null,    // use default
				null,    // use default
				limit,
				prettyPrint,
				showBinary);
		return loggingFeature;
	}
}

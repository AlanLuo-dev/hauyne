package com.luoyx.hauyne.web.autoconfigure;

import com.luoyx.hauyne.web.datetime.config.DateTimeFormatConfig;
import com.luoyx.hauyne.web.enums.config.EnumWebMvcConfiguration;
import com.luoyx.hauyne.web.enums.config.JacksonConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DateTimeFormatConfig.class, JacksonConfig.class, EnumWebMvcConfiguration.class})
public class GlobalWebAutoConfiguration {
}

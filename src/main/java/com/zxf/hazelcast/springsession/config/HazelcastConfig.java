package com.zxf.hazelcast.springsession.config;

import com.hazelcast.client.config.ClientClasspathYamlConfig;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.spring.session.HazelcastSessionConfiguration;
import org.springframework.boot.hazelcast.autoconfigure.HazelcastConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Boot 4.x 将 Hazelcast 自动配置外置为独立的 spring-boot-starter-hazelcast,
 * 它通过 {@link HazelcastConnectionDetails} 构建 Hazelcast 客户端, 但不会注册
 * hazelcast-spring-session 所需的 Compact 序列化器。
 *
 * <p>这里覆盖 {@link HazelcastConnectionDetails}, 在 ClientConfig 上调用
 * {@link HazelcastSessionConfiguration#applySerializationConfig(ClientConfig)},
 * 使 BackingMapSession / AttributeValue 能在 client-server 拓扑下正确序列化。
 */
@Configuration
public class HazelcastConfig {

	@Bean
	HazelcastConnectionDetails hazelcastConnectionDetails() {
		return () -> {
			ClientConfig clientConfig = new ClientClasspathYamlConfig("hazelcast-client.yaml");
			return HazelcastSessionConfiguration.applySerializationConfig(clientConfig);
		};
	}
}

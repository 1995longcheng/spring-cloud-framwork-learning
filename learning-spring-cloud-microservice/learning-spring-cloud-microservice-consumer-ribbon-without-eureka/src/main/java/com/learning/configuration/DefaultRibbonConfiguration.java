package com.learning.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;

@ExcludeFromComponentScan
@Configuration
public class DefaultRibbonConfiguration {
	
	/**
	 * ping的通的则保留，ping不通的则丢弃
	 * @return
	 */
	@Bean
	public IPing ribbonPing() {
		return new PingUrl();
	}
	
	
	/**
	 * 自定义配置
	 * @return
	 */
	@Bean
	public ServerList initServerList(){
		
		ServerList<Server> serverList = new ServerList<Server>() {

			@Override
			public List<Server> getInitialListOfServers() {
				List<Server> servers = new ArrayList<Server>();
				servers.add(new Server("localhost", 9001));
				servers.add(new Server("localhost", 9002));
				return servers;
			}

			@Override
			public List<Server> getUpdatedListOfServers() {
				List<Server> servers = new ArrayList<Server>();
				servers.add(new Server("localhost", 9001));
				servers.add(new Server("localhost", 9002));
				return servers;
			}
		};
		return serverList;
	}
	
	
}

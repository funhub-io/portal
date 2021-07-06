package org.hango.cloud.dashboard.envoy.meta;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hango.cloud.dashboard.envoy.web.dto.HttpRetryDto;

import java.util.List;

/**
 * 路由规则发布信息
 */
public class EnvoyRouteRuleProxyInfo extends RouteRuleMatchInfo {

	private long id;

	/**
	 * 路由规则发布指定网关id
	 */
	private long gwId;

	/**
	 * 发布时指定的路由规则id
	 */
	private long routeRuleId;

	/**
	 * 路由规则发布时间
	 */
	private long createTime;

	/**
	 * 路由规则发布信息更新时间
	 */
	private long updateTime;

	/**
	 * 路由规则发布指定目标服务
	 */
	private List<EnvoyDestinationInfo> destinationServiceList;

	private String destinationServices;

	/**
	 * 路由规则所发布的服务id，用于已发布路由规则搜索
	 */
	private long serviceId;

	/**
	 * 路由规则发布所属项目id
	 */
	private long projectId;

	/**
	 * 使能状态
	 */
	private String enableState;

	/**
	 * virtual service中hosts列表
	 */
	private String hosts;

	/**
	 * HttpRetry 路由重试
	 */
	private HttpRetryDto httpRetryDto;

	/**
	 * 路由重试条件，数据库string存储
	 */
	private String httpRetry;

	/**
	 * 路由超时时间
	 */
	private long timeout;

	/**
	 * virtualCluster 路由监控信息
	 */
	private String virtualCluster;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getGwId() {
		return gwId;
	}

	public void setGwId(long gwId) {
		this.gwId = gwId;
	}

	public long getRouteRuleId() {
		return routeRuleId;
	}

	public void setRouteRuleId(long routeRuleId) {
		this.routeRuleId = routeRuleId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public List<EnvoyDestinationInfo> getDestinationServiceList() {
		return destinationServiceList;
	}

	public void setDestinationServiceList(List<EnvoyDestinationInfo> destinationServiceList) {
		this.destinationServiceList = destinationServiceList;
	}

	public String getDestinationServices() {
		return destinationServices;
	}

	public void setDestinationServices(String destinationServices) {
		this.destinationServices = destinationServices;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public String getEnableState() {
		return enableState;
	}

	public void setEnableState(String enableState) {
		this.enableState = enableState;
	}

	public String getHosts() {
		return hosts;
	}

	public void setHosts(String hosts) {
		this.hosts = hosts;
	}

	public HttpRetryDto getHttpRetryDto() {
		return httpRetryDto;
	}

	public void setHttpRetryDto(HttpRetryDto httpRetryDto) {
		this.httpRetryDto = httpRetryDto;
	}

	public String getHttpRetry() {
		return httpRetry;
	}

	public void setHttpRetry(String httpRetry) {
		this.httpRetry = httpRetry;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public String getVirtualCluster() {
		return virtualCluster;
	}

	public void setVirtualCluster(String virtualCluster) {
		this.virtualCluster = virtualCluster;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

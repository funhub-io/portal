package org.hango.cloud.dashboard.envoy.web.dto;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

/**
 * 一致性哈希负载均衡策略
 *
 * @date 2020/2/3 上午11:30.
 */
public class EnvoyServiceConsistentHashDto implements Serializable {

	/**
	 * 用于区分用作哈希的资源类型，包含httpHeaderName, httpCookie, useSourceIp
	 */
	@Pattern(regexp = "|httpHeaderName|httpCookie|useSourceIp")
	@JSONField(name = "Type")
	private String type;

	/**
	 * 基于源IP计算哈希值
	 */
	@JSONField(name = "UseSourceIp")
	private Boolean useSourceIp;

	/**
	 * 计算哈希的Header
	 */
	@JSONField(name = "HttpHeaderName")
	private String httpHeaderName;

	/**
	 * 计算哈希所用的Cookie
	 */
	@JSONField(name = "HttpCookie")
	private EnvoyServiceConsistentHashCookieDto cookieDto;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getUseSourceIp() {
		return useSourceIp;
	}

	public void setUseSourceIp(Boolean useSourceIp) {
		this.useSourceIp = useSourceIp;
	}

	public String getHttpHeaderName() {
		return httpHeaderName;
	}

	public void setHttpHeaderName(String httpHeaderName) {
		this.httpHeaderName = httpHeaderName;
	}

	public EnvoyServiceConsistentHashCookieDto getCookieDto() {
		return cookieDto;
	}

	public void setCookieDto(EnvoyServiceConsistentHashCookieDto cookieDto) {
		this.cookieDto = cookieDto;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	/**
	 * 一致性哈希使用Cookie
	 *
	 * @date 2020/2/3 下午2:20.
	 */
	public static class EnvoyServiceConsistentHashCookieDto implements Serializable {

		@JSONField(name = "Name")
		private String name;

		@JSONField(name = "TTL")
		private Integer ttl;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getTtl() {
			return ttl;
		}

		public void setTtl(Integer ttl) {
			this.ttl = ttl;
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		}

	}

}


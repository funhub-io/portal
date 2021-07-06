package org.hango.cloud.dashboard.envoy.web.dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @version 1.0
 * @Type
 * @Desc
 * @date 2020/3/23
 */
public class EnvoyPluginManagerDto {

	@JSONField(name = "Enable")
	private boolean enable;

	@JSONField(name = "Name")
	private String name;

	@JSONField(name = "DisplayName")
	private String displayName;

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}

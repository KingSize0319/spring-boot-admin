/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.codecentric.boot.admin.model;

import java.io.Serializable;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The domain model for all registered application at the spring boot admin application.
 */
public class Application implements Serializable {
	private static final long serialVersionUID = 1L;

	private final String id;
	private final String name;
	private final String managementUrl;
	private final String healthUrl;
	private final String serviceUrl;

	public Application(String healthUrl, String managementUrl, String serviceUrl,
			String name) {
		this(healthUrl, managementUrl, serviceUrl, name, null);
	}

	public Application(String healthUrl, String managementUrl, String serviceUrl,
			String name, String id) {
		this.healthUrl = healthUrl;
		this.managementUrl = managementUrl;
		this.serviceUrl = serviceUrl;
		this.name = name;
		this.id = id;
	}

	@JsonCreator
	public static Application create(@JsonProperty("url") String url,
			@JsonProperty("managementUrl") String managementUrl,
			@JsonProperty("healthUrl") String healthUrl,
			@JsonProperty("serviceUrl") String serviceUrl,
			@JsonProperty("name") String name,
			@JsonProperty("id") String id) {

		Assert.hasText(name, "name must not be empty!");
		if (StringUtils.hasText(url)) {
			// old format
			return new Application(url.replaceFirst("/+$", "") + "/health", url, null,
					name, id);
		}
		else {
			Assert.hasText(healthUrl, "healthUrl must not be empty!");
			return new Application(healthUrl, managementUrl, serviceUrl, name, id);
		}
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getManagementUrl() {
		return managementUrl;
	}

	public String getHealthUrl() {
		return healthUrl;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	@Override
	public String toString() {
		return "Application [id=" + id + ", name=" + name + ", managementUrl="
				+ managementUrl + ", healthUrl=" + healthUrl + ", serviceUrl="
				+ serviceUrl + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((healthUrl == null) ? 0 : healthUrl.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((managementUrl == null) ? 0 : managementUrl.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((serviceUrl == null) ? 0 : serviceUrl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Application other = (Application) obj;
		if (healthUrl == null) {
			if (other.healthUrl != null) {
				return false;
			}
		}
		else if (!healthUrl.equals(other.healthUrl)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else if (!id.equals(other.id)) {
			return false;
		}
		if (managementUrl == null) {
			if (other.managementUrl != null) {
				return false;
			}
		}
		else if (!managementUrl.equals(other.managementUrl)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		}
		else if (!name.equals(other.name)) {
			return false;
		}
		if (serviceUrl == null) {
			if (other.serviceUrl != null) {
				return false;
			}
		}
		else if (!serviceUrl.equals(other.serviceUrl)) {
			return false;
		}
		return true;
	}

}

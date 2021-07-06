package org.hango.cloud.ncegdashboard.envoy.service;

import org.hango.cloud.ncegdashboard.envoy.web.dto.ServiceInfoDto;
import org.hango.cloud.ncegdashboard.envoy.meta.ServiceInfo;
import org.hango.cloud.ncegdashboard.envoy.meta.errorcode.ErrorCode;

import java.util.List;

/**
 * @Date: 创建时间: 2017/12/26 17:03.
 */
public interface IServiceInfoService {

	/**
	 * 添加新服务
	 */
	long add(ServiceInfo serviceInfo);

	/**
	 * 通过服务基本信息创建服务
	 *
	 * @param serviceInfoDto 服务基本信息
	 * @param projectId      项目id
	 */
	ServiceInfo addServiceInfo(ServiceInfoDto serviceInfoDto, long projectId);

	/**
	 * 通过项目id获取所有服务
	 */
	List<ServiceInfo> findAllServiceByProjectId(long projectId);

	/**
	 * 分页获取项目id下的所有服务
	 */
	List<ServiceInfo> findAllServiceByProjectIdLimit(String pattern, long offset, long limit, long projectId);

	/**
	 * 获取项目id下的服务数量
	 *
	 * @param pattern pattern支持模糊搜索
	 */
	long getServiceCountByProjectId(String pattern, long projectId);

	/**
	 * 查询所有的服务方信息
	 */
	List<ServiceInfo> findAll();

	/**
	 * 更新服务信息
	 */
	boolean updateService(ServiceInfo service);

	/**
	 * 根据服务Id查询
	 */
	ServiceInfo getServiceById(String id);

	ServiceInfo getServiceByServiceId(long serviceId);

	/**
	 * 根据服务Id判断服务是否存在
	 */
	boolean isServiceExists(long serviceId);

	/**
	 * 删除服务
	 */
	void delete(long serviceId);

	/**
	 * 根据服务标识查询
	 */
	ServiceInfo getServiceByServiceName(String serviceName);

	ServiceInfo getServiceByServiceNameAndProject(String serviceName, long projectId);

	boolean isDisplayNameExists(String displayName);

	/**
	 * 创建service，参数校验
	 * * @param serviceInfoDto
	 */
	ErrorCode checkCreateServiceParam(ServiceInfoDto serviceInfoDto);

	/**
	 * 更新service, 参数校验
	 */
	ErrorCode checkUpdateServiceParam(ServiceInfoDto serviceInfoDto);

	/**
	 * 根据服务显示名称，projectId信息获取所属projectId下的服务
	 */
	ServiceInfo describeDisplayName(String displayName, long projectId);

	/**
	 * 根据服务名称模糊查询满足条件的id列表
	 *
	 * @param serviceName 服务名称，实际上对接数据库中display_name字段
	 * @param projectId   项目id
	 *
	 * @return {@link List<Long>} 满足条件的服务id列表
	 */
	List<Long> getServiceIdListByDisplayNameFuzzy(String serviceName, long projectId);

	/**
	 * 根据服务id列表查询服务详情列表
	 *
	 * @param serviceIdList 服务id列表
	 *
	 * @return {@link List<ServiceInfo>} 服务详情列表
	 */
	List<ServiceInfo> getServiceInfoList(List<Long> serviceIdList);

}

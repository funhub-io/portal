package org.hango.cloud.ncegdashboard.envoy.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.hango.cloud.ncegdashboard.BaseServiceImplTest;
import org.hango.cloud.ncegdashboard.envoy.web.dto.GatewayDto;
import org.hango.cloud.ncegdashboard.envoy.meta.GatewayInfo;
import org.hango.cloud.ncegdashboard.envoy.meta.ServiceInfo;
import org.hango.cloud.ncegdashboard.envoy.meta.errorcode.CommonErrorCode;
import org.hango.cloud.ncegdashboard.envoy.meta.errorcode.ErrorCode;
import org.hango.cloud.ncegdashboard.envoy.service.IGatewayInfoService;
import org.hango.cloud.ncegdashboard.envoy.service.IServiceInfoService;
import org.hango.cloud.ncegdashboard.envoy.util.Const;
import org.hango.cloud.ncegdashboard.envoy.innerdto.EnvoyActiveHealthCheckRuleDto;
import org.hango.cloud.ncegdashboard.envoy.innerdto.EnvoyPassiveHealthCheckRuleDto;
import org.hango.cloud.ncegdashboard.envoy.innerdto.EnvoyServiceWithPortDto;
import org.hango.cloud.ncegdashboard.envoy.meta.EnvoyServiceProxyInfo;
import org.hango.cloud.ncegdashboard.envoy.service.IEnvoyServiceProxyService;
import org.hango.cloud.ncegdashboard.envoy.web.dto.EnvoyServiceConnectionPoolDto;
import org.hango.cloud.ncegdashboard.envoy.web.dto.EnvoyServiceLoadBalancerDto;
import org.hango.cloud.ncegdashboard.envoy.web.dto.EnvoyServiceProxyDto;
import org.hango.cloud.ncegdashboard.envoy.web.dto.EnvoyServiceTrafficPolicyDto;
import org.hango.cloud.ncegdashboard.envoy.web.dto.EnvoySubsetDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest(HttpCommonUtil.class)
////@AutoConfigureMockMvc
//@PowerMockIgnore("javax.management.*")
//@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
public class EnvoyServiceProxyServiceImplTest extends BaseServiceImplTest {

	private static final Logger logger = LoggerFactory.getLogger(EnvoyServiceProxyServiceImplTest.class);

	@Autowired
	private IServiceInfoService serviceInfoService;

	@Autowired
	@InjectMocks
	private IEnvoyServiceProxyService serviceProxyService;

	@Autowired
	private IGatewayInfoService gatewayInfoService;

	@Autowired
	@InjectMocks
	private EnvoyServiceProxyServiceImpl serviceProxyServiceImpl;

	@Mock
	private GetFromApiPlaneServiceImpl getFromApiPlaneService;

	private ServiceInfo serviceInfo;

	private GatewayInfo gatewayInfo;

	private EnvoyServiceProxyDto envoyServiceProxyDto;

	private EnvoySubsetDto subsetDto;

	private EnvoyServiceProxyDto envoyStaticServiceProxyDto;

	private EnvoySubsetDto staticSubsetDto;

	//负载均衡&连接池 相关类
	private EnvoyServiceTrafficPolicyDto envoyServiceTrafficPolicyDto;

	private EnvoyServiceLoadBalancerDto loadBalancer;

	private EnvoyActiveHealthCheckRuleDto activeHealthCheckRule;

	private EnvoyPassiveHealthCheckRuleDto passiveHealthCheckRule;

	private EnvoyServiceConnectionPoolDto connectionPoolDto;

	private EnvoyServiceConnectionPoolDto.EnvoyServiceTcpConnectionPoolDto serviceTcpConnectionPoolDto;

	private EnvoyServiceConnectionPoolDto.EnvoyServiceHttpConnectionPoolDto serviceHttpConnectionPoolDto;

	private long gwId;

	private long serviceId;

	@Before
	public void init() {

		Mockito.when(getFromApiPlaneService.publishServiceByApiPlane(Mockito.any(), Mockito.any())).thenReturn(true);
		Mockito.doReturn(true).when(getFromApiPlaneService).offlineServiceByApiPlane(Mockito.any(), Mockito.any());
		//        Mockito.doReturn(Lists.newArrayList()).when(getFromApiPlaneService).getServiceListFromApiPlane
		//        (Mockito.any(),
		//                Mockito.any(), Mockito.any(), Mockito.any());

		//初始化ServiceInfo
		serviceInfo = new ServiceInfo();
		serviceInfo.setDisplayName(displayName);
		serviceInfo.setServiceName(serviceName);
		serviceInfo.setContacts(user);
		serviceInfo.setProjectId(projectId);
		serviceInfo.setServiceType(serviceType);
		//创建service
		serviceId = serviceInfoService.add(serviceInfo);

		gatewayInfo = gatewayInfoService.getGatewayByName(envoyGwName);
		gwId = gatewayInfo.getId();

		//k8s方式发布
		envoyServiceProxyDto = new EnvoyServiceProxyDto();
		envoyServiceProxyDto.setServiceId(serviceId);
		envoyServiceProxyDto.setBackendService("a.pilot-test.svc.cluster.local");
		envoyServiceProxyDto.setGwId(gwId);
		envoyServiceProxyDto.setPublishType(Const.DYNAMIC_PUBLISH_TYPE);
		envoyServiceProxyDto.setRegistryCenterType("Kubernetes");
		//版本
		subsetDto = new EnvoySubsetDto();
		subsetDto.setName("testSubset");
		Map<String, String> label = new HashMap<>();
		label.put("aaa", "bbb");
		subsetDto.setLabels(label);
		envoyServiceProxyDto.setSubsets(Arrays.asList(new EnvoySubsetDto[]{subsetDto}));

		//静态地址发布
		envoyStaticServiceProxyDto = new EnvoyServiceProxyDto();
		envoyStaticServiceProxyDto.setServiceId(serviceId);
		envoyStaticServiceProxyDto.setBackendService("127.0.0.1:8888,127.0.0.2:8888");
		envoyStaticServiceProxyDto.setGwId(gwId);
		envoyStaticServiceProxyDto.setPublishType(Const.STATIC_PUBLISH_TYPE);
		envoyStaticServiceProxyDto.setPublishProtocol("HTTP");
		//版本
		staticSubsetDto = new EnvoySubsetDto();
		staticSubsetDto.setName("testStaticSubset");
		List<String> staticAddrList = new ArrayList<>();
		staticAddrList.add("127.0.0.1:8888");
		staticSubsetDto.setStaticAddrList(staticAddrList);
		envoyStaticServiceProxyDto.setSubsets(Arrays.asList(new EnvoySubsetDto[]{staticSubsetDto}));

		//负载均衡&连接池 相关类
		loadBalancer = new EnvoyServiceLoadBalancerDto();
		loadBalancer.setType("Simple");
		loadBalancer.setSimple("ROUND_ROBIN");

		activeHealthCheckRule = new EnvoyActiveHealthCheckRuleDto();
		activeHealthCheckRule.setPath("/healthcheck");
		activeHealthCheckRule.setHealthyThreshold(1);
		activeHealthCheckRule.setUnhealthyThreshold(1);
		activeHealthCheckRule.setHealthyInterval(1);
		activeHealthCheckRule.setUnhealthyInterval(1);
		activeHealthCheckRule.setTimeout(10);
		List<Integer> status = new ArrayList<>();
		status.add(200);
		activeHealthCheckRule.setExpectedStatuses(status);

		passiveHealthCheckRule = new EnvoyPassiveHealthCheckRuleDto();
		passiveHealthCheckRule.setMaxEjectionPercent(10);
		passiveHealthCheckRule.setConsecutiveErrors(2);
		passiveHealthCheckRule.setBaseEjectionTime(10);

		connectionPoolDto = new EnvoyServiceConnectionPoolDto();
		serviceTcpConnectionPoolDto = new EnvoyServiceConnectionPoolDto.EnvoyServiceTcpConnectionPoolDto();
		serviceHttpConnectionPoolDto = new EnvoyServiceConnectionPoolDto.EnvoyServiceHttpConnectionPoolDto();
		connectionPoolDto.setServiceHttpConnectionPoolDto(serviceHttpConnectionPoolDto);
		connectionPoolDto.setServiceTcpConnectionPoolDto(serviceTcpConnectionPoolDto);
		serviceTcpConnectionPoolDto.setConnectTimeout(10);
		serviceTcpConnectionPoolDto.setMaxConnections(100);
		serviceHttpConnectionPoolDto.setMaxRequestsPerConnection(0);
		serviceHttpConnectionPoolDto.setHttp1MaxPendingRequests(1024);
		serviceHttpConnectionPoolDto.setHttp2MaxRequests(1024);
		serviceHttpConnectionPoolDto.setIdleTimeout(1000);

		envoyServiceTrafficPolicyDto = new EnvoyServiceTrafficPolicyDto();
		envoyServiceTrafficPolicyDto.setLoadBalancer(loadBalancer);
		envoyServiceTrafficPolicyDto.setActiveHealthCheckRule(activeHealthCheckRule);
		envoyServiceTrafficPolicyDto.setPassiveHealthCheckRule(passiveHealthCheckRule);
		envoyServiceTrafficPolicyDto.setConnectionPoolDto(connectionPoolDto);
		envoyServiceProxyDto.setTrafficPolicy(envoyServiceTrafficPolicyDto);
	}

	@After
	public void tearDownClass() {
		logger.info("tear down class .... ServiceProxyServiceImplTest");
		//清除service
		serviceInfoService.delete(serviceId);
	}

	@Test
	public void getServiceListFromApiPlane() {
		List<EnvoyServiceWithPortDto> serviceListFromApiPlane = getFromApiPlaneService.getServiceListFromApiPlane(gwId,
		                                                                                                          "",
		                                                                                                          "Kubernetes");
		assertTrue(serviceListFromApiPlane.size() == 0);
	}

	/**
	 * 校验服务和版本 负载均衡策略 & 连接池 且 根据Type字段将冗余字段置空不处理
	 */
	@Test
	public void checkEnvoyServiceProxyDto() {
		ErrorCode errorCode = serviceProxyServiceImpl.checkEnvoyServiceProxyDto(envoyServiceProxyDto);
		assertEquals(CommonErrorCode.Success.getCode(), errorCode.getCode());

		//校验静态发布
		errorCode = serviceProxyServiceImpl.checkEnvoyServiceProxyDto(envoyStaticServiceProxyDto);
		assertEquals(CommonErrorCode.Success.getCode(), errorCode.getCode());

		//静态发布其版本中配置的地址要包含在服务发布的地址中
		List<String> staticAddrList = new ArrayList<>();
		staticAddrList.add("127.0.0.1:9999");
		staticSubsetDto.setStaticAddrList(staticAddrList);
		envoyStaticServiceProxyDto.setSubsets(Arrays.asList(new EnvoySubsetDto[]{staticSubsetDto}));
		errorCode = serviceProxyServiceImpl.checkEnvoyServiceProxyDto(envoyStaticServiceProxyDto);
		assertEquals(CommonErrorCode.InvalidSubsetStaticAddr.getCode(), errorCode.getCode());

		//同一个版本里配置的静态地址不能重复
		staticAddrList = new ArrayList<>();
		staticAddrList.add("127.0.0.1:8888");
		staticAddrList.add("127.0.0.1:8888");
		staticSubsetDto.setStaticAddrList(staticAddrList);
		envoyStaticServiceProxyDto.setSubsets(Arrays.asList(new EnvoySubsetDto[]{staticSubsetDto}));
		errorCode = serviceProxyServiceImpl.checkEnvoyServiceProxyDto(envoyStaticServiceProxyDto);
		assertEquals(CommonErrorCode.DuplicatedSubsetStaticAddr.getCode(), errorCode.getCode());

		//每个地址仅能出现在0或1个版本中
		staticAddrList = new ArrayList<>();
		staticAddrList.add("127.0.0.1:8888");
		staticSubsetDto.setStaticAddrList(staticAddrList);

		staticAddrList = new ArrayList<>();
		staticAddrList.add("127.0.0.1:8888");
		EnvoySubsetDto staticSubsetDtoNew = new EnvoySubsetDto();
		staticSubsetDtoNew.setStaticAddrList(staticAddrList);
		envoyStaticServiceProxyDto.setSubsets(Arrays.asList(new EnvoySubsetDto[]{
			staticSubsetDto, staticSubsetDtoNew
		}));
		errorCode = serviceProxyServiceImpl.checkEnvoyServiceProxyDto(envoyStaticServiceProxyDto);
		assertEquals(CommonErrorCode.DuplicatedStaticAddr.getCode(), errorCode.getCode());
	}

	@Test
	public void publishServiceToGw() {
		serviceProxyService.publishServiceToGw(envoyServiceProxyDto);
		assertTrue(serviceProxyService.deleteServiceProxy(gwId, serviceId));
	}

	@Test
	public void updateServiceToGw() {
		serviceProxyService.publishServiceToGw(envoyServiceProxyDto);
		serviceProxyService.updateServiceToGw(envoyServiceProxyDto);
		assertTrue(serviceProxyService.deleteServiceProxy(gwId, serviceId));
	}

	@Test
	public void checkPublishParam() {
		ErrorCode errorCode = serviceProxyService.checkPublishParam(envoyServiceProxyDto);
		assertTrue(errorCode.getCode().equals(CommonErrorCode.Success.getCode()));
	}

	@Test
	public void checkUpdatePublishParam() {
		envoyServiceProxyDto.setId(serviceProxyService.publishServiceToGw(envoyServiceProxyDto));
		ErrorCode errorCode = serviceProxyService.checkUpdatePublishParam(envoyServiceProxyDto);
		assertTrue(errorCode.getCode().equals(CommonErrorCode.Success.getCode()));
		assertTrue(serviceProxyService.deleteServiceProxy(gwId, serviceId));
	}

	@Test
	public void getEnvoyServiceProxyByLimit() {
		serviceProxyService.publishServiceToGw(envoyServiceProxyDto);
		long projectid = serviceInfoService.getServiceByServiceId(serviceId).getProjectId();
		List<EnvoyServiceProxyInfo> envoyServiceProxyByLimit = serviceProxyService.getEnvoyServiceProxyByLimit(gwId,
		                                                                                                       serviceId,
		                                                                                                       projectId,
		                                                                                                       0, 100);
		assertTrue(envoyServiceProxyByLimit.size() > 0);
		assertTrue(serviceProxyService.deleteServiceProxy(gwId, serviceId));
	}

	@Test
	public void getServiceProxyCountByLimit() {
		serviceProxyService.publishServiceToGw(envoyServiceProxyDto);
		long count = serviceProxyService.getServiceProxyCountByLimit(gwId, serviceId);
		assertTrue(count > 0);
		assertTrue(serviceProxyService.deleteServiceProxy(gwId, serviceId));
	}

	@Test
	public void checkDeleteServiceProxy() {
		ErrorCode errorCode = serviceProxyService.checkDeleteServiceProxy(gwId, serviceId);
		assertTrue(errorCode.getCode().equals(CommonErrorCode.ServiceNotPublished.getCode()));
	}

	@Test
	public void getServiceProxyByServiceIdAndGwId() {
		serviceProxyService.publishServiceToGw(envoyServiceProxyDto);
		EnvoyServiceProxyInfo serviceProxyInfo = serviceProxyService.getServiceProxyByServiceIdAndGwId(gwId,
		                                                                                               serviceId);
		assertTrue(serviceProxyInfo.getServiceId() == serviceId);
		assertTrue(serviceProxyService.deleteServiceProxy(gwId, serviceId));
	}

	@Test
	public void getServiceProxyInterByServiceIdAndGwIds() {
		serviceProxyService.publishServiceToGw(envoyServiceProxyDto);
		EnvoyServiceProxyInfo serviceProxyInfo = serviceProxyService.
			                                                            getServiceProxyInterByServiceIdAndGwIds(
				                                                            Arrays.asList(new Long[]{gwId}),
				                                                            serviceId);
		assertTrue(serviceProxyInfo.getServiceId() == serviceId);
		assertTrue(serviceProxyService.deleteServiceProxy(gwId, serviceId));
	}

	@Test
	public void getServiceProxyByServiceId() {
		List<EnvoyServiceProxyInfo> serviceProxyByServiceId =
			serviceProxyService.getServiceProxyByServiceId(serviceId);
		assertTrue(CollectionUtils.isEmpty(serviceProxyByServiceId));
	}

	@Test
	public void fromMeta() {
		serviceProxyService.publishServiceToGw(envoyServiceProxyDto);
		EnvoyServiceProxyDto envoyServiceProxyDto = serviceProxyService.
			                                                               fromMeta(serviceProxyService
				                                                                        .getServiceProxyByServiceIdAndGwId(
					                                                                        gwId, serviceId));
		assertTrue(envoyServiceProxyDto.getServiceId() == serviceId);
		assertTrue(serviceProxyService.deleteServiceProxy(gwId, serviceId));
	}

	@Test
	public void getPublishedServiceGateway() {
		List<GatewayDto> publishedServiceGateway = serviceProxyService.getPublishedServiceGateway(serviceId);
		assertTrue(CollectionUtils.isEmpty(publishedServiceGateway));
	}

	@Test
	public void getRouteRuleNameWithServiceSubset() {
		ErrorCode errorCode = serviceProxyService.getRouteRuleNameWithServiceSubset(envoyServiceProxyDto);
		assertTrue(errorCode.getCode().equals(CommonErrorCode.Success.getCode()));
	}

}
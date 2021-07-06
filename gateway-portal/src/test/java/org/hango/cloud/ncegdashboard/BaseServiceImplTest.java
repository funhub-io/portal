package org.hango.cloud.ncegdashboard;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@TestPropertySource("classpath:unit-test.properties")
public abstract class BaseServiceImplTest {

	@Value("${projectId}")
	public long projectId;

	@Value("${gwAddr}")
	public String gwAddr;

	@Value("${tenantId}")
	public long tenantId;

	@Value("${tokenId}")
	public String tokenId;

	@Value("${accountId}")
	public String accountId;

	@Value("${gwUniId}")
	public String gwUniId;

	@Value("${gwNameReal}")
	public String gwNameReal;

	@Value("${envId}")
	public String envId;

	@Value("${serviceAuthAddr}")
	public String serviceAuthAddr;

	public String serviceName = "serviceNameUnitTest";

	public String displayName = "displayNameUnitTest";

	public String user = "admin";

	public String serviceType = "http";

	public String gwName = "testUnit";

	public String routeName = "testRouteUnit";

	public String description = "desc";

	public String envoyGwName = "envoy";

}

package org.hango.cloud.gdashboard.api.util;

/**
 * @Date: 创建时间: 2018/1/29 下午8:23.
 */
public class Const {

	// 当不确定HashMap的初始值大小多少合适值，使用该值
	public static final int DEFAULT_MAP_SIZE = 16;

	public static final String DEFAULT_ENCODING = "utf-8";

	public static final String DEFAULT_CONTENT_TYPE = "application/json";

	public static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";

	/**
	 * Some time constants
	 */
	public static final long MS_OF_MINUTE = 60 * 1000;

	public static final long MS_OF_HOUR = 60 * 60 * 1000;

	public static final long MS_OF_DAY = 24 * MS_OF_HOUR;

	public static final String EXAMPLE = "example";

	/**
	 * Http Method Type
	 */
	public static final String GET_METHOD = "GET";

	public static final String POST_METHOD = "POST";

	public static final String PUT_METHOD = "PUT";

	public static final String HEAD_METHOD = "HEAD";

	public static final String DELETE_METHOD = "DELETE";

	public static final String OPTIONS_METHOD = "OPTIONS";

	public static final String SP = "##";

	public static final int OK = 200;

	/**
	 * 正则相关
	 */
	/**
	 * 网关服务管理
	 */

	/**
	 * 服务名称：必填，支持中文、数字、英文大小写、中划线、下划线，最大长度32字符
	 */
	public static final String REGEX_SERVICE_NAME = "^[\\u4e00-\\u9fa5a-zA-Z0-9_\\-]{1,32}";

	/**
	 * 服务标识，必填，支持英文大小写，数字，最长32字符
	 */
	public static final String REGEX_SERVICE_TAG = "^[a-zA-Z0-9]{1,32}";

	/**
	 * 备注信息，选填，支持全文本，最长64字符
	 */
	public static final String REGEX_DESCRIPTION = "^[\\s\\S]{0,200}";

	/**
	 * 通用，选填，支持全文本，最长64字符
	 */
	public static final String REGEX_COMMON = "^[\\s\\S]{0,64}";

	/**
	 * 网关API管理
	 */
	/**
	 * 接口名称：必填，支持中文、数字、英文大小写、中划线、下划线、最大长度64字符
	 */
	public static final String REGEX_API_NAME = "^[\\u4e00-\\u9fa5a-zA-Z0-9_\\-]{1,32}";

	/**
	 * 接口标识：必填，支持英文大小写，
	 */
	public static final String REGEX_API_ALIASNAME = "^[a-zA-Z0-9]{0,32}";

	/**
	 * API path：必填，支持英文大小写，数字，url规范，最大长度64字符
	 */
	public static final String REGEX_API_PATH = "^/[0-9A-Za-z\\/\\{\\}\\-\\_\\.]*$";

	/**
	 * 数据模型管理
	 */
	/**
	 * 模型名称：必填，支持英文大小写,数字，最大长度32字符
	 */
	public static final String REGEX_MODEL_NAME = "[a-zA-Z0-9]{1,32}";

	/**
	 * api method
	 */
	public static final String REGEX_API_METHOD = "GET|POST|DELETE|PUT|HEAD|OPTIONS";

	//api type
	public static final String REGEX_API_TYPE = "RESTFUL";

	//api description
	public static final String REGEX_API_DESCRIPTION = "\\S{1,255}|(\\s)";

	//公共参数
	public static final String ACTION = "Action";

	public static final String VERSION = "Version";

	/**
	 * API测试用例 校验规则
	 */

	public static final String CONTAINS = "contains";

	public static final String STATUS_CODE = "status_code";

	/**
	 * API测试用例 校验规则 所在的位置body 或 header
	 */
	public static final String POSITION_BODY = "body";

	public static final String POSITION_HEADER = "header";

	//记录操作日志时，区分对象类型
	public static final String API = "api";

	//判断参数类型是否是Object
	public static final String OBJECT_PARAM_TYPE = "Object";

	/**
	 * API风格，分为ACTION和RESTFUL
	 */
	public static final String API_ACTION_TYPE = "ACTION";

	/**
	 * 参数类型，分为REQUEST和RESPONSE和QUERYSTRING
	 */
	public static final String REQUEST_PARAM_TYPE = "REQUEST";

	public static final String RESPONSE_PARAM_TYPE = "RESPONSE";

	public static final String QUERYSTRING_PARAM_TYPE = "QUERYSTRING";

	/**
	 * API状态
	 */
	public static final String API_DOCUMENT_STATUS = "开发中";

	public static final String BLANK_ARRAY_CONST = "_blank_array";

	public static final String BLANK_CONST = "_blank";

	public static final String MODEL_NAME_PREFIX = "_model_name_";

	/**
	 * swagger状态
	 */
	public static final String MODEL_TYPE = "数据模型";

	public static final String API_TYPE = "接口";

	public static final String SWAGGER_SYNC_NEW = "新增";

	public static final String SWAGGER_SYNC_COVER = "覆盖";

	public static final String SWAGGER_SYNC_CONFLICT = "冲突";

	public static final String SWAGGER_DETAILS = "SwaggerDetails";

	public static final String SWAGGER_DETAILS_KEY = "SwaggerDetailKey";

}

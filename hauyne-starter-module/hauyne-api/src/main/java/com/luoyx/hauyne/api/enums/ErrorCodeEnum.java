package com.luoyx.hauyne.api.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 错误码枚举类
 *
 * @author 罗英雄
 * @since 2018/7/27 9:40
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCodeEnum {

    /**
     * 成功
     */
    SUCCESS("00000", "成功"),

    /**
     * 一级宏观错误码【用户端错误】
     */
    CLIENT_ERROR("A0001", "用户端错误"),

    /**
     * 二级宏观错误码【用户注册错误】
     */
    USER_REGISTRATION_ERROR("A0100", "用户注册错误"),

    /**
     * 用户未同意隐私协议
     */
    USER_DOES_NOT_AGREE_TO_PRIVACY_AGREEMENT("A0101", "用户未同意隐私协议"),

    /**
     * 注册国家或地区受限
     */
    LIMITED_COUNTRY_OR_REGION_OF_REGISTRATION("A0102", "注册国家或地区受限"),

    /**
     * 用户名校验失败
     */
    USER_NAME_VERIFICATION_FAILED("A0110", "用户名校验失败"),

    /**
     * 用户名已存在
     */
    USER_NAME_ALREADY_EXISTS("A0111", "用户名已存在"),

    /**
     * 用户名包含敏感词
     */
    USER_NAME_CONTAINS_SENSITIVE_WORDS("A0112", "用户名包含敏感词"),

    /**
     * 用户名包含特殊字符
     */
    USER_NAME_CONTAINS_SPECIAL_CHARACTERS("A0113", "用户名包含特殊字符"),

    /**
     * 密码校验失败
     */
    PASSWORD_VERIFICATION_FAILED("A0120", "密码校验失败"),

    /**
     * 密码长度不够
     */
    PASSWORD_LENGTH_IS_NOT_ENOUGH("A0121", "密码长度不够"),

    /**
     * 密码强度不够
     */
    PASSWORD_STRENGTH_IS_NOT_ENOUGH("A0122", "密码强度不够"),

    /**
     * 校验码输入错误
     */
    CHECK_CODE_INPUT_ERROR("A0130", "校验码输入错误"),

    /**
     * 短信校验码输入错误
     */
    SMS_VERIFICATION_CODE_INPUT_ERROR("A0131", "短信校验码输入错误"),

    /**
     * 邮件校验码输入错误
     */
    EMAIL_VERIFICATION_CODE_INPUT_ERROR("A0132", "邮件校验码输入错误"),

    /**
     * 语音校验码输入错误
     */
    VOICE_CHECK_CODE_INPUT_ERROR("A0133", "语音校验码输入错误"),

    /**
     * 用户证件异常
     */
    USER_ID_IS_ABNORMAL("A0140", "用户证件异常"),

    /**
     * 用户证件类型未选择
     */
    USER_ID_TYPE_IS_NOT_SELECTED("A0141", "用户证件类型未选择"),

    /**
     * 大陆身份证编号校验非法
     */
    ILLEGAL_VERIFICATION_OF_ID_CARD_NUMBER_IN_MAINLAND_CHINA("A0142", "大陆身份证编号校验非法"),

    /**
     * 护照编号校验非法
     */
    ILLEGAL_VERIFICATION_OF_PASSPORT_NUMBER("A0143", "护照编号校验非法"),

    /**
     * 军官证编号校验非法
     */
    ILLEGAL_VERIFICATION_OF_OFFICER_CERTIFICATE_NUMBER("A0144", "军官证编号校验非法"),

    /**
     * 用户基本信息校验失败
     */
    USER_BASIC_INFORMATION_VERIFICATION_FAILED("A0150", "用户基本信息校验失败"),

    /**
     * 手机格式校验失败
     */
    MOBILE_PHONE_FORMAT_VERIFICATION_FAILED("A0151", "手机格式校验失败"),

    /**
     * 地址格式校验失败
     */
    ADDRESS_FORMAT_VERIFICATION_FAILED("A0152", "地址格式校验失败"),

    /**
     * 邮箱格式校验失败
     */
    EMAIL_FORMAT_VERIFICATION_FAILED("A0153", "邮箱格式校验失败"),

    /**
     * 二级宏观错误码 【用户登录异常】
     */
    USER_LOGIN_EXCEPTION("A0200", "用户登录异常"),

    /**
     * 用户账户不存在
     */
    USER_ACCOUNT_DOES_NOT_EXIST("A0201", "用户账户不存在"),

    /**
     * 用户账户被冻结
     */
    USER_ACCOUNT_IS_FROZEN("A0202", "用户账户被冻结"),

    /**
     * 用户账户已作废
     */
    USER_ACCOUNT_HAS_BEEN_VOIDED("A0203", "用户账户已作废"),

    /**
     * 用户密码错误
     */
    USER_PASSWORD_IS_WRONG("A0210", "用户密码错误"),

    /**
     * 用户输入密码错误次数超限
     */
    USER_INPUT_PASSWORD_ERROR_TIMES_EXCEED_LIMIT("A0211", "用户输入密码错误次数超限"),

    /**
     * 用户身份校验失败
     */
    USER_IDENTITY_VERIFICATION_FAILED("A0220", "用户身份校验失败"),

    /**
     * 用户指纹识别失败
     */
    USER_FINGERPRINT_RECOGNITION_FAILED("A0221", "用户指纹识别失败"),

    /**
     * 用户面容识别失败
     */
    USER_FACE_RECOGNITION_FAILED("A0222", "用户面容识别失败"),

    /**
     * 用户未获得第三方登录授权
     */
    USER_IS_NOT_AUTHORIZED_TO_LOG_IN_BY_A_THIRD_PARTY("A0223", "用户未获得第三方登录授权"),

    /**
     * 用户登录已过期
     */
    USER_LOGIN_HAS_EXPIRED("A0230", "用户登录已过期"),

    /**
     * 用户验证码错误
     */
    USER_VERIFICATION_CODE_ERROR("A0240", "用户验证码错误"),

    /**
     * 用户验证码尝试次数超限
     */
    THE_NUMBER_OF_ATTEMPTS_OF_USER_VERIFICATION_CODE_EXCEEDS_THE_LIMIT("A0241", "用户验证码尝试次数超限"),

    /**
     * 二级宏观错误码【访问权限异常】
     */
    ACCESS_PERMISSION_EXCEPTION("A0300", "访问权限异常"),

    /**
     * 访问未授权
     */
    ACCESS_NOT_AUTHORIZED("A0301", "访问未授权"),

    /**
     * 正在授权中
     */
    AUTHORIZATION_IN_PROGRESS("A0302", "正在授权中"),

    /**
     * 用户授权申请被拒绝
     */
    USER_AUTHORIZATION_APPLICATION_REJECTED("A0303", "用户授权申请被拒绝"),

    /**
     * 因访问对象隐私设置被拦截
     */
    BLOCKED_DUE_TO_ACCESS_OBJECT_PRIVACY_SETTINGS("A0310", "因访问对象隐私设置被拦截"),

    /**
     * 授权已过期
     */
    AUTHORIZATION_EXPIRED("A0311", "授权已过期"),

    /**
     * 无权限使用API
     */
    NO_PERMISSION_TO_USE_API("A0312", "无权限使用API"),

    /**
     * 用户访问被拦截
     */
    USER_ACCESS_BLOCKED("A0320", "用户访问被拦截"),

    /**
     * 黑名单用户
     */
    BLACKLIST_USERS("A0321", "黑名单用户"),

    /**
     * 账号被冻结
     */
    ACCOUNT_BLOCKED("A0322", "账号被冻结"),

    /**
     * 非法IP地址
     */
    ILLEGAL_IP_ADDRESS("A0323", "非法IP地址"),

    /**
     * 网关访问受限
     */
    GATEWAY_ACCESS_RESTRICTED("A0324", "网关访问受限"),

    /**
     * 地域黑名单
     */
    REGIONAL_BLACKLIST("A0325", "地域黑名单"),

    /**
     * 服务已欠费
     */
    SERVICE_IS_OVERDUE("A0330", "服务已欠费"),

    /**
     * 用户签名异常
     */
    USER_SIGNATURE_EXCEPTION("A0340", "用户签名异常"),

    /**
     * RSA签名错误
     */
    RSA_SIGNATURE_ERROR("A0341", "RSA签名错误"),

    /**
     * 二级宏观错误码【用户请求参数错误】
     */
    USER_REQUEST_PARAMETER_ERROR("A0400", "用户请求参数错误"),

    /**
     * 包含非法恶意跳转链接
     */
    CONTAINS_ILLEGAL_AND_MALICIOUS_JUMP_LINKS("A0401", "包含非法恶意跳转链接"),

    /**
     * 无效的用户输入
     */
    INVALID_USER_INPUT("A0402", "无效的用户输入"),

    /**
     * 请求的资源不存在
     */
    RESOURCE_NOT_FOUND("A0404", "请求的资源不存在"),

    /**
     * 请求的URL不存在
     */
    URL_NOT_FOUND("A1404", "请求的URL不存在"),

    /**
     * 不支持的请求方法
     */
    REQUEST_METHOD_NOT_SUPPORTED("A0405", "不支持的请求方法"),

    /**
     * 请求必填参数为空
     */
    REQUEST_REQUIRED_PARAMETER_IS_EMPTY("A0410", "请求必填参数为空"),

    /**
     * 用户订单号为空
     */
    USER_ORDER_NUMBER_IS_EMPTY("A0411", "用户订单号为空"),

    /**
     * 订购数量为空
     */
    ORDER_QUANTITY_IS_EMPTY("A0412", "订购数量为空"),

    /**
     * 缺少时间戳参数
     */
    MISSING_TIMESTAMP_PARAMETER("A0413", "缺少时间戳参数"),

    /**
     * 非法的时间戳参数
     */
    ILLEGAL_TIMESTAMP_PARAMETER("A0414", "非法的时间戳参数"),

    /**
     * 请求参数值超出允许的范围
     */
    THE_REQUEST_PARAMETER_VALUE_IS_OUT_OF_THE_ALLOWED_RANGE("A0420", "请求参数值超出允许的范围"),

    /**
     * 参数格式不匹配
     */
    PARAMETER_FORMAT_MISMATCH("A0421", "参数格式不匹配"),

    /**
     * 地址不在服务范围
     */
    THE_ADDRESS_IS_OUT_OF_SERVICE("A0422", "地址不在服务范围"),

    /**
     * 时间不在服务范围
     */
    TIME_IS_OUT_OF_SERVICE("A0423", "时间不在服务范围"),

    /**
     * 金额超出限制
     */
    AMOUNT_OUT_OF_LIMIT("A0424", "金额超出限制"),

    /**
     * 数量超出限制
     */
    QUANTITY_OUT_OF_LIMIT("A0425", "数量超出限制"),

    /**
     * 请求批量处理总个数超出限制
     */
    THE_TOTAL_NUMBER_OF_REQUESTS_FOR_BATCH_PROCESSING_EXCEEDS_THE_LIMIT("A0426", "请求批量处理总个数超出限制"),

    /**
     * 请求JSON解析失败
     */
    REQUEST_FOR_JSON_PARSING_FAILED("A0427", "请求JSON解析失败"),

    /**
     * 用户输入内容非法
     */
    ILLEGAL_USER_INPUT("A0430", "用户输入内容非法"),

    /**
     * 包含违禁敏感词
     */
    CONTAINS_PROHIBITED_SENSITIVE_WORDS("A0431", "包含违禁敏感词"),

    /**
     * 图片包含违禁信息
     */
    THE_PICTURE_CONTAINS_PROHIBITED_INFORMATION("A0432", "图片包含违禁信息"),

    /**
     * 文件侵犯版权
     */
    COPYRIGHT_INFRINGEMENT("A0433", "文件侵犯版权"),

    /**
     * 用户操作异常
     */
    USER_OPERATION_IS_ABNORMAL("A0440", "用户操作异常"),

    /**
     * 用户支付超时
     */
    USER_PAYMENT_TIMEOUT("A0441", "用户支付超时"),

    /**
     * 确认订单超时
     */
    CONFIRM_ORDER_TIMEOUT("A0442", "确认订单超时"),

    /**
     * 订单已关闭
     */
    ORDER_CLOSED("A0443", "订单已关闭"),

    /**
     * 二级宏观错误码【用户请求服务异常】
     */
    USER_REQUEST_SERVICE_EXCEPTION("A0500", "用户请求服务异常"),

    /**
     * 请求次数超出限制
     */
    THE_NUMBER_OF_REQUESTS_EXCEEDS_THE_LIMIT("A0501", "请求次数超出限制"),

    /**
     * 请求并发数超出限制
     */
    THE_NUMBER_OF_CONCURRENT_REQUESTS_EXCEEDS_THE_LIMIT("A0502", "请求并发数超出限制"),

    /**
     * 用户操作请等待
     */
    USER_OPERATION_PLEASE_WAIT("A0503", "用户操作请等待"),

    /**
     * WebSocket连接异常
     */
    WEBSOCKET_CONNECTION_EXCEPTION("A0504", "WebSocket连接异常"),

    /**
     * WebSocket连接断开
     */
    WEBSOCKET_CONNECTION_DISCONNECTED("A0505", "WebSocket连接断开"),

    /**
     * 用户重复请求
     */
    USER_REPEAT_REQUEST(" A0506", " 用户重复请求"),

    /**
     * 用户资源异常二级宏观错误码
     */
    USER_RESOURCE_EXCEPTION_LEVEL_II_MACRO_ERROR_CODE("A0600", "用户资源异常二级宏观错误码"),

    /**
     * _账户余额不足
     */
    ACCOUNT_BALANCE_IS_INSUFFICIENT("A0601", "账户余额不足"),

    /**
     * 用户磁盘空间不足
     */
    USER_DISK_SPACE_IS_INSUFFICIENT("A0602", "用户磁盘空间不足"),

    /**
     * 用户内存空间不足
     */
    USER_MEMORY_IS_INSUFFICIENT("A0603", "用户内存空间不足"),

    /**
     * 用户OSS容量不足
     */
    INSUFFICIENT_USER_OSS_CAPACITY("A0604", "用户OSS容量不足"),
    /**
     * 蚂蚁森林浇水数或每天抽奖数
     */
    USER_QUOTA_USED_UP("A0605", "用户配额已用光"),

    /**
     * 二级宏观错误码【用户上传文件异常】
     */
    USER_UPLOAD_FILE_EXCEPTION("A0700", "用户上传文件异常"),

    /**
     * 用户上传文件类型不匹配
     */
    USER_UPLOAD_FILE_TYPE_MISMATCH("A0701", "用户上传文件类型不匹配"),

    /**
     * 用户上传文件太大
     */
    USER_UPLOAD_FILE_TOO_LARGE("A0702", "用户上传文件太大"),

    /**
     * 用户上传图片太大
     */
    USER_UPLOADED_IMAGE_IS_TOO_LARGE("A0703", "用户上传图片太大"),

    /**
     * 用户上传视频太大
     */
    USER_UPLOAD_VIDEO_TOO_LARGE("A0704", "用户上传视频太大"),

    /**
     * 用户上传压缩文件太大
     */
    THE_COMPRESSED_FILE_UPLOADED_BY_THE_USER_IS_TOO_LARGE("A0705", "用户上传压缩文件太大"),
    /**
     * 二级宏观错误码【用户当前版本异常】
     */
    USERS_CURRENT_VERSION_IS_ABNORMAL("A0800", "用户当前版本异常"),

    /**
     * 用户安装版本与系统不匹配
     */
    USER_INSTALLED_VERSION_DOES_NOT_MATCH_THE_SYSTEM("A0801", "用户安装版本与系统不匹配"),

    /**
     * 用户安装版本过低
     */
    USER_INSTALLED_VERSION_IS_TOO_LOW("A0802", "用户安装版本过低"),

    /**
     * 用户安装版本过高
     */
    USER_INSTALLED_VERSION_IS_TOO_HIGH("A0803", "用户安装版本过高"),

    /**
     * 用户安装版本已过期
     */
    USER_INSTALLED_VERSION_EXPIRED("A0804", "用户安装版本已过期"),

    /**
     * 用户API请求版本不匹配
     */
    USER_API_REQUEST_VERSION_MISMATCH("A0805", "用户API请求版本不匹配"),

    /**
     * 用户API请求版本过高
     */
    USER_API_REQUEST_VERSION_TOO_HIGH("A0806", "用户API请求版本过高"),

    /**
     * 用户API请求版本过低
     */
    USER_API_REQUEST_VERSION_TOO_LOW("A0807", "用户API请求版本过低"),

    /**
     * 二级宏观错误码【用户隐私未授权】
     */
    UNAUTHORIZED_USER_PRIVACY("A0900", "用户隐私未授权"),

    /**
     * 用户隐私未签署
     */
    USER_PRIVACY_NOT_SIGNED("A0901", "用户隐私未签署"),

    /**
     * 用户相机未授权
     */
    USER_CAMERA_NOT_AUTHORIZED("A0903", "用户相机未授权"),

    /**
     * 用户图片库未授权
     */
    USER_IMAGE_LIBRARY_IS_NOT_AUTHORIZED("A0904", "用户图片库未授权"),

    /**
     * 用户文件未授权
     */
    USER_FILE_NOT_AUTHORIZED("A0905", "用户文件未授权"),

    /**
     * 用户位置信息未授权
     */
    UNAUTHORIZED_USER_LOCATION_INFORMATION("A0906", "用户位置信息未授权"),

    /**
     * 用户通讯录未授权
     */
    UNAUTHORIZED_USER_ADDRESS_BOOK("A0907", "用户通讯录未授权"),

    /**
     * 二级宏观错误码【用户设备异常】
     */
    USER_EQUIPMENT_ABNORMAL("A1000", "用户设备异常"),

    /**
     * 用户相机异常
     */
    USER_CAMERA_ABNORMAL("A1001", "用户相机异常"),

    /**
     * 用户麦克风异常
     */
    USER_MICROPHONE_ABNORMAL("A1002", "用户麦克风异常"),

    /**
     * 用户听筒异常
     */
    ABNORMAL_USER_HANDSET("A1003", "用户听筒异常"),

    /**
     * 用户扬声器异常
     */
    USER_SPEAKER_ABNORMAL("A1004", "用户扬声器异常"),

    /**
     * 用户GPS定位异常
     */
    USER_GPS_ABNORMAL_POSITIONING("A1005", "用户GPS定位异常"),

    /**
     * 一级宏观错误码【系统执行出错】
     */
    SYSTEM_EXECUTION_ERROR("B0001", "系统执行出错"),

    /**
     * 二级宏观错误码【系统执行超时】
     */
    SYSTEM_EXECUTION_TIMEOUT("B0100", "系统执行超时"),

    /**
     * 系统订单处理超时
     */
    SYSTEM_ORDER_PROCESSING_TIMEOUT("B0101", "系统订单处理超时"),

    /**
     * 二级宏观错误码【系统容灾功能被触发】
     */
    SYSTEM_DISASTER_RECOVERY_FUNCTION_TRIGGERED("B0200", "系统容灾功能被触发"),

    /**
     * 系统限流
     */
    SYSTEM_CURRENT_LIMITING("B0210", "系统限流"),

    /**
     * 系统功能降级
     */
    SYSTEM_FUNCTION_DEGRADATION("B0220", "系统功能降级"),

    /**
     * 二级宏观错误码【系统资源异常】
     */

    SYSTEM_RESOURCE_EXCEPTION("B0300", "系统资源异常"),

    /**
     * 系统资源耗尽
     */
    SYSTEM_RESOURCE_EXHAUSTED("B0310", "系统资源耗尽"),

    /**
     * 系统磁盘空间耗尽
     */
    SYSTEM_DISK_SPACE_EXHAUSTED("B0311", "系统磁盘空间耗尽"),

    /**
     * 系统内存耗尽
     */
    SYSTEM_MEMORY_EXHAUSTED("B0312", "系统内存耗尽"),

    /**
     * 文件句柄耗尽
     */
    FILE_HANDLE_EXHAUSTED("B0313", "文件句柄耗尽"),

    /**
     * 系统连接池耗尽
     */
    SYSTEM_CONNECTION_POOL_EXHAUSTED("B0314", "系统连接池耗尽"),

    /**
     * 系统线程池耗尽
     */
    SYSTEM_THREAD_POOL_EXHAUSTED("B0315", "系统线程池耗尽"),

    /**
     * 系统资源访问异常
     */
    SYSTEM_RESOURCE_ACCESS_EXCEPTION("B0320", "系统资源访问异常"),

    /**
     * 系统读取磁盘文件失败
     */
    SYSTEM_FAILED_TO_READ_DISK_FILE("B0321", "系统读取磁盘文件失败"),

    /**
     * 一级宏观错误码【调用第三方服务出错】
     */
    ERROR_CALLING_THIRD_PARTY_SERVICE("C0001", "调用第三方服务出错"),

    /**
     * 二级宏观错误码【中间件服务出错】
     */
    MIDDLEWARE_SERVICE_ERROR("C0100", "中间件服务出错"),

    /**
     * RPC服务出错
     */
    RPC_SERVICE_ERROR("C0110", "RPC服务出错"),

    /**
     * RPC服务未找到
     */
    RPC_SERVICE_NOT_FOUND("C0111", "RPC服务未找到"),

    /**
     * RPC服务未注册
     */
    RPC_SERVICE_NOT_REGISTERED("C0112", "RPC服务未注册"),

    /**
     * 接口不存在
     */
    INTERFACE_DOES_NOT_EXIST("C0113", "接口不存在"),

    /**
     * 消息服务出错
     */
    MESSAGE_SERVICE_ERROR("C0120", "消息服务出错"),

    /**
     * 消息投递出错
     */
    MESSAGE_DELIVERY_ERROR("C0121", "消息投递出错"),

    /**
     * 消息消费出错
     */
    MESSAGE_CONSUMPTION_ERROR("C0122", "消息消费出错"),

    /**
     * 消息订阅出错
     */
    MESSAGE_SUBSCRIPTION_ERROR("C0123", "消息订阅出错"),

    /**
     * 消息分组未查到
     */
    MESSAGE_GROUP_NOT_FOUND("C0124", "消息分组未查到"),

    /**
     * 缓存服务出错
     */
    CACHE_SERVICE_ERROR("C0130", "缓存服务出错"),

    /**
     * KEY长度超过限制
     */
    KEY_LENGTH_EXCEEDS_LIMIT("C0131", "KEY长度超过限制"),

    /**
     * VALUE长度超过限制
     */
    VALUE_LENGTH_EXCEEDS_LIMIT("C0132", "VALUE长度超过限制"),

    /**
     * 存储容量已满
     */
    STORAGE_CAPACITY_FULL("C0133", "存储容量已满"),

    /**
     * 不支持的数据格式
     */
    UNSUPPORTED_DATA_FORMAT("C0134", "不支持的数据格式"),

    /**
     * 配置服务出错
     */
    CONFIGURATION_SERVICE_ERROR("C0140", "配置服务出错"),

    /**
     * 网络资源服务出错
     */
    NETWORK_RESOURCE_SERVICE_ERROR("C0150", "网络资源服务出错"),

    /**
     * VPN服务出错
     */
    VPN_SERVICE_ERROR("C0151", "VPN服务出错"),

    /**
     * CDN服务出错
     */
    CDN_SERVICE_ERROR("C0152", "CDN服务出错"),

    /**
     * 域名解析服务出错
     */
    DOMAIN_NAME_RESOLUTION_SERVICE_ERROR("C0153", "域名解析服务出错"),

    /**
     * 网关服务出错
     */
    GATEWAY_SERVICE_ERROR("C0154", "网关服务出错"),


    /**
     * 二级宏观错误码【第三方系统执行超时】
     */
    THIRD_PARTY_SYSTEM_EXECUTION_TIMEOUT("C0200", "第三方系统执行超时"),


    /**
     * RPC执行超时
     */
    RPC_EXECUTION_TIMEOUT("C0210", "RPC执行超时"),

    /**
     * 消息投递超时
     */
    MESSAGE_DELIVERY_TIMEOUT("C0220", "消息投递超时"),

    /**
     * 缓存服务超时
     */
    CACHE_SERVICE_TIMEOUT("C0230", "缓存服务超时"),

    /**
     * 配置服务超时
     */
    CONFIGURATION_SERVICE_TIMEOUT("C0240", "配置服务超时"),

    /**
     * 数据库服务超时
     */
    DATABASE_SERVICE_TIMEOUT("C0250", "数据库服务超时"),
    /**
     * 二级宏观错误码【数据库服务出错】
     */
    DATABASE_SERVICE_ERROR("C0300", "数据库服务出错"),

    /**
     * 表不存在
     */
    TABLE_DOES_NOT_EXIST("C0311", "表不存在"),

    /**
     * 列不存在
     */
    COLUMN_DOES_NOT_EXIST("C0312", "列不存在"),

    /**
     * 多表关联中存在多个相同名称的列
     */
    MULTIPLE_COLUMNS_WITH_THE_SAME_NAME_EXIST_IN_MULTI_TABLE_ASSOCIATION("C0321", "多表关联中存在多个相同名称的列"),

    /**
     * 数据库死锁
     */
    DATABASE_DEADLOCK("C0331", "数据库死锁"),

    /**
     * 主键冲突
     */
    PRIMARY_KEY_CONFLICT("C0341", "主键冲突"),

    /**
     * 二级宏观错误码【第三方容灾系统被触发】
     */
    THIRD_PARTY_DISASTER_RECOVERY_SYSTEM_TRIGGERED("C0400", "第三方容灾系统被触发"),

    /**
     * 第三方系统限流
     */
    CURRENT_LIMITING_BY_THIRD_PARTY_SYSTEM("C0401", "第三方系统限流"),

    /**
     * 第三方功能降级
     */
    THIRD_PARTY_FUNCTION_DEGRADATION("C0402", "第三方功能降级"),
    /**
     * 二级宏观错误码【通知服务出错】
     */
    NOTIFICATION_SERVICE_ERROR("C0500", "通知服务出错"),

    /**
     * 短信提醒服务失败
     */
    SMS_REMINDER_SERVICE_FAILED("C0501", "短信提醒服务失败"),

    /**
     * 语音提醒服务失败
     */
    VOICE_ALERT_SERVICE_FAILED("C0502", "语音提醒服务失败"),


    /**
     * 邮件提醒服务失败
     */
    EMAIL_ALERT_SERVICE_FAILED("C0503", "邮件提醒服务失败");


    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误描述
     */
    private final String msg;
}
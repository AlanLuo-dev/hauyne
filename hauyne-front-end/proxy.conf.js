/**
 * For more configuration, please refer to https://angular.io/guide/build#proxying-to-a-backend-server
 *
 * 更多配置描述请参考 https://angular.cn/guide/build#proxying-to-a-backend-server
 *
 * Note: The proxy is only valid for real requests, Mock does not actually generate requests, so the priority of Mock will be higher than the proxy
 */
// module.exports = {
  /**
   * The following means that all requests are directed to the backend `https://localhost:9000/`
   */
  // '/api': {
  //   target: 'https://localhost:9000/',
  //   secure: false, // Ignore invalid SSL certificates
  //   changeOrigin: true
  // }
// };

/**
 * 代理服务配置文件，
 *
 * Angular支持使用json、js文件配置代理服务；
 *
 * 由于JSON的语法标准根本就不支持注释，所以用JSON做配置文件是极不利于维护的，这里使用js做配置
 *
 */

const PROXY_CONFIG = [
    {
        context: ["/api"],
        target: "http://localhost:8700",
        secure: false,
        logLevel: "debug",
        changeOrigin: true
    }
];

module.exports = PROXY_CONFIG;

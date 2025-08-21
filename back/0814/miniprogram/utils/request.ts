// request.ts
import { IAppOption } from '../appoption'

// 获取全局应用实例
const app = getApp<IAppOption>()

/**
 * 封装的网络请求函数
 * @param options 请求参数
 */
export function request(options: WechatMiniprogram.RequestOption) {
  // 如果没有传入header，则初始化一个空对象
  if (!options.header) {
    options.header = {}
  }

  // 从全局数据中获取token
  const token = app.globalData.token

  // 如果存在token，则添加到请求头中
  if (token) {
    options.header['Authorization'] = token
  }

  // 设置默认的请求头Content-Type为application/json
  if (!options.header['Content-Type']) {
    options.header['Content-Type'] = 'application/json'
  }

  // 保存原始的成功回调函数
  const originalSuccess = options.success

  // 重写成功回调函数
  options.success = (res: WechatMiniprogram.RequestSuccessCallbackResult) => {
    // 检查响应数据是否包含特定的错误码（token过期等）
    if (res.data && typeof res.data === 'object' && 'code' in res.data) {
      const code = (res.data as any).code;
      // 如果返回码是 1001, 1002, 1003（token过期或无效）
      if (code === 1001 || code === 1002 || code === 1003) {
        // 清理掉 storage 里的 token
        wx.removeStorageSync('token');
        app.globalData.token = '';

        // 重新执行登录逻辑
        wx.login({
          success: loginRes => {
            console.log("用户临时code： " + loginRes.code)
            wx.request({
              url: app.globalData.baseUrl + '/api/wechat/login?code=' + loginRes.code,
              method: 'POST',
              success: (loginResponse) => {
                if (typeof loginResponse.data === 'object' && loginResponse.data !== null && 'data' in loginResponse.data) {
                  const newToken = (loginResponse.data as any).data
                  if (typeof newToken === 'string') {
                    app.globalData.token = newToken
                    wx.setStorageSync('token', newToken)
                    console.log('Token已重新获取并存储:', newToken)

                    // 重新发送原始请求
                    options.header = options.header || {}
                    options.header['Authorization'] = newToken
                    wx.request(options)
                  } else {
                    console.warn('获取到的token不是字符串类型:', newToken)
                  }
                } else {
                  console.warn('响应数据格式不正确:', loginResponse.data)
                }
              },
              fail: (err) => {
                console.error('重新获取token失败:', err)
              }
            })
          },
        })
        return; // 终止当前请求的处理
      }
    }

    // 调用原始的成功回调函数
    if (originalSuccess) {
      originalSuccess(res)
    }
  }

  // 发起网络请求
  return wx.request(options)
}
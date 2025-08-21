// app.ts
import { IAppOption } from './appoption'
import { request } from './utils/request'

// 抽取登录逻辑为独立函数
function performLogin(app: IAppOption) {
    wx.login({
        success: res => {
            console.log("用户临时code： " + res.code)
            request({
                url: app.globalData.baseUrl + '/api/wechat/login?code=' + res.code,
                method: 'POST',
                success: (loginRes) => {
                    if (typeof loginRes.data === 'object' && loginRes.data !== null && 'data' in loginRes.data) {
                        const newToken = loginRes.data.data
                        if (typeof newToken === 'string') {
                            app.globalData.token = newToken
                            wx.setStorageSync('token', newToken)
                            console.log('Token已存储到cookie:', newToken)

                            fetchUserInfo(newToken, app)
                        } else {
                            console.warn('获取到的token不是字符串类型:', newToken)
                        }
                    } else {
                        console.warn('响应数据格式不正确:', loginRes.data)
                    }
                },
                fail: (err) => {
                    console.error('获取token失败:', err)
                    console.error('请求地址:', app.globalData.baseUrl + '/api/wechat/login?code=' + res.code)
                    console.error('请检查后端服务是否正常运行，网络连接是否正常')
                }
            }, app)
        },
    })
}

// 将 fetchUserInfo 定义为一个独立函数，传递 app 实例
function fetchUserInfo(token: string, app: IAppOption) {
    request({
        url: app.globalData.baseUrl + '/api/user/info',
        method: 'GET',
        success: (infoRes) => {
            console.log('用户信息:', infoRes.data)
            // 增加对特定错误码的处理
            if (infoRes.data && typeof infoRes.data === 'object' && 'code' in infoRes.data) {
                const code = infoRes.data.code;
                // 如果返回码是 1001, 1002, 1003
                if (code === 1001 || code === 1002 || code === 1003) {
                    // 清理掉 storage 里的 token
                    wx.removeStorageSync('token');
                    app.globalData.token = '';
                    
                    // 重新执行登录逻辑
                    performLogin(app);
                }
            }
        },
        fail: (err) => {
            console.error('获取用户信息失败:', err)
        }
    }, app)
}

App<IAppOption>({
    globalData: {
        token: '',
        baseUrl: 'http://localhost:8080'
        // baseUrl: 'https://health-record-y6ve.onrender.com'
    },
    onLaunch() {
        // 展示本地存储能力
        const logs = wx.getStorageSync('logs') || []
        logs.unshift(Date.now())
        wx.setStorageSync('logs', logs)

        // 登录
        const storedToken = wx.getStorageSync('token')
        // 直接使用 this 而不是 getApp()
        const app = this

        if (storedToken) {
            console.log('从storage中获取token:', storedToken)
            app.globalData.token = storedToken

            // 获取token后请求用户信息接口
            fetchUserInfo(storedToken, app)
        } else {
            performLogin(app);
        }
    }
})

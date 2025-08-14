// app.ts
App<IAppOption>({
    globalData: {
        token: '',
        baseUrl: 'http://localhost:8080'
    },
    onLaunch() {
        // 展示本地存储能力
        const logs = wx.getStorageSync('logs') || []
        logs.unshift(Date.now())
        wx.setStorageSync('logs', logs)

        // 登录
        wx.login({
            success: res => {
                console.log("用户临时code： " + res.code)
                // 发送 res.code 到后台换取 openId, sessionKey, unionId
                wx.request({
                    url: getApp().globalData.baseUrl + '/api/wechat/login?code=' + res.code,
                    method: 'POST',
                    success: (loginRes) => {
                        console.log('响应结果:', loginRes.data)
                        // 确保 loginRes.data 是对象后再访问其 data 属性
                        if (typeof loginRes.data === 'object' && loginRes.data !== null && 'data' in loginRes.data) {
                            const token = loginRes.data.data
                            if (typeof token === 'string') {
                                this.globalData.token = token
                                wx.setStorageSync('token', token)
                                console.log('Token已存储到cookie:', token)
                                
                                // 获取token后请求用户信息接口
                                wx.request({
                                    url: getApp().globalData.baseUrl + '/api/user/info',
                                    method: 'GET',
                                    header: {
                                        'Authorization': token
                                    },
                                    success: (infoRes) => {
                                        console.log('用户信息:', infoRes.data)
                                    },
                                    fail: (err) => {
                                        console.error('获取用户信息失败:', err)
                                    }
                                })
                            } else {
                                console.warn('获取到的token不是字符串类型:', token)
                            }
                        } else {
                            console.warn('响应数据格式不正确:', loginRes.data)
                        }
                    },
                    fail: (err) => {
                        console.error('获取token失败:', err)
                        console.error('请求地址:', getApp().globalData.baseUrl + '/api/wechat/login?code=' + res.code)
                        console.error('请检查后端服务是否正常运行，网络连接是否正常')
                    }
                })
            },
        })
    },
})
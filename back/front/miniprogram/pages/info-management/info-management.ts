// info-management.ts
Page({
  data: {
    // 用户基本信息
    name: '',
    age: '',
    height: '',
    weight: '',
    genderIndex: 0,
    genders: ['男', '女'],
    isSmoke: 0, // 0-否, 1-是
    
    // 健康信息ID（如果已存在）
    healthInfoId: null
  },

  onLoad: function() {
    // 页面加载时执行
    this.loadUserInfo();
    this.loadHealthInfo();
  },

  onShow: function() {
    // 页面显示时执行
  },

  // 加载用户基本信息
  loadUserInfo: function() {
    wx.request({
      url: getApp().globalData.baseUrl + '/api/user/info',
      method: 'GET',
      header: {
        'Authorization': wx.getStorageSync('token')
      },
      success: (res: any) => {
        if (res.data.code === 200) {
          const userInfo = res.data.data;
          this.setData({
            name: userInfo.name || '',
            age: userInfo.age || '',
            height: userInfo.height || '',
            weight: userInfo.weight || '',
            genderIndex: userInfo.gender === '女' ? 1 : 0
          });
        } else {
          wx.showToast({
            title: '获取用户信息失败',
            icon: 'none'
          });
        }
      },
      fail: () => {
        wx.showToast({
          title: '网络错误',
          icon: 'none'
        });
      }
    });
  },

  // 加载健康信息
  loadHealthInfo: function() {
    wx.request({
      url: getApp().globalData.baseUrl + '/api/health-info',
      method: 'GET',
      header: {
        'Authorization': wx.getStorageSync('token')
      },
      success: (res: any) => {
        if (res.data.code === 200) {
          const healthInfo = res.data.data;
          if (healthInfo) {
            this.setData({
              healthInfoId: healthInfo.id,
              height: healthInfo.height || '',
              weight: healthInfo.weight || '',
              age: healthInfo.age || '',
              isSmoke: healthInfo.isSmoke || 0
            });
          }
        } else {
          wx.showToast({
            title: '获取健康信息失败',
            icon: 'none'
          });
        }
      },
      fail: () => {
        wx.showToast({
          title: '网络错误',
          icon: 'none'
        });
      }
    });
  },

  // 输入框事件处理
  onNameInput: function(e: any) {
    this.setData({
      name: e.detail.value
    });
  },

  onAgeInput: function(e: any) {
    this.setData({
      age: e.detail.value
    });
  },

  onHeightInput: function(e: any) {
    this.setData({
      height: e.detail.value
    });
  },

  onWeightInput: function(e: any) {
    this.setData({
      weight: e.detail.value
    });
  },

  onGenderChange: function(e: any) {
    this.setData({
      genderIndex: e.detail.value
    });
  },

  onSmokeChange: function(e: any) {
    this.setData({
      isSmoke: parseInt(e.detail.value)
    });
  },

  // 保存健康信息
  saveHealthInfo: function() {
    // 构造用户信息数据
    const userInfo = {
      name: this.data.name,
      age: this.data.age ? parseInt(this.data.age) : null,
      gender: this.data.genders[this.data.genderIndex],
      height: this.data.height ? parseFloat(this.data.height) : null,
      weight: this.data.weight ? parseFloat(this.data.weight) : null
    };

    // 构造健康信息数据
    const healthInfo = {
      id: this.data.healthInfoId,
      height: this.data.height ? parseFloat(this.data.height) : null,
      weight: this.data.weight ? parseFloat(this.data.weight) : null,
      age: this.data.age ? parseInt(this.data.age) : null,
      isSmoke: this.data.isSmoke
    };

    // 保存用户信息
    wx.request({
      url: getApp().globalData.baseUrl + '/api/user',
      method: 'PUT',
      header: {
        'Authorization': wx.getStorageSync('token'),
        'Content-Type': 'application/json'
      },
      data: userInfo,
      success: (res: any) => {
        if (res.data) {
          // 保存健康信息
          wx.request({
            url: getApp().globalData.baseUrl + '/api/health-info',
            method: 'POST',
            header: {
              'Authorization': wx.getStorageSync('token'),
              'Content-Type': 'application/json'
            },
            data: healthInfo,
            success: (res: any) => {
              if (res.data.code === 200) {
                wx.showToast({
                  title: '保存成功',
                  icon: 'success'
                });
                
                // 返回上一页
                setTimeout(() => {
                  wx.navigateBack();
                }, 1500);
              } else {
                wx.showToast({
                  title: '保存健康信息失败',
                  icon: 'none'
                });
              }
            },
            fail: () => {
              wx.showToast({
                title: '网络错误',
                icon: 'none'
              });
            }
          });
        } else {
          wx.showToast({
            title: '保存用户信息失败',
            icon: 'none'
          });
        }
      },
      fail: () => {
        wx.showToast({
          title: '网络错误',
          icon: 'none'
        });
      }
    });
  }
})
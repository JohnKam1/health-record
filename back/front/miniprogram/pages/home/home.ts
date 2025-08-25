// home.ts
Page({
  data: {
    // 页面数据
  },

  onLoad: function() {
    // 页面加载时执行
  },

  onShow: function() {
    // 页面显示时执行
  },

  // 导航到信息管理页面
  goToInfoManagement: function() {
    wx.navigateTo({
      url: '/pages/info-management/info-management'
    });
  },

  // 导航到健康记录页面
  goToHealthRecord: function() {
    wx.navigateTo({
      url: '/pages/health-record/health-record'
    });
  },

  // 导航到报告管理页面
  goToReportManagement: function() {
    wx.navigateTo({
      url: '/pages/report-management/report-management'
    });
  },

  // 导航到个人中心页面
  goToPersonalCenter: function() {
    wx.navigateTo({
      url: '/pages/personal-center/personal-center'
    });
  }
})
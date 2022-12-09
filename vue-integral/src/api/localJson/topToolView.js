/**
 * Created by gfj on 2018/6/6.
 */


export const botBarNav = [
  {
    class: "account",
    navTxt: "账户设置",
    navChild: [
      {URL:'acinfo',head_logo:'&#xe80a;',name:'账号信息'},
      {URL:'upPassword',head_logo:'&#xe738;',name:'修改密码'},
      {URL:'acSubacount',head_logo:'&#xe630;',name:'子账号管理'}]
  },
  {
    class: "",
    navTxt: "消息",
    navLink: ""
  },
  {
    class: "iconfont icon-exit",
    navTxt: "",
    navLink: "login"
  }
];


export const topBarWrap = {
  selected:{
    id: 1,
    pic_url: "http://yun.duiba.com.cn/images/201708/tfkaaspluq.png",
    txt : "宜丰红商城"
  },
  navItem:[
    {
      id: 1,
      pic_url: "http://hd.dlp.duiba.com.cn/complete/styles/images/default_appicon.png",
      txt : "宜丰红商城"
    },
    {
      id: 2,
      pic_url: "http://hd.dlp.duiba.com.cn/complete/styles/images/default_appicon.png",
      txt : "积分商城"
    }
]};

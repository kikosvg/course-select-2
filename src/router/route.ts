import type { RouteRecordRaw } from 'vue-router'

/** 默认布局 */
const Layout = () => import('@/layout/index.vue')

/** 系统路由 */
export const systemRoutes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue'),
    meta: { hidden: true },
    children: [

    ],
  },

  {
    path: '/',
    name: 'Dashboard',
    component: Layout,
    redirect: '/dashboard/workplace',
    meta: { title: 'dashboard', icon: 'dashboard', hidden: false },
    children: [
      {
        path: '/dashboard/workplace',
        name: 'Workplace',
        component: () => import('@/views/dashboard/workplace/index.vue'),
        meta: { title: 'workplace', icon: 'desktop', hidden: false, affix: true },
      },
    ],
  },
  {
    path: '/aboutUs',
    name: 'AboutUs',
    component: () => import('@/views/about/us/us.vue'),
    meta: { hidden: true },
  },
  {
    path: '/social/callback',
    component: () => import('@/views/login/social/index.vue'),
    meta: { hidden: true },
  },
  {
    path: '/pwdExpired',
    component: () => import('@/views/login/pwdExpired/index.vue'),
    meta: { hidden: true },
  },
  {
    path: '/setting',
    name: 'Setting',
    component: Layout,
    meta: { hidden: true },
    children: [
      {
        path: '/setting/profile',
        name: 'SettingProfile',
        component: () => import('@/views/setting/profile/index.vue'),
        meta: { title: '个人中心', showInTabs: false },
      },
      {
        path: '/setting/message',
        name: 'SettingMessage',
        component: () => import('@/views/setting/message/index.vue'),
        meta: { title: '消息中心', showInTabs: false },
      },
    ],
  },

]

// 固定路由（默认路由）
export const constantRoutes: RouteRecordRaw[] = [

  {
    path: '/redirect',
    component: Layout,
    meta: { hidden: true },
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/default/redirect/index.vue'),
      },
    ],
  },
  {
    path: '/:pathMatch(.*)*',
    component: () => import('@/views/default/error/404.vue'),
    meta: { hidden: true },
  },
  {
    path: '/403',
    component: () => import('@/views/default/error/403.vue'),
    meta: { hidden: true },
  },
]

import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/Login'
import AdminLayout from '@/views/AdminLayout'
import Dashboard from '@/views/Dashboard'
import RoomManagement from '@/views/RoomManagement'
import OrderManagement from '@/views/OrderManagement'
import UserManagement from '@/views/UserManagement'

Vue.use(Router)

export default new Router({
  mode: 'hash',
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/admin',
      component: AdminLayout,
      children: [
        {
          path: 'dashboard',
          name: 'Dashboard',
          component: Dashboard
        },
        {
          path: 'room',
          name: 'RoomManagement',
          component: RoomManagement
        },
        {
          path: 'order',
          name: 'OrderManagement',
          component: OrderManagement
        },
        {
          path: 'user',
          name: 'UserManagement',
          component: UserManagement
        }
      ]
    },
    // 快捷路由
    {
      path: '/dashboard',
      redirect: '/admin/dashboard'
    },
    {
      path: '/room',
      redirect: '/admin/room'
    },
    {
      path: '/order',
      redirect: '/admin/order'
    },
    {
      path: '/user',
      redirect: '/admin/user'
    }
  ]
})

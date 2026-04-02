import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/',
        component: () => import('../views/Login.vue'),
        redirect: '/login',
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '/managerlogin',
        name: 'managerlogin',
        component: () => import('../views/ManagerLogin.vue')
    },
    {
        path: '/enroll',
        name: 'enroll',
        component: () => import('../views/Enroll.vue')
     },
     {
        path: '/404',
        name: '404',
        component: () => import('../views/NotFound.vue')
     },
    {
        path: '/main',
        name: 'main',
        component: () => import('../views/Main.vue'),
        children: [
            {
                path: '/main/borrrow',
                name: 'borrow',
                component: () => import('../views/Borrow.vue')
            },
          {
              path: '/main/reader',
              name: 'reader',
              component: () => import('../views/Reader.vue')
          },
          {
              path: '/main/book',
              name: 'book',
              component: () => import('../views/Book.vue')
          },
          {
              path: '/main/kind',
              name: 'kind',
              component: () => import('../views/Kind.vue')
          },
          {
              path: '/main/manager',
              name: 'manager',
              component: () => import('../views/Manager.vue')
          },
          {
            path: '/main/message',
            name: 'message',
            component: () => import('../views/Message.vue')
            },
          {
                path: '/main/addbook',
                name: 'addbook',
                component: () => import('../views/AddBook.vue')
           },
        ]
    },
    {
        path: '/mainuser',
        name: 'mainuser',
        component: () => import('../views/MainUser.vue'),
        children: [
        {
              path: '/mainuser/bookuser',
              name: 'bookuser',
              component: () => import('../views/BookUser.vue')
         },
        {
            path: '/mainuser/homeuser',
            name: 'homeuser',
            component: () => import('../views/HomeUser.vue')
        },
        {
            path: '/mainuser/messageuser',
            name: 'messageuser',
            component: () => import('../views/MessageUser.vue')
         },
        {
                path: '/mainuser/user',
                name: 'user',
                component: () => import('../views/User.vue')
         },
        ] 
    },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router

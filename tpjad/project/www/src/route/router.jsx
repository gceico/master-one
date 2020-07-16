import React, { lazy, Suspense, useContext } from 'react'
import { BrowserRouter, Redirect, Route, Switch } from 'react-router-dom'

import Header from '../components/header/header'
import { CART_ROUTE, CHECKOUT_ROUTE, HOME_ROUTE, ORDERS_ROUTE, PRODUCTS_ROUTE } from '../config'
import { Content } from '../styles'
import PrivateRoute from './private-router'

export default function Router() {
  return (
    <BrowserRouter>
      <Header />
      <Content>
        <Suspense fallback={<div />}>
          <Switch>
            <PrivateRoute
              path={CHECKOUT_ROUTE}
              allowed={true}
              component={lazy(() => import('../modules/checkout/checkout-router'))}
            />
            <PrivateRoute
              path={CART_ROUTE}
              allowed={true}
              component={lazy(() => import('../modules/cart/cart-router'))}
            />
            <PrivateRoute
              path={PRODUCTS_ROUTE}
              allowed={true}
              component={lazy(() => import('../modules/products/products-router'))}
            />
            <PrivateRoute
              path={ORDERS_ROUTE}
              allowed={true}
              component={lazy(() => import('../modules/checkout/views/orders'))}
            />
            <Route path='' render={() => <Redirect to={HOME_ROUTE} />} />
          </Switch>
        </Suspense>
      </Content>
    </BrowserRouter>
  )
}

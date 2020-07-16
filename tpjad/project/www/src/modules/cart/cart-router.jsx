import React, { lazy } from 'react'
import { Route, Switch } from 'react-router'

export default function ProductsRouter({ match }) {
  return (
    <Switch>
      <Route component={lazy(() => import('./views/cart'))} />
    </Switch>
  )
}

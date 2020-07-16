import React, { lazy } from 'react'
import { Route, Switch } from 'react-router'

export default function CheckoutRouter({ match }) {
  return (
    <Switch>
      <Route component={lazy(() => import('./views/checkout'))} />
    </Switch>
  )
}

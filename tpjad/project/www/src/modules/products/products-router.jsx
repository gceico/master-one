import React, { lazy } from 'react'
import { Route, Switch } from 'react-router'

export default function ProductsRouter({ match }) {
  return (
    <Switch>
      {/* <Route
        exact
        path={`${match.url}${ADD_ROUTE}`}
        component={lazy(() => import('./views/products/products-add'))}
      />
      <Route
        exact
        path={`${match.url}/:id${EDIT_ROUTE}`}
        component={lazy(() => import('./views/products/products-edit'))}
      />
      <Route
        exact
        path={`${match.url}/:id`}
        component={lazy(() => import('./views/products/products-view'))}
      /> */}
       <Route
        exact
        path={`${match.url}/view/:id`}
        component={lazy(() => import('./views/product-view'))}
      />
      <Route component={lazy(() => import('./views/products-list'))} />
    </Switch>
  )
}

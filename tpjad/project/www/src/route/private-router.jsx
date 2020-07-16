import React from 'react'
import { Redirect, Route } from 'react-router-dom'

import { LOGIN_ROUTE } from '../config'

export default function PrivateRoute({
  component: Component,
  redirectTo = LOGIN_ROUTE,
  path,
  render,
  allowed,
  ...rest
}) {
  return (
    <Route
      {...rest}
      render={props =>
        allowed ? (
          render ? (
            render(props)
          ) : (
            <Component {...props} />
          )
        ) : (
          <Redirect
            to={{
              pathname: redirectTo,
              state: { from: props.location }
            }}
          />
        )
      }
    />
  )
}

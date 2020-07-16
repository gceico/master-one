import React from 'react'
import Breadcrumb from 'react-bootstrap/Breadcrumb'
import { Route } from 'react-router-dom'

import BreadcrumbsItem from './breadcrumbs-item'

export default function Breadcrumbs() {
  return (
    <Breadcrumb>
      <Breadcrumb.Item href={'/'}>Home</Breadcrumb.Item>
      <Route path='/:path' component={BreadcrumbsItem} />
    </Breadcrumb>
  )
}

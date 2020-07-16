import _ from 'lodash'
import React from 'react'
import Breadcrumb from 'react-bootstrap/Breadcrumb'
import { Route } from 'react-router-dom'

export default function BreadcrumbsItem({ match }) {
  const urlPaths = _.split(match.url, '/')
  const urlName = _.capitalize(urlPaths[urlPaths.length - 1])
  return (
    <>
      <Breadcrumb.Item href={match.url || ''}>{urlName}</Breadcrumb.Item>
      <Route path={`${match.url}/:path`} component={BreadcrumbsItem} />
    </>
  )
}

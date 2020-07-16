import UilCheck from '@iconscout/react-unicons/icons/uil-check'
import UilTimes from '@iconscout/react-unicons/icons/uil-times'
import _ from 'lodash'
import React, { useContext, useEffect, useState } from 'react'
import Table from 'react-bootstrap/Table'

import Loader from '../../../components/loader/loader'
import { getCheckout } from '../checkout-actions'
import { CheckoutContext } from '../checkout-context'

export default function Orders() {
  const checkoutContext = useContext(CheckoutContext)
  const { loading, order } = checkoutContext

  useEffect(() => {
    getCheckout({ checkoutContext, accountId: 1 })
  }, [])

  if (loading) {
    return <Loader />
  }
  return (
    <Table striped bordered hover>
      <thead>
        <tr>
          <th>Firstname</th>
          <th>Lastname</th>
          <th>Address</th>
          <th>Payment</th>
          <th>Total</th>
          <th>Shipped</th>
        </tr>
      </thead>
      <tbody>
        {_.map(order, item => (
          <tr>
            <td>{item.firstName}</td>
            <td>{item.lastName}</td>
            <td>{item.address}</td>
            <td>{item.payment}</td>
            <td>{item.total}</td>
            <td>{item.shipped ? <UilCheck /> : <UilTimes />}</td>
          </tr>
        ))}
      </tbody>
    </Table>
  )
}

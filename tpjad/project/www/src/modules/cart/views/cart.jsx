import _ from 'lodash'
import React, { useContext, useEffect, useState } from 'react'
import Button from 'react-bootstrap/Button'
import Card from 'react-bootstrap/Card'
import Col from 'react-bootstrap/Col'
import Row from 'react-bootstrap/Row'
import Table from 'react-bootstrap/Table'

import Loader from '../../../components/loader/loader'
import { CHECKOUT_ROUTE, PRODUCTS_ROUTE } from '../../../config'
import { deleteItemFromCart, getItemsFromCart, updateItemInCart } from '../cart-actions'
import { CartContext } from '../cart-context'
import CartQuantity from './cart-quantity'

export default function Cart() {
  const cartContext = useContext(CartContext)
  const { loading, cart, changed } = cartContext
  useEffect(() => {
    getItemsFromCart({ cartContext, accountId: 1 })
  }, [changed])

  const onItemDelete = cartItemId => deleteItemFromCart({ cartContext, cartItemId })

  const onQuantityChange = (item, quantity) => {
    if (_.isNumber(parseInt(quantity, 10))) {
      const { id, productId , productName, price } = item
      updateItemInCart({
        cartContext,
        cartItemId: item.id,
        data: {id, productId, productName, price, quantity: quantity, accountId: 1 }
      })
    }
  }

  if (loading) {
    return <Loader />
  }
  let total = 0
  _.map(cart, item => (total += item.price * item.quantity))
  return (
    <>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Product</th>
            <th>Price/Unit</th>
            <th>Quantity</th>
            <th>Total</th>
          </tr>
        </thead>
        <tbody>
          {_.map(cart, item => (
            <tr>
              <td>{item.productName}</td>
              <td>{item.price}</td>
              <td>
                <CartQuantity
                  product={item}
                  onQuantityChange={onQuantityChange}
                  onItemDelete={onItemDelete}
                />
              </td>
              <td>{item.price * item.quantity}</td>
            </tr>
          ))}
        </tbody>
      </Table>
      <Card>
        <Card.Body>
          <Card.Title>Total: {total} RON</Card.Title>
        </Card.Body>
        <Card.Footer>
          <Row>
            <Col>
              <Button variant={'link'} href={PRODUCTS_ROUTE}>
                Continue shopping
              </Button>
              <Button href={CHECKOUT_ROUTE}>Place Order</Button>
            </Col>
          </Row>
        </Card.Footer>
      </Card>
    </>
  )
}

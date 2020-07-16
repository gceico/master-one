import arrayMutators from 'final-form-arrays'
import _ from 'lodash'
import React, { useContext, useEffect, useState } from 'react'
import Button from 'react-bootstrap/Button'
import Card from 'react-bootstrap/Card'
import Col from 'react-bootstrap/Col'
import Row from 'react-bootstrap/Row'
import Table from 'react-bootstrap/Table'
import { Field, Form } from 'react-final-form'
import { useHistory } from 'react-router-dom'

import DefaultInput from '../../../components/input/default-input'
import DefaultSelect from '../../../components/input/default-select'
import Loader from '../../../components/loader/loader'
import { CART_ROUTE, PRODUCTS_ROUTE } from '../../../config'
import { cleanCart, getItemsFromCart } from '../../cart/cart-actions'
import { CartContext } from '../../cart/cart-context'
import { placeOrder } from '../checkout-actions'
import { CheckoutContext } from '../checkout-context'
import checkoutValidator from './checkout-validator'

export default function CheckoutForm({ errors, loading }) {
  const cartContext = useContext(CartContext)
  const checkoutContext = useContext(CheckoutContext)
  const { cart, changed } = cartContext
  const history = useHistory()
  useEffect(() => {
    getItemsFromCart({ cartContext, accountId: 1 })
  }, [changed])

  if (loading) {
    return <Loader />
  }

  let total = 0
  _.map(cart, item => (total += item.price * item.quantity))

  const onSubmit = values => {
    const data = { ...values, total, accountId: 1 }
    placeOrder({ checkoutContext, data })
    cleanCart({ cartContext })
    history.replace(PRODUCTS_ROUTE)
  }

  return (
    <Form
      onSubmit={onSubmit}
      validate={checkoutValidator}
      mutators={{
        ...arrayMutators
      }}
      render={({ handleSubmit, pristine, invalid }) => (
        <form onSubmit={handleSubmit}>
          <Row>
            <Col md={12}>
              <Table striped bordered size='sm'>
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
                      <td>{item.quantity}</td>
                      <td>{item.price * item.quantity}</td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </Col>
            <Col md={12}>
              <Card bg='info' text='white' style={{ marginBottom: 15 }}>
                <Card.Body>
                  <Card.Title style={{ marginBottom: 0 }}>Total: {total} RON</Card.Title>
                </Card.Body>
              </Card>
            </Col>
          </Row>
          <Card>
            <Card.Body>
              <Row>
                <Col md={6}>
                  <Field
                    component={DefaultInput}
                    label={'First name'}
                    type={'text'}
                    name={'firstName'}
                    placeholder={'Eg. James'}
                  />
                </Col>
                <Col md={6}>
                  <Field
                    component={DefaultInput}
                    label={'Last name'}
                    type={'text'}
                    name={'lastName'}
                    placeholder={'Eg. Doe'}
                  />
                </Col>
                <Col md={12}>
                  <Field
                    component={DefaultInput}
                    label={'Complete address'}
                    type={'text'}
                    name={'address'}
                    placeholder={
                      'Eg. Bucuresti, str. Cantemir, no. 23, bl. S12, sc. A, ap. 32'
                    }
                  />
                </Col>
                <Col md={12}>
                  <Field
                    component={DefaultSelect}
                    label={'Payment method'}
                    options={[
                      { value: 'cash', name: 'Cash' },
                      { value: 'card', name: 'Card' }
                    ]}
                    name={'payment'}
                    placeholder={'Eg. Cash'}
                  />
                </Col>

                <Col>
                  <Button variant={'link'} href={CART_ROUTE}>
                    Cancel
                  </Button>
                  <Button type={'submit'}>Place order</Button>
                </Col>
              </Row>
            </Card.Body>
          </Card>
        </form>
      )}
    />
  )
}

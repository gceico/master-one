import React, { useContext, useEffect, useState } from 'react'
import Button from 'react-bootstrap/Button'
import Card from 'react-bootstrap/Card'
import Col from 'react-bootstrap/Col'
import Image from 'react-bootstrap/Image'
import Row from 'react-bootstrap/Row'
import { useLocation } from 'react-router-dom'

import Loader from '../../../components/loader/loader'
import { PRODUCTS_ROUTE } from '../../../config'
import { addToCart } from '../../cart/cart-actions'
import { CartContext } from '../../cart/cart-context'
import { getProductById } from '../products-actions'
import { ProductsContext } from '../products-context'

export default function ProductView() {
  const productsContext = useContext(ProductsContext)
  const cartContext = useContext(CartContext)
  const { loading, current } = productsContext
  const location = useLocation()
  const { pathname } = location
  let id = pathname.split('/')
  id = id[id.length - 1]

  useEffect(() => {
    getProductById({ productsContext, id })
  }, [id])

  const onAdd = item => {
    const { id: productId, name: productName, price } = item
    addToCart({
      cartContext,
      data: { productId, productName, price, quantity: 1, accountId: 1 }
    })
  }

  if (loading) {
    return <Loader />
  }
  return (
    <Row>
      <Col md={4}>
        <Image  thumbnail src={current.photo}  />
      </Col>
      <Col md={8}>
        <Card>
          <Card.Body>
            <Card.Title>{current.name}</Card.Title>
            <Card.Text>{current.description}</Card.Text>
            <Card.Text>{current.category}</Card.Text>
            <Card.Text>{current.price} RON</Card.Text>
            <Button variant='primary' onClick={() => onAdd(current)}>
              Add to cart
            </Button>
            <Button variant={'link'} href={PRODUCTS_ROUTE}>
              Back to shopping
            </Button>
          </Card.Body>
        </Card>
      </Col>
    </Row>
  )
}

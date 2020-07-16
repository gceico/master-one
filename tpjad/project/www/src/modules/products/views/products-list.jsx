import _ from 'lodash'
import React, { useContext, useEffect, useState } from 'react'
import Button from 'react-bootstrap/Button'
import Card from 'react-bootstrap/Card'
import CardColumns from 'react-bootstrap/CardColumns'
import { useLocation } from 'react-router-dom'

import Loader from '../../../components/loader/loader'
import { PRODUCTS_ROUTE } from '../../../config'
import { addToCart } from '../../cart/cart-actions'
import { CartContext } from '../../cart/cart-context'
import { getProducts } from '../products-actions'
import { ProductsContext } from '../products-context'

const categories = ['laptop', 'tablet', 'phone']
export default function ProductsList() {
  const productsContext = useContext(ProductsContext)
  const cartContext = useContext(CartContext)
  const { loading, products } = productsContext
  const location = useLocation()
  const { pathname } = location
  let category = pathname.split('/')
  category = category[category.length - 1]

  useEffect(() => {
    if (categories.includes(category)) {
      getProducts({ productsContext, category })
    } else {
      getProducts({ productsContext })
    }
  }, [])

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
    <>
      <CardColumns>
        {_.map(products, item => (
          <Card>
            <Card.Img variant='top' src={item.photo} />
            <Card.Body>
              <Card.Title>{item.name}</Card.Title>
              <Card.Text>{item.description}</Card.Text>
              <Card.Text>{item.category}</Card.Text>
              <Card.Text className={'font-weight-bold text-primary'}>
                {item.price} RON
              </Card.Text>
              <Button variant='primary' onClick={() => onAdd(item)}>
                Add to cart
              </Button>
              <Button href={`${PRODUCTS_ROUTE}/view/${item.id}`} variant='link'>
                Details
              </Button>
            </Card.Body>
          </Card>
        ))}
      </CardColumns>
    </>
  )
}

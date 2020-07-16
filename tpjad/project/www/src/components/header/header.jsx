import UilCart from '@iconscout/react-unicons/icons/uil-cart'
import _ from 'lodash'
import React, { useContext, useEffect, useState } from 'react'
import Badge from 'react-bootstrap/Badge'
import Nav from 'react-bootstrap/Nav'
import Navbar from 'react-bootstrap/Navbar'
import NavDropdown from 'react-bootstrap/NavDropdown'
import { useHistory } from 'react-router-dom'

import { CART_ROUTE, ORDERS_ROUTE, PRODUCTS_ROUTE } from '../../config'
import { getItemsFromCart } from '../../modules/cart/cart-actions'
import { CartContext } from '../../modules/cart/cart-context'
import { ProductsContext } from '../../modules/products/products-context'
import Breadcrumbs from '../breadcrumbs/breadcrumbs'
import { HeaderWrapper } from './styles'

export default function Header() {
  const [category, setCategory] = useState(undefined)
  const cartContext = useContext(CartContext)
  const productsContext = useContext(ProductsContext)
  const history = useHistory()

  const {
    location: { pathname }
  } = history
  const { cart, changed } = cartContext
  const numberOfItems = _.size(cart)
  useEffect(() => {
    getItemsFromCart({ cartContext, accountId: 1 })
  }, [changed])

  return (
    <HeaderWrapper>
      <Navbar collapseOnSelect expand='lg' bg='dark' variant='dark'>
        <Navbar.Brand href='#'>One true shop</Navbar.Brand>
        <Navbar.Toggle aria-controls='responsive-navbar-nav' />
        <Navbar.Collapse id='responsive-navbar-nav'>
          <Nav className='mr-auto'>
            <Nav.Link href={PRODUCTS_ROUTE}>Products</Nav.Link>
            <NavDropdown title='Categories' id='collasible-nav-dropdown'>
              <NavDropdown.Item href={`${pathname}/laptop`}>Laptops</NavDropdown.Item>
              <NavDropdown.Item href={`${pathname}/phone`}>Phones</NavDropdown.Item>
              <NavDropdown.Item href={`${pathname}/tablet`}>Tablets</NavDropdown.Item>
              <NavDropdown.Divider />
              <NavDropdown.Item href={PRODUCTS_ROUTE}>All</NavDropdown.Item>
            </NavDropdown>
          </Nav>
          <Nav>
            <Nav.Link eventKey={2} href={CART_ROUTE}>
              <UilCart />
              <Badge pill variant='danger'>
                {numberOfItems}
              </Badge>
            </Nav.Link>
            <NavDropdown title='Welcome, Gabriel' id='collasible-nav-dropdown'>
              <NavDropdown.Item href={`${ORDERS_ROUTE}`}>Orders</NavDropdown.Item>
              <NavDropdown.Item href='#'>Logout</NavDropdown.Item>
            </NavDropdown>
          </Nav>
        </Navbar.Collapse>
      </Navbar>
      <Breadcrumbs />
    </HeaderWrapper>
  )
}

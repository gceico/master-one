import './App.css'

import React from 'react'

import { CartContextProvider } from './modules/cart/cart-context'
import { CheckoutContextProvider } from './modules/checkout/checkout-context'
import { ProductsContextProvider } from './modules/products/products-context'
import Router from './route/router'

function App() {
  return (
    <ProductsContextProvider>
      <CartContextProvider>
        <CheckoutContextProvider>
          <Router />
        </CheckoutContextProvider>
      </CartContextProvider>
    </ProductsContextProvider>
  )
}

export default App

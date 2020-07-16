import Axios from 'axios'
import _ from 'lodash'

import { API_URL } from '../../config'

export const ADD_ITEM = 'ADD_ITEM'
export const ADD_ITEM_SUCCESS = 'ADD_ITEM_SUCCESS'
export const ADD_ITEM_FAIL = 'ADD_ITEM_FAIL'
export const GET_CART = 'GET_CART'
export const GET_CART_SUCCESS = 'GET_CART_SUCCESS'
export const GET_CART_FAIL = 'GET_CART_FAIL'
export const UPDATE_ITEM = 'UPDATE_ITEM'
export const UPDATE_ITEM_SUCCESS = 'UPDATE_ITEM_SUCCESS'
export const UPDATE_ITEM_FAIL = 'UPDATE_ITEM_FAIL'
export const DELETE_ITEM = 'DELETE_ITEM'
export const DELETE_ITEM_SUCCESS = 'DELETE_ITEM_SUCCESS'
export const DELETE_ITEM_FAIL = 'DELETE_ITEM_FAIL'
export const CLEAN_CART = 'CLEAN_CART'

export const getItemsFromCart = async ({ cartContext, accountId }) => {
  let response
  cartContext.dispatch({
    type: GET_CART
  })
  try {
    response = await Axios({
      url: `${API_URL}/lineItems?accountId=${accountId}`,
      method: 'get'
    })
    cartContext.dispatch({
      type: GET_CART_SUCCESS,
      payload: response.data
    })
  } catch (err) {
    const error = _.get(err, 'response.data.errors')
    cartContext.dispatch({
      type: GET_CART_FAIL,
      payload: error
    })
  }
}

export const addToCart = async ({ cartContext, data }) => {
  let response
  cartContext.dispatch({
    type: ADD_ITEM
  })
  try {
    response = await Axios({
      url: `${API_URL}/lineItems`,
      method: 'post',
      data: data
    })
    cartContext.dispatch({
      type: ADD_ITEM_SUCCESS,
      payload: response.data
    })
  } catch (err) {
    const error = _.get(err, 'response.data.errors')
    cartContext.dispatch({
      type: ADD_ITEM_FAIL,
      payload: error
    })
  }
}

export const updateItemInCart = async ({ cartContext, cartItemId, data }) => {
  let response
  cartContext.dispatch({
    type: UPDATE_ITEM
  })
  try {
    response = await Axios({
      url: `${API_URL}/lineItems/${cartItemId}`,
      method: 'put',
      data: data
    })
    cartContext.dispatch({
      type: UPDATE_ITEM_SUCCESS,
      payload: response.data
    })
  } catch (err) {
    const error = _.get(err, 'response.data.errors')
    cartContext.dispatch({
      type: UPDATE_ITEM_FAIL,
      payload: error
    })
  }
}

export const cleanCart = async ({ cartContext }) => {
  const { cart } = cartContext
  _.map(cart, item => {
    deleteItemFromCart({ cartContext, cartItemId: item.id })
  })
  cartContext.dispatch({
    type: CLEAN_CART
  })
}

export const deleteItemFromCart = async ({ cartContext, cartItemId }) => {
  let response
  cartContext.dispatch({
    type: DELETE_ITEM
  })
  try {
    response = await Axios({
      url: `${API_URL}/lineItems/${cartItemId}`,
      method: 'delete'
    })
    cartContext.dispatch({
      type: DELETE_ITEM_SUCCESS,
      payload: cartItemId
    })
  } catch (err) {
    const error = _.get(err, 'response.data.errors')
    cartContext.dispatch({
      type: DELETE_ITEM_FAIL,
      payload: error
    })
  }
}

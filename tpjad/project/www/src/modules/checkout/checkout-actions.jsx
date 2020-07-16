import Axios from 'axios'
import _ from 'lodash'

import { API_URL } from '../../config'

export const GET_CHECKOUT = 'GET_CHECKOUT'
export const GET_CHECKOUT_SUCCESS = 'GET_CHECKOUT_SUCCESS'
export const GET_CHECKOUT_FAIL = 'GET_CHECKOUT_FAIL'
export const PLACE_ORDER = 'PLACE_ORDER'
export const PLACE_ORDER_SUCCESS = 'PLACE_ORDER_SUCCESS'
export const PLACE_ORDER_FAIL = 'PLACE_ORDER_FAIL'

export const getCheckout = async ({ checkoutContext, accountId }) => {
  let response
  checkoutContext.dispatch({
    type: GET_CHECKOUT
  })
  try {
    response = await Axios({
      url: `${API_URL}/checkouts?accountId=${accountId}`,
      method: 'get'
    })
    checkoutContext.dispatch({
      type: GET_CHECKOUT_SUCCESS,
      payload: response.data
    })
  } catch (err) {
    const error = _.get(err, 'response.data.errors')
    checkoutContext.dispatch({
      type: GET_CHECKOUT_FAIL,
      payload: error
    })
  }
}

export const placeOrder = async ({ checkoutContext, data }) => {
  let response
  checkoutContext.dispatch({
    type: PLACE_ORDER
  })
  try {
    response = await Axios({
      url: `${API_URL}/checkouts`,
      method: 'post',
      data
    })
    checkoutContext.dispatch({
      type: PLACE_ORDER_SUCCESS,
      payload: response.data
    })
  } catch (err) {
    const error = _.get(err, 'response.data.errors')
    checkoutContext.dispatch({
      type: PLACE_ORDER_FAIL,
      payload: error
    })
  }
}

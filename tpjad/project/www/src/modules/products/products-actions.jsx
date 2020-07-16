import Axios from 'axios'
import _ from 'lodash'

import { API_URL } from '../../config'

export const GET_PRODUCTS = 'GET_PRODUCTS'
export const GET_PRODUCTS_SUCCESS = 'GET_PRODUCTS_SUCCESS'
export const GET_PRODUCTS_FAIL = 'GET_PRODUCTS_FAIL'
export const GET_SINGLE = 'GET_SINGLE'
export const GET_SINGLE_SUCCESS = 'GET_SINGLE_SUCCESS'
export const GET_SINGLE_FAIL = 'GET_SINGLE_FAIL'

export const getProducts = async ({ productsContext, category = 'all' }) => {
  const query = category ? `category=${category}` : ''
  let response
  productsContext.dispatch({
    type: GET_PRODUCTS
  })
  try {
    response = await Axios({
      url: `${API_URL}/products?${query}`,
      method: 'get'
    })
    productsContext.dispatch({
      type: GET_PRODUCTS_SUCCESS,
      payload: response.data
    })
  } catch (err) {
    const error = _.get(err, 'response.data.errors')
    productsContext.dispatch({
      type: GET_PRODUCTS_FAIL,
      payload: error
    })
  }
}
export const getProductById = async ({ productsContext, id = 1 }) => {
  let response
  productsContext.dispatch({
    type: GET_SINGLE
  })
  try {
    response = await Axios({
      url: `${API_URL}/products/${id}`,
      method: 'get'
    })
    productsContext.dispatch({
      type: GET_SINGLE_SUCCESS,
      payload: response.data
    })
  } catch (err) {
    const error = _.get(err, 'response.data.errors')
    productsContext.dispatch({
      type: GET_SINGLE_FAIL,
      payload: error
    })
  }
}

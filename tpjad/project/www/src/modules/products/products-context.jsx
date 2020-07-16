import { generateContext } from '../../helpers'
import {
  GET_PRODUCTS,
  GET_PRODUCTS_FAIL,
  GET_PRODUCTS_SUCCESS,
  GET_SINGLE,
  GET_SINGLE_FAIL,
  GET_SINGLE_SUCCESS,
} from './products-actions'

const initialState = {
  products: [],
  current: {},
  deleted: {},
  loading: false,
  error: undefined
}

const reducer = (state, action) => {
  switch (action.type) {
    case GET_PRODUCTS:
      return {
        ...state,
        loading: true
      }
    case GET_PRODUCTS_SUCCESS:
      return {
        ...state,
        products: action.payload,
        loading: false
      }
    case GET_PRODUCTS_FAIL:
      return {
        ...state,
        loading: false,
        error: action.payload
      }
      case GET_SINGLE:
        return {
          ...state,
          loading: true
        }
      case GET_SINGLE_SUCCESS:
        return {
          ...state,
          current: action.payload,
          loading: false
        }
      case GET_SINGLE_FAIL:
        return {
          ...state,
          loading: false,
          error: action.payload
        }
    default:
      return state
  }
}

const { Context, ContextProvider } = generateContext(initialState, reducer)
export const ProductsContext = Context
export const ProductsContextProvider = ContextProvider

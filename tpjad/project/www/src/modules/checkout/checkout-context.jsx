import { generateContext } from '../../helpers'
import {
  GET_CHECKOUT,
  GET_CHECKOUT_FAIL,
  GET_CHECKOUT_SUCCESS,
  PLACE_ORDER,
  PLACE_ORDER_FAIL,
  PLACE_ORDER_SUCCESS,
} from './checkout-actions'

const initialState = {
  order: {},
  loading: false,
  error: undefined
}

const reducer = (state, action) => {
  switch (action.type) {
    case GET_CHECKOUT:
      return {
        ...state,
        loading: true
      }
    case GET_CHECKOUT_SUCCESS:
      return {
        ...state,
        order: action.payload,
        loading: false
      }
    case GET_CHECKOUT_FAIL:
      return {
        ...state,
        loading: false,
        error: action.payload
      }
      case PLACE_ORDER:
        return {
          ...state,
          loading: true
        }
      case PLACE_ORDER_SUCCESS:
        return {
          ...state,
          order: action.payload,
          loading: false
        }
      case PLACE_ORDER_FAIL:
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
export const CheckoutContext = Context
export const CheckoutContextProvider = ContextProvider

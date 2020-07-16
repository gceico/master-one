import { generateContext } from '../../helpers'
import {
  ADD_ITEM,
  ADD_ITEM_FAIL,
  ADD_ITEM_SUCCESS,
  CLEAN_CART,
  DELETE_ITEM,
  DELETE_ITEM_FAIL,
  DELETE_ITEM_SUCCESS,
  GET_CART,
  GET_CART_FAIL,
  GET_CART_SUCCESS,
  UPDATE_ITEM,
  UPDATE_ITEM_FAIL,
  UPDATE_ITEM_SUCCESS,
} from './cart-actions'

const initialState = {
  cart: [],
  changed: {},
  loading: false,
  error: undefined
}

const reducer = (state, action) => {
  switch (action.type) {
    case GET_CART:
      return {
        ...state,
        loading: true,
      }
    case GET_CART_SUCCESS:
      return {
        ...state,
        loading: false,
        cart: action.payload
      }
    case GET_CART_FAIL:
      return {
        ...state,
        loading: false,
        error: action.payload
      }
      case ADD_ITEM:
      return {
        ...state,
        loading: true,
      }
    case ADD_ITEM_SUCCESS:
      return {
        ...state,
        changed: action.payload,
        loading: false,
      }
    case ADD_ITEM_FAIL:
      return {
        ...state,
        loading: false,
        error: action.payload
      }
    case UPDATE_ITEM:
      return {
        ...state,
        loading: true,
      }
    case UPDATE_ITEM_SUCCESS:
      return {
        ...state,
        changed: action.payload,
        loading: false,
      }
    case UPDATE_ITEM_FAIL:
      return {
        ...state,
        loading: false,
        error: action.payload
      }
    case DELETE_ITEM:
      return {
        ...state,
        loading: true,
      }
    case DELETE_ITEM_SUCCESS:
      return {
        ...state,
        changed: action.payload,
        loading: false,
      }
    case DELETE_ITEM_FAIL:
      return {
        ...state,
        loading: false,
        error: action.payload
      }
    case CLEAN_CART:
      return {
        ...state,
        cart: [],
        changed: undefined
      }
    default:
      return state
  }
}

const { Context, ContextProvider } = generateContext(initialState, reducer)
export const CartContext = Context
export const CartContextProvider = ContextProvider

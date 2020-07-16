import React from 'react'

import { reducerPersister, statePersister } from './_persisters'

export function generateContext(initialState, reducer, storageKey) {
  const Context = React.createContext(initialState)
  const ContextProvider = ({ children }) => {
    const localReducer = storageKey ? reducerPersister(storageKey, reducer) : reducer
    const localState = storageKey
      ? statePersister(storageKey, initialState)
      : initialState
    const [state, dispatch] = React.useReducer(localReducer, localState)
    return <Context.Provider value={{ ...state, dispatch }}>{children}</Context.Provider>
  }
  return { Context, ContextProvider }
}


export const statePersister = (STORAGE_KEY = 'key', initialState = {}) => {
  let state = sessionStorage  .getItem(STORAGE_KEY)
  if (state) {
    state = JSON.parse(state)
  } else {
    state = initialState
  }
  return state
}

export const reducerPersister = (STORAGE_KEY, reducer) => (state, action) => {
  const newState = reducer(state, action)
  sessionStorage  .setItem(STORAGE_KEY, JSON.stringify(newState))
  return newState
}

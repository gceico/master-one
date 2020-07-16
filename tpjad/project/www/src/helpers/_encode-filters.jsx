import _ from 'lodash'

export const encodeFilters = filters => {
  let query = ''
  _.map(filters, filter => {
    _.map(
      filter,
      ({ group, name, checked }) => (query += checked ? `${group}=${name}&` : '')
    )
  })
  return query
}

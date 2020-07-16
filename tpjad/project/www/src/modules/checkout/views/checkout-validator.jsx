import { composeValidators, isRequired } from '../../../helpers'

export default ({ firstName, lastName, address, payment }) => ({
  firstName: composeValidators(isRequired)(firstName),
  lastName: composeValidators(isRequired)(lastName),
  address: composeValidators(isRequired)(address),
  payment: composeValidators(isRequired)(payment)
})

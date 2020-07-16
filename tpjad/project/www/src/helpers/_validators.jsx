import {
  INVALID_EMAIL,
  INVALID_PASSWORD,
  MUST_BE_NUMBER,
  NO_MATCH_PASSWORD,
  REQUIRED_FIELD,
  TOO_LARGE,
  TOO_SMALL,
} from '../config'

export const composeValidators = (...validators) => value =>
  validators.reduce((error, validator) => error || validator(value), undefined)

export const isValidEmail = email =>
  /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(
    email
  )
    ? undefined
    : INVALID_EMAIL

export const isValidPassword = password =>
  password && password.length >= 6 ? undefined : INVALID_PASSWORD

export const isRequired = value => (value ? undefined : REQUIRED_FIELD)

export const mustBeNumber = value => (isNaN(value) ? MUST_BE_NUMBER : undefined)

export const minValue = min => value =>
  isNaN(value) || value >= min ? undefined : `${TOO_SMALL} ${min}`

export const maxValue = max => value =>
  isNaN(value) || value <= max ? undefined : `${TOO_LARGE} ${max}`

export const matchPassword = word => value =>
  value === word ? undefined : NO_MATCH_PASSWORD

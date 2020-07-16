import _ from 'lodash'
import React from 'react'
import Alert from 'react-bootstrap/Alert'
import Form from 'react-bootstrap/Form'

export default function DefaultSelect({
  meta: { touched, error },
  input,
  label,
  options,
  ...rest
}) {
  return (
    <Form.Group>
      <Form.Label>{label}</Form.Label>
      <Form.Control {...input} {...rest} as='select'>
      <option value={undefined}>Select</option>
        {_.map(options, item => (
          <option value={item.value}>{item.name}</option>
        ))}
      </Form.Control>
      {touched && error && <Alert variant={'danger'}>{error}</Alert>}
    </Form.Group>
  )
}
